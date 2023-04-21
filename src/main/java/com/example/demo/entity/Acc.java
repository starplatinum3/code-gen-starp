package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

/**
 * @description acc
 * @author mqp
 * @date 2021-12-20
 */
@Entity
@Data
@Table(name="acc")
@ApiModel("acc")
@Builder
@AllArgsConstructor
public class Acc implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue

    @Id
    @ApiModelProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    * x
    */
    @ApiModelProperty("x")
    @Column(name="x")
    private Integer x;

    /**
    * y
    */
    @ApiModelProperty("y")
    @Column(name="y")
    private Integer y;

    /**
    * z
    */
    @ApiModelProperty("z")
    @Column(name="z")
    private Integer z;


    @ApiModelProperty("create_time")
    @Column(name="create_time")
   Date createTime;
    public Acc() {
    }

}