package com.threedlink.ivotalents.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.threedlink.ivotalents.R;

/**
 * Created by diiaz94 on 29-01-2017.
 */
public class ContactViewHolder {
    ImageView imContact;

    TextView txtName;

    TextView txtSector;

    public ImageView getImAutor() {
        return imContact;
    }

    public void setImContact(ImageView imContact) {
        this.imContact = imContact;
    }

    public TextView getTxtName() {
        return txtName;
    }

    public void setTxtName(TextView txtName) {
        this.txtName = txtName;
    }

    public TextView getTxtSector() {
        return txtSector;
    }

    public void setTxtSector(TextView txtSector) {
        this.txtSector = txtSector;
    }

    public ContactViewHolder(View v) {
        this.imContact = (ImageView) v.findViewById(R.id.contact_item_image);
        this.txtName = (TextView) v.findViewById(R.id.contact_item_name);
        this.txtSector = (TextView) v.findViewById(R.id.contact_item_sector);
    }
}
