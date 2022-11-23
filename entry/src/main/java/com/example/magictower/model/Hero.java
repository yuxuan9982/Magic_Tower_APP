package com.example.magictower.model;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "Hero")
public class Hero extends OrmObject {
    @PrimaryKey //将Hash设置为主键
    private Integer id;
    int health,attack,defence,level,red_k,blue_k,yellow_k,stair,x,y;
    public Hero(int health,int attack,int defence,int level,int red_k,int blue_k,int yellow_k,int stair,int x,int y){
        this.health=health;
        this.attack= attack;
        this.level=level;
        this.defence=defence;
        this.red_k=red_k;
        this.blue_k=blue_k;
        this.yellow_k=yellow_k;
        this.stair=stair;
        this.x=x;this.y=y;
    }
    public Hero(){}
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

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRed_k() {
        return red_k;
    }

    public void setRed_k(int red_k) {
        this.red_k = red_k;
    }

    public int getBlue_k() {
        return blue_k;
    }

    public void setBlue_k(int blue_k) {
        this.blue_k = blue_k;
    }

    public int getYellow_k() {
        return yellow_k;
    }

    public void setYellow_k(int yellow_k) {
        this.yellow_k = yellow_k;
    }

    public int getStair() {
        return stair;
    }

    public void setStair(int stair) {
        this.stair = stair;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
