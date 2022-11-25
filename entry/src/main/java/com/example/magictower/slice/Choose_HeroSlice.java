package com.example.magictower.slice;

import com.example.magictower.MapAbility;
import com.example.magictower.ResourceTable;
import com.example.magictower.model.Hero;
import com.example.magictower.model.Map_db;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;
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
                MapAbilitySlice slice=new MapAbilitySlice();
                Intent intent1=new Intent();
                intent1.setParam("name",hero.getName());
                present(slice,intent1);terminate();
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
