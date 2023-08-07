public class Hum {
    private Integer id;
private Double temp;
private Double hum;
private Date createTime; // 生成字段声明代码

    public Hum(Integer id, Double temp, Double hum, Date createTime) {
        this.id = id;
this.temp = temp;
this.hum = hum;
this.createTime = createTime; // 生成参数赋值语句
        // 编写其他构造器逻辑
    }

    public Integer getId() { return this.id; }

public Double getTemp() { return this.temp; }

public Double getHum() { return this.hum; }

public Date getCreateTime() { return this.createTime; } // 生成 getter 方法代码

    public void setId(Integer id) { this.id = id; }

public void setTemp(Double temp) { this.temp = temp; }

public void setHum(Double hum) { this.hum = hum; }

public void setCreateTime(Date createTime) { this.createTime = createTime; } // 生成 setter 方法代码

    @Override
public String toString() {
  try {
    return JsonUtil.toJsonString(this);
} catch (IOException e) {
    throw new RuntimeException(e);
}
} // 生成 toString() 方法代码

    public static Builder builder() {
    return new Builder();
}

public static class Builder {
    private Integer id;
private Double temp;
private Double hum;
private Date createTime; // 生成 Builder 类的字段声明代码

    public Builder() {}

    public Builder id(Integer id) { this.id = id; return this; }
public Builder temp(Double temp) { this.temp = temp; return this; }
public Builder hum(Double hum) { this.hum = hum; return this; }
public Builder createTime(Date createTime) { this.createTime = createTime; return this; } // 生成 Builder 类的 setter 方法代码

    public Hum build() {
        Hum instance = new Hum();
        this.id = builder.id;
this.temp = builder.temp;
this.hum = builder.hum;
this.createTime = builder.createTime; // 生成构建实例时的赋值语句
        return instance;
    }
} // 生成 Builder 类代码 // 生成 Builder 类代码

    // 编写其他类逻辑
}