package top.starp.util;

public class Req {
    Integer modelId;
    String token;

    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Override
    public String toString() {
        return "Req{" +
                "modelId=" + modelId +
                ", token='" + token + '\'' +
                '}';
    }
}
