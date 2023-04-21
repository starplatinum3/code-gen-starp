package com.example.demo.util.codeGen;

import com.example.demo.util.FileUtil;
import com.example.demo.util.StringUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

public class IviewToElmUi {
    static Map<String, String> tagMap = new HashMap<String, String>();
    //    static List<String >components=new ArrayList<>();
    static List<String> components = Arrays.asList("Button", "Upload", "Button", "Row", "Card",
            "Table","Progress","Alert","Col","Icon");

    static {
//        tagMap.put("Button", "el-button");
        for (String component : components) {
            tagMap.put(component, iViewComponentToElmUi(component));
        }
    }

   static String iViewComponentToElmUi(String tagName){
        String s = StringUtils.lowerCaseFirst(tagName);
        return "el-"+s;
    }
    static String  changeLine(String line){


//            line.re
//                    iViewComponentToElmUi(line);
//            tagMap.forEach((k,v)->{
//                line= line.replace(k,v);
//            });
//            遍历 map java
//            for (Map.Entry<String ,String >d:tagMap) {
//                line= line.replace(k,v);
//            }

        for (Map.Entry<String, String> entry : tagMap.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
//                System.out.println(mapKey + "：" + mapValue);
            line= line.replace(mapKey,mapValue);
        }
        System.out.println(line);
        return line;
    }

    static void iViewToElmUiCode(String iViewFilePath,String  outFilePath){
//        File file = new File("D:\\private\\party-school-vue-handout\\src\\views\\import\\DataImportManage.vue");
        File file = new File(iViewFilePath);
//        try {
//            Scanner scanner=new Scanner(file);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

       boolean  doChange=true;
        StringBuilder out= new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.contains("</template>")){
//                    break;
                    doChange=false;
                }
                if(doChange){
                    line= changeLine(line);
                }

                out.append(line).append("\n");
            }

            FileUtil.writeCode(Paths.get(outFilePath), out.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        iViewToElmUiCode("D:\\private\\party-school-vue-handout\\src\\views\\import\\DataImportManage.vue",
               "D:\\elmui.vue" );
//        File file = new File("D:\\private\\party-school-vue-handout\\src\\views\\import\\DataImportManage.vue");
////        try {
////            Scanner scanner=new Scanner(file);
////        }catch (Exception e){
////            e.printStackTrace();
////        }
//
//        String out="";
//        try (Scanner scanner = new Scanner(file)) {
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                line= changeLine(line);
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
