package com.bmstu.testingsystem.integration_tests;

import com.bmstu.testingsystem.TestingSystemApplication;
import com.bmstu.testingsystem.domain.*;
import com.bmstu.testingsystem.repositiry.ExamRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(classes = TestingSystemApplication.class)
@Transactional
public class ExamRepositoryTest {

    @Autowired
    ExamRepository examRepository;

    private User user;

    @Before
    public void init() {
        user = new User("admin", "admin", "admin");
        user.setId(UUID.fromString("12412cdb-398f-4def-9cec-325b11968b56"));
    }

    @Test
    public void save() {
        Exam exam = new Exam(user, "some name", "some description",
                new java.sql.Date(1195333200000L), new ArrayList<>());
        Exam toDBExam = examRepository.save(exam);

        UUID id = toDBExam.getId();

        Optional<Exam> fromDBExam = examRepository.findById(id);

        assertEquals(Optional.of(toDBExam), fromDBExam);
    }

    @Test
    public void findById() {
        Exam exam = new Exam(user, "тест главный", "большое описание со словом математика",
                new java.sql.Date(1195333200000L), new ArrayList<>());

        Optional<Exam> fromDBexam = examRepository.findById(UUID.fromString("0596c2c0-a70a-47dd-81c8-31411a5b132a"));

        assertEquals(exam, fromDBexam.get());
    }

    @Test
    public void update() {
        UUID id = UUID.fromString("66bcd4a3-a3d5-409e-9a38-e0d7b029a020");
        Optional<Exam> fromDBExam = examRepository.findById(id);
        Exam exam = fromDBExam.get();
        exam.setStatus(ExamStatus.REJECTED);

        examRepository.save(exam);
        Optional<Exam> updatedDBExam = examRepository.findById(id);

        assertEquals(Optional.of(exam), updatedDBExam);
    }

    @Test
    public void delete() {
        UUID id = UUID.fromString("66bcd4a3-a3d5-409e-9a38-e0d7b029a020");
        examRepository.deleteById(id);

        Optional<Exam> emptyExam = Optional.empty();

        Optional<Exam> fromDBExam = examRepository.findById(id);

        assertEquals(emptyExam, fromDBExam);
    }

    @Test
    // все результаты для этого теста
    public void findAllResultsForExam() {
        UUID examId = UUID.fromString("0596c2c0-a70a-47dd-81c8-31411a5b132a");
        UUID userId = UUID.fromString("12412cdb-398f-4def-9cec-325b11968b56");
        ExamResult result = new ExamResult("11", new java.sql.Date(1195333200000L), examId, userId);
        Exam exam = new Exam(user, "тест главный", "большое описание со словом математика",
                new java.sql.Date(1195333200000L), new ArrayList<>());

        Optional<Exam> fromDbExam = examRepository.findById(examId);
        Exam getExam = fromDbExam.get();

        assertEquals(exam, getExam);
        assertArrayEquals(getExam.getResults().toArray(), Arrays.asList(result).toArray());
    }

    @Test
    public void markDeleted() {
        Exam exam = new Exam(user, "тест главный", "большое описание со словом математика",
                new java.sql.Date(1195333200000L), new ArrayList<>());
        exam.setStatus(ExamStatus.DELETED);
        UUID id = UUID.fromString("0596c2c0-a70a-47dd-81c8-31411a5b132a");
        examRepository.markDeleted(id);

        Optional<Exam> fromDBExam = examRepository.findById(id);

        assertEquals(Optional.of(exam), fromDBExam);
    }

}
