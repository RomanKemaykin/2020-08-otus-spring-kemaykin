package ru.otus.springBootstudentTesting.service;

import ru.otus.springBootstudentTesting.domain.StudentTest;

public interface TestingService {
    public StudentTest testStudent();
    public void runStudentTesting();
}
