//package com.example.demo.entity;
//
//
//import com.baomidou.mybatisplus.annotation.TableField;
//
//import java.io.Serializable;
//
//
//
//public class AskTrainMongoDO implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Override
//    public String toString() {
//        return "AskTrainMongoDO{" +
//                "askText='" + askText + '\'' +
//                ", category='" + category + '\'' +
//                ", idMax=" + idMax +
//                ", idMin=" + idMin +
//                ", categoryFrom='" + categoryFrom + '\'' +
//                ", categoryTo='" + categoryTo + '\'' +
//                '}';
//    }
//
//
//    private Object id;
//
//    public Object getId() {
//        return id;
//    }
//
//    public void setId(Object id) {
//        this.id = id;
//    }
//
//
//    @TableField("ask_text")
//    private String askText;
//    private String ask_text;
//
//    public String getAsk_text() {
//        return ask_text;
//    }
//
//    public void setAsk_text(String ask_text) {
//        this.ask_text = ask_text;
//    }
//
//    @TableField("category")
//    private String category;
//    @TableField(exist = false)
//    private Integer idMax;
//
//    @TableField(exist = false)
//    private Integer idMin;
//    @TableField(exist = false)
//    private String categoryFrom;
//    @TableField(exist = false)
//    private String categoryTo;
//
//
//
//
//    public AskTrainMongoDO() {
//    }
//
//    public String getAskText() {
//        return ask_text;
//    }
//
//    public void setAskText(String askText) {
//        this.ask_text = askText;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public Integer getIdMax() {
//        return idMax;
//    }
//
//    public void setIdMax(Integer idMax) {
//        this.idMax = idMax;
//    }
//
//    public Integer getIdMin() {
//        return idMin;
//    }
//
//    public void setIdMin(Integer idMin) {
//        this.idMin = idMin;
//    }
//
//    public String getCategoryFrom() {
//        return categoryFrom;
//    }
//
//    public void setCategoryFrom(String categoryFrom) {
//        this.categoryFrom = categoryFrom;
//    }
//
//    public String getCategoryTo() {
//        return categoryTo;
//    }
//
//    public void setCategoryTo(String categoryTo) {
//        this.categoryTo = categoryTo;
//    }
//
//}
