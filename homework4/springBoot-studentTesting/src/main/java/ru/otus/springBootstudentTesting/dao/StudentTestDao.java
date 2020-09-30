package ru.otus.springBootstudentTesting.dao;

import ru.otus.springBootstudentTesting.domain.StudentTest;

import java.util.List;

public interface StudentTestDao {
    public void save(StudentTest studentTest);
    public List<StudentTest> findALL();
    public void removeAll();
}
