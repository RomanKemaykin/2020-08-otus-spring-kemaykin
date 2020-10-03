package ru.otus.springBootstudentTesting.dao;

import org.springframework.stereotype.Component;
import ru.otus.springBootstudentTesting.domain.StudentTest;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentTestDaoSimpleList implements StudentTestDao {
    private List<StudentTest> studentTests;

    public StudentTestDaoSimpleList() {
        this.studentTests = new ArrayList<StudentTest>();
    }

    @Override
    public void save(StudentTest studentTest) {
        studentTests.add(studentTest);
    }

    @Override
    public void removeAll() {
        studentTests.clear();
    }

    @Override
    public List<StudentTest> findALL() {
        List<StudentTest> studentTestsFound = new ArrayList<StudentTest>();
        for (int i = 0; i < studentTests.size(); i++) {
            studentTestsFound.add(studentTests.get(i));
        }
        return studentTestsFound;
    }
}
