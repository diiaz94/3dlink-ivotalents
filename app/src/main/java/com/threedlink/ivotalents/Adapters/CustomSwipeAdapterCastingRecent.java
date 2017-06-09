package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;

/**
 * Created by diiaz94 on 29-12-2016.
 */
public class CustomSwipeAdapterCastingRecent extends PagerAdapter {
    private static int GRID_SIZE = 5;
    private  int[] image_resources = {R.drawable.talent_1,R.drawable.casting_2,R.drawable.casting_3,R.drawable.talent_1,R.drawable.talent_1,R.drawable.talent_1,R.drawable.casting_2,R.drawable.casting_3,R.drawable.talent_1,R.drawable.talent_1};
    private  String[] category_resources = {"MÚSICA","DISEÑO","MÚSICA","MÚSICA","DISEÑO","MÚSICA","DISEÑO","MÚSICA","MÚSICA","DISEÑO"};
    private  String[] title_resources = {"Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting"};
    private  String[] talent_resources = {"Compositora","Fotografo1","Modelo Editorial","Compositora","Compositora","Fotografo","Modelo Editorial","Compositora"};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private IvoTalentsApp mApp;
    public CustomSwipeAdapterCastingRecent(Context ctx) {
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
        View item_view = layoutInflater.inflate(R.layout.swipe_layout_dashboard_casting_recent,container,false);

        LinearLayout[] layouts_castings_dashboard_casting = new LinearLayout[GRID_SIZE];
        ImageView[] recent_image_castings = new ImageView[GRID_SIZE];
        TextView[] recent_category_castings = new TextView[GRID_SIZE];
        TextView[] recent_title_castings = new TextView[GRID_SIZE];
        Button[] buttons_read_more = new Button[GRID_SIZE];
        for (int i=0;i<GRID_SIZE;i++) {
            layouts_castings_dashboard_casting[i] = (LinearLayout) item_view.findViewById(mApp.getResourcebyname("layout_dashboard_casting_"+String.valueOf(i+1)));
            recent_image_castings[i] = (ImageView) item_view.findViewById(mApp.getResourcebyname("recent_image_casting_"+String.valueOf(i+1)));
            recent_category_castings[i] = (TextView) item_view.findViewById(mApp.getResourcebyname("recent_category_casting_"+String.valueOf(i+1)));
            recent_title_castings[i] = (TextView) item_view.findViewById(mApp.getResourcebyname("recent_title_casting_"+String.valueOf(i+1)));

            buttons_read_more[i] = (Button) item_view.findViewById(mApp.getResourcebyname("button_ver_casting_"+String.valueOf(i+1)));

            recent_image_castings[i].setImageResource(image_resources[first+i]);
            recent_category_castings[i].setText(category_resources[first+i]);
            recent_title_castings[i].setText(title_resources[first+i]);

            recent_category_castings[i].setTypeface(FontApplyTask.getFont(ctx));
            recent_title_castings[i].setTypeface(FontApplyTask.getFontBold(ctx));

            buttons_read_more[i].setTypeface(FontApplyTask.getFont(ctx));

        }


        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
