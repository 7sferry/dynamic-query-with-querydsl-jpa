package app.netlify.ferry.example.dynamic_query;

import app.netlify.ferry.example.dynamic_query.filter.StudentFilter;
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
	private final EntityManager entityManager;

	public static void main(String[] args){
		SpringApplication.run(DynamicQueryApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args){
//		List<Student> studentList = studentDslRepository.findSpecificStudents();
//		for(Student student : studentList){
//			System.out.println("student.getStudentName() = " + student.getStudentName());
//		}

		StudentFilter studentFilter = StudentFilter.builder()
				.active(true)
				.studentName("s")
				.nik("1313727230300101")
				.birthDateRangeEnd(LocalDate.now())
				.birthDateRangeStart(LocalDate.of(2019, Month.JANUARY, 1))
				.npm("1133080")
				.batchYear(2013)
				.build();
		List<StudentNameAndBatchNameProjection> projections = studentRepository.findWithFilterProjections(studentFilter, StudentNameAndBatchNameProjection.class);
		System.out.println("projections.size() = " + projections.size());
		for(StudentNameAndBatchNameProjection projection : projections){
			System.out.println("projection.getBatchName() = " + projection.getBatchName());
			System.out.println("projection.getStudentName() = " + projection.getStudentName());
		}
//		List<Student> studentList = studentDslRepository.findByFilter(studentFilter);
//		for(Student student : studentList){
//			System.out.println("student.getStudentName() = " + student.getStudentName());
//			System.out.println("student.getStudentBatch() = " + student.getStudentBatch());
//		}
//		StudentSpecification spec = new StudentSpecification(studentFilter);
//		List<Student> students = studentRepository.findAll(spec);
//		for(Student student : students){
//			System.out.println("student.getStudentName() = " + student.getStudentName());
//			System.out.println("student.getStudentBatch() = " + student.getStudentBatch());
//		}

//		List<Student> manualQuery = studentRepository.findWithNativeQuery(studentFilter);
//		System.out.println("manualQuery.size() = " + manualQuery.size());

//		List<Student> withManualQuery = studentRepository.findAll();
//		List<Student> withManualQuery = studentRepository.findWithManualQuery(studentFilter);
//		System.out.println("withManualQuery.size() = " + withManualQuery.size());
//		StudentBatch studentBatch1 = withManualQuery.get(0).getStudentBatch();
//		System.out.println("studentBatch1 = " + studentBatch1.getBatchName());

//		List<Student> withManualQueryJoinBatch = studentRepository.findWithManualQueryJoinBatch(studentFilter);
//		System.out.println("withManualQueryJoinBatch.size() = " + withManualQueryJoinBatch.size());
//		StudentBatch studentBatch = withManualQueryJoinBatch.get(0).getStudentBatch();
//		System.out.println("studentBatch = " + studentBatch.getBatchName());

		//jpql standard
//		TypedQuery<Student> query = entityManager.createQuery("select s from Student s", Student.class);
//		List<Student> list = query.getResultList();
//		for(Student student : list){
//			System.out.println("student.getStudentName() = " + student.getStudentName());
//		}

		//hibernate query standard
//		Session session = entityManager.unwrap(Session.class);
//		Query<Student> hibernateQuery = session.createQuery("select s from Student s", Student.class);
//		List<Student> resultList = hibernateQuery.getResultList();
//		for(Student student : resultList){
//			System.out.println("student.getStudentName() = " + student.getStudentName());
//		}
//
//		List<Student> all = studentRepository.findAll();
//		for(Student student : all){
//			System.out.println("student.getStudentName() = " + student.getStudentName());
//		}
//		System.out.println("all.size() = " + all.size());
//		List<StudentDefaultProjection> byActive = studentRepository.findByActive(true, StudentDefaultProjection.class);
//		for(StudentDefaultProjection studentDefaultProjection : byActive){
//			System.out.println("studentDefaultProjection.getStudentName() = " + studentDefaultProjection.getStudentName());
//		}
	}

}
