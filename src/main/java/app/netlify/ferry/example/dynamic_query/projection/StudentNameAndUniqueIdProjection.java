package app.netlify.ferry.example.dynamic_query.projection;

import lombok.Value;

import java.time.LocalDate;

/************************
 * Made by [MR Ferry™]  *
 * on July 2022         *
 ************************/

@Value
public class StudentNameAndUniqueIdProjection{
	String studentName;
	String nik;
	String npm;
}
