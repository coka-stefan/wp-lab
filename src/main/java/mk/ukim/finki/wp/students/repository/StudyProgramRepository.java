package mk.ukim.finki.wp.students.repository;

import mk.ukim.finki.wp.students.model.StudyProgram;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyProgramRepository extends CrudRepository<StudyProgram, Long> {
    StudyProgram findByName(String name);
}
