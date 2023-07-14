package top.starp.util;

//import com.example.demo.util.JsonUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {


    public  static    void writeJsonObjToFile( String outDir,String inputFilePath,Object chMap)
            throws IOException {

        String timeUnderScore = TimeUtil.nowTimeStr();

        String docName = FileUtil.getWinFileName(inputFilePath);
        Path path1 = Paths.get(outDir, docName + "_" + timeUnderScore + ".json");
        String outPath = path1.toString();
        System.out.println("outPath");
        System.out.println(outPath);
        String outJsonStr = JsonUtil.toJsonString(chMap);
        FileUtil.writeToFileMust(outPath, outJsonStr);
    }


    public static String getWinDocName(   String path){

        String[] split = path.split("\\\\");
        String docName = split[split.length - 1].replace(".doc", "");
        return docName;
    }

    public static String getWinFileName(   String path){

        String[] split = path.split("\\\\");
        String docName = split[split.length - 1];

        if(docName.endsWith(":")){
            docName= docName.substring(0,docName.length()-1);
        }
        return docName;
    }

    public static  String readAll(String pathName) throws FileNotFoundException {
//        File file = new File( "D:\\proj\\job\\res_eng_chinese_map_underscore_lower.json");
        File file = new File( pathName);

        Scanner scanner = new Scanner(file);
        StringBuilder res= new StringBuilder();
        while (scanner.hasNextLine()) {
            res.append(scanner.nextLine()).append("\n");
        }
        return res.toString();
    }
   public static void writeToFile(String filePath,String data) throws IOException {

        log.info("filePath  "+filePath);
//        log.info(filePath);
                FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(data);
        fileWriter.close();
    }
    public  static  void writeToFileMust( String filePath,String  code) throws IOException {

        log.info("filePath  "+filePath);

        Path javaFileNameAbs = Paths.get(filePath);
        Path parent = javaFileNameAbs.getParent();
        File parentFile = parent.toFile();
        if (!parentFile.exists()) {
            boolean mkdirs = parentFile.mkdirs();
        }

        try(FileWriter fileWriter=new FileWriter(javaFileNameAbs.toFile())){
            fileWriter.write(code);
        }
    }


    public static void main(String[] args) {
        String docDir = "G:\\";
//        G:\工程学院
        String dirName = FileUtil.getWinFileName(docDir);
        System.out.println("dirName");
        System.out.println(dirName);
    }
    public static void main_visitAllDirsAndFilesGetFilePathListOfDoc(String[] args) throws IOException {

        String  dirPath="E:\\原来的";
        File file = new File(dirPath);
        List<String> list = visitAllDirsAndFilesGetFilePathListOfDoc(file);
        StringUtils.printList(list);
        String res="";
        String outDir="D:\\brain\\docFiles";
        for (String s : list) {
            res+=s+"\n";
        }
        String outPath = Paths.get(outDir, "fileListDoc.txt").toString();
        FileUtil.writeToFile(outPath,res);

    }

    public static void visitAllDirsAndFiles(File dir, List<String>absPathList) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                File file = new File(dir, child);
                String absPath = file.toString();
                absPathList.add(absPath);
                visitAllDirsAndFiles(file,absPathList);
            }
        }
    }

    public static void visitAllDirsAndFilesOfDoc(File dir, List<String>absPathList) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                if(!child.endsWith(".doc")){
                    continue;
                }
                File file = new File(dir, child);
                String absPath = file.toString();
                absPathList.add(absPath);
                visitAllDirsAndFiles(file,absPathList);
            }
        }
    }

    public static void visitAllDirsParseDoc(File dir, List<String>absPathList) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                if(!child.endsWith(".doc")){
                    continue;
                }
                File file = new File(dir, child);
                String absPath = file.toString();
                absPathList.add(absPath);
                visitAllDirsParseDoc(file,absPathList);
            }
        }
    }
    public static List<String> visitAllDirsAndFilesGetFilePathList(File dir) {
        List<String>absPathList=new ArrayList<>();
        visitAllDirsAndFiles(dir,absPathList);
        return  absPathList;
    }
    public static List<String> visitAllDirsAndFilesGetFilePathListOfDoc(File dir) {
        List<String>absPathList=new ArrayList<>();
        visitAllDirsAndFilesOfDoc(dir,absPathList);
        return  absPathList;
    }


}
