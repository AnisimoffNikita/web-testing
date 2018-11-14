package com.bmstu.testingsystem.unit_test;

import com.bmstu.testingsystem.repositiry.ExamResultRepository;
import com.bmstu.testingsystem.services.ExamResultServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

// todo написать тесты, когда будут точно определены структуры UserAnswer и Question

@RunWith(MockitoJUnitRunner.class)
public class TestResultServiceImplTest {

    @Mock
    private ExamResultRepository repositoryMock;

    @InjectMocks
    private ExamResultServiceImpl testResultService;


    @Before
    public void init() {
    }

    @Test
    public void passTestIfAllRight() {

    }

    @Test
    public void passTestIfAllFail() {
    }

    @Test
    public void passTestIfSmthRight() {
    }
}
