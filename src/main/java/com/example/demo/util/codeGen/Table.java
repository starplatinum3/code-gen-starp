package com.example.demo.util.codeGen;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Table {
    private String tableCatalog;
    private String tableComment;
    private String tableName;
    private String checksum;
    private String tableSchema;
    private String checkTime;
    private String engine;
    private String tableType;
    private long tableRows;
    private long avgRowLength;
    private String updateTime;
    private long dataLength;
    private long dataFree;
    private long indexLength;
    private String rowFormat;
    private long autoIncrement;
    private long version;
    private String createOptions;
    private boolean temporary;
    private String createTime;
    private long maxIndexLength;
    private long maxDataLength;
    private String tableCollation;

//    public Table(Map<String ,Object> map){
//        this.tableCatalog = (String) map.get("TABLE_CATALOG");
//    }

    public static List<Table>  mapsToObjs( List<Map<String, Object>> maps){
        List<Table> collect = maps.stream().map(o -> {
            Table table = new Table(o);
            return table;
        }).collect(Collectors.toList());
        return collect;
    }
    public Table(Map<String, Object> map) {
        this.tableCatalog = (String) map.get("TABLE_CATALOG");
        this.tableComment = (String) map.get("TABLE_COMMENT");
        this.tableName = (String) map.get("TABLE_NAME");
        this.checksum = (String) map.get("CHECKSUM");
        this.tableSchema = (String) map.get("TABLE_SCHEMA");
        this.checkTime = (String) map.get("CHECK_TIME");
        this.engine = (String) map.get("ENGINE");
        this.tableType = (String) map.get("TABLE_TYPE");
        this.tableRows = (Long) map.get("TABLE_ROWS");
        this.avgRowLength = (Long) map.get("AVG_ROW_LENGTH");
        this.updateTime = (String) map.get("UPDATE_TIME");
        this.dataLength = (Long) map.get("DATA_LENGTH");
        this.dataFree = (Long) map.get("DATA_FREE");
        this.indexLength = (Long) map.get("INDEX_LENGTH");
        this.rowFormat = (String) map.get("ROW_FORMAT");
        this.autoIncrement = (Long) map.get("AUTO_INCREMENT");
        this.version = (Long) map.get("VERSION");
        this.createOptions = (String) map.get("CREATE_OPTIONS");
        this.temporary = (Boolean) map.get("TEMPORARY");
        this.createTime = (String) map.get("CREATE_TIME");
        this.maxIndexLength = (Long) map.get("MAX_INDEX_LENGTH");
        this.maxDataLength = (Long) map.get("MAX_DATA_LENGTH");
        this.tableCollation = (String) map.get("TABLE_COLLATION");
    }


    public Table(String tableCatalog, String tableComment, String tableName, String checksum, String tableSchema, String checkTime, String engine, String tableType, long tableRows, long avgRowLength, String updateTime, long dataLength, long dataFree, long indexLength, String rowFormat, long autoIncrement, long version, String createOptions, boolean temporary, String createTime, long maxIndexLength, long maxDataLength, String tableCollation) {
        this.tableCatalog = tableCatalog;
        this.tableComment = tableComment;
        this.tableName = tableName;
        this.checksum = checksum;
        this.tableSchema = tableSchema;
        this.checkTime = checkTime;
        this.engine = engine;
        this.tableType = tableType;
        this.tableRows = tableRows;
        this.avgRowLength = avgRowLength;
        this.updateTime = updateTime;
        this.dataLength = dataLength;
        this.dataFree = dataFree;
        this.indexLength = indexLength;
        this.rowFormat = rowFormat;
        this.autoIncrement = autoIncrement;
        this.version = version;
        this.createOptions = createOptions;
        this.temporary = temporary;
        this.createTime = createTime;
        this.maxIndexLength = maxIndexLength;
        this.maxDataLength = maxDataLength;
        this.tableCollation = tableCollation;
    }

    // Getters and Setters for all properties
    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public long getTableRows() {
        return tableRows;
    }

    public void setTableRows(long tableRows) {
        this.tableRows = tableRows;
    }

    public long getAvgRowLength() {
        return avgRowLength;
    }

    public void setAvgRowLength(long avgRowLength) {
        this.avgRowLength = avgRowLength;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public long getDataLength() {
        return dataLength;
    }

    public void setDataLength(long dataLength) {
        this.dataLength = dataLength;
    }

    public long getDataFree() {
        return dataFree;
    }

    public void setDataFree(long dataFree) {
        this.dataFree = dataFree;
    }

    public long getIndexLength() {
        return indexLength;
    }

    public void setIndexLength(long indexLength) {
        this.indexLength = indexLength;
    }

    public String getRowFormat() {
        return rowFormat;
    }

    public void setRowFormat(String rowFormat) {
        this.rowFormat = rowFormat;
    }

    public long getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(long autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getCreateOptions() {
        return createOptions;
    }

    public void setCreateOptions(String createOptions) {
        this.createOptions = createOptions;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getMaxIndexLength() {
        return maxIndexLength;
    }

    public void setMaxIndexLength(long maxIndexLength) {
        this.maxIndexLength = maxIndexLength;
    }

    public long getMaxDataLength() {
        return maxDataLength;
    }

    public void setMaxDataLength(long maxDataLength) {
        this.maxDataLength = maxDataLength;
    }

    public String getTableCollation() {
        return tableCollation;
    }

    public void setTableCollation(String tableCollation) {
        this.tableCollation = tableCollation;
    }
}
