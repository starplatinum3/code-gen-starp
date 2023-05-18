package top.starp.util;
 
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
 
public class RuanZhuOut {
 
    public static void main(String[] args) throws IOException {
//        List<File> allFile = getAllFile("D:\\coordination");
        String outPath="D:\\exam-vue-admin3.txt";
        List<File> allFile = getAllFile("D:\\proj\\bishe\\exam-vue-admin3\\src");
        ArrayList<String> strings = new ArrayList<>();
        for (File file :allFile) {
            System.out.println(file);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            //网友推荐更加简洁的写法
            while ((line = bufferedReader.readLine()) != null) {
                // 一次读入一行数据
                if (!line.equals("")){
                    strings.add(line);
                }
            }
        }
//        File writeName = new File("D:\\source.txt");
        File writeName = new File(outPath);
        System.out.println("outPath");
        System.out.println(outPath);
        writeName.createNewFile();
        FileWriter writer = new FileWriter(writeName);
        BufferedWriter out = new BufferedWriter(writer);
        for (String string : strings) {
            out.write(string+"\r\n");
            out.flush();
        }
    }
 
    public static List<File> getAllFile(String dirFilePath) {
        return getAllFile(new File(dirFilePath));
    }
     
    public static  List<File> getAllFile(File dirFile) {
        if (Objects.isNull(dirFile) || !dirFile.exists() || dirFile.isFile())
            return null;
 
        File[] childrenFiles = dirFile.listFiles();
        if (Objects.isNull(childrenFiles) || childrenFiles.length == 0)
            return null;
 
        List<File> files = new ArrayList<>();
        for (File childFile : childrenFiles) {
            if (childFile.isFile()) {
                files.add(childFile);
            }else {
                List<File> cFiles = getAllFile(childFile);
                if (Objects.isNull(cFiles) || cFiles.isEmpty()) continue;
                files.addAll(cFiles);
            }
        }
        return files;
    }
 
}