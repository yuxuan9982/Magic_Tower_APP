package com.example.magictower.slice;

import com.example.magictower.MapAbility;
import com.example.magictower.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button start= (Button) findComponentById(ResourceTable.Id_begin);
        start.setClickedListener(new Component.ClickedListener() {//添加逻辑
            @Override
            public void onClick(Component component) {
                Intent intent1=new Intent();
                MapAbilitySlice slice=new MapAbilitySlice();
                present(slice,intent);
            }
        });
        Button get_save=(Button) findComponentById(ResourceTable.Id_get_save);
        get_save.setClickedListener(new Component.ClickedListener() {//添加逻辑
            @Override
            public void onClick(Component component) {

            }
        });
        Button info=(Button) findComponentById(ResourceTable.Id_info);
        info.setClickedListener(new Component.ClickedListener() {//添加逻辑
            @Override
            public void onClick(Component component) {

            }
        });
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
