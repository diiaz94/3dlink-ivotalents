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
 * Created by VP50343 on 27/12/2016.
 */
public class CustomSwipeAdapterIndustryCastings extends PagerAdapter{
    private  int[] image_resources = {R.drawable.music_records_casting,R.drawable.music_records_casting,R.drawable.music_records_casting};
    private  String[] title_casting_resources = {"Título del casting","Título del casting2","Título del casting3"};
    private  String[] subtitle_casting_resources = {"Subtitulo","Desmuestra tu talento2","Desmuestra tu talento3"};
    private  String[] description_casting_resources = {"Subtitulo","Desmuestra tu talento2","Desmuestra tu talento3"};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private IvoTalentsApp mApp;
    public CustomSwipeAdapterIndustryCastings(Context ctx) {
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
        View item_view = layoutInflater.inflate(R.layout.swipe_layout_industry_castings,container,false);
        LinearLayout ind_layout_casting_1 = (LinearLayout) item_view.findViewById(R.id.ind_layout_casting_1);
        TextView ind_category_casting_1 = (TextView) item_view.findViewById(R.id.ind_category_casting_1);
        ImageView ind_image_casting_1 = (ImageView) item_view.findViewById(R.id.ind_image_casting_1);
        TextView ind_title_casting_1 = (TextView) item_view.findViewById(R.id.ind_title_casting_1);
        TextView ind_subtitle_casting_1 = (TextView) item_view.findViewById(R.id.ind_subtitle_casting_1);
        TextView ind_description_casting_1 = (TextView) item_view.findViewById(R.id.ind_description_casting_1);
        LinearLayout ind_layout_casting_2 = (LinearLayout) item_view.findViewById(R.id.ind_layout_casting_2);
        TextView ind_category_casting_2 = (TextView) item_view.findViewById(R.id.ind_category_casting_2);
        ImageView ind_image_casting_2 = (ImageView) item_view.findViewById(R.id.ind_image_casting_2);
        TextView ind_title_casting_2 = (TextView) item_view.findViewById(R.id.ind_title_casting_2);
        TextView ind_subtitle_casting_2 = (TextView) item_view.findViewById(R.id.ind_subtitle_casting_2);
        TextView ind_description_casting_2 = (TextView) item_view.findViewById(R.id.ind_description_casting_2);

        if (position>0) {
            ind_layout_casting_1.setVisibility(View.VISIBLE);
            ind_category_casting_1.setText("MUSICA");
            ind_category_casting_1.setTypeface(mApp.getFontBold());

            ind_image_casting_1.setImageResource(image_resources[position]);

            ind_title_casting_1.setText(title_casting_resources[position]);
            ind_title_casting_1.setTypeface(mApp.getFontBold());

            ind_subtitle_casting_1.setText(subtitle_casting_resources[position]);
            ind_subtitle_casting_1.setTypeface(mApp.getFontBold());
        }else{
            ind_layout_casting_1.setVisibility(View.GONE);
        }
        if (position+1<getCount()-1) {
            ind_layout_casting_2.setVisibility(View.VISIBLE);
            ind_category_casting_2.setText("MUSICA");
            ind_category_casting_2.setTypeface(mApp.getFontBold());

            ind_image_casting_2.setImageResource(image_resources[position+1]);

            ind_title_casting_2.setText(title_casting_resources[position+1]);
            ind_title_casting_2.setTypeface(mApp.getFontBold());

            ind_subtitle_casting_2.setText(subtitle_casting_resources[position+1]);
            ind_subtitle_casting_2.setTypeface(mApp.getFontBold());
        }else{
            ind_layout_casting_2.setVisibility(View.GONE);
        }

        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
