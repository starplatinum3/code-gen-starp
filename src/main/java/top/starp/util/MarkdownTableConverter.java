package top.starp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkdownTableConverter {
    public static String convertListMapToMarkdownTable(List<Map<String, Object>> data) {
        // 获取所有的列名
        List<String> columns = new ArrayList<>();
        for (Map<String, Object> item : data) {
            for (String column : item.keySet()) {
                if (!columns.contains(column)) {
                    columns.add(column);
                }
            }
        }

        // 构建表头
        StringBuilder header = new StringBuilder("| ");
        for (String column : columns) {
            header.append(column).append(" | ");
        }
        header.append("\n");

        // 构建分隔线
        StringBuilder separator = new StringBuilder("| ");
        for (String column : columns) {
            separator.append("---").append(" | ");
        }
        separator.append("\n");

        // 构建每行数据
        StringBuilder rows = new StringBuilder();
        for (Map<String, Object> item : data) {
            StringBuilder row = new StringBuilder("| ");
            for (String column : columns) {
                Object value = item.getOrDefault(column, "");
                row.append(value).append(" | ");
            }
            row.append("\n");
            rows.append(row);
        }

        return header.toString() + separator.toString() + rows.toString();
    }

    public static void main(String[] args) {
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("Name", "John");
        item1.put("Age", 25);
        item1.put("Country", "USA");
        data.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("Name", "Alice");
        item2.put("Age", 30);
        item2.put("Country", "Canada");
        data.add(item2);

        Map<String, Object> item3 = new HashMap<>();
        item3.put("Name", "Bob");
        item3.put("Country", "Australia");
        data.add(item3);

        String markdownTable = convertListMapToMarkdownTable(data);
        System.out.println(markdownTable);
    }
}
