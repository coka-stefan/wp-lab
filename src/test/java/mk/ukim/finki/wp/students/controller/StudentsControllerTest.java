package mk.ukim.finki.wp.students.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import mk.ukim.finki.wp.students.model.Student;
import mk.ukim.finki.wp.students.repository.StudentsRepository;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentsControllerTest {

    static Student sStudent;
    @Autowired
    StudentsRepository repository;
    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;

        repository.deleteAll();
        sStudent = new Student();
        sStudent.setName("stefan");
        sStudent.setLastName("petrushevski");
        sStudent.setIndex("123456");

        System.out.println(sStudent.getIndex());
        repository.save(sStudent);

        Student student = new Student();
        student.setName("petko");
        student.setLastName("petkovski");
        student.setIndex("654321");

        repository.save(student);
    }

    @Test
    public void add() {
        given().accept(ContentType.JSON)
                .when()
                .formParam("name", "stefan")
                .formParam("lastName", "petrushevski")
                .formParam("index", "666666")
                .formParam("studyProgramName", "kni")
                .log().all()
                .post("/students")
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void index() {
        given().accept(ContentType.JSON)
                .when()
                .log().all()
                .get("/students")
                .then()
                .log().all()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body(is(not(isEmptyOrNullString())));
    }

    @Test
    public void get() {
        given().accept(ContentType.JSON)
                .when()
                .log().all()
                .get("/students/123456")
                .then()
                .log().all()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body(containsString("stefan"));
    }

    @Test
    public void getByStudyProgram() {
        given().accept(ContentType.JSON)
                .when()
                .log().all()
                .get("/students/by_study_program/1")
                .then()
                .log().all()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void patch() {
        given().accept(ContentType.JSON)
                .when()
                .formParam("name", "stefan")
                .formParam("lastName", "petrushevski1")
                .log().all()
                .patch("/students/" + sStudent.getIndex())
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void delete() {
        Student student = new Student();
        student.setName("stefan");
        student.setName("petrushevski");
        student.setIndex("123456");

        System.out.println(student.getIndex());
        repository.save(student);

        given().accept(ContentType.JSON)
                .when()
                .log().all()
                .delete("/students/123456")
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
}