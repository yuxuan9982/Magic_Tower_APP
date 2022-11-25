package com.example.magictower.slice;

import com.example.magictower.ResourceTable;
import com.example.magictower.model.Hero;
import com.example.magictower.model.Map_db;
import com.example.magictower.model.Now_select;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

import java.util.List;

public class SuccessSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_success);
        Button button=(Button) findComponentById(ResourceTable.Id_ret_but);
        String name=intent.getStringParam("name");
        DatabaseHelper helper=new DatabaseHelper(this);
        OrmContext o_ctx=helper.getOrmContext("database","database.db", Map_db.class);
        List<Hero> lst=o_ctx.query(o_ctx.where(Hero.class));
        int idx;
        if(name.equals("勇士"))idx=4;
        else if(name.equals("白衣武士"))idx=3;
        else if(name.equals("大主教"))idx=2;
        else idx=5;
        Hero hero=lst.get(idx-1);
        hero.setSelectable(1);
        o_ctx.update(hero);o_ctx.flush();
        button.setClickedListener(o->{
            terminate();
        });
        List<Now_select>selects=o_ctx.query(o_ctx.where(Now_select.class));
        for(Now_select s:selects){
            o_ctx.delete(s);o_ctx.flush();
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
