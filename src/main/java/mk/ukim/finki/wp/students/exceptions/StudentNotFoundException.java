package mk.ukim.finki.wp.students.exceptions;

import mk.ukim.finki.wp.students.model.Student;

public class StudentNotFoundException extends Throwable {
    public StudentNotFoundException(Student student) {

    }

    public StudentNotFoundException(String index) {

    }
}
