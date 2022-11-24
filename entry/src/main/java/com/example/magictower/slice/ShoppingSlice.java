package com.example.magictower.slice;

import com.example.magictower.ResourceTable;
import com.example.magictower.model.Hero;
import com.example.magictower.model.Map_db;
import com.example.magictower.model.Supply;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

import java.util.List;

public class ShoppingSlice extends AbilitySlice {
    Supply sup;
    int money;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_shopping);

        Button ret=(Button) findComponentById(ResourceTable.Id_ret);
        ret.setClickedListener(p->{
            terminate();
        });
        Text t1=(Text) findComponentById(ResourceTable.Id_get_yellow);
        Text t2=(Text) findComponentById(ResourceTable.Id_get_blue);
        Text t3=(Text) findComponentById(ResourceTable.Id_get_red);
        Text t4=(Text) findComponentById(ResourceTable.Id_get_small_sword);
        Text t5=(Text) findComponentById(ResourceTable.Id_get_big_sword);
        Text t6=(Text) findComponentById(ResourceTable.Id_get_small_shield);
        Text t7=(Text) findComponentById(ResourceTable.Id_get_big_shield);
        Text t8=(Text) findComponentById(ResourceTable.Id_get_small_blood);
        Text t9=(Text) findComponentById(ResourceTable.Id_get_big_bllod);
        t1.setClickedListener(this::click);
        t2.setClickedListener(this::click);
        t3.setClickedListener(this::click);
        t4.setClickedListener(this::click);
        t5.setClickedListener(this::click);
        t6.setClickedListener(this::click);
        t7.setClickedListener(this::click);
        t8.setClickedListener(this::click);
        t9.setClickedListener(this::click);
    }
    public void click(Component o){
        switch (o.getId()){
            case ResourceTable.Id_get_red:
                sup=new Supply(0,0,0,0,1,0,0);break;
            case ResourceTable.Id_get_blue:
                sup=new Supply(0,0,0,0,0,1,0);break;
            case ResourceTable.Id_get_yellow:
                sup=new Supply(0,0,0,0,0,0,1);break;
            case ResourceTable.Id_get_small_sword:
                sup=new Supply(0,0,20,0,0,0,0);break;
            case ResourceTable.Id_get_big_sword:
                sup=new Supply(0,0,50,0,0,0,0);break;
            case ResourceTable.Id_get_small_shield:
                sup=new Supply(0,0,0,20,0,0,0);break;
            case ResourceTable.Id_get_big_shield:
                sup=new Supply(0,0,0,50,0,0,0);break;
            case ResourceTable.Id_get_small_blood:
                sup=new Supply(0,200,0,0,0,0,0);break;
            case ResourceTable.Id_get_big_bllod:
                sup=new Supply(0,400,0,0,0,0,0);break;
        }
        Text t=(Text)o;
        String s=t.getText();
        String ss=new String();
        for (int i=0;i<s.length();i++){
            if(s.charAt(i)>='0'&&s.charAt(i)<='9')
                ss+=s.charAt(i);
        }
        money=Integer.valueOf(ss);
        DatabaseHelper helper=new DatabaseHelper(getContext());
        OrmContext o_ctx=helper.getOrmContext("database","database.db", Map_db.class);
        List<Hero> heroList=o_ctx.query(o_ctx.where(Hero.class));
        Hero hero=heroList.get(0);
        if(hero.getMoney()>=money){
            hero.setHealth(hero.getHealth()+sup.getHealth());
            hero.setAttack(hero.getAttack()+sup.getAttack());
            hero.setDefence(hero.getDefence()+sup.getShield());
            hero.setRed_k(hero.getRed_k()+sup.getRed_key());
            hero.setBlue_k(hero.getBlue_k()+sup.getBlue_key());
            hero.setMoney(hero.getMoney()-money);
            hero.setYellow_k(hero.getYellow_k()+sup.getYellow_key());
            o_ctx.update(hero);o_ctx.flush();
            terminate();
        }else{
            ToastDialog td=new ToastDialog(this);
            td.setText("金额不足！");
            td.show();
        }

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
