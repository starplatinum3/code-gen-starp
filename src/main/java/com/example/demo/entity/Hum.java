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
/**
 * @description hum
 * @author mqp
 * @date 2021-12-19
 */
@Entity
@Data
@Table(name="hum")
@ApiModel("hum")
@Builder
@AllArgsConstructor
public class Hum implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @ApiModelProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    * temp
    */
    @ApiModelProperty("temp")
    @Column(name="temp")
    private Double temp;

    /**
    * hum
    */
    @ApiModelProperty("hum")
    @Column(name="hum")
    private Double hum;

    /**
    * create_time
    */
    @ApiModelProperty("create_time")
    @Column(name="create_time")
    private Date createTime;

    public Hum() {
    }

}