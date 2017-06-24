package com.threedlink.ivotalents.utils;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.activities.PhotoVisorActivity;
import com.threedlink.ivotalents.adapters.ProfilePhotosGridAdapter;
import com.threedlink.ivotalents.dtos.MediaResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diiaz94 on 17-05-2017.
 */
public class Util {

    private final int PUNTUATION_LIMIT = 5;

    public void setStarsPuntuation(LinearLayout parent, float points) {
        if (parent!=null){
            int integerPart = (int) points;
            ImageView template = (ImageView) parent.findViewById(R.id.template_star);
            for (int i = 0; i < PUNTUATION_LIMIT ; i++){
                ImageView iv = new ImageView(parent.getContext());
                iv.setLayoutParams(template.getLayoutParams());
                iv.setVisibility(View.VISIBLE);
                iv.setImageDrawable(template.getResources().getDrawable(i<integerPart?R.drawable.fill_star:R.drawable.empty_star));
                parent.addView(iv);
            }
        }
    }
    private  int selectedIndex;
    public void setPagination(LinearLayout parent, final ArrayList<MediaResource> list, final int realSize, final GridView gridView, final int LIMIT, final int simplePoint, final int selectedPoint) {
        if (parent!=null){
            selectedIndex=1;
            final ImageView template = (ImageView) parent.findViewById(R.id.template_point);
            for (int i = 0; i < list.size()/LIMIT ; i++){
                final ImageView iv = new ImageView(parent.getContext());
                iv.setTag(i+1);
                iv.setLayoutParams(template.getLayoutParams());
                iv.setVisibility(View.VISIBLE);
                iv.setImageDrawable(parent.getResources().getDrawable(i==0?selectedPoint:simplePoint));
                parent.addView(iv);
                iv.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View popupView) {
                        LinearLayout parent = (LinearLayout) popupView.getParent();
                        selectedIndex = (int) popupView.getTag();
                        for (int i= 0; i<parent.getChildCount() ; i++) {
                            ((ImageView)parent.getChildAt(i)).setImageDrawable(parent.getResources().getDrawable(i==selectedIndex?selectedPoint:simplePoint));
                        }
                        gridView
                                .setAdapter(
                                        new ProfilePhotosGridAdapter(parent.getContext()
                                                .getApplicationContext(),list.subList((selectedIndex-1)*LIMIT,(selectedIndex-1)*LIMIT+LIMIT)));
                    }
                });
            }

            gridView
                    .setAdapter(
                            new ProfilePhotosGridAdapter(parent.getContext()
                                    .getApplicationContext(),list.subList(0,LIMIT)));
            final Intent mIntent = new Intent(parent.getContext(),PhotoVisorActivity.class);
            mIntent.setFlags(mIntent.FLAG_ACTIVITY_NEW_TASK);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    int index = (position+((selectedIndex-1)*LIMIT));
                    Toast.makeText(parent.getContext(), "selected " + index,
                            Toast.LENGTH_SHORT).show();
                    if(index<realSize){
                        PhotoVisorActivity.mList = list.subList(0,realSize);
                        PhotoVisorActivity.mIndex = index;
                        parent.getContext().startActivity(mIntent);
                    }
                }
            });
        }
    }

    public int indexOf(String[] array, String str) {
        for (int i=0;i<array.length;i++) {
            if (array[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public static String formatDuration(int duration) {

        String minutes = String.valueOf((duration/60000));
        String seconds = String.valueOf((duration%60000) / 1000);

        return (minutes.length()>1?minutes:"0"+minutes) + " : " + (seconds.length()>1?seconds:"0"+seconds);
    }
}
