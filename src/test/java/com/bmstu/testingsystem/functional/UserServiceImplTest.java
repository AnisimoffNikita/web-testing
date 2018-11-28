package com.bmstu.testingsystem.functional;

import com.bmstu.testingsystem.TestingSystemApplication;
import com.bmstu.testingsystem.controller.SignUp;
import com.bmstu.testingsystem.domain.User;
import com.bmstu.testingsystem.exception.NoUserException;
import com.bmstu.testingsystem.form_data.UserData;
import com.bmstu.testingsystem.repositiry.UserRepository;
import com.bmstu.testingsystem.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(classes = TestingSystemApplication.class)
@Transactional
public class UserServiceImplTest {


    @Autowired
    private UserServiceImpl userService;

    private User user1;
    private User user2;
    private UserData userData;
    private SignUp.RegistrationData registrationData;

    @Before
    public void init() {
        user1 = new User("admin", "admin", "admin");
        user1.setId(UUID.fromString("12412cdb-398f-4def-9cec-325b11968b56"));
        user2 = new User("admin4", "admin4", "admin4");
    }

    @Test
    public void findByUsernameIfExist() {
        User findUser = userService.findByUsername("admin");

        Assert.assertEquals(user1, findUser);
    }

    @Test(expected = NoUserException.class)
    public void findByUsernameIfNotExist() {
        userService.findByUsername("2admin");
    }

    @Test
    public void createUserIfNotExist() {
        registrationData = new SignUp.RegistrationData("admin4", "admin4", "admin4");

        User newUser = userService.registerUser(registrationData);

        Assert.assertEquals(user2, newUser);
    }

    @Test
    public void createUserIfExist() {
        registrationData = new SignUp.RegistrationData("admin", "admin", "admin");

        User newUser = userService.registerUser(registrationData);

        Assert.assertNull(newUser);
    }

    @Test
    public void updateUser() {
        userData = new UserData(user1);
        userData.setUsername("newUsername");
        userData.setEmail("newEmail");
        userData.setPassword("newPassword");

        User res = userService.updateUser(user1, userData);

        Assert.assertEquals("newUsername", res.getUsername());
        Assert.assertEquals("newEmail", res.getEmail());
        Assert.assertEquals("newPassword", res.getPassword());
    }

    @Test
    public void updateUserNameFail() {
        userData = new UserData(user1);
        userData.setUsername("admin4");


        User res = userService.updateUser(user1, userData);

        Assert.assertEquals(res, user1);

    }

    @Test
    public void updatePerson() {
        userData = new UserData(user1);
        userData.setFirstName("newFirstName");
        userData.setLastName("newLastName");
        Date date = new Date();
        userData.setBirthday(date);

        User res = userService.updateUser(user1, userData);

        Assert.assertEquals("newFirstName", res.getPerson().getFirstName());
        Assert.assertEquals("newLastName", res.getPerson().getLastName());
        Assert.assertEquals(date, res.getPerson().getBirthday());
    }
}
