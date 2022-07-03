package app.netlify.ferry.example.dynamic_query.projection;

import app.netlify.ferry.example.dynamic_query.persistence.StudentMajor;
import lombok.Value;

import java.time.LocalDate;

/************************
 * Made by [MR Ferry™]  *
 * on July 2022         *
 ************************/

@Value
public class StudentNameAndBirthDateProjection{
	String studentName;
	LocalDate birthDate;
}
