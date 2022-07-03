/************************
 * Made by [MR Ferry™]  *
 * on July 2022         *
 ************************/

package app.netlify.ferry.example.dynamic_query.repository;

import app.netlify.ferry.example.dynamic_query.persistence.Student;
import app.netlify.ferry.example.dynamic_query.filter.StudentFilter;
import app.netlify.ferry.example.dynamic_query.projection.StudentNameAndBatchNameProjection;
import app.netlify.ferry.example.dynamic_query.projection.StudentNameAndBirthDateProjection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static app.netlify.ferry.example.dynamic_query.persistence.QStudent.student;
import static app.netlify.ferry.example.dynamic_query.persistence.QStudentBatch.studentBatch;

@RequiredArgsConstructor
public class StudentDslRepositoryImpl implements StudentDslRepository{

	private final EntityManager entityManager;

	private final Map<Class<?>, Expression<?>> selectionByProjection = Map.ofEntries(
			getConstructorEntry(StudentNameAndBirthDateProjection.class, student.studentName, student.birthDate),
			getConstructorEntry(StudentNameAndBatchNameProjection.class, student.studentName, studentBatch.batchName)
	);

	private final Map<Class<?>, Set<EntityPathBase<?>>> entityPathByProjection = Map.of(
			StudentNameAndBatchNameProjection.class, Set.of(studentBatch)
	);

	@Override
	public <C> List<C> findByAdvancedFilterProjections(StudentFilter filter, Class<C> clazz){
		JPQLQuery<C> query = new JPAQuery<>(entityManager);
		query.select(selectionByProjection.get(clazz))
				.from(student);
		Set<EntityPathBase<?>> joinPaths = entityPathByProjection.getOrDefault(clazz, Set.of());
		if(filter.getBatchYear() != null || joinPaths.contains(studentBatch)){
			query.innerJoin(studentBatch)
					.on(studentBatch.id.eq(student.studentBatchId));
		}
		query.where(constructWhereClause(filter));
		return query.fetch();
	}

	private Map.Entry<Class<?>, Expression<?>> getConstructorEntry(Class<?> clazz, Expression<?>... expressions){
		return Map.entry(clazz, Projections.constructor(clazz, expressions));
	}

	@Override
	public <C> List<C> findWithFilterProjections(StudentFilter filter, Class<C> clazz){
		JPQLQuery<C> query = new JPAQuery<>(entityManager);
		query.select(selectionByProjection.get(clazz))
				.from(student);
		if(filter.getBatchYear() != null){
			query.innerJoin(studentBatch)
					.on(studentBatch.id.eq(student.studentBatchId));
		}
		query.where(constructWhereClause(filter));
		return query.fetch();
	}

	@Override
	public List<Student> findByFilter(StudentFilter filter){
		JPQLQueryFactory jpqlQueryFactory = new JPAQueryFactory(entityManager);
		JPQLQuery<Student> query = jpqlQueryFactory.selectFrom(student);
		if(filter.getBatchYear() != null){
			query.innerJoin(studentBatch)
					.on(studentBatch.id.eq(student.studentBatchId));
		}
		query.where(constructWhereClause(filter));
		return query.fetch();
	}

	private Predicate constructWhereClause(StudentFilter filter){
		BooleanBuilder builder = new BooleanBuilder();
		if(filter.getActive() != null){
			builder.and(student.active.eq(filter.getActive()));
		}
		if(filter.getNpm() != null){
			builder.and(student.npm.eq(filter.getNpm()));
		}
		if(filter.getStudentName() != null){
			builder.and(student.studentName.lower().contains(filter.getStudentName().toLowerCase()));
		}
		if(filter.getNik() != null){
			builder.and(student.nik.eq(filter.getNik()));
		}
		if(filter.getBirthDateRangeStart() != null && filter.getBirthDateRangeEnd() != null){
			builder.and(student.birthDate.between(filter.getBirthDateRangeStart(), filter.getBirthDateRangeEnd()));
		}
		if(filter.getBatchYear() != null){
			builder.and(studentBatch.batchYear.eq(filter.getBatchYear()));
		}
		return builder.getValue();
	}

	@Override
	public List<Student> findSpecificStudents(){
		JPQLQueryFactory jpqlQueryFactory = new JPAQueryFactory(entityManager);
		return jpqlQueryFactory.selectFrom(student)
				.where(student.active.isTrue()
						.and(student.studentName.contains("ferry"))
						.and(student.id.between(1, 100)))
				.fetch();
	}

	@Override
	public List<Student> findStudents(){
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
		return jpaQueryFactory.selectFrom(student).fetch();
	}

}
