package com.example.magictower.model;

import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "Monster")
public class Monster extends OrmObject {
    @PrimaryKey //将Hash设置为主键
    private Integer id;
    public int kind;
    public int health;
    public int attack;
    public int defence;
    public Monster(){}
    public Monster(char kind,int health,int attack,int defence){
        this.kind=(int)kind;
        this.health=health;
        this.attack=attack;
        this.defence=defence;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
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

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }
}
