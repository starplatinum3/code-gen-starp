//package com.example.demo.config;
//
//import org.neo4j.ogm.config.Configuration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
//import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
//import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
//
//@Configuration
//@EnableConfigurationProperties
//public class Neo4jConfig {
//
//    @Bean("db1Config")
//    @ConditionalOnProperty(prefix = "neo4j", name = "active-db", havingValue = "db1")
//    @ConfigurationProperties(prefix = "neo4j.db1")
//    public Neo4jProperties db1Properties() {
//        return new Neo4jProperties();
//    }
//
//    @Bean("db2Config")
//    @ConditionalOnProperty(prefix = "neo4j", name = "active-db", havingValue = "db2")
//    @ConfigurationProperties(prefix = "neo4j.db2")
//    public Neo4jProperties db2Properties() {
//        return new Neo4jProperties();
//    }
//
//    @Bean("db1")
//    public org.neo4j.ogm.config.Configuration configurationDb1(@Autowired Neo4jProperties db1Config) {
//        Configuration neo4jConfig = new Configuration.Builder()
//                .uri(db1Config.getUrl())
//                .credentials(db1Config.getUsername(), db1Config.getPassword())
//                .build();
//        return neo4jConfig;
//    }
//
//    @Bean("db2")
//    public org.neo4j.ogm.config.Configuration configurationDb2(@Autowired Neo4jProperties db2Config) {
//        Configuration neo4jConfig = new Configuration.Builder()
//                .uri(db2Config.getUrl())
//                .credentials(db2Config.getUsername(), db2Config.getPassword())
//                .build();
//        return neo4jConfig;
//    }
//
//    @Bean
//    public org.neo4j.ogm.session.SessionFactory sessionFactoryDb1(@Autowired("db1") org.neo4j.ogm.config.Configuration configurationDb1,
//                                                                   Neo4jMappingContext mappingContext) {
//        return new org.neo4j.ogm.session.SessionFactory(configurationDb1, "com.example.db1.model");
//    }
//
//    @Bean
//    public org.neo4j.ogm.session.SessionFactory sessionFactoryDb2(@Autowired("db2") org.neo4j.ogm.config.Configuration configurationDb2,
//                                                                   Neo4jMappingContext mappingContext) {
//        return new org.neo4j.ogm.session.SessionFactory(configurationDb2, "com.example.db2.model");
//    }
//
//    @Bean
//    public Neo4jTransactionManager transactionManagerDb1(@Autowired("db1") org.neo4j.ogm.config.Configuration configurationDb1,
//                                                         Neo4jMappingContext mappingContext) {
//        return new Neo4jTransactionManager(sessionFactoryDb1(configurationDb1, mappingContext));
//    }
//
//    @Bean
//    public Neo4jTransactionManager transactionManagerDb2(@Autowired("db2") org.neo4j.ogm.config.Configuration configurationDb2,
//                                                         Neo4jMappingContext mappingContext) {
//        return new Neo4jTransactionManager(sessionFactoryDb2(configurationDb2, mappingContext));
//    }
//}
