package com.example.demo.util.codeGen;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.starp.util.StringUtils;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Table {
    private String tableCatalog;
    private String tableComment;
    private String tableName;
    private String checksum;
    private String tableSchema;
    private String checkTime;
    private String engine;
    private String tableType;
//    private BigInteger tableRows;
    private BigInteger tableRows;
//    private BigInteger avgRowLength;
    private BigInteger avgRowLength;
//    private String updateTime;
    private Timestamp updateTime;
    private BigInteger dataLength;
    private BigInteger dataFree;
    private BigInteger indexLength;
    private String rowFormat;
    private BigInteger autoIncrement;
    private BigInteger version;
    private String createOptions;
//    private boolean temporary;
    private String temporary;
//    private String createTime;
    private Timestamp createTime;
    private BigInteger maxIndexLength;
    private BigInteger maxDataLength;
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
//        this.tableRows = (BigInteger) map.get("TABLE_ROWS");
        this.tableRows = (BigInteger) map.get("TABLE_ROWS");
//        java.math.BigInteger cannot be cast to java.lang.BigInteger
//        this.avgRowLength = (BigInteger) map.get("AVG_ROW_LENGTH");
        this.avgRowLength = (BigInteger) map.get("AVG_ROW_LENGTH");
//        this.updateTime = (String) map.get("UPDATE_TIME");
        this.updateTime = (Timestamp) map.get("UPDATE_TIME");
        this.dataLength = (BigInteger) map.get("DATA_LENGTH");
        this.dataFree = (BigInteger) map.get("DATA_FREE");
        this.indexLength = (BigInteger) map.get("INDEX_LENGTH");
        this.rowFormat = (String) map.get("ROW_FORMAT");
        this.autoIncrement = (BigInteger) map.get("AUTO_INCREMENT");
        this.version = (BigInteger) map.get("VERSION");
        this.createOptions = (String) map.get("CREATE_OPTIONS");
//        this.temporary = (Boolean) map.get("TEMPORARY");
        this.temporary = (String) map.get("TEMPORARY");
//        this.createTime = (String) map.get("CREATE_TIME");
        this.createTime = (Timestamp) map.get("CREATE_TIME");
        this.maxIndexLength = (BigInteger) map.get("MAX_INDEX_LENGTH");
        this.maxDataLength = (BigInteger) map.get("MAX_DATA_LENGTH");
        this.tableCollation = (String) map.get("TABLE_COLLATION");
    }


//    public Table(String tableCatalog, String tableComment, String tableName, String checksum, String tableSchema, String checkTime, String engine, String tableType, BigInteger tableRows, BigInteger avgRowLength, String updateTime, BigInteger dataLength, BigInteger dataFree, BigInteger indexLength, String rowFormat, BigInteger autoIncrement, BigInteger version, String createOptions, boolean temporary, String createTime, BigInteger maxIndexLength, BigInteger maxDataLength, String tableCollation) {
//        this.tableCatalog = tableCatalog;
//        this.tableComment = tableComment;
//        this.tableName = tableName;
//        this.checksum = checksum;
//        this.tableSchema = tableSchema;
//        this.checkTime = checkTime;
//        this.engine = engine;
//        this.tableType = tableType;
//        this.tableRows = tableRows;
//        this.avgRowLength = avgRowLength;
//        this.updateTime = updateTime;
//        this.dataLength = dataLength;
//        this.dataFree = dataFree;
//        this.indexLength = indexLength;
//        this.rowFormat = rowFormat;
//        this.autoIncrement = autoIncrement;
//        this.version = version;
//        this.createOptions = createOptions;
//        this.temporary = temporary;
//        this.createTime = createTime;
//        this.maxIndexLength = maxIndexLength;
//        this.maxDataLength = maxDataLength;
//        this.tableCollation = tableCollation;
//    }

    // Getters and Setters for all properties
    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getTableComment() {
//        tableComment
        return tableComment;
    }

    public String getTableCommentShow() {
//        if(tableComment==null){
//            return  tableName;
//        }
        if(StringUtils.isNullOrEmpty(tableComment)){
            return tableName;
        }
//        tableComment
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

//    public BigInteger getTableRows() {
//        return tableRows;
//    }
//
//    public void setTableRows(BigInteger tableRows) {
//        this.tableRows = tableRows;
//    }

//    public BigInteger getAvgRowLength() {
//        return avgRowLength;
//    }
//
//    public void setAvgRowLength(BigInteger avgRowLength) {
//        this.avgRowLength = avgRowLength;
//    }

//    public String getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(String updateTime) {
//        this.updateTime = updateTime;
//    }

    public BigInteger getDataLength() {
        return dataLength;
    }

    public void setDataLength(BigInteger dataLength) {
        this.dataLength = dataLength;
    }

    public BigInteger getDataFree() {
        return dataFree;
    }

    public void setDataFree(BigInteger dataFree) {
        this.dataFree = dataFree;
    }

    public BigInteger getIndexLength() {
        return indexLength;
    }

    public void setIndexLength(BigInteger indexLength) {
        this.indexLength = indexLength;
    }

    public String getRowFormat() {
        return rowFormat;
    }

    public void setRowFormat(String rowFormat) {
        this.rowFormat = rowFormat;
    }

    public BigInteger getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(BigInteger autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getCreateOptions() {
        return createOptions;
    }

    public void setCreateOptions(String createOptions) {
        this.createOptions = createOptions;
    }

//    public boolean isTemporary() {
//        return temporary;
//    }
//
//    public void setTemporary(boolean temporary) {
//        this.temporary = temporary;
//    }

//    public String getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(String createTime) {
//        this.createTime = createTime;
//    }

    public BigInteger getMaxIndexLength() {
        return maxIndexLength;
    }

    public void setMaxIndexLength(BigInteger maxIndexLength) {
        this.maxIndexLength = maxIndexLength;
    }

    public BigInteger getMaxDataLength() {
        return maxDataLength;
    }

    public void setMaxDataLength(BigInteger maxDataLength) {
        this.maxDataLength = maxDataLength;
    }

    public String getTableCollation() {
        return tableCollation;
    }

    public void setTableCollation(String tableCollation) {
        this.tableCollation = tableCollation;
    }
}
