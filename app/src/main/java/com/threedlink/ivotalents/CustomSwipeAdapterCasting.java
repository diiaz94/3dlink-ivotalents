package com.threedlink.ivotalents;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by VP50343 on 26/12/2016.
 */
public class CustomSwipeAdapterCasting extends PagerAdapter {
    private  int[] image_resources = {R.drawable.casting_slide,R.drawable.casting_slide,R.drawable.casting_slide};
    private  String[] name_resources = {"Haz tu audición","Haz tu audición2","Haz tu audición3"};
    private  String[] talent_resources = {"Desmuestra tu talento","Desmuestra tu talento2","Desmuestra tu talento3"};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private IvoTalentsApp mApp;
    public CustomSwipeAdapterCasting(Context ctx) {
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
        nameView.setTypeface(mApp.getFontBold());
        nameView.setText(name_resources[position]);
        talentView.setTypeface(mApp.getFont());
        talentView.setText(talent_resources[position]);

        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
