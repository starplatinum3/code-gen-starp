package #packageName#.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Map;
import javax.persistence.*;
import java.io.Serializable;
import com.ruoyi.common.core.text.Convert;
/**
 * @description #tableName#
 * @author starp
 * @date #date#
 */
@Entity
@Builder
@AllArgsConstructor
@Data
@Table(name="#tableName#")
public class #类名# implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    #jpaEntityFields#


    public #类名#() {
    }

            public  void fromMap(Map<String ,Object>map){
                    #fromMapRows#
          }

}
