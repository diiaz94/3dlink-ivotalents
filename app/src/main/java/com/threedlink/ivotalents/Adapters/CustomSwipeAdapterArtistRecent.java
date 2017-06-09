package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;

/**
 * Created by diiaz94 on 29-12-2016.
 */
public class CustomSwipeAdapterArtistRecent extends PagerAdapter {
    private static int GRID_SIZE = 4;
    private  int[] image_resources = {R.drawable.talent_1,R.drawable.talent_2,R.drawable.talent_3,R.drawable.talent_4,R.drawable.talent_4,R.drawable.talent_1,R.drawable.talent_2,R.drawable.talent_3};
    private  String[] category_resources = {"MUSICA","FOTOGRAFIA","MODELAJE","DISEÑO","MUSICA","FOTOGRAFIA","MODELAJE","DISEÑO"};
    private  String[] name_resources = {"Catherine Lewis","Catherine Lewis","Catherine Lewis","Catherine Lewis","Catherine Lewis2","Catherine Lewis","Catherine Lewis","Catherine Lewis"};
    private  String[] talent_resources = {"Compositora","Fotografo1","Modelo Editorial","Compositora","Compositora","Fotografo","Modelo Editorial","Compositora"};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private IvoTalentsApp mApp;

    public CustomSwipeAdapterArtistRecent(Context ctx) {
        this.ctx = ctx;
        mApp = ((IvoTalentsApp) ctx);

    }

    @Override
    public int getCount() {
        return image_resources.length/GRID_SIZE+image_resources.length%GRID_SIZE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int first = position*GRID_SIZE;

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout_dashboard_artist_recent,container,false);

        LinearLayout[] layouts_talents_dashboard_artist = new LinearLayout[GRID_SIZE];
        ImageView[] recent_image_talents = new ImageView[GRID_SIZE];
        TextView[] recent_category_talents = new TextView[GRID_SIZE];
        TextView[] recent_name_talents = new TextView[GRID_SIZE];
        TextView[] recent_talents = new TextView[GRID_SIZE];
        for (int i=0;i<GRID_SIZE;i++) {

            layouts_talents_dashboard_artist[i] = (LinearLayout) item_view.findViewById(mApp.getResourcebyname("layout_talents_dashboard_artist_"+String.valueOf(i+1)));
            recent_image_talents[i] = (ImageView) item_view.findViewById(mApp.getResourcebyname("recent_image_talent_"+String.valueOf(i+1)));
            recent_category_talents[i] = (TextView) item_view.findViewById(mApp.getResourcebyname("recent_category_talent_"+String.valueOf(i+1)));
            recent_name_talents[i] = (TextView) item_view.findViewById(mApp.getResourcebyname("recent_name_talent_"+String.valueOf(i+1)));
            recent_talents[i] = (TextView) item_view.findViewById(mApp.getResourcebyname("recent_talent_"+String.valueOf(i+1)));

            recent_image_talents[i].setImageResource(image_resources[first+i]);
            recent_category_talents[i].setText(category_resources[first+i]);
            recent_name_talents[i].setText(name_resources[first+i]);
            recent_talents[i].setText(talent_resources[first+i]);

            recent_category_talents[i].setTypeface(FontApplyTask.getFont(ctx));
            recent_name_talents[i].setTypeface(FontApplyTask.getFontBold(ctx));
            recent_category_talents[i].setTypeface(FontApplyTask.getFont(ctx));
            recent_talents[i].setTypeface(FontApplyTask.getFont(ctx));
        }


        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
