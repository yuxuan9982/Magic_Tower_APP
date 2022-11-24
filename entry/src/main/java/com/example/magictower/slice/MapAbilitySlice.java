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
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
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
    int dx,dy;
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
            case 'a':
                return ResourceTable.Media_skeleton;
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
    int new_x,new_y;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        //super.setUIContent(ResourceTable.Layout_MapAbility);
        //hero=new Hero(1000,30,10,1,1,1,1,1,1,1);
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
        List<Hero> heroes=o_ctx.query(o_ctx.where(Hero.class));
        hero=heroes.get(0);
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
            int x=hero.getX(),y=hero.getY();
            hero_img.setContentPositionX(mp[x][y].getContentPositionX());hero_img.setContentPositionY(mp[x][y].getContentPositionY());
            //sx=sy=0;
            //hero.setX(0);hero.setY(0);
        });
        new_x=hero.getX();new_y=hero.getY();
        //上下左右分别添加响应
        up.setClickedListener(o->{
            if(hero.getX()>0){
                new_x=(hero.getX()-1);
                interact();
            }
        });
        down.setClickedListener(p->{
            if(hero.getX()<cnt-1){
                new_x=(hero.getX()+1);
                interact();
            }
        });
        left.setClickedListener(o->{
            if(hero.getY()>0){
                new_y=(hero.getY()-1);
                interact();
            }
        });
        right.setClickedListener(p->{
            if(hero.getY()<cnt-1){
                new_y=(hero.getY()+1);
                interact();
            }
        });
        super.setUIContent(dl);
    }
    int fg;
    public void interact(){
        int x=new_x,y=new_y;
        if(info_s.charAt(new_x*10+new_y)>='a') {
            CommonDialog cd=new CommonDialog(getContext());
            cd.setAutoClosable(true);
            cd.setContentText("是否要攻击敌人？");
            cd.setButton(1, "否", new IDialog.ClickedListener() {
                @Override
                public void onClick(IDialog iDialog, int i) {
                    cd.destroy();
                }
            });
            cd.setButton(2, "是", new IDialog.ClickedListener() {
                @Override
                public void onClick(IDialog iDialog, int i) {
                    String new_s=new String();
                    for(int j=0;j<info_s.length();j++){
                        if(j==x*10+y) new_s+='0';
                        else new_s+= info_s.charAt(j);
                    }
                    info_s=new_s;
                    set_Background(mp[x][y],ResourceTable.Media_ground);
                    update_move();
                    cd.destroy();
                }
            });
            cd.show();
        }else if(info_s.charAt(x*10+y)=='1'){
            return ;
        }else{
            hero.setX(new_x);hero.setY(new_y);
            update_move();
        }
    }
    public void attack(){

    }
    public boolean update_move(){
        int x=hero.getX(),y=hero.getY();
        hero_img.setContentPositionX(mp[x][y].getContentPositionX());hero_img.setContentPositionY(mp[x][y].getContentPositionY());
        return true;
    }
    public DirectionalLayout build_dl(int type){
        DirectionalLayout dl=new DirectionalLayout(this);
        if(type==0)dl.setOrientation(Component.VERTICAL);else dl.setOrientation(Component.HORIZONTAL);
        dl.setWidth(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        dl.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        dl.setAlignment(LayoutAlignment.CENTER);
        return dl;
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
