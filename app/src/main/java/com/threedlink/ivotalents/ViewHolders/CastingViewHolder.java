package com.threedlink.ivotalents.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.threedlink.ivotalents.R;

/**
 * Created by diiaz94 on 17-01-2017.
 */
public class CastingViewHolder {
    ImageView imCasting;
    TextView txtCategory;
    TextView txtExpiration;
    TextView txtDescription;
    TextView btnSeeMore;

    public TextView getBtnSeeMore() {
        return btnSeeMore;
    }

    public void setBtnSeeMore(TextView btnSeeMore) {
        this.btnSeeMore = btnSeeMore;
    }

    public CastingViewHolder(View v) {
        this.imCasting = (ImageView) v.findViewById(R.id.casting_image);
        this.txtCategory = (TextView) v.findViewById(R.id.casting_category);
        this.txtDescription = (TextView) v.findViewById(R.id.casting_description);
        this.txtExpiration = (TextView) v.findViewById(R.id.casting_expiration);
        this.btnSeeMore = (Button) v.findViewById(R.id.casting_btn_detail);
    }
    public ImageView getImCasting() {
        return imCasting;
    }

    public void setImCasting(ImageView imCasting) {
        this.imCasting = imCasting;
    }

    public TextView getTxtCategory() {
        return txtCategory;
    }

    public void setTxtCategory(TextView txtCategory) {
        this.txtCategory = txtCategory;
    }

    public TextView getTxtExpiration() {
        return txtExpiration;
    }

    public void setTxtExpiration(TextView txtExpiration) {
        this.txtExpiration = txtExpiration;
    }

    public TextView getTxtDescription() {
        return txtDescription;
    }

    public void setTxtDescription(TextView txtDescription) {
        this.txtDescription = txtDescription;
    }
}
