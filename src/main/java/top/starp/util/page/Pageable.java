package top.starp.util.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
      "sort": {
        "sorted": false,
                "unsorted": true,
                "empty": true
    },
            "offset": 0,
            "pageSize": 1,
            "pageNumber": 0,
            "unpaged": false,
            "paged": true

 */
@Data
@ToString
@AllArgsConstructor
//@NoArgsConstructor
public class Pageable {
    Sort sort;
    int offset;
    int pageSize;
    int pageNumber;
    boolean unpaged;
    boolean paged;

    public Pageable() {
        sort = new Sort();
        offset = 0;
        unpaged = false;
        paged = true;

    }

    public Pageable(int pageNumber,int pageSize ) {
        this();
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }
}


