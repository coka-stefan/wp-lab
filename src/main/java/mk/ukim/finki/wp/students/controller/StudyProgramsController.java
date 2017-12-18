package mk.ukim.finki.wp.students.controller;

import mk.ukim.finki.wp.students.model.StudyProgram;
import mk.ukim.finki.wp.students.service.StudyProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudyProgramsController {

    StudyProgramService studyProgramService;

    public StudyProgramsController(StudyProgramService studyProgramService) {
        this.studyProgramService = studyProgramService;
    }

    @GetMapping("/study_programs")
    public ResponseEntity<Iterable<StudyProgram>> index() {
        return ResponseEntity.ok(studyProgramService.getAll());
    }

    @PostMapping("/study_programs")
    public ResponseEntity add(@RequestParam String name) {
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.setName(name);
        studyProgramService.add(studyProgram);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/study_programs/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        studyProgramService.delete(Long.valueOf(id));
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
