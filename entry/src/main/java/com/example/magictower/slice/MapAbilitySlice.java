package com.example.magictower.slice;

import com.example.magictower.model.Hero;
import com.example.magictower.ResourceTable;
import com.example.magictower.model.Map;
import com.example.magictower.model.Map_db;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.components.element.PixelMapElement;
import ohos.agp.utils.LayoutAlignment;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;

import java.io.IOException;
import java.util.List;

public class MapAbilitySlice extends AbilitySlice {
    int cnt=10;
    Image [] [] mp=new Image[cnt][cnt];
    Hero hero;
    Image hero_img;
    int sx=1,sy=1,dx,dy;
    OrmContext o_ctx;
    Map info;String info_s;
    public int get_resource(char c){
        switch (c){
            case '0':
                return ResourceTable.Media_ground;
            case '1':
                return ResourceTable.Media_wall;
            case '2':
            case '3':
            case '4':
                return ResourceTable.Media_stair;
        }
        return 0;
    }
    public void set_Background(Component component, int id){
        try {
            Resource resource= getResourceManager().getResource(id);
            PixelMapElement pixelMapElement=new PixelMapElement(resource);
            component.setBackground(pixelMapElement);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        //super.setUIContent(ResourceTable.Layout_MapAbility);
        hero=new Hero(1000,30,10,1,1,1,1,1,1,1);
        hero_img=new Image(this);
        DirectionalLayout dl=(DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_MapAbility,null,false);
        //DirectionalLayout dl=new DirectionalLayout(this);
        dl.setAlignment(LayoutAlignment.HORIZONTAL_CENTER);


        TableLayout tl=new TableLayout(this);
        tl.setRowCount(cnt);tl.setColumnCount(cnt);

        DatabaseHelper helper=new DatabaseHelper(this);
        o_ctx=helper.getOrmContext("database","database.db", Map_db.class);
//        info=o_ctx.query(o_ctx.where(Map.class).equalTo("level",hero.getLevel()) );
        List<Map> data=o_ctx.query(o_ctx.where(Map.class) );
        info=data.get(0);
        info_s=info.getS();
        //获取到宽度
        int tot_width= getLayoutParams().width;
        dx=dy=tot_width/cnt;
        tot_width-=100;
        tl.setMarginLeft(50);tl.setMarginTop(50);
        for(int i=0;i<cnt;i++){
            for(int j=0;j<cnt;j++){
                mp[i][j]=new Image(this);
                set_Background(mp[i][j],ResourceTable.Media_ground);
                set_Background(mp[i][j],get_resource(info_s.charAt(i*10+j) ) );
                mp[i][j].setWidth(tot_width/cnt);
                mp[i][j].setHeight(tot_width/cnt);
                tl.addComponent(mp[i][j]);
            }
        }
        tl.addComponent(hero_img);
        dl.addComponent(tl);

        Image up=new Image(this),down=new Image(this),left=new Image(this),right=new Image(this);

        up.setHeight(AttrHelper.vp2px(30,this));
        up.setWidth(AttrHelper.vp2px(60,this));
        set_Background(up,ResourceTable.Media_up);

        dl.addComponent(up);

        DirectionalLayout ddl=new DirectionalLayout(this);

        ddl.setOrientation(Component.HORIZONTAL);
        ddl.setAlignment(LayoutAlignment.HORIZONTAL_CENTER);
        left.setHeight(AttrHelper.vp2px(60,this));
        left.setWidth(AttrHelper.vp2px(30,this));
        right.setHeight(AttrHelper.vp2px(60,this));
        right.setWidth(AttrHelper.vp2px(30,this));
        set_Background(left,ResourceTable.Media_left);set_Background(right,ResourceTable.Media_right);


        Image start=new Image(this);
        start.setWidth(AttrHelper.vp2px(30,this));
        start.setHeight(AttrHelper.vp2px(30,this));
        int sz=(int)AttrHelper.vp2px(15,this);
        start.setMarginsTopAndBottom(sz,sz);
        start.setMarginsLeftAndRight(sz,sz);
        ddl.addComponent(left);ddl.addComponent(start);ddl.addComponent(right);

        down.setHeight(AttrHelper.vp2px(30,this));
        down.setWidth(AttrHelper.vp2px(60,this));
        set_Background(down,ResourceTable.Media_down);

        dl.addComponent(ddl);
        dl.addComponent(down);


        hero_img.setWidth(tot_width/cnt);
        hero_img.setHeight(tot_width/cnt);
        hero_img.setVisibility(Component.INVISIBLE);

        set_Background(hero_img,ResourceTable.Media_hero);

        set_Background(start,ResourceTable.Media_start2);
        start.setClickedListener(o->{
            hero_img.setVisibility(Component.VISIBLE);
            hero_img.setContentPositionX(mp[0][0].getContentPositionX());hero_img.setContentPositionY(mp[0][0].getContentPositionY());
            sx=sy=0;
        });
        //上下左右分别添加响应
        up.setClickedListener(o->{
            if(sx>0){
                sx--;
            }
            if(!update_move())sx++;
        });
        down.setClickedListener(p->{
            if(sx<cnt-1){
                sx++;
                if(!update_move())sx--;
            }
        });
        left.setClickedListener(o->{
            if(sy>0){
                sy--;
                if(!update_move())sy++;
            }
        });
        right.setClickedListener(p->{
            if(sy<cnt-1){
                sy++;
                if(!update_move())sy--;
            }
        });
        super.setUIContent(dl);
    }
    public boolean update_move(){
        hero_img.setContentPositionX(mp[sx][sy].getContentPositionX());hero_img.setContentPositionY(mp[sx][sy].getContentPositionY());
        return true;
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
