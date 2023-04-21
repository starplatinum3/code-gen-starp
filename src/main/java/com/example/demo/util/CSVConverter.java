package com.example.demo.util;
/*

 * To change this template, choose Tools | Templates

 * and open the template in the editor.

 */

/**
 * @author
 */

import java.io.*;

public class CSVConverter {

    private File csvFile;

    private BufferedReader reader;

    private StringBuffer strBuffer;

    private BufferedWriter writer;

    int startNumber = 0;

    private String strString[];

    public CSVConverter(String location, int startNumber) {

        csvFile = new File(location);

        strBuffer = new StringBuffer("");

        this.startNumber = startNumber;

//Read

        try {

            reader = new BufferedReader(new FileReader(csvFile));

            String line = "";

            while ((line = reader.readLine()) != null) {

                String[] array = line.split(",");

                String inputQuery = "insertQuery[" + startNumber + "] = \"insert into WordList_Table ('Engl','Port','EnglishH','PortugueseH','Numbe','NumberOf','NumberOfTime','NumberOfTimesPor')values('" + array[0] + "','" + array[2] + "','" + array[1] + "','" + array[3] + "',0,0,0,0)\"";

                strBuffer.append(inputQuery + ";" + "\r\n");

                startNumber++;

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        System.out.println(strBuffer.toString());

//Write

        try {

            File file = new File("C:/Users/list.txt");

            FileWriter filewrite = new FileWriter(file);

            if (!file.exists()) {

                file.createNewFile();

            }

            writer = new BufferedWriter(filewrite);

            writer.write(strBuffer.toString());

            writer.flush();

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static void main(String[] args) {

        new CSVConverter("C:/Users/list.csv", 90);

    }

}
//————————————————
//        版权声明：本文为CSDN博主「田鸡饭」的原创文章，遵循CC4.0BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/weixin_42394573/article/details/114071959
