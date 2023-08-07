package top.starp.util;

//import com.example.demo.util.JsonUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUtil {

//    public  static   File[] listFiles(  String folderPath){
////        String folderPath = "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\api\\openai\\resp";
//
////        String  folderPath="";
//        File folder = new File(folderPath);
//
//        if (folder.exists() && folder.isDirectory()) {
//            File[] files = folder.listFiles();
//
//            if (files != null) {
//                return  files;
////                for (File file : files) {
////                    if (file.isFile()) {
////                        // 处理文件
////                        System.out.println("文件：" + file.getName());
////                    } else if (file.isDirectory()) {
////                        // 处理文件夹
////                        System.out.println("文件夹：" + file.getName());
////                    }
////                }
//            } else {
//                System.out.println("文件夹为空 "+folderPath);
//            }
//        } else {
//            System.out.println("文件夹路径不存在或不是一个文件夹 "+folderPath);
//        }
//        return null;
//    }


    public  static   List<File> listFiles(  String folderPath){
//        String folderPath = "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\api\\openai\\resp";

//        String  folderPath="";
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
//                return u.list(files);
                List<File> fileList = new ArrayList<>(Arrays.asList(files));
                return  fileList;
//                for (File file : files) {
//                    if (file.isFile()) {
//                        // 处理文件
//                        System.out.println("文件：" + file.getName());
//                    } else if (file.isDirectory()) {
//                        // 处理文件夹
//                        System.out.println("文件夹：" + file.getName());
//                    }
//                }
            } else {
                System.out.println("文件夹为空 "+folderPath);
            }
        } else {
            System.out.println("文件夹路径不存在或不是一个文件夹 "+folderPath);
        }
        return new ArrayList<>();
    }

    public static void createParentDirectory(String filePath) {

        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            log.info("parentDir");
            log.info(String.valueOf(parentDir));
            if (created) {
                System.out.println("父路径创建成功！");
            } else {
                System.out.println("父路径创建失败！");
            }
        }
    }
    public static void writeJsonObjToFile(String outDir, String inputFilePath, Object chMap)
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


    public static String getWinDocName(String path) {

        String[] split = path.split("\\\\");
        String docName = split[split.length - 1].replace(".doc", "");
        return docName;
    }

    public static String getWinFileName(String path) {

        String[] split = path.split("\\\\");
        String docName = split[split.length - 1];

        if (docName.endsWith(":")) {
            docName = docName.substring(0, docName.length() - 1);
        }
        return docName;
    }

    public static String readAll(String pathName) throws FileNotFoundException {
//        File file = new File( "D:\\proj\\job\\res_eng_chinese_map_underscore_lower.json");
        File file = new File(pathName);
       return readAll(file);

//        Scanner scanner = new Scanner(file);
//        StringBuilder res = new StringBuilder();
//        while (scanner.hasNextLine()) {
//            res.append(scanner.nextLine()).append("\n");
//        }
//        return res.toString();
    }

    public static String readAll( File file ) throws FileNotFoundException {
//        File file = new File(pathName);
        Scanner scanner = new Scanner(file);
        StringBuilder res = new StringBuilder();
        while (scanner.hasNextLine()) {
            res.append(scanner.nextLine()).append("\n");
        }
        return res.toString();
    }

    public static String    getFileName(  String path){
//        File file = new File("/path/to/file.txt");
        File file = new File(path);
        String fileName = file.getName();
//        File parentFile = file.getParentFile();
        return fileName;
    }

    public static String addTimeToFilePath(String originalFilePath) {
        // Get the current date and time
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String formattedDate = sdf.format(currentDate);

        // Extract the file extension from the original file path (if any)
        int dotIndex = originalFilePath.lastIndexOf('.');
        String fileExtension = "";
        if (dotIndex != -1) {
            fileExtension = originalFilePath.substring(dotIndex);
        }

        // Create the new file path with time

        return originalFilePath.substring(0, dotIndex) + "_" + formattedDate + fileExtension;
    }

    public static void writeToFileWithTimeStr(String filePath, String data) throws IOException {
        String filePathAddTime = addTimeToFilePath(filePath);
        writeToFile(filePathAddTime,data);
//        String nowTimeStr = TimeUtil.nowTimeStr();
//
//        String fileName = FileUtil.getFileName(filePath);
//        fileName
//       /file/model.txt 给这个路径加上时间 /file/model_2022_10_02_08_09_09.txt  java 代码
//        log.info("filePath  " + filePath);
//        try (
//                FileWriter fileWriter = new FileWriter(filePath)
//        ) {
//            fileWriter.write(data);
//        }



//        log.info(filePath);
//                FileWriter fileWriter = new FileWriter(filePath);
//        fileWriter.write(data);
//        fileWriter.close();
    }
    public static void writeToFile(String filePath, String data) throws IOException {

        log.info("filePath  " + filePath);
        try (
                FileWriter fileWriter = new FileWriter(filePath)
        ) {
            fileWriter.write(data);
        }
//        log.info(filePath);
//                FileWriter fileWriter = new FileWriter(filePath);
//        fileWriter.write(data);
//        fileWriter.close();
    }

   public  static  String  makeCodeGenDir( String outCodeDir){
        String nowTimeStr = TimeUtil.nowTimeStr();
//        String outCodeDir="/file/codeGen";
        String outCodePathBase="{outCodeDir}/codeGen_{nowTimeStr}"
                .replace("{nowTimeStr}",nowTimeStr)
                .replace("{outCodeDir}",outCodeDir)
                ;
        return outCodePathBase;
    }

    public  static  String  makeCodeGenDir( ){
       return makeCodeGenDir("/file/codeGen");
    }
    public static void writeToFileMust(String filePath, String code) throws IOException {

        log.info("filePath  " + filePath);

        Path javaFileNameAbs = Paths.get(filePath);
        Path parent = javaFileNameAbs.getParent();
        File parentFile = parent.toFile();
        if (!parentFile.exists()) {
            boolean mkdirs = parentFile.mkdirs();
        }

        try (FileWriter fileWriter = new FileWriter(javaFileNameAbs.toFile())) {
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

        String dirPath = "E:\\原来的";
        File file = new File(dirPath);
        List<String> list = visitAllDirsAndFilesGetFilePathListOfDoc(file);
        StringUtils.printList(list);
        StringBuilder res = new StringBuilder();
        String outDir = "D:\\brain\\docFiles";
        for (String s : list) {
            res.append(s).append("\n");
        }
        String outPath = Paths.get(outDir, "fileListDoc.txt").toString();
        FileUtil.writeToFile(outPath, res.toString());

    }

    public static void visitAllDirsAndFiles(File dir, List<String> absPathList) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                File file = new File(dir, child);
                String absPath = file.toString();
                absPathList.add(absPath);
                visitAllDirsAndFiles(file, absPathList);
            }
        }
    }

    public static void visitAllDirsAndFilesOfDoc(File dir, List<String> absPathList) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                if (!child.endsWith(".doc")) {
                    continue;
                }
                File file = new File(dir, child);
                String absPath = file.toString();
                absPathList.add(absPath);
                visitAllDirsAndFiles(file, absPathList);
            }
        }
    }

    public static void visitAllDirsParseDoc(File dir, List<String> absPathList) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                if (!child.endsWith(".doc")) {
                    continue;
                }
                File file = new File(dir, child);
                String absPath = file.toString();
                absPathList.add(absPath);
                visitAllDirsParseDoc(file, absPathList);
            }
        }
    }

    public static List<String> visitAllDirsAndFilesGetFilePathList(File dir) {
        List<String> absPathList = new ArrayList<>();
        visitAllDirsAndFiles(dir, absPathList);
        return absPathList;
    }

    public static List<String> visitAllDirsAndFilesGetFilePathListOfDoc(File dir) {
        List<String> absPathList = new ArrayList<>();
        visitAllDirsAndFilesOfDoc(dir, absPathList);
        return absPathList;
    }


}
