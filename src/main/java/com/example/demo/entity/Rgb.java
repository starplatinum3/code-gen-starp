package com.example.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
/**
 * @description rgb
 * @author zhengkai.blog.csdn.net
 * @date 2021-12-26
 */
@Entity
@Data
@Table(name="rgb")
@Builder
@AllArgsConstructor
public class Rgb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    * r
    */
    @Column(name="r")
    private Integer r ;

    /**
    * g
    */
    @Column(name="g")
    private Integer g;

    /**
    * b
    */
    @Column(name="b")
    private Integer b;

    /**
    * create_time
    */
    @Column(name="create_time")
    private Date createTime;

    public Rgb() {
    }

}