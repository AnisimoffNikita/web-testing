package com.bmstu.testingsystem.unit_test;

import com.bmstu.testingsystem.domain.User;
import com.bmstu.testingsystem.repositiry.TestRepository;
import com.bmstu.testingsystem.services.TestServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

// todo как-то подсунуть тестам фейковую бд

public class TestServiceImplTest {

    private User user;

    @Before
    public void init() {
        user = new User("username", "email", "password");
    }

    @Test
    public void findByKeywordIfAllRight() {

    }

    @Test
    public void findByKeywordIfAllFail() {
    }

    @Test
    public void findByKeywordIfSmthRight() {
    }
}
