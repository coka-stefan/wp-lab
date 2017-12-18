package mk.ukim.finki.wp.students.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StudyProgram {
    @Id
    @GeneratedValue
    Long id;
    String name;

    public StudyProgram() {
    }

    public StudyProgram(String name) {

        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
