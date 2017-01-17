package com.threedlink.ivotalents.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.threedlink.ivotalents.R;

/**
 * Created by VP50343 on 17/01/2017.
 */
public class RolEntityViewHolder {
    ImageView imRolEntity;
    TextView txtCategory;
    TextView txtName;
    TextView txtAbilitie;

    public ImageView getImRolEntity() {
        return imRolEntity;
    }

    public void setImRolEntity(ImageView imRolEntity) {
        this.imRolEntity = imRolEntity;
    }

    public TextView getTxtCategory() {
        return txtCategory;
    }

    public void setTxtCategory(TextView txtCategory) {
        this.txtCategory = txtCategory;
    }

    public TextView getTxtName() {
        return txtName;
    }

    public void setTxtName(TextView txtName) {
        this.txtName = txtName;
    }

    public TextView getTxtAbilitie() {
        return txtAbilitie;
    }

    public void setTxtAbilitie(TextView txtAbilitie) {
        this.txtAbilitie = txtAbilitie;
    }

    public RolEntityViewHolder(View v) {
        this.imRolEntity = (ImageView) v.findViewById(R.id.rol_entity_image);
        this.txtCategory = (TextView) v.findViewById(R.id.rol_entity_category);
        this.txtName = (TextView) v.findViewById(R.id.rol_entity_name);
        this.txtAbilitie = (TextView) v.findViewById(R.id.rol_entity_abilitie);
    }
}
