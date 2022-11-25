package com.example.magictower.slice;

import com.example.magictower.MapAbility;
import com.example.magictower.ResourceTable;
import com.example.magictower.model.Hero;
import com.example.magictower.model.Map_db;
import com.example.magictower.model.Now_select;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

import java.util.List;

public class Choose_HeroSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_choose_Hero);
        DatabaseHelper helper=new DatabaseHelper(this);

        OrmContext o_ctx= helper.getOrmContext("database","database.db", Map_db.class);

        List<Hero > heroList=o_ctx.query(o_ctx.where(Hero.class));
        ListContainer lcImgList = (ListContainer) findComponentById(ResourceTable.Id_list1);

//provider needed
        ListProvider provider= new ListProvider(heroList,this);
        provider.setListener(new ListProvider.ClickedListener() {
            @Override
            public void click(int pos) {
                Hero hero=heroList.get(pos);
                if(hero.getSelectable()==1){
                    MapAbilitySlice slice=new MapAbilitySlice();
                    Intent intent1=new Intent();
                    intent1.setParam("name",hero.getName());
                    present(slice,intent1);
                    List<Now_select> now_selects=o_ctx.query(o_ctx.where(Now_select.class));
                    if(now_selects.size()==0){
                        Now_select select=new Now_select();
                        select.setNowchose(pos);
                        o_ctx.insert(select);o_ctx.flush();
                    }else{
                        Now_select select=now_selects.get(0);
                        select.setNowchose(pos);
                        o_ctx.update(select);o_ctx.flush();
                    }
                    terminate();
                }else{
                    ToastDialog td=new ToastDialog(getContext());
                    td.setText("你还没有解锁这个人物，先通关吧！");
                    td.show();
                }

            }
        });
        lcImgList.setItemProvider(provider);
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
