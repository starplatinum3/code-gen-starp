package top.starp.util.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *      "sort": {
 *         "sorted": false,
 *                 "unsorted": true,
 *                 "empty": true
 *     },
 */
@Data
@ToString
@AllArgsConstructor
//@NoArgsConstructor
public class Sort {
    boolean sorted;
    boolean unsorted;
    boolean empty;

    public Sort() {
        sorted=false;
        unsorted=true;
        empty=false;
    }
}
