package mk.ukim.finki.wp.students.controller;

import mk.ukim.finki.wp.students.exceptions.InvalidIndexException;
import mk.ukim.finki.wp.students.exceptions.MissingArgumentException;
import mk.ukim.finki.wp.students.exceptions.StudentNotFoundException;
import mk.ukim.finki.wp.students.exceptions.StudyProgramNotFoundException;
import mk.ukim.finki.wp.students.model.Student;
import mk.ukim.finki.wp.students.service.StudentService;
import mk.ukim.finki.wp.students.service.StudyProgramService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
public class StudentsController {
    StudentService studentService;
    StudyProgramService studyProgramService;

    public StudentsController(StudentService studentService, StudyProgramService studyProgramService) {
        this.studentService = studentService;
        this.studyProgramService = studyProgramService;
    }

    @GetMapping("students")
    public ResponseEntity<Iterable<Student>> index() {
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("students/{index}")
    public ResponseEntity<Student> get(@PathVariable String index) {
        try {
            return new ResponseEntity<>(studentService.findOne(index), HttpStatus.OK);
        } catch (StudentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/students/by_study_program/{id}")
    public ResponseEntity<List<Student>> getByStudyProgram(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.findAllByStudyProgram(id), HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity add(@RequestParam String name,
                              @RequestParam String lastName,
                              @RequestParam String index,
                              @RequestParam String studyProgramName,
                              UriComponentsBuilder b) throws URISyntaxException {
        try {
            studentService.addStudent(name, lastName, index, studyProgramName);
        } catch (InvalidIndexException | MissingArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        URI location = new URI(b.path("students/" + index).toUriString());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);


//        return ResponseEntity.created(uriComponents.toUri()).build();
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @PatchMapping("/students/{index}")
    public ResponseEntity patch(@PathVariable String index,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String lastName,
                                @RequestParam(required = false) String studyProgramName) {
        try {
            studentService.updateStudent(index, name, lastName, studyProgramName);
            return new ResponseEntity(HttpStatus.OK);
        } catch (StudentNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (StudyProgramNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/students/{index}")
    public ResponseEntity delete(@PathVariable String index) {
        try {
            studentService.deleteStudent(index);
        } catch (StudentNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
