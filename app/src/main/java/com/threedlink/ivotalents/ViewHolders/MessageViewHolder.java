package com.threedlink.ivotalents.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.threedlink.ivotalents.R;

/**
 * Created by diiaz94 on 23-01-2017.
 */
public class MessageViewHolder {

    ImageView imAutor;

    TextView txtName;

    TextView txtResume;

    public ImageView getImAutor() {
        return imAutor;
    }

    public void setImAutor(ImageView imAutor) {
        this.imAutor = imAutor;
    }

    public TextView getTxtName() {
        return txtName;
    }

    public void setTxtName(TextView txtName) {
        this.txtName = txtName;
    }

    public TextView getTxtResume() {
        return txtResume;
    }

    public void setTxtResume(TextView txtResume) {
        this.txtResume = txtResume;
    }

    public MessageViewHolder(View v) {
        this.imAutor = (ImageView) v.findViewById(R.id.message_item_author);
        this.txtName = (TextView) v.findViewById(R.id.message_item_name);
        this.txtResume = (TextView) v.findViewById(R.id.message_item_resume);
    }
}
