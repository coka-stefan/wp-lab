package mk.ukim.finki.wp.students.service;

import mk.ukim.finki.wp.students.model.StudyProgram;
import mk.ukim.finki.wp.students.repository.StudyProgramRepository;
import org.springframework.stereotype.Service;

@Service
public class StudyProgramService {
    StudyProgramRepository studyProgramRepository;

    public StudyProgramService(StudyProgramRepository studyProgramRepository) {
        this.studyProgramRepository = studyProgramRepository;
    }

    public Iterable<StudyProgram> getAll() {
        return studyProgramRepository.findAll();
    }

    public void add(StudyProgram studyProgram) {
        studyProgramRepository.save(studyProgram);
    }

    public void delete(Long id) {
        studyProgramRepository.delete(id);
    }

    public StudyProgram find(String name) {
        return studyProgramRepository.findByName(name);
    }
}
