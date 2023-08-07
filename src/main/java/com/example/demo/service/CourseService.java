package com.example.demo.service;

import com.example.demo.entity.neo4j.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class CourseService {

    @Resource
    private Neo4jClient neo4jClient;

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course getCourseById(Long id) {
//        courseRepository.fi
        return courseRepository.findById(id).orElse(null);
    }
}
