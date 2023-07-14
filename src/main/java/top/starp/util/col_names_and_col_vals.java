package top.starp.util;

//import lombok.Data;

import java.util.List;

//@Data
public class col_names_and_col_vals {
    public List<String> getCol_vals() {
        return col_vals;
    }

    public void setCol_vals(List<String> col_vals) {
        this.col_vals = col_vals;
    }

    public List<String> getCol_names() {
        return col_names;
    }

    public void setCol_names(List<String> col_names) {
        this.col_names = col_names;
    }

    //    List<String> col_vals=new ArrayList<>();
//    List<String> col_names=new ArrayList<>();
    List<String> col_vals;
    List<String> col_names;
}
