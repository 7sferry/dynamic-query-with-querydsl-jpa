package app.netlify.ferry.example.dynamic_query.repository;

import app.netlify.ferry.example.dynamic_query.persistence.Student;
import app.netlify.ferry.example.dynamic_query.filter.StudentFilter;

import java.util.List;

/************************
 * Made by [MR Ferry™]  *
 * on July 2022         *
 ************************/



public interface StudentDslRepository{
	List<Student> findSpecificStudents();
	List<Student> findStudents();


	List<Student> findByFilter(StudentFilter filter);

	<C> List<C> findByAdvancedFilterProjections(StudentFilter filter, Class<C> clazz);

	<C> List<C> findWithFilterProjections(StudentFilter filter, Class<C> clazz);
}
