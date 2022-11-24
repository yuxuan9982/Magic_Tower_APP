package com.example.magictower.slice;

import com.example.magictower.model.Hero;
import com.example.magictower.model.Map;
import com.example.magictower.model.Map_db;
import com.example.magictower.ResourceTable;
import com.example.magictower.model.Monster;
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
                MapAbilitySlice slice=new MapAbilitySlice();
                present(slice,intent);
            }
        });
        Button get_save=(Button) findComponentById(ResourceTable.Id_get_save);

        get_save.setClickedListener(new Component.ClickedListener() {//添加逻辑
            @Override
            public void onClick(Component component) {
                List<Hero> hero= o_ctx.query(o_ctx.where(Hero.class));
                if(hero.size()==0){
                    ToastDialog td=new ToastDialog(getContext());
                    td.setText("你还没有存档记录！");
                    td.show();
                }else{
                    Intent intent1=new Intent();
                    MapAbilitySlice slice=new MapAbilitySlice();
                    present(slice,intent);
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
        //初始化英雄
        Hero hero= new Hero(1000,30,15,1,1,1,1,1,0,4);
        o_ctx.insert(hero);
        //初始化地图
        char[][] map1={
                {'1','1','1','1','0','1','1','1','1','1'},
                {'1','1','1','1','0','1','1','1','1','1'},
                {'1','1','1','1','0','1','1','1','1','1'},
                {'1','1','1','1','0','1','1','1','1','1'},
                {'1','1','1','1','0','1','1','1','1','1'},
                {'1','1','1','1','0','1','1','1','1','1'},
                {'1','1','1','1','6','1','1','1','1','1'},
                {'1','1','0','0','5','a','0','0','1','1'},
                {'1','1','1','0','4','0','1','1','1','1'},
                {'1','1','1','1','3','1','1','1','1','1'}
        };
        String s=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s+=map1[i][j];
        Map mp=new Map();
        mp.setS_x(0);mp.setS_y(4);
        mp.setS(s);mp.setLevel(1);
        o_ctx.insert(mp);

        char[][] map2={
                {'3','0','0','0','0','0','0','0','0','0'},
                {'1','1','1','1','1','1','1','1','1','0'},
                {'0','0','a','6','0','1','1','1','1','0'},
                {'0','a','1','1','0','1','1','1','1','0'},
                {'1','1','1','1','0','1','1','1','1','0'},
                {'1','a','a','0','0','1','1','1','1','1'},
                {'1','a','1','1','6','1','1','1','1','1'},
                {'1','5','0','0','5','a','0','0','1','1'},
                {'1','a','1','0','0','0','1','1','1','1'},
                {'1','0','1','1','2','1','1','1','1','1'}
        };
        Map mp2=new Map();
        String s2=new String();
        for(int i=0;i<10;i++) for(int j=0;j<10;j++) s2+=map1[i][j];
        mp2.setS(s2);mp.setLevel(2);
        mp2.setS_x(8);mp2.setS_y(4);
        o_ctx.insert(mp2);

        o_ctx.flush();

        List<Monster> monsters=o_ctx.query(o_ctx.where(Monster.class));
        if(monsters.size()==0){
            Monster monster=new Monster('a',200,20,10);
            o_ctx.insert(monster);
            o_ctx.flush();
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
