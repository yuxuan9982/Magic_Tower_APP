package com.example.magictower;

import com.example.magictower.slice.Choose_HeroSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Choose_Hero extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(Choose_HeroSlice.class.getName());
    }
}
