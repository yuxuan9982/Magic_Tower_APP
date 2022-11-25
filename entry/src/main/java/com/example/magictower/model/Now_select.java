package com.example.magictower.model;

import ohos.aafwk.content.Intent;
import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "sel")
public class Now_select extends OrmObject {
    @PrimaryKey //将Hash设置为主键
    private Integer id;
    private Integer nowchose;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNowchose() {
        return nowchose;
    }

    public void setNowchose(Integer nowchose) {
        this.nowchose = nowchose;
    }
}
