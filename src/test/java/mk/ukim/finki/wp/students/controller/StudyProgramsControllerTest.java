package mk.ukim.finki.wp.students.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import mk.ukim.finki.wp.students.model.StudyProgram;
import mk.ukim.finki.wp.students.repository.StudyProgramRepository;
import net.minidev.json.JSONObject;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudyProgramsControllerTest {

    @Autowired
    StudyProgramRepository studyProgramRepository;

    @LocalServerPort
    private int port;

    static StudyProgram sStudyProgram;

    @Before
    public void setUp() {
        RestAssured.port = port;

        studyProgramRepository.deleteAll();
        studyProgramRepository.save(new StudyProgram("kni"));

        sStudyProgram = studyProgramRepository.findByName("kni");
    }


    @Test
    public void index() {
        given().accept(ContentType.JSON)
                .when()
                .log().all()
                .get("/study_programs")
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(is(notNullValue()));
    }

    @Test
    public void add() {
        given().accept(ContentType.JSON)
                .when()
                .formParam("name", "kni")
                .log().all()
                .post("/study_programs")
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void delete() {
        given().accept(ContentType.JSON)
                .when()
                .log().all()
                .delete("/study_programs/" + studyProgramRepository.findByName("kni").getId())
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }
}