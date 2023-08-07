package top.starp.util;

import java.util.Map;

public class TrainResp {


    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getNowTimeStr() {
        return nowTimeStr;
    }

    public void setNowTimeStr(String nowTimeStr) {
        this.nowTimeStr = nowTimeStr;
    }

    public Integer getTrainDataSize() {
        return trainDataSize;
    }

    public void setTrainDataSize(Integer trainDataSize) {
        this.trainDataSize = trainDataSize;
    }

    public Map<String, String[]> getTestData() {
        return testData;
    }

    public void setTestData(Map<String, String[]> testData) {
        this.testData = testData;
    }

    String modelPath;
    String testResult;
    String nowTimeStr;
    Integer trainDataSize;
   Map<String, String[]> testData;
}
