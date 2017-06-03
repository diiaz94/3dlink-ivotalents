package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;
import com.threedlink.ivotalents.dtos.RolEntity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by diiaz94 on 28-05-2017.
 */
public class CustomSwipeAdapterIndustryCastingView extends PagerAdapter {

    Context ctx;
    List<RolEntity> mList;
    private LayoutInflater layoutInflater;
    private final ImageLoader imageLoader;
    private final int ELEMENTSCOUNT = 3;

    public CustomSwipeAdapterIndustryCastingView(Context ctx,List<RolEntity> mList) {
        this.ctx = ctx;
        this.mList = mList;
        imageLoader = ((IvoTalentsApp)ctx.getApplicationContext()).getImageLoader();
    }

    @Override
    public int getCount() {
        return mList.size()/ELEMENTSCOUNT+mList.size()%ELEMENTSCOUNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int first=position*ELEMENTSCOUNT;
        int second=first+1;
        int third=second+1;
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout_industry_casting_view,container,false);

        LinearLayout item_1 = (LinearLayout) item_view.findViewById(R.id.item_1);
        LinearLayout item_2 = (LinearLayout) item_view.findViewById(R.id.item_2);
        LinearLayout item_3 = (LinearLayout) item_view.findViewById(R.id.item_3);

        ImageView rol_entity_image_1 = (ImageView) item_view.findViewById(R.id.rol_entity_image_1);
        ImageView rol_entity_image_2 = (ImageView) item_view.findViewById(R.id.rol_entity_image_2);
        ImageView rol_entity_image_3 = (ImageView) item_view.findViewById(R.id.rol_entity_image_3);

        TextView rol_entity_category_1 = (TextView) item_view.findViewById(R.id.rol_entity_category_1);
        TextView rol_entity_category_2 = (TextView) item_view.findViewById(R.id.rol_entity_category_2);
        TextView rol_entity_category_3 = (TextView) item_view.findViewById(R.id.rol_entity_category_3);

        TextView rol_entity_name_1 = (TextView) item_view.findViewById(R.id.rol_entity_name_1);
        TextView rol_entity_name_2 = (TextView) item_view.findViewById(R.id.rol_entity_name_2);
        TextView rol_entity_name_3 = (TextView) item_view.findViewById(R.id.rol_entity_name_3);

        TextView rol_entity_abilitie_1 = (TextView) item_view.findViewById(R.id.rol_entity_abilitie_1);
        TextView rol_entity_abilitie_2 = (TextView) item_view.findViewById(R.id.rol_entity_abilitie_2);
        TextView rol_entity_abilitie_3 = (TextView) item_view.findViewById(R.id.rol_entity_abilitie_3);


        if (first>=0) {
            item_1.setVisibility(View.VISIBLE);
            imageLoader.displayImage(mList.get(first).getImgUrl(),rol_entity_image_1);
            rol_entity_name_1.setText(mList.get(first).getName());
            rol_entity_abilitie_1.setText(mList.get(first).getAbility());
            rol_entity_category_1.setText(mList.get(first).getCategory());

            rol_entity_name_1.setTypeface(FontApplyTask.getFontBold(ctx));
            rol_entity_abilitie_1.setTypeface(FontApplyTask.getFont(ctx));
            rol_entity_category_1.setTypeface(FontApplyTask.getFont(ctx));
        }else{
            item_1.setVisibility(View.GONE);
        }
        if (second<mList.size()-1) {
            item_2.setVisibility(View.VISIBLE);
            imageLoader.displayImage(mList.get(second).getImgUrl(),rol_entity_image_2);
            rol_entity_name_2.setText(mList.get(second).getName());
            rol_entity_abilitie_2.setText(mList.get(second).getAbility());
            rol_entity_category_2.setText(mList.get(second).getCategory());

            rol_entity_name_2.setTypeface(FontApplyTask.getFontBold(ctx));
            rol_entity_abilitie_2.setTypeface(FontApplyTask.getFont(ctx));
            rol_entity_category_2.setTypeface(FontApplyTask.getFont(ctx));
        }else{
            item_2.setVisibility(View.GONE);
        }
        if (third<mList.size()) {
            item_3.setVisibility(View.VISIBLE);
            imageLoader.displayImage(mList.get(third).getImgUrl(),rol_entity_image_3);
            rol_entity_name_3.setText(mList.get(third).getName());
            rol_entity_abilitie_3.setText(mList.get(third).getAbility());
            rol_entity_category_3.setText(mList.get(third).getCategory());

            rol_entity_name_3.setTypeface(FontApplyTask.getFontBold(ctx));
            rol_entity_abilitie_3.setTypeface(FontApplyTask.getFont(ctx));
            rol_entity_category_3.setTypeface(FontApplyTask.getFont(ctx));
        }else{
            item_3.setVisibility(View.GONE);
        }


        container.addView(item_view);
        return item_view;
    }
}
