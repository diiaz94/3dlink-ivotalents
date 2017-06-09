package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;

/**
 * Created by VP50343 on 27/12/2016.
 */
public class CustomSwipeAdapterIndustryCastings extends PagerAdapter{
    private  int[] image_resources = {R.drawable.music_records_casting,R.drawable.music_records_casting,R.drawable.music_records_casting,R.drawable.music_records_casting,R.drawable.music_records_casting};
    private  String[] title_casting_resources = {"Título del casting","Título del casting2","Título del casting3","Título del casting4","Título del casting5"};
    private  String[] subtitle_casting_resources = {"Subtitulo","Desmuestra tu talento2","Desmuestra tu talento3","Subtitulo4","Subtitulo5"};
    private  String[] description_casting_resources = {"Subtitulo","Desmuestra tu talento2","Desmuestra tu talento3"};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private IvoTalentsApp mApp;

    private ImageButton ind_slide_left_arrow;
    private ImageButton ind_slide_right_arrow;
    public CustomSwipeAdapterIndustryCastings(Context ctx,ImageButton ind_slide_left_arrow,ImageButton ind_slide_right_arrow) {
        this.ctx = ctx;
        mApp = ((IvoTalentsApp) ctx);
        this.ind_slide_left_arrow = ind_slide_left_arrow;
        this.ind_slide_right_arrow = ind_slide_right_arrow;
    }
    @Override
    public int getCount() {
        return image_resources.length/2+image_resources.length%2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int first=position*2;
        int second=first+1;
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


        if (first>=0) {
            ind_layout_casting_1.setVisibility(View.VISIBLE);
            ind_category_casting_1.setText("MUSICA");
            ind_category_casting_1.setTypeface(FontApplyTask.getFontBold(ctx));

            ind_image_casting_1.setImageResource(image_resources[first]);

            ind_title_casting_1.setText(title_casting_resources[first]);
            ind_title_casting_1.setTypeface(FontApplyTask.getFontBold(ctx));

            ind_subtitle_casting_1.setText(subtitle_casting_resources[first]);
            ind_subtitle_casting_1.setTypeface(FontApplyTask.getFontBold(ctx));
        }else{
            ind_layout_casting_1.setVisibility(View.GONE);
        }
        if (second<image_resources.length-1) {
            ind_layout_casting_2.setVisibility(View.VISIBLE);
            ind_category_casting_2.setText("MUSICAA");
            ind_category_casting_2.setTypeface(FontApplyTask.getFontBold(ctx));

            ind_image_casting_2.setImageResource(image_resources[second]);

            ind_title_casting_2.setText(title_casting_resources[second]);
            ind_title_casting_2.setTypeface(FontApplyTask.getFontBold(ctx));

            ind_subtitle_casting_2.setText(subtitle_casting_resources[second]);
            ind_subtitle_casting_2.setTypeface(FontApplyTask.getFontBold(ctx));
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
