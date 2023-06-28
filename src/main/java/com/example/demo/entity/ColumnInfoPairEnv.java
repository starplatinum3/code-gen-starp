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
 * @description column_info_pair_env
 * @author starp
 * @date 2023-06-18 21:54:52
 */
@Entity
@Builder
@AllArgsConstructor
@Data
@ApiModel("column_info_pair_env")
//@Table(name="column_info_pair_env")
//@TableName("column_info_pair_env")
@Table(name="column_info_pair_env")
//@TableName("column_info_pair_env")
public class ColumnInfoPairEnv implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("id")
//    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     /**
     * column_info_id
     */
    @ApiModelProperty("column_info_id")
    @Column(name="column_info_id")
    private Integer columnInfoId;
 /**
     * env_id
     */
    @ApiModelProperty("env_id")
    @Column(name="env_id")
    private Integer envId;


public  static  class Names{
    public  static String  id="id";
         public  static String  column_info_id="column_info_id"; 
     public  static String  env_id="env_id"; 
//     public  static String  id="id";

}


    public ColumnInfoPairEnv() {
    }

    public  void fromMap(Map<String ,Object>map){
                      columnInfoId =  Convert.toInt(map.get("column_info_id")); 
          envId =  Convert.toInt(map.get("env_id")); 
          id =  Convert.toInt(map.get("id")); 

    }

//public ColumnInfoPairEnvEs toEsEntity(){
//        ColumnInfoPairEnvEs #entityName#Es = new ColumnInfoPairEnvEs();
//           columnInfoPairEnvEs.setColumnInfoId(columnInfoId);
//   columnInfoPairEnvEs.setEnvId(envId);
//   columnInfoPairEnvEs.setId(id);
//
//
//        return #entityName#Es;
//        }

    public static List<ColumnInfoPairEnv> fromMaps(List<Map<String, Object>> maps) {
            List<ColumnInfoPairEnv> list = new ArrayList<>();
            for (Map<String, Object> map : maps) {
                ColumnInfoPairEnv obj = new ColumnInfoPairEnv();
                obj.fromMap(map);
                list.add(obj);
            }
            return list;
        }
}
