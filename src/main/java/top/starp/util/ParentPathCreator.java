package top.starp.util;

import java.io.File;
import java.io.IOException;

public class ParentPathCreator {
    public static void createParentDirectory(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (created) {
                System.out.println("父路径创建成功！");
            } else {
                System.out.println("父路径创建失败！");
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "path/to/file.txt";  // 文件路径

//        FileUtil
        createParentDirectory(filePath);
    }
}
