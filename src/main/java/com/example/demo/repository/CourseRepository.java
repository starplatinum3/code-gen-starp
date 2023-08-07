package com.example.demo.repository;

import com.example.demo.entity.neo4j.Course;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface CourseRepository extends Neo4jRepository<Course, Long> {

    List<Course>findByCourseName(String courseName);
}
