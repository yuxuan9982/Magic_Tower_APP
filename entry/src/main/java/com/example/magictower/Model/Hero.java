package com.example.magictower.Model;

import ohos.agp.components.TableLayout;

public class Hero {
    int health,attack,defence,level,red_k,blue_k,yellow_k,stair;

    public Hero(int health,int attack,int defence,int level,int red_k,int blue_k,int yellow_k,int stair){
        this.health=health;
        this.attack= attack;
        this.level=level;
        this.defence=defence;
        this.red_k=red_k;
        this.blue_k=blue_k;
        this.yellow_k=yellow_k;
        this.stair=stair;
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
}
