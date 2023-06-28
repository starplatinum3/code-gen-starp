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
 * @description column_info
 * @author starp
 * @date 2023-06-18 21:43:44
 */
@Entity
@Builder
@AllArgsConstructor
@Data
@ApiModel("column_info")
//@Table(name="column_info")
//@TableName("column_info")
@Table(name="column_info")
//@TableName("column_info")
public class ColumnInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("id")
//    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


//    Convert
     /**
     * column_name
     */
    @ApiModelProperty("column_name")
    @Column(name="column_name")
    private String columnName;
 /**
     * comment
     */
    @ApiModelProperty("comment")
    @Column(name="comment")
    private String comment;
 /**
     * column_type
     */
    @ApiModelProperty("column_type")
    @Column(name="column_type")
    private String columnType;


public  static  class Names{
    public  static String  id="id";
         public  static String  column_name="column_name"; 
     public  static String  comment="comment"; 
     public  static String  column_type="column_type"; 
//     public  static String  id="id";

}


    public ColumnInfo() {
    }

    public  void fromMap(Map<String ,Object>map){
                      columnName =  Convert.toStr(map.get("column_name")); 
          comment =  Convert.toStr(map.get("comment")); 
          columnType =  Convert.toStr(map.get("column_type")); 
          id =  Convert.toInt(map.get("id")); 

    }

//public ColumnInfoEs toEsEntity(){
//        ColumnInfoEs #entityName#Es = new ColumnInfoEs();
//           columnInfoEs.setColumnName(columnName);
//   columnInfoEs.setComment(comment);
//   columnInfoEs.setColumnType(columnType);
//   columnInfoEs.setId(id);
//
//
//        return #entityName#Es;
//        }

    public static List<ColumnInfo> fromMaps(List<Map<String, Object>> maps) {
            List<ColumnInfo> list = new ArrayList<>();
            for (Map<String, Object> map : maps) {
                ColumnInfo obj = new ColumnInfo();
                obj.fromMap(map);
                list.add(obj);
            }
            return list;
        }
}
