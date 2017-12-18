package mk.ukim.finki.wp.students.service

import mk.ukim.finki.wp.students.exceptions.InvalidIndexException
import mk.ukim.finki.wp.students.model.Student
import mk.ukim.finki.wp.students.model.StudyProgram
import mk.ukim.finki.wp.students.repository.StudentsRepository
import mk.ukim.finki.wp.students.repository.StudyProgramRepository

class StudentServiceTest extends spock.lang.Specification {
    StudentService service
    StudentsRepository studentsRepository
    StudyProgramRepository studyProgramRepository

    void setup() {
        studentsRepository = Mock(StudentsRepository)
        studyProgramRepository = Mock(StudyProgramRepository)
        service = new StudentService(studentsRepository, studyProgramRepository)
    }

    def "AddStudent"() {
        given:
        StudyProgram studyProgram = new StudyProgram()
        studyProgram.name = "kni"
        studyProgramRepository.save(studyProgram)
        studyProgram = studyProgramRepository.findByName(studyProgram.name)

        expect:
        def student = new Student()

        when:
        student.index = "123456"
        student.lastName = "petrushevski"
        student.name = "stefan"

        service.addStudent(student.name, student.lastName, student.index,studyProgram.name)

        then:
        1 * studentsRepository.save(student)
    }

    def "FailAddStudent"() {
        expect:
        def student = new Student()

        when:
        student.index = "12345"
        student.lastName = "petrushevski"
        student.name = "stefan"

        service.addStudent(student.name, student.lastName, student.index, "kni")

        then:
        thrown InvalidIndexException
    }
}
