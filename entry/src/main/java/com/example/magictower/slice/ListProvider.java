package com.example.magictower.slice;

import com.example.magictower.ResourceTable;
import com.example.magictower.model.Hero;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.List;

public class ListProvider extends BaseItemProvider {
    List<Hero> heroList;
    Context ctx;
    ClickedListener listener;
    public void setListener(ClickedListener listener){this.listener=listener;}
    public static interface  ClickedListener{
        void click(int pos);
    }
    public ListProvider(List<Hero> imgList,Context ctx){
        this.heroList =imgList;this.ctx=ctx;
    }
    @Override
    public int getCount() {
        return heroList.size();
    }

    @Override
    public Object getItem(int i) {
        return heroList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        DependentLayout dl=(DependentLayout) LayoutScatter.getInstance(ctx).parse(ResourceTable.Layout_list_item,null,false);
        Image img=(Image) dl.findComponentById(ResourceTable.Id_list_img);
        img.setImageAndDecodeBounds(heroList.get(i).getHead());
        img.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                listener.click(i);
            }
        });
        Text txt1=(Text) dl.findComponentById(ResourceTable.Id_list_health);
        Text txt2=(Text) dl.findComponentById(ResourceTable.Id_list_atk);
        Text txt3=(Text) dl.findComponentById(ResourceTable.Id_list_name);
        txt1.setText("血量:"+String.valueOf(heroList.get(i).getHealth()));
        txt2.setText("攻击:"+String.valueOf(heroList.get(i).getAttack()+"防御:"+String.valueOf(heroList.get(i).getDefence())));
        txt3.setText(heroList.get(i).getName());

        dl.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                listener.click(i);
            }
        });
        return dl;
    }
}
