package com.example.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
/**
 * @description oled
 * @author zhengkai.blog.csdn.net
 * @date 2021-12-26
 */
@Entity
@Data
@Table(name="oled")
@Builder
@AllArgsConstructor
public class Oled implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    * payload
    */
    @Column(name="payload")
    private String payload;

    /**
    * create_time
    */
    @Column(name="create_time")
    private Date createTime;

    public Oled() {
    }

}