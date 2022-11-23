package com.example.magictower;

import com.example.magictower.slice.MapAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MapAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MapAbilitySlice.class.getName());
    }
}
