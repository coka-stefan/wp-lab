package mk.ukim.finki.wp.students.service;

import mk.ukim.finki.wp.students.exceptions.InvalidIndexException;
import mk.ukim.finki.wp.students.exceptions.MissingArgumentException;
import mk.ukim.finki.wp.students.exceptions.StudentNotFoundException;
import mk.ukim.finki.wp.students.exceptions.StudyProgramNotFoundException;
import mk.ukim.finki.wp.students.model.Student;
import mk.ukim.finki.wp.students.repository.StudentsRepository;
import mk.ukim.finki.wp.students.repository.StudyProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    StudentsRepository studentsRepository;
    StudyProgramRepository studyProgramRepository;

    public StudentService(StudentsRepository studentsRepository, StudyProgramRepository studyProgramRepository) {
        this.studentsRepository = studentsRepository;
        this.studyProgramRepository = studyProgramRepository;
    }

    public Student findOne(String index) throws StudentNotFoundException {
        Student student = studentsRepository.findOne(index);
        if (student == null) {
            throw new StudentNotFoundException(student);
        }
        return student;
    }

    public Iterable<Student> findAll() {
        return studentsRepository.findAll();
    }

    public List<Student> findAllByStudyProgram(Long id) {
        System.out.println(id);
        return studentsRepository.findAllByStudyProgramId(id);
    }

    public Boolean deleteStudent(String index) throws StudentNotFoundException {
        if (studentsRepository.findOne(index) == null) {
            throw new StudentNotFoundException(index);
        }
        studentsRepository.delete(index);
        return Boolean.TRUE;
    }

    public boolean updateStudent(String index, String name, String lastName, String studyProgramName) throws StudentNotFoundException, StudyProgramNotFoundException {
        Student student = studentsRepository.findOne(index);
        if (student == null) {
            throw new StudentNotFoundException(index);
        }

        if (studyProgramName != null && studyProgramRepository.findByName(studyProgramName) == null) {
            throw new StudyProgramNotFoundException(studyProgramName);
        }

        if (name != null) {
            student.setName(name);
        }
        if (lastName != null) {
            student.setLastName(lastName);
        }

        return studentsRepository.save(student) != null;
    }

    public void addStudent(String name, String lastName, String index, String studyProgramName) throws MissingArgumentException, InvalidIndexException {

        if (name == null || lastName == null || index == null || studyProgramName == null) {
            throw new MissingArgumentException();
        }


        if (index.length() != 6) {
            throw new InvalidIndexException(index);
        }

        Student student = new Student();
        student.setLastName(lastName);
        student.setName(name);
        student.setIndex(index);
        student.setStudyProgram(studyProgramRepository.findByName(studyProgramName));

        studentsRepository.save(student);
    }
}
