package com.example.magictower.slice;

import com.example.magictower.model.*;
import com.example.magictower.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    OrmContext o_ctx;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        DatabaseHelper helper=new DatabaseHelper(this);
        o_ctx=helper.getOrmContext("database","database.db", Map_db.class);

        Button start= (Button) findComponentById(ResourceTable.Id_begin);
        start.setClickedListener(new Component.ClickedListener() {//添加逻辑
            @Override
            public void onClick(Component component) {
                init_gragh();
                Intent intent1=new Intent();
                //MapAbilitySlice slice=new MapAbilitySlice();
                Choose_HeroSlice slice=new Choose_HeroSlice();
                present(slice,intent);
            }
        });
        Button get_save=(Button) findComponentById(ResourceTable.Id_get_save);

        get_save.setClickedListener(new Component.ClickedListener() {//添加逻辑
            @Override
            public void onClick(Component component) {
                List<Hero> hero= o_ctx.query(o_ctx.where(Hero.class));
                List<Now_select> selects=o_ctx.query(o_ctx.where(Now_select.class));
                if(hero.size()==0||selects.size()==0){
                    ToastDialog td=new ToastDialog(getContext());
                    td.setText("你还没有存档记录！");
                    td.show();
                }else{
                    Intent intent1=new Intent();
                    MapAbilitySlice slice=new MapAbilitySlice();
                    String name=new String();
                    int id=selects.get(0).getNowchose();
                    if(id==0)name="勇士";
                    else if(id==1)name="红衣魔王";
                    else if(id==2)name="大主教";
                    else if(id==3)name="白衣武士";
                    else name="吸血鬼";
                    intent1.setParam("name",name);
                    present(slice,intent1);
                }
            }
        });
        Button info=(Button) findComponentById(ResourceTable.Id_info);
        info.setClickedListener(new Component.ClickedListener() {//添加逻辑
            @Override
            public void onClick(Component component) {

            }
        });
    }
    public void init_gragh(){
        List<Hero> heroes=o_ctx.query(o_ctx.where(Hero.class));
        int sel=0;
        for(Hero h:heroes){
            if(h.getSelectable()==1)sel++;
            o_ctx.delete(h);o_ctx.flush();
        }
//        if(heroes.size()==0){
            //初始化英雄
            Hero hero= new Hero(1000,30,15,1,1,1,1,1,0,4,50,ResourceTable.Media_hero,"勇士",sel>=0?1:0,0);
            o_ctx.insert(hero);o_ctx.flush();
            hero=new Hero(30000,2666,2666,1,1,1,1,1,0,4,50,ResourceTable.Media_hero2,"红衣魔王",sel>=4?1:0,0);
            o_ctx.insert(hero);o_ctx.flush();
            hero=new Hero(3000,2200,1900,1,1,1,1,1,0,4,50,ResourceTable.Media_hero3,"大主教",sel>=3?1:0,0);
            o_ctx.insert(hero);o_ctx.flush();
            hero=new Hero(1300,300,150,1,1,1,1,1,0,4,50,ResourceTable.Media_hero4,"白衣武士",sel>=2?1:0,0);
            o_ctx.insert(hero);o_ctx.flush();
            hero=new Hero(45000,2550,2250,1,1,1,1,1,0,4,50,ResourceTable.Media_hero5,"吸血鬼",sel>=5?1:0,0);
            o_ctx.insert(hero);o_ctx.flush();
//        }
        List<Map> maps=o_ctx.query(o_ctx.where(Map.class));
        for(Map m:maps){
            o_ctx.delete(m);o_ctx.flush();
        }
        //初始化地图
        char[][] map1={
                {'1','1','1','1','0','1','1','1','1','1'},
                {'1','1','1','1','p','1','1','1','1','1'},
                {'1','1','1','1','q','1','1','1','1','1'},
                {'1','1','1','1','r','1','1','1','1','1'},
                {'1','1','1','1','s','1','1','1','1','1'},
                {'1','w','v','u','t','x','y','z','1','1'},
                {'1','1','1','1','b','1','1','1','1','1'},
                {'1','1','0','0','0','a','0','0','1','1'},
                {'1','1','1','0','0','0','1','1','1','1'},
                {'1','1','1','1','3','1','1','1','1','1'}
        };
        String s=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s+=map1[i][j];
        Map mp=new Map();
        mp.setS_x(0);mp.setS_y(4);
        mp.setS(s);mp.setLevel(1);
        o_ctx.insert(mp);o_ctx.flush();

        char[][] map2={
                {'3','0','0','0','0','0','0','0','0','0'},
                {'1','1','4','1','0','1','1','1','1','0'},
                {'0','0','a','6','0','1','1','1','1','0'},
                {'0','a','1','1','0','1','1','1','1','0'},
                {'1','1','1','1','0','1','1','1','1','0'},
                {'1','a','a','0','0','1','1','1','1','1'},
                {'1','a','1','1','0','1','1','1','1','1'},
                {'1','5','0','0','0','a','0','0','1','1'},
                {'1','a','1','0','0','0','1','1','1','1'},
                {'1','0','1','1','2','1','1','1','1','1'}
        };
        Map mp2=new Map();
        String s2=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s2+=map2[i][j];
        mp2.setS(s2);mp2.setLevel(2);
        mp2.setS_x(8);mp2.setS_y(4);
        o_ctx.insert(mp2);o_ctx.flush();

        List<Monster> monsters=o_ctx.query(o_ctx.where(Monster.class));
        if(monsters.size()==0){
            Monster monster=new Monster('a',200,20,10,5,30);
            Monster m2=new Monster('b',100,10,5,2,10);
            Monster m3=new Monster('c',400,30,15,10,100);
            o_ctx.insert(monster);o_ctx.insert(m2);o_ctx.insert(m3);
            o_ctx.flush();
            m2=new Monster('d',2000,300,30,500,500);
            o_ctx.insert(m2);o_ctx.flush();
            m2=new Monster('e',800,100,30,100,1000);
            o_ctx.insert(m2);o_ctx.flush();
            m2=new Monster('f',90000,5000,3000,9999,9999);
            o_ctx.insert(m2);o_ctx.flush();
        }

        List<Supply> sup=o_ctx.query(o_ctx.where(Supply.class));
        if(sup.size()==0){
            Supply supply=new Supply('s',0,0,100,0,0,0);
            o_ctx.insert(supply);o_ctx.flush();
            Supply supply2=new Supply('t',0,100,0,0,0,0);
            o_ctx.insert(supply2);o_ctx.flush();
            Supply supply3=new Supply('u',0,50,0,0,0,0);
            o_ctx.insert(supply3);o_ctx.flush();
            Supply supply4=new Supply('v',0,20,0,0,0,0);
            o_ctx.insert(supply4);o_ctx.flush();
            Supply supply5=new Supply('w',0,0,50,0,0,0);
            o_ctx.insert(supply5);o_ctx.flush();
            Supply supply6=new Supply('x',0,0,20,0,0,0);
            o_ctx.insert(supply6);o_ctx.flush();
            Supply supply7=new Supply('y',400,0,0,0,0,0);
            o_ctx.insert(supply7);o_ctx.flush();
            Supply supply8=new Supply('z',200,0,0,0,0,0);
            o_ctx.insert(supply8);o_ctx.flush();
            supply=new Supply('p',0,0,0,1,0,0);
            o_ctx.insert(supply);o_ctx.flush();
            supply=new Supply('q',0,0,0,0,1,0);
            o_ctx.insert(supply);o_ctx.flush();
            supply=new Supply('r',0,0,0,0,0,1);
            o_ctx.insert(supply);o_ctx.flush();
        }
    }
    public DirectionalLayout build_dl(int type){
        DirectionalLayout dl=new DirectionalLayout(this);
        if(type==0)dl.setOrientation(Component.VERTICAL);else dl.setOrientation(Component.HORIZONTAL);
        dl.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        dl.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        dl.setAlignment(LayoutAlignment.CENTER);
        return dl;
    }
    public void set_but_back(Component component,int r,int g,int b){
        ShapeElement element=new ShapeElement();
        element.setShape(ShapeElement.RECTANGLE);
        element.setCornerRadius(30);
        element.setRgbColor(new RgbColor(r,g,b));
        element.setStroke(10,new RgbColor(0,0,255));
        component.setBackground(element);
    }
    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
