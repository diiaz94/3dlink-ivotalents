package com.threedlink.ivotalents;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.View.VISIBLE;

/**
 * Created by diiaz94 on 29-12-2016.
 */
public class CustomSwipeAdapterCastingRecent extends PagerAdapter {

    private  int[] image_resources = {R.drawable.talent_1,R.drawable.casting_2,R.drawable.casting_3,R.drawable.talent_1,R.drawable.talent_1,R.drawable.talent_1,R.drawable.casting_2,R.drawable.casting_3,R.drawable.talent_1,R.drawable.talent_1};
    private  String[] category_resources = {"MÚSICA","DISEÑO","MÚSICA","MÚSICA","DISEÑO","MÚSICA","DISEÑO","MÚSICA","MÚSICA","DISEÑO"};
    private  String[] title_resources = {"Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting","Título del Casting"};
    private  String[] talent_resources = {"Compositora","Fotografo1","Modelo Editorial","Compositora","Compositora","Fotografo","Modelo Editorial","Compositora"};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private IvoTalentsApp mApp;
    private LinearLayout paginator_swipe_casting_recent;
    public CustomSwipeAdapterCastingRecent(Context ctx,LinearLayout paginator_swipe_casting_recent) {
        this.ctx = ctx;
        this.paginator_swipe_casting_recent = paginator_swipe_casting_recent;
        mApp = ((IvoTalentsApp) ctx);

    }

    @Override
    public int getCount() {
        return image_resources.length/5+image_resources.length%5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int first = position*5;

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout_dashboard_casting_recent,container,false);

        LinearLayout[] layouts_castings_dashboard_casting = new LinearLayout[5];
        ImageView[] recent_image_castings = new ImageView[5];
        TextView[] recent_category_castings = new TextView[5];
        TextView[] recent_title_castings = new TextView[5];
        for (int i=0;i<5;i++) {
            layouts_castings_dashboard_casting[i] = (LinearLayout) item_view.findViewById(mApp.getResourcebyname("layout_dashboard_casting_"+String.valueOf(i+1)));
            recent_image_castings[i] = (ImageView) item_view.findViewById(mApp.getResourcebyname("recent_image_casting_"+String.valueOf(i+1)));
            recent_category_castings[i] = (TextView) item_view.findViewById(mApp.getResourcebyname("recent_category_casting_"+String.valueOf(i+1)));
            recent_title_castings[i] = (TextView) item_view.findViewById(mApp.getResourcebyname("recent_title_casting_"+String.valueOf(i+1)));

            recent_image_castings[i].setImageResource(image_resources[first+i]);
            recent_category_castings[i].setText(category_resources[first+i]);
            recent_title_castings[i].setText(title_resources[first+i]);

            recent_category_castings[i].setTypeface(mApp.getFont());
            recent_title_castings[i].setTypeface(mApp.getFontBold());

        }

        for ( int i = 0; i < paginator_swipe_casting_recent.getChildCount(); i++) {
            ImageButton b =(ImageButton) paginator_swipe_casting_recent.getChildAt(i);
            if(b.getVisibility()==VISIBLE) {
                b.setImageResource(i == position + 1? R.drawable.selected_point_green : R.drawable.simple_point_white);
            }
        }
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
