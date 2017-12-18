package mk.ukim.finki.wp.students.exceptions;

import mk.ukim.finki.wp.students.model.Student;

public class MissingArgumentException extends Throwable {
    public MissingArgumentException(Student student) {
    }

    public MissingArgumentException() {

    }
}
