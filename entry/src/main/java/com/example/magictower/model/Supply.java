package com.example.magictower.model;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "supplement") //这里也可以添加索引，这里我简单处理
public class Supply extends OrmObject {
    @PrimaryKey //将Hash设置为主键
    private Integer id;
    public int kind;
    public int health;
    public int attack;
    public int shield;
    public int red_key;
    public int blue_key;
    public int yellow_key;

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public Supply(){}
    public Supply(int kind,int health,int attack,int shield,int red_key,int blue_key,int yellow_key){
        this.health=health;this.attack=attack;
        this.shield=shield;this.red_key=red_key;
        this.blue_key=blue_key;this.yellow_key=yellow_key;
        this.kind=kind;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getRed_key() {
        return red_key;
    }

    public void setRed_key(int red_key) {
        this.red_key = red_key;
    }

    public int getBlue_key() {
        return blue_key;
    }

    public void setBlue_key(int blue_key) {
        this.blue_key = blue_key;
    }

    public int getYellow_key() {
        return yellow_key;
    }

    public void setYellow_key(int yellow_key) {
        this.yellow_key = yellow_key;
    }
}
