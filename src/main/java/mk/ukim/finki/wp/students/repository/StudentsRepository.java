package mk.ukim.finki.wp.students.repository;

import mk.ukim.finki.wp.students.model.Student;
import mk.ukim.finki.wp.students.model.StudyProgram;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsRepository extends CrudRepository<Student, String> {
    List<Student> findAllByStudyProgramId(Long id);
}
