package com.example.demo.dto;

import java.io.Serializable;



public class AskTrainVo implements Serializable {

    Boolean stream;

    public Boolean getStream() {
        return stream;
    }

    public Boolean getStreamOrDefault() {
        if(stream==null){
            return false;
        }
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
//        JackSon
        return "AskTrainVo{" +
                "id=" + id +
                ", askText='" + askText + '\'' +
                ", category='" + category + '\'' +
                ", idMax=" + idMax +
                ", idMin=" + idMin +
                ", categoryFrom='" + categoryFrom + '\'' +
                ", categoryTo='" + categoryTo + '\'' +
                '}';
    }

    private static final long serialVersionUID = 1L;


//    @TableId(type = IdType.AUTO)
    private Integer id;


//    @TableField("ask_text")
    private String askText;
//    @TableField("category")
    private String category;
//    @TableField(exist = false)
    private Integer idMax;

//    @TableField(exist = false)
    private Integer idMin;
//    @TableField(exist = false)
    private String categoryFrom;
//    @TableField(exist = false)
    private String categoryTo;
// @TableField("id")
//private Integer id;

    public static class Names {
        public static String id = "id";
        public static String ask_text = "ask_text";
        public static String category = "category";
//     public  static String  id="id";

    }


    public AskTrainVo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAskText() {
        return askText;
    }

    public void setAskText(String askText) {
        this.askText = askText;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getIdMax() {
        return idMax;
    }

    public void setIdMax(Integer idMax) {
        this.idMax = idMax;
    }

    public Integer getIdMin() {
        return idMin;
    }

    public void setIdMin(Integer idMin) {
        this.idMin = idMin;
    }

    public String getCategoryFrom() {
        return categoryFrom;
    }

    public void setCategoryFrom(String categoryFrom) {
        this.categoryFrom = categoryFrom;
    }

    public String getCategoryTo() {
        return categoryTo;
    }

    public void setCategoryTo(String categoryTo) {
        this.categoryTo = categoryTo;
    }
//public AskTrainEs toEsEntity(){
//        AskTrainEs #entityName#Es = new AskTrainEs();
//           askTrainEs.setAskText(askText);
//   askTrainEs.setCategory(category);
//   askTrainEs.setId(id);
//
//
//        return #entityName#Es;
//        }

//
//    public void fromMap(Map<String, Object> map) {
//        askText = Convert.toStr(map.get("ask_text"));
//        category = Convert.toStr(map.get("category"));
//        id = Convert.toInt(map.get("id"));
//
//    }
//
//    public static List<AskTrain> fromMaps(List<Map<String, Object>> maps) {
//        List<AskTrain> list = new ArrayList<>();
//        for (Map<String, Object> map : maps) {
//            AskTrain obj = new AskTrain();
//            obj.fromMap(map);
//            list.add(obj);
//        }
//        return list;
//    }
}
