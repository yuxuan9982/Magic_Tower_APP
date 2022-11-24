package com.example.magictower.model;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "record") //这里也可以添加索引，这里我简单处理
public class Map extends OrmObject {
    @PrimaryKey //将Hash设置为主键
    private Integer id;
    int level,s_x,s_y;
    String s;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getS_x() {
        return s_x;
    }

    public void setS_x(int s_x) {
        this.s_x = s_x;
    }

    public int getS_y() {
        return s_y;
    }

    public void setS_y(int s_y) {
        this.s_y = s_y;
    }
}
