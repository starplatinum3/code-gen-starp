package com.example.demo.entity.neo4j;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.*;

@Node
public class Person {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int age;

    // Constructors, getters, setters, etc. (omitted for brevity)
}
