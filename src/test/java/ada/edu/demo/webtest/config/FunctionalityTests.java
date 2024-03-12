package ada.edu.demo.webtest.config;

import ada.edu.demo.webtest.entity.Student;
import ada.edu.demo.webtest.repository.StudentRepository;
import ada.edu.demo.webtest.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FunctionalityTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    @DisplayName("Test finding s student by ID")
    public void testStudentSearchById() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(new Student()));
        Student result = studentService.getStudentById(1);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Search by first or last name")
    public void testStudentSearch() {
        Student s1 = new Student(1,"Jamal","Hasanov","a@b.com",new Date(),null,null);
        Student s2 = new Student(2,"Aliya","Mammadova","a@b.com",new Date(),null,null);
        Student s3 = new Student(3,"Kamran","Aliyev","a@b.com",new Date(),null,null);

        List<Student> stList = List.of(s1,s2,s3);

        when(studentRepository.findAll()).thenReturn(stList);
        List<Student> students = studentService.getStudentByEitherName("Jamal","Aliyev");
        System.out.printf("Found students: "+students.size());
        assertEquals(2, students.size() );
    }

    @Test
    @DisplayName("Test saving a student")
    public void testSaveStudent() {
        // Create a sample student
        Student studentToSave = new Student(1, "Manaf", "Aghazada", "maghazada12152@ada.edu.az", new Date(), null, null);

        // Mock the behavior of the student repository to return the saved student
        when(studentRepository.save(studentToSave)).thenReturn(studentToSave);

        // Call the saveStudent method in the service
        Student savedStudent = studentService.saveStudent(studentToSave);

        // Verify that the saved student is not null
        assertNotNull(savedStudent);

        // Verify that the saved student has the correct information
        assertEquals(studentToSave.getStudentId(), savedStudent.getStudentId());
        assertEquals(studentToSave.getFirstName(), savedStudent.getFirstName());
        assertEquals(studentToSave.getLastName(), savedStudent.getLastName());
        assertEquals(studentToSave.getEmail(), savedStudent.getEmail());
    }
}
