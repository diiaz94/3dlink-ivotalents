package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;

/**
 * Created by VP50343 on 26/12/2016.
 */
public class CustomSwipeAdapterArtist extends PagerAdapter {
    private  int[] image_resources = {R.drawable.juan_esteban,R.drawable.juan_esteban,R.drawable.juan_esteban};
    private  String[] name_resources = {"Juan Esteban","Juan Esteban2","Juan Esteban3",};
    private  String[] talent_resources = {"Cantante","Cantante2","Cantante3",};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private IvoTalentsApp mApp;
    public CustomSwipeAdapterArtist(Context ctx) {
        this.ctx = ctx;
        mApp = ((IvoTalentsApp) ctx);
    }

    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout_dashboard_artist,container,false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
        TextView nameView = (TextView) item_view.findViewById(R.id.name_artist_dashboard);
        TextView talentView = (TextView) item_view.findViewById(R.id.talent_artist_dashboard);
        imageView.setImageResource(image_resources[position]);
        nameView.setTypeface(FontApplyTask.getFontBold(ctx));
        nameView.setText(name_resources[position]);
        talentView.setTypeface(FontApplyTask.getFont(ctx));
        talentView.setText(talent_resources[position]);

        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
