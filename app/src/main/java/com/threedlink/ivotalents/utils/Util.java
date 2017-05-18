package com.threedlink.ivotalents.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.threedlink.ivotalents.R;

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

    public int indexOf(String[] array, String str) {
        for (int i=0;i<array.length;i++) {
            if (array[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }
}
