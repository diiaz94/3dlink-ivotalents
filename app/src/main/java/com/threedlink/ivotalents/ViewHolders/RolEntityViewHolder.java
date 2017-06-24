package com.threedlink.ivotalents.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.dtos.RolEntity;

/**
 * Created by VP50343 on 17/01/2017.
 */
public class RolEntityViewHolder  extends RecyclerView.ViewHolder {

    RolEntity mItem;
    View mView;
    ImageView imRolEntity;
    TextView txtCategory;
    TextView txtName;
    TextView txtAbilitie;

    public void setItem(RolEntity mItem) {
        this.mItem = mItem;
    }

    public void setView(View mView) {
        this.mView = mView;
    }

    public RolEntity getItem() {
        return mItem;
    }

    public View getView() {
        return mView;
    }

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
        super(v);
        mView = v;

        this.imRolEntity = (ImageView) v.findViewById(R.id.rol_entity_image);
        this.txtCategory = (TextView) v.findViewById(R.id.rol_entity_category);
        this.txtName = (TextView) v.findViewById(R.id.rol_entity_name);
        this.txtAbilitie = (TextView) v.findViewById(R.id.rol_entity_abilitie);
    }
}
