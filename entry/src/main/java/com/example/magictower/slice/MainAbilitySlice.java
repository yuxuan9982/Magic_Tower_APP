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
                CommonDialog cd=new CommonDialog(getContext());
                cd.setAutoClosable(true);
                DirectionalLayout dl2=build_dl(0);
                Text title= new Text(getContext());
                //set_but_back(title,104,0,254);
                title.setTextSize(50);title.setMultipleLine(true);
                title.setText("信息说明");
                Text content= new Text(getContext());
                content.setTextSize(50);content.setMultipleLine(true);
                content.setMultipleLine(true);
                content.setText("游戏名：魔塔（仿）\n作者：于轩\n联系方式：2309941940@qq.com");
                Text end=new Text(getContext());
                end.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);
                end.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
                end.setTextSize(50);
                end.setText("ver:--1.01");
                dl2.setPadding(20,20,20,20);
                dl2.addComponent(title);dl2.addComponent(content);dl2.addComponent(end);
                set_but_back(dl2,230,231,249);
                cd.setContentCustomComponent(dl2);
                cd.show();
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
            hero=new Hero(45000,2550,2250,1,1,1,1,1,0,4,50,ResourceTable.Media_hero5,"吸血鬼",sel>=5?1:1,0);
            o_ctx.insert(hero);o_ctx.flush();
//        }
        List<Map> maps=o_ctx.query(o_ctx.where(Map.class));
        for(Map m:maps){
            o_ctx.delete(m);o_ctx.flush();
        }
        /*
                {'0','2','0','0','0','0','0','0','0','0'},
                {'3','0','0','0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0','0','0','0'},
                {'0','0','0','0','0','0','0','0','0','0'}
         */
        //初始化地图
        char[][] map1={
                {'3','0','0','0','0','0','b','0','0','0'},
                {'1','1','1','1','0','1','1','1','1','0'},
                {'k','k','b','6','0','1','1','1','1','0'},
                {'z','0','0','1','0','1','1','1','1','0'},
                {'r','r','r','1','0','1','1','1','1','b'},
                {'1','6','1','1','0','x','y','z','1','0'},
                {'1','6','1','1','0','0','0','0','0','b'},
                {'u','e','0','1','1','6','1','5','1','1'},
                {'y','0','0','1','r','0','1','c','i','0'},
                {'x','0','0','1','z','0','1','l','z','x'}
        };
        String s=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s+=map1[i][j];
        Map mp=new Map();
        mp.setS_x(9);mp.setS_y(4);
        mp.setS(s);mp.setLevel(1);
        o_ctx.insert(mp);o_ctx.flush();


        map1= new char[][]{
                {'2', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
                {'0', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
                {'0', '1', '1', '0', '0', '0', '0', '0', '0', '1'},
                {'0', '1', '1', '0', '0', '1', '1', '1', '0', '1'},
                {'0', 'p', '4', '0', '0', '1', 't', '7', '0', '1'},
                {'0', '1', '1', '1', '0', '1', '1', '1', '0', '1'},
                {'0', '1', '1', '1', '0', '0', '0', '0', '0', '1'},
                {'0', '1', '0', '1', '1', '5', '1', '1', '1', '1'},
                {'0', '1', '0', '1', 'r', 'w', '1', '1', '1', '1'},
                {'3', '1', '0', '1', 'z', 'z', '1', '1', '1', '1'}
        };
        s=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s+=map1[i][j];
        mp=new Map();
        mp.setS_x(1);mp.setS_y(0);
        mp.setS(s);mp.setLevel(2);
        o_ctx.insert(mp);o_ctx.flush();


        char[][] map2={
                {'3','0','0','0','0','0','0','0','0','0'},
                {'1','1','5','1','0','1','1','1','1','0'},
                {'z','y','a','1','0','1','1','1','1','0'},
                {'0','c','1','1','0','1','1','1','1','0'},
                {'1','6','1','1','0','1','1','1','1','0'},
                {'z','b','a','1','0','1','1','1','1','i'},
                {'0','b','1','1','0','1','1','1','1','j'},
                {'1','5','0','1','0','l','0','z','1','q'},
                {'0','k','0','1','0','0','1','1','1','l'},
                {'1','x','1','1','2','1','1','1','1','v'}
        };
        Map mp2=new Map();
        String s2=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s2+=map2[i][j];
        mp2.setS(s2);mp2.setLevel(3);
        mp2.setS_x(8);mp2.setS_y(4);
        o_ctx.insert(mp2);o_ctx.flush();


        map1= new char[][]{
                {'3','0','0','0','0','0','b','0','0','0'},
                {'1','5','1','1','0','1','1','1','1','1'},
                {'0','h','b','1','d','1','1','1','1','0'},
                {'k','1','0','1','0','c','1','1','1','0'},
                {'h','1','r','1','i','k','1','z','h','b'},
                {'c','1','1','1','1','c','1','h','z','0'},
                {'6','1','1','1','1','0','1','0','0','b'},
                {'y','1','w','1','1','0','1','6','1','1'},
                {'a','1','0','1','r','0','y','i','i','y'},
                {'b','0','z','1','z','2','1','j','j','x'}
        };
        s=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s+=map1[i][j];
        mp=new Map();
        mp.setS_x(8);mp.setS_y(5);
        mp.setS(s);mp.setLevel(4);
        o_ctx.insert(mp);o_ctx.flush();


        map1= new char[][]{
                {'0','0','0','0','2','0','0','0','r','q'},
                {'0','0','0','0','0','0','1','0','1','1'},
                {'0','0','1','0','0','0','1','h','c','0'},
                {'0','1','1','k','k','k','1','c','h','q'},
                {'6','0','0','1','k','1','1','z','h','q'},
                {'0','x','1','k','k','k','1','r','r','r'},
                {'1','0','1','0','0','0','1','0','0','q'},
                {'y','6','1','0','0','0','1','1','1','1'},
                {'z','0','1','0','0','0','q','0','i','1'},
                {'v','z','1','0','3','0','1','0','j','1'}
        };
        s=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s+=map1[i][j];
        mp=new Map();
        mp.setS_x(1);mp.setS_y(4);
        mp.setS(s);mp.setLevel(5);
        o_ctx.insert(mp);o_ctx.flush();


        map1= new char[][]{
                {'2','0','0','0','0','0','0','0','0','0'},
                {'3','0','1','4','1','1','1','1','0','0'},
                {'0','1','1','0','0','0','0','1','1','0'},
                {'0','1','0','0','1','0','1','0','1','0'},
                {'0','1','0','0','0','m','0','0','1','0'},
                {'0','1','0','0','1','0','1','0','1','0'},
                {'0','1','0','0','0','0','0','0','1','0'},
                {'0','1','1','0','0','0','0','1','1','0'},
                {'0','0','1','1','1','1','1','1','0','0'},
                {'0','0','0','0','0','0','0','0','0','0'}
        };
        s=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s+=map1[i][j];
        mp=new Map();
        mp.setS_x(1);mp.setS_y(1);
        mp.setS(s);mp.setLevel(6);
        o_ctx.insert(mp);o_ctx.flush();


        map1= new char[][]{
                {'0','0','e','6','i','i','c','6','0','2'},
                {'u','0','d','0','y','0','k','1','0','0'},
                {'y','0','1','0','0','0','0','1','1','5'},
                {'0','k','1','1','1','1','1','1','0','0'},
                {'1','0','5','0','0','0','i','0','0','0'},
                {'1','1','1','1','1','1','1','1','1','5'},
                {'x','x','1','b','y','0','y','1','0','0'},
                {'v','v','1','b','y','k','g','1','1','5'},
                {'g','1','1','0','k','0','0','1','0','0'},
                {'k','y','y','g','0','0','0','0','0','3'}
        };
        s=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s+=map1[i][j];
        mp=new Map();
        mp.setS_x(1);mp.setS_y(9);
        mp.setS(s);mp.setLevel(7);
        o_ctx.insert(mp);o_ctx.flush();


        map1= new char[][]{
                {'1','1','1','1','0','3','1','1','1','1'},
                {'1','1','1','0','0','0','0','1','1','1'},
                {'1','1','1','1','n','1','1','1','1','1'},
                {'1','0','0','m','n','m','0','0','0','1'},
                {'0','0','0','0','m','0','0','0','0','0'},
                {'0','0','1','1','0','1','1','0','0','0'},
                {'0','0','1','1','0','1','1','0','0','0'},
                {'0','0','1','1','n','1','1','0','0','0'},
                {'1','1','1','1','0','1','1','1','1','1'},
                {'2','0','4','5','0','0','0','0','0','0'}
        };
        s=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s+=map1[i][j];
        mp=new Map();
        mp.setS_x(9);mp.setS_y(1);
        mp.setS(s);mp.setLevel(8);
        o_ctx.insert(mp);o_ctx.flush();



        List<Monster> monsters=o_ctx.query(o_ctx.where(Monster.class));
        if(monsters.size()==0){
            Monster monster=new Monster('a',50,42,6,6,6);
            Monster m2=new Monster('b',35,18,1,1,1);
            Monster m3=new Monster('c',55,52,12,10,10);
            o_ctx.insert(monster);o_ctx.insert(m2);o_ctx.insert(m3);
            o_ctx.flush();
            m2=new Monster('d',2000,300,30,500,500);
            o_ctx.insert(m2);o_ctx.flush();
            m2=new Monster('e',800,100,30,100,100);
            o_ctx.insert(m2);o_ctx.flush();
            m2=new Monster('f',9000,500,300,999,999);
            o_ctx.insert(m2);o_ctx.flush();
            m2=new Monster('h',130,60,3,8,8);
            o_ctx.insert(m2);o_ctx.flush();
            m2=new Monster('i',20,100,68,28,28);
            o_ctx.insert(m2);o_ctx.flush();
            m2=new Monster('j',50,48,22,12,12);
            o_ctx.insert(m2);o_ctx.flush();
            m2=new Monster('k',500,150,50,50,50);
            o_ctx.insert(m2);o_ctx.flush();
            m2=new Monster('l',60,32,8,5,5);
            o_ctx.insert(m2);o_ctx.flush();
            m2=new Monster('g',300,200,30,50,50);
            o_ctx.insert(m2);o_ctx.flush();

            m2=new Monster('m',5000,180,190,500,500);
            o_ctx.insert(m2);o_ctx.flush();
            m2=new Monster('n',7000,250,200,900,900);
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
