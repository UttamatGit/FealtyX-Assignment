package org.fealtyx.sms_ai.services;

import org.fealtyx.sms_ai.exceptions.StudentNotFoundException;
import org.fealtyx.sms_ai.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentService {
    private final Map<Integer, Student> students = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public Student getStudentById(int id) {
        Student student = students.get(id);
        if (student == null) {
            throw new StudentNotFoundException("Student with ID " + id + " not found");
        }
        return student;
    }

    public Student createStudent(Student student) {
        int id = idCounter.getAndIncrement();
        student.setId(id);
        students.put(id, student);
        return student;
    }

    public Student updateStudent(int id, Student student) {
        if (!students.containsKey(id)) {
            throw new StudentNotFoundException("Student with ID " + id + " not found");
        }
        student.setId(id);
        students.put(id, student);
        return student;
    }

    public void deleteStudent(int id) {
        if (!students.containsKey(id)) {
            throw new StudentNotFoundException("Student with ID " + id + " not found");
        }
        students.remove(id);
    }
}