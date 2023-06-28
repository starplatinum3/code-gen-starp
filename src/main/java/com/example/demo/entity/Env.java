package com.example.demo.entity;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Map;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import    com.starp.exam.util.Convert;
import    top.starp.util.Convert;



/**
 * @description env
 * @author starp
 * @date 2023-06-18 21:54:52
 */
@Entity
@Builder
@AllArgsConstructor
@Data
@ApiModel("env")
//@Table(name="env")
//@TableName("env")
@Table(name="env")
//@TableName("env")
public class Env implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("id")
//    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     /**
     * env_name
     */
    @ApiModelProperty("env_name")
    @Column(name="env_name")
    private String envName;


public  static  class Names{
    public  static String  id="id";
         public  static String  env_name="env_name"; 
//     public  static String  id="id";

}


    public Env() {
    }

    public  void fromMap(Map<String ,Object>map){
                      envName =  Convert.toStr(map.get("env_name")); 
          id =  Convert.toInt(map.get("id")); 

    }

//public EnvEs toEsEntity(){
//        EnvEs #entityName#Es = new EnvEs();
//           envEs.setEnvName(envName);
//   envEs.setId(id);
//
//
//        return #entityName#Es;
//        }

    public static List<Env> fromMaps(List<Map<String, Object>> maps) {
            List<Env> list = new ArrayList<>();
            for (Map<String, Object> map : maps) {
                Env obj = new Env();
                obj.fromMap(map);
                list.add(obj);
            }
            return list;
        }
}
