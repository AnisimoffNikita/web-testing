package com.bmstu.testingsystem.unit_test;

import com.bmstu.testingsystem.controller.EditProfile;
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

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository repositoryMock;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserData userData;
    private SignUp.RegistrationData registrationData;

    @Before
    public void init() {
        user = new User("username", "email", "password");
    }

    @Test
    public void findByUsernameIfExist() {
        Mockito.when(repositoryMock.findByUsername("username")).thenReturn(user);

        User findUser = userService.findByUsername("username");

        Assert.assertEquals(user, findUser);
        Mockito.verify(repositoryMock, Mockito.times(1)).findByUsername(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(repositoryMock);
    }

    @Test(expected = NoUserException.class)
    public void findByUsernameIfNotExist() {
        Mockito.when(repositoryMock.findByUsername("username")).thenReturn(null);

        userService.findByUsername("username");
    }

    @Test
    public void createUserIfNotExist() {
        registrationData = new SignUp.RegistrationData("username", "email", "password");

        Mockito.when(repositoryMock.save(user)).thenReturn(user);
        Mockito.when(repositoryMock.findByUsername("username")).thenReturn(null);

        User newUser = userService.registerUser(registrationData);

        Assert.assertEquals(user, newUser);
        Mockito.verify(repositoryMock, Mockito.times(1)).findByUsername(registrationData.getUsername());
        Mockito.verify(repositoryMock, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void createUserIfExist() {
        registrationData = new SignUp.RegistrationData("username", "email", "password");
        Mockito.when(repositoryMock.findByUsername("username")).thenReturn(user);

        User newUser = userService.registerUser(registrationData);

        Assert.assertNull(newUser);
        Mockito.verify(repositoryMock, Mockito.times(1)).findByUsername(registrationData.getUsername());
        Mockito.verify(repositoryMock, Mockito.times(0)).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void updateUser() {
        userData = new UserData(user);
        userData.setUsername("newUsername");
        userData.setEmail("newEmail");
        userData.setPassword("newPassword");

        Mockito.when(repositoryMock.save(user)).thenReturn(user);
        Mockito.when(repositoryMock.findByUsername("newUsername")).thenReturn(null);

        User res = userService.updateUser(user, userData);

        Assert.assertEquals("newUsername", user.getUsername());
        Assert.assertEquals("newEmail", user.getEmail());
        Assert.assertEquals("newPassword", user.getPassword());
        //Assert.assertTrue(res);

        Mockito.verify(repositoryMock, Mockito.times(1)).findByUsername(userData.getUsername());
        Mockito.verify(repositoryMock, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void updateUserNameFail() {
        User user2 = new User("newUsername", "", "");
        userData = new UserData(user);
        userData.setUsername("newUsername");

        Mockito.when(repositoryMock.findByUsername("newUsername")).thenReturn(user2);

        User oldUser = new User(user.getUsername(), user.getEmail(), user.getPassword());
        User res = userService.updateUser(user, userData);

        Assert.assertEquals(oldUser, res);

        Mockito.verify(repositoryMock, Mockito.times(1)).findByUsername(userData.getUsername());
        Mockito.verify(repositoryMock, Mockito.times(0)).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void updatePerson() {
        userData = new UserData(user);
        userData.setFirstName("newFirstName");
        userData.setLastName("newLastName");
        Date date = new Date();
        userData.setBirthday(date);

        Mockito.when(repositoryMock.save(user)).thenReturn(user);

        userService.updateUser(user, userData);

        Assert.assertEquals("newFirstName", user.getPerson().getFirstName());
        Assert.assertEquals("newLastName", user.getPerson().getLastName());
        Assert.assertEquals(date, user.getPerson().getBirthday());

        Mockito.verify(repositoryMock, Mockito.times(1)).findByUsername(userData.getUsername());
        Mockito.verify(repositoryMock, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(repositoryMock);
    }
}
