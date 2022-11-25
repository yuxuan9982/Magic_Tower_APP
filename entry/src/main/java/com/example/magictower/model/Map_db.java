package com.example.magictower.model;


//数据库

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

@Database(entities = {Map.class,Hero.class,Monster.class, Supply.class,Now_select.class},version = 1)
public abstract class Map_db extends OrmDatabase {
}

