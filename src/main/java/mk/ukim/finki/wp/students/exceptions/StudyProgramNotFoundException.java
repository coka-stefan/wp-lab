package mk.ukim.finki.wp.students.exceptions;

import mk.ukim.finki.wp.students.model.StudyProgram;

public class StudyProgramNotFoundException extends Throwable {
    public StudyProgramNotFoundException(StudyProgram studyProgram) {
    }

    public StudyProgramNotFoundException(String studyProgramName) {

    }
}
