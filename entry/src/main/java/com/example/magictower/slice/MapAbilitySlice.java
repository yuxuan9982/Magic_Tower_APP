package com.example.magictower.slice;

import com.example.magictower.model.*;
import com.example.magictower.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.*;
import ohos.agp.components.element.PixelMapElement;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;


import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MapAbilitySlice extends AbilitySlice{
    int cnt=10;
    Image [] [] mp=new Image[cnt][cnt];
    Hero hero;
    int dx,dy;
    OrmContext o_ctx;
    Map info;String info_s;
    public int get_resource(char c){
        //根据类型，判断应该给什么样的背景图片
        switch (c){
            case '0':
                return ResourceTable.Media_ground;
            case '1':
                return ResourceTable.Media_wall;
            case '2':
            case '3':
                return ResourceTable.Media_stair;
            case '4':
                return ResourceTable.Media_red_door;
            case '5':
                return ResourceTable.Media_blue_door;
            case '6':
                return ResourceTable.Media_yellow_door;
            case 'a':
                return ResourceTable.Media_skeleton;
            case 'b':
                return ResourceTable.Media_slime;
            case 'c':
                return ResourceTable.Media_skeleton_warrior;
            case 'd':
                return ResourceTable.Media_abyss_wacher;
            case 'e':
                return ResourceTable.Media_ash_judge;
            case 'f':
                return ResourceTable.Media_final_boss;
            case 's':
                return ResourceTable.Media_god_shield;
            case 't':
                return ResourceTable.Media_god_sword;
            case 'u':
                return ResourceTable.Media_big_sword;
            case 'v':
                return ResourceTable.Media_small_sword;
            case 'w':
                return ResourceTable.Media_big_shield;
            case 'x':
                return ResourceTable.Media_small_shield;
            case 'y':
                return ResourceTable.Media_big_blood;
            case 'z':
                return ResourceTable.Media_small_blood;
            case 'p':
                return ResourceTable.Media_red_key;
            case 'q':
                return ResourceTable.Media_blue_key;
            case 'r':
                return ResourceTable.Media_yellow_key;

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
    DirectionalLayout dl;
    @Override
    public void onStart(Intent intent) {
        System.out.println("begin");
        super.onStart(intent);
        //super.setUIContent(ResourceTable.Layout_MapAbility);
        //hero=new Hero(1000,30,10,1,1,1,1,1,1,1);

        DatabaseHelper helper=new DatabaseHelper(this);
        o_ctx=helper.getOrmContext("database","database.db", Map_db.class);
        List<Hero> heroes=o_ctx.query(o_ctx.where(Hero.class));
        hero=heroes.get(0);
//        info=o_ctx.query(o_ctx.where(Map.class).equalTo("level",hero.getLevel()) );
        List<Map> data=o_ctx.query(o_ctx.where(Map.class) );

        for (int i=0;i<data.size();i++){
            if(data.get(i).getLevel()==hero.getStair()){
                info=data.get(i);
            }
        }
        info_s=info.getS();

        hero.setX(info.getS_x());hero.setY(info.getS_y());


        dl=(DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_MapAbility,null,false);
        //DirectionalLayout dl=new DirectionalLayout(this);
        dl.setAlignment(LayoutAlignment.HORIZONTAL_CENTER);
        update_all();

        TableLayout tl=new TableLayout(this);
        tl.setRowCount(cnt);tl.setColumnCount(cnt);

        //获取到宽度
        int tot_width= getLayoutParams().width;
        dx=dy=tot_width/cnt;
        tot_width-=100;
        tl.setMarginLeft(50);tl.setMarginTop(50);
        for(int i=0;i<cnt;i++){
            for(int j=0;j<cnt;j++){
                mp[i][j]=new Image(this);
                set_Background(mp[i][j],get_resource(info_s.charAt(i*10+j) ) );
                if(i==hero.getX()&&j==hero.getY()){
                    set_Background(mp[i][j],ResourceTable.Media_hero );
                }
                mp[i][j].setWidth(tot_width/cnt);
                mp[i][j].setHeight(tot_width/cnt);
                tl.addComponent(mp[i][j]);
            }
        }
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


        set_Background(start,ResourceTable.Media_start2);
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
    //更新英雄的显示状态，所有状态
    public void update_rk(){
        Text rk=(Text) dl.findComponentById(ResourceTable.Id_red_key);
        rk.setText(String.valueOf(hero.getRed_k()));
    }
    public void update_bk(){
        Text bk=(Text) dl.findComponentById(ResourceTable.Id_blue_key);
        bk.setText(String.valueOf(hero.getBlue_k()));
    }
    public void update_yk(){
        Text yk=(Text) dl.findComponentById(ResourceTable.Id_yellow_key);
        yk.setText(String.valueOf(hero.getYellow_k()));
    }
    public void update_st(){
        Text st=(Text) dl.findComponentById(ResourceTable.Id_stair);
        st.setText(String.valueOf(hero.getStair()));
    }
    public void update_heal(){
        Text health=(Text) dl.findComponentById(ResourceTable.Id_health);
        health.setText(String.valueOf(hero.getHealth() ) );
    }
    public void update_attack(){
        Text attack=(Text) dl.findComponentById(ResourceTable.Id_attack);
        attack.setText(String.valueOf(hero.getAttack() ) );
    }
    public void update_defence(){
        Text defence=(Text) dl.findComponentById(ResourceTable.Id_defence);
        defence.setText(String.valueOf(hero.getDefence()));
    }
    public void update_level(){
        Text level=(Text) dl.findComponentById(ResourceTable.Id_level);
        level.setText(String.valueOf(hero.getLevel() ));
    }
    public void update_all(){
        update_rk();update_bk();update_yk();update_st();update_heal();update_attack();update_defence();update_level();
    }
    int fg;
    Monster monster;
    //走一步需要的可能的交互情况
    public void interact(){
        int x=new_x,y=new_y;
        char c=info_s.charAt(x*10+y);
        if(c>='a'&&c<'p') {
            //获取到是哪种怪物
            List<Monster> monsters=o_ctx.query(o_ctx.where(Monster.class).equalTo("kind",info_s.charAt(new_x*10+new_y)));
            if(monsters.size()>=1)
                monster=monsters.get(0);
            //显示提示界面
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
                    show_status();
                    change_info(x,y,'0');
                    new_x=hero.getX();new_y=hero.getY();
                    //点了就销毁
                    cd.destroy();
                }
            });
            cd.show();
        }else if(info_s.charAt(x*10+y)=='1'){
            new_x=hero.getX();new_y=hero.getY();return ;
        }else if(info_s.charAt(x*10+y)=='0'){
            update_move();
        }else if(c=='4'){
            reset_pos();
            if(hero.getRed_k()<=0){
                //开不了门
                ToastDialog td=new ToastDialog(getContext());td.setText("钥匙不足");td.show();
            }else{
                //能开门
                ToastDialog td=new ToastDialog(getContext());td.setText("开锁成功");td.show();
                hero.setRed_k(hero.getRed_k()-1);change_info(x,y,'0');reset_pos();
            }
        } else if (c == '5') {
            reset_pos();
            if(hero.getBlue_k()<=0){
                ToastDialog td=new ToastDialog(getContext());td.setText("钥匙不足");td.show();
            }else{
                ToastDialog td=new ToastDialog(getContext());td.setText("开锁成功");td.show();
                hero.setBlue_k(hero.getBlue_k()-1);change_info(x,y,'0');reset_pos();
            }
        }else if (c=='6'){
            reset_pos();
            if(hero.getYellow_k()<=0){
                ToastDialog td=new ToastDialog(getContext());td.setText("钥匙不足");td.show();
            }else{
                ToastDialog td=new ToastDialog(getContext());td.setText("开锁成功");td.show();
                hero.setYellow_k(hero.getYellow_k()-1);
                change_info(x,y,'0');
            }

        }else if(c=='3'){
            hero.setStair(hero.getStair()+1);
            Intent in1=new Intent();
            MapAbilitySlice slice=new MapAbilitySlice();
            o_ctx.update(hero);o_ctx.flush();
            info.setS(info_s);o_ctx.update(info);o_ctx.flush();
            present(slice,in1);
            terminate();
        }else if(c=='2'){
            hero.setStair(hero.getStair()-1);
            Intent in1=new Intent();
            MapAbilitySlice slice=new MapAbilitySlice();
            o_ctx.update(hero);o_ctx.flush();
            info.setS(info_s);o_ctx.update(info);o_ctx.flush();
            present(slice,in1);
            terminate();
        }else if(c>='p'){
            List<Supply> sup=o_ctx.query(o_ctx.where(Supply.class).equalTo("kind",c));
            Supply supply=sup.get(0);
            hero.setHealth(hero.getHealth()+supply.getHealth());
            hero.setAttack(hero.getAttack()+supply.getAttack());
            hero.setDefence(hero.getDefence()+supply.getShield());
            hero.setRed_k(hero.getRed_k()+supply.getRed_key());
            hero.setBlue_k(hero.getBlue_k()+supply.getBlue_key());
            hero.setYellow_k(hero.getYellow_k()+supply.getYellow_key());
            update_all();change_info(x,y,'0');
        }
    }
    public void reset_pos(){
        new_x=hero.getX();new_y=hero.getY();
    }
    public void change_info(int x,int y,char c){
        //杀了怪物以后，怪物位置变成可以行走的土地
        String new_s=new String();
        //把字符串改了，字符串不支持直接修改，勉强这样改吧，反正常数也就100多
        for(int j=0;j<info_s.length();j++){
            if(j==x*10+y) new_s+='0';else new_s+= info_s.charAt(j);
        }
        info_s=new_s;set_Background(mp[x][y],ResourceTable.Media_ground);
    }
    DirectionalLayout show_l;
    public void update_show(){
        Text health=(Text) show_l.findComponentById(ResourceTable.Id_status_health);
        Text attack=(Text) show_l.findComponentById(ResourceTable.Id_status_attack);
        Text defence=(Text) show_l.findComponentById(ResourceTable.Id_status_defence);
        health.setText(String.valueOf(hero.getHealth() ) );
        attack.setText(String.valueOf(hero.getAttack() ) );
        defence.setText(String.valueOf(hero.getDefence()));

        Text health2=(Text) show_l.findComponentById(ResourceTable.Id_status_health_monster);
        Text attack2=(Text) show_l.findComponentById(ResourceTable.Id_status_attack_monster);
        Text defence2=(Text) show_l.findComponentById(ResourceTable.Id_status_defence_monster);
        health2.setText(String.valueOf(monster.getHealth() ) );
        attack2.setText(String.valueOf(monster.getAttack() ) );
        defence2.setText(String.valueOf(monster.getDefence()));

    }
    int dmg1,dmg2,begin_h1,begin_h2,end_h1,end_h2;
    //互相攻击，首先显示状态页面，然后每隔一段时间update
    public void show_status(){
        CommonDialog cd=new CommonDialog(getContext());
        show_l= (DirectionalLayout) LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_status,null,false);
        update_show();
        cd.setAutoClosable(true);
        cd.setContentCustomComponent(show_l);
        cd.setAlignment(LayoutAlignment.HORIZONTAL_CENTER);
        cd.show();
        dmg1= Math.max(1,hero.getAttack()-monster.getDefence());dmg2=Math.max(1,monster.getAttack()-hero.getDefence());
        int need=Math.min((hero.getHealth()+dmg2-1)/dmg2,(monster.health+dmg1-1)/dmg1);
        begin_h1=hero.getHealth();begin_h2=monster.getHealth();
        end_h1=begin_h1-need*dmg2;end_h2=begin_h2-need*dmg1;
        Image img=(Image) show_l.findComponentById(ResourceTable.Id_status_monster);
        set_Background(img,get_resource(info_s.charAt(10*new_x+new_y)));
        AnimatorValue animatorValue=new AnimatorValue();
        animatorValue.setDuration(1000);
        animatorValue.setValueUpdateListener(new AnimatorValue.ValueUpdateListener() {
            @Override
            public void onUpdate(AnimatorValue animatorValue, float v) {
                hero.setHealth(Math.max(0,(int)(begin_h1-(begin_h1-end_h1)*v) ) );
                monster.setHealth(Math.max(0,(int)(begin_h2- (begin_h2-end_h2)*v)));
                update_show();
                if(v==1){
                    if(end_h1>0){
                        ToastDialog td=new ToastDialog(getContext());
                        td.setText("挑战成功");
                        td.show();td.setAutoClosable(true);
                        cd.destroy();
                    }else{
                        CommonDialog cd2=new CommonDialog(getContext());
                        cd2.setContentText("很遗憾，你并没有打败怪物！");
                        cd2.setButton(1, "重新开始", new IDialog.ClickedListener() {
                            @Override
                            public void onClick(IDialog iDialog, int i) {
                                cd2.destroy();
                            }
                        });
                        cd2.show();cd.destroy();terminate();
                    }
                    hero.setHealth(end_h1);
                    monster.setHealth(end_h2);
                    update_heal();
                }
            }

        });
        animatorValue.start();
    }
    //这里后面要加动画
    public boolean update_move(){
        int x=hero.getX(),y=hero.getY();
        set_Background(mp[x][y],ResourceTable.Media_ground);
        set_Background(mp[new_x][new_y],ResourceTable.Media_hero);
        hero.setX(new_x);hero.setY(new_y);
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
