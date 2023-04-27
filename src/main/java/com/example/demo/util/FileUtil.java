package com.example.demo.util;

//import cn.hutool.core.convert.Convert;

//import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class FileUtil {

    public static  InputStream  getResourceInputStream(String relativePath) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(relativePath);
        InputStream inputStream =classPathResource.getInputStream();
        return inputStream;
    }

    public static  String  readResourceFileData(String relativePath) throws Exception {
        InputStream resourceInputStream = getResourceInputStream(relativePath);
        String s = readStreamToString(resourceInputStream);
        return  s;

    }


    /**
     * 读取流
     *
     * @param inStream
     * @return 字节数组
     * @throws Exception
     */
    // utf-8
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }
    public static String readStreamToString(InputStream inStream) throws Exception {
        byte[] bytes = readStream(inStream);
        String string = new String(bytes,"UTF-8");
        return string;

    }


    public static void saveFile(String file, String data, boolean append) throws IOException {
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;

        File f = new File(file);
        FileOutputStream fos = new FileOutputStream(f, append);
        try {
            // write UTF8 BOM mark if file is empty
            if (f.length() < 1) {
                final byte[] bom = new byte[] { (byte)0xEF, (byte)0xBB, (byte)0xBF };
                fos.write(bom);
            }

            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw);
            if (data != null) bw.write(data);
        } catch (IOException ex) {
            throw ex;
        } finally {
            try { bw.close(); fos.close(); } catch (Exception ex) { }
        }
    }
//————————————————
//    版权声明：本文为CSDN博主「huyishero」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/huyishero/article/details/77833191
//
    public  static  void writeCodeBom( java.nio.file.Path  javaFileNameAbs,String  code) throws IOException {
        String javaFileNameAbsStr = javaFileNameAbs.toString();

        FileUtil.renameIf存在(javaFileNameAbs.toString());
//        System.out.println("写文件 "+javaFileNameAbsStr);


        Path parent = javaFileNameAbs.getParent();
        File parentFile = parent.toFile();
        if (!parentFile.exists()) {
            boolean mkdirs = parentFile.mkdirs();
        }
//        parent.toFile().exists()
//        FileWriter ansi
//        try(FileWriter fileWriter=new FileWriter(javaFileNameAbsStr)){
//            fileWriter.write(code);
//        }
        saveFile(javaFileNameAbsStr,code,false);
    }

    public  static  void writeCode( java.nio.file.Path  javaFileNameAbs,String  code) throws IOException {
        String javaFileNameAbsStr = javaFileNameAbs.toString();

        FileUtil.renameIf存在(javaFileNameAbs.toString());
//        System.out.println("写文件 "+javaFileNameAbsStr);


        Path parent = javaFileNameAbs.getParent();
        File parentFile = parent.toFile();
        if (!parentFile.exists()) {
            boolean mkdirs = parentFile.mkdirs();
        }
//        parent.toFile().exists()
//        FileWriter ansi
// FileWriter utf-8 = new FileWriter(javaFileNameAbsStr, Charset.forName("UTF-8"));
    //     try(FileWriter fileWriter=new FileWriter(javaFileNameAbsStr,
    //    Charset.forName("UTF-8"))){
    //         fileWriter.write(code);
    //     }

        try(BufferedWriter fileWriter = new BufferedWriter (new OutputStreamWriter
         (new FileOutputStream (javaFileNameAbsStr,true),"UTF-8"));
        ){
             fileWriter.write(code);
         }
//        saveFile(javaFileNameAbsStr,code,false);
    }

//    rename If exists

    public static void renameIfExists(String filePath) throws IOException {
        File file = new File(filePath);
        boolean exists = file.exists();
        if(exists){

            String notExistFileName = FileUtil.generateNotExistFileName(filePath);
            System.out.println("notExistFileName");
            System.out.println(notExistFileName);
            FileUtil.rename(filePath,notExistFileName);
            System.out.println(String.format("重命名 从 %s 到 %s",filePath ,notExistFileName));
        }
    }

   public static void renameIf存在(String filePath) throws IOException {
        File file = new File(filePath);
        boolean exists = file.exists();
        if(exists){

            String notExistFileName = FileUtil.generateNotExistFileName(filePath);
            System.out.println("notExistFileName");
            System.out.println(notExistFileName);
            FileUtil.rename(filePath,notExistFileName);
            System.out.println(String.format("重命名 从 %s 到 %s",filePath ,notExistFileName));
//            System.out.println("重命名");
        }

//        boolean exist = FileUtil.exist(filePath);

    }
   public    static  boolean exist(String filePath){

        File file = new File(filePath);
      return  file.exists();
    }

    static String getExtension(String filePath){
        StringBuilder res= new StringBuilder();
        for (int i = filePath.length()-1; i >=0 ; i--) {
            char c = filePath.charAt(i);
            if(c=='.'){
//                System.out.println("i  "+i);
//                String reverse = new StringBuffer(string).reverse().toString();
//
//                return res.reve
                return StringUtils.reverse(res.toString());
            }
            res.append(c);

        }
        return StringUtils.reverse(res.toString());
    }


    /**
     * 如果文件已存在 则自动重命名为带后缀的文件名
     * path
     * D:/javaRepository(1).java
     * path
     * D:/javaRepository(2).java
     * path
     * D:/javaRepository(3).java
     * notExistFileName
     * D:/javaRepository(3).java
     */
    public static String generateNotExistFileName(String src) {
        String path = src;
        String extension = FileUtil.getExtension(path);
//        boolean hasExtension = StrUtil.isNotBlank(extension);
//        boolean hasExtension = StringUtils.isNotBlank(extension);
        boolean hasExtension = org.apache.commons.lang.StringUtils.isNotBlank(extension);
//        org.dom4j.util.StringUtils.
//        antlr.StringUtils.
//        org.springframework.util.StringUtils.is
        int i = 1;
        while (FileUtil.exist(path)) {
            if (hasExtension) {
//                path = StrUtil.subBefore(path, ".", true);
                path = org.apache.commons.lang.
                        StringUtils.substringBefore(path, ".");
            }
            boolean hasSuffix = path.contains("(") && path.endsWith(")");
            if (!hasSuffix) {
                path = path + "(" + i + ")";
                i++;
            } else {
//                String suffix = StrUtil.subAfter(path, "(", true);
//                path = StrUtil.subBefore(path, "(", true);

                String suffix =org.apache.commons.lang.
                        StringUtils.substringAfter(path, "(");
                path =org.apache.commons.lang.
                        StringUtils.substringBefore(path, "(");
                final char c = suffix.charAt(0);
//                final int integer = Convert.toInt(c) + 1;
                final int integer =   Integer.parseInt(c+"")+1;
                path = path + "(" + integer + ")";
            }
            path = path + "." + extension;
            System.out.println("path");
            System.out.println(path);

        }
        return path;
    }

//    实验步骤不要打印 就自己大概的 3-4页
    public static void main(String[] args) throws IOException {
//        FileUtil.rename("C:\\Users\\25004\\Documents\\录音\\录音 (9).m4a",
//                "C:\\Users\\25004\\Documents\\录音\\录音 (9) 啥子玩意.m4a");

//        String extension = getExtension("D:/javaRepository.java");
//        System.out.println(extension);

//        CodeGen
//        D:/javaRepository(1).java
        String filePath= "D:/javaRepository.java";
//        String notExistFileName = generateNotExistFileName("D:/javaRepository.java");
//        System.out.println(notExistFileName);

        FileUtil.renameIf存在(filePath);
    }
    public  static void rename(String oldNameStr, String newNameStr) throws IOException {
        // 旧的文件或目录
        File oldName = new File(oldNameStr);
        // 新的文件或目录
        File newName = new File(newNameStr);
        if (newName.exists()) {  //  确保新的文件名不存在
            throw new java.io.IOException("file exists "+newNameStr);
        }
        if(oldName.renameTo(newName)) {
            System.out.println("已重命名 从 "+oldNameStr+" 到 "+newNameStr);
        } else {
            System.out.println("Error");
        }
    }

    public static String 获取当前类的所在工程路径() throws IOException {
        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath() ;
//        System.out.println(courseFile);
        return courseFile;
    }
    /*
     * 获取文件大小的方法01:
     * 通过文件的length() 方法获取文件大小，这个通常可以应用于本地文件的大小计算；
     * */
    public static long getFileSizeByLength(File file) {

        return file.length();
    }

    /*
     * 获取文件大小的方法02:
     * 通过FileChannel类来获取文件大小，这个方法通常结合输入流相关，
     * 因此可以用于文件网络传输时实时计算文件大小；
     * */
    public static long getFileSizeByChannel(File file) throws IOException {

        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        return fc.size();
    }

    /*
     * 获取文件大小的方法03:
     * FileInputStream的available()方法看可以用于小文件的文件大小计算，
     * 因为available()的返回值类型为int型，可能会存在溢出的情况，
     * 所以 available()方法计算文件大小不建议使用；
     * */
    public static int getFileSizeByAvailable(File file) throws IOException {

        FileInputStream fis = new FileInputStream(file);

        return fis.available();
    }

//————————————————
//    版权声明：本文为CSDN博主「山里樵夫俗称大叔」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/u014143369/article/details/53164791
}
