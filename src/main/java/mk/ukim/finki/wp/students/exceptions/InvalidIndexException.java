package mk.ukim.finki.wp.students.exceptions;

import mk.ukim.finki.wp.students.model.Student;

public class InvalidIndexException extends Throwable {
    public InvalidIndexException(Student student) {
    }

    public InvalidIndexException(String index) {

    }
}
