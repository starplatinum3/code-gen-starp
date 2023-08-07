package com.example.demo.entity.neo4j;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

@Node("Course")
@Data
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @Property("courseName")
    private String courseName;

    // Getters and setters
}
