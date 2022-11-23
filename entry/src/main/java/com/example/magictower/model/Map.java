package com.example.magictower.model;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "record") //这里也可以添加索引，这里我简单处理
public class Map extends OrmObject {
    @PrimaryKey //将Hash设置为主键
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
