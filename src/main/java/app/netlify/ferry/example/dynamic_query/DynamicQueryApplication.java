package app.netlify.ferry.example.dynamic_query;

import app.netlify.ferry.example.dynamic_query.filter.StudentFilter;
import app.netlify.ferry.example.dynamic_query.persistence.Student;
import app.netlify.ferry.example.dynamic_query.projection.StudentNameAndBatchNameProjection;
import app.netlify.ferry.example.dynamic_query.repository.StudentSpringJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class DynamicQueryApplication implements CommandLineRunner{
	private final StudentSpringJpaRepository studentRepository;

	public static void main(String[] args){
		SpringApplication.run(DynamicQueryApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args){
		//find specific students
		List<Student> studentList = studentRepository.findSpecificStudents();
		for(Student student : studentList){
			System.out.println("student.getStudentName() = " + student.getStudentName());
		}

		StudentFilter studentFilter = StudentFilter.builder()
				.active(true)
				.studentName("s")
				.nik("1313727230300101")
				.birthDateRangeEnd(LocalDate.now())
				.birthDateRangeStart(LocalDate.of(2019, Month.JANUARY, 1))
				.npm("1133080")
				.batchYear(2013)
				.build();

		//filter with standard filter
		List<Student> students = studentRepository.findByFilter(studentFilter);
		for(Student projection : students){
			System.out.println("projection.getStudentName() = " + projection.getStudentName());
		}

		//filter with projections filter
		List<StudentNameAndBatchNameProjection> projectionList = studentRepository.findWithFilterProjections(studentFilter,
				StudentNameAndBatchNameProjection.class);
		for(StudentNameAndBatchNameProjection projection : projectionList){
			System.out.println("projection.getBatchName() = " + projection.getBatchName());
			System.out.println("projection.getStudentName() = " + projection.getStudentName());
		}

		//filter with advanced filter
		List<StudentNameAndBatchNameProjection> projections = studentRepository.findByAdvancedFilterProjections(studentFilter,
				StudentNameAndBatchNameProjection.class);
		for(StudentNameAndBatchNameProjection projection : projections){
			System.out.println("projection.getBatchName() = " + projection.getBatchName());
			System.out.println("projection.getStudentName() = " + projection.getStudentName());
		}

	}

}
