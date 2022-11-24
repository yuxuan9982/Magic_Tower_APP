package com.example.magictower;

import com.example.magictower.slice.ShoppingSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Shopping extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ShoppingSlice.class.getName());
    }
}
