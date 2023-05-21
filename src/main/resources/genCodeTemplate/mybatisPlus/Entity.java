package #包名#.entity;

import java.math.BigDecimal;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import    #包名#.util.Convert;
import    top.starp.util.Convert;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;


/**
 * @description #tableName#
 * @author starp
 * @date #date#
 */
@Builder
@AllArgsConstructor
@Data
//@ApiModel("#tableName#")
//@Table(name="#tableName#")
//@TableName("#tableName#")
@TableName("#tablePreffix##tableName#")
public class #类名# implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.AUTO)
    private Integer id;

        {myBatisPlusEntityFields}

public  static  class Names{
    public  static String  id="id";
    {NamesRows}
}


    public #类名#() {
    }

    public  void fromMap(Map<String ,Object>map){
            #fromMapRows#
    }

public #类名#Es toEsEntity(){
        #类名#Es #entityName#Es = new #类名#Es();
        {toEsEntityRows}

        return #entityName#Es;
        }

    public static List<#类名#> fromMaps(List<Map<String, Object>> maps) {
            List<#类名#> list = new ArrayList<>();
            for (Map<String, Object> map : maps) {
                #类名# obj = new #类名#();
                obj.fromMap(map);
                list.add(obj);
            }
            return list;
        }
}
