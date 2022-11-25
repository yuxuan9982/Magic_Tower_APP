package com.example.magictower;

import com.example.magictower.slice.SuccessSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Success extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SuccessSlice.class.getName());
    }
}
