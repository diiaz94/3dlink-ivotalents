package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.dtos.RolEntity;
import com.threedlink.ivotalents.R;

import java.util.List;

/**
 * Created by diiaz94 on 17-01-2017.
 */

public class CustomRecentViewAdapter extends RecyclerView.Adapter<CustomRecentViewAdapter.EntityViewHolder> {
    private List<RolEntity> items;
    private Context context;
    private View.OnClickListener btnListener;
    private final ImageLoader imageLoader;

    public CustomRecentViewAdapter(Context context, List<RolEntity> items) {
        this.context = context;
        this.items= items;
        imageLoader = ((IvoTalentsApp)context.getApplicationContext()).getImageLoader();
    }
    public CustomRecentViewAdapter(Context context, List<RolEntity> items,View.OnClickListener btnListener) {
        this.context = context;
        this.items= items;
        this.btnListener = btnListener;
        imageLoader = ((IvoTalentsApp)context.getApplicationContext()).getImageLoader();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(EntityViewHolder itemsViewHolder, int i) {
        imageLoader.displayImage(items.get(i).getImgUrl(), itemsViewHolder.imRolEntity);
        itemsViewHolder.imRolEntity.setImageResource(items.get(i).getImageId());
        itemsViewHolder.txtAbilitie.setText(items.get(i).getAbility());
        itemsViewHolder.txtCategory.setText(items.get(i).getCategory());
        itemsViewHolder.txtName.setText(items.get(i).getName());
        if(btnListener!=null){
            itemsViewHolder.seeAudition.setVisibility(View.VISIBLE);
            itemsViewHolder.seeAudition.setOnClickListener(btnListener);
        }
    }

    @Override
    public EntityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_rol_entity_recent, viewGroup, false);

        return new EntityViewHolder(itemView);
    }

    public static class EntityViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imRolEntity;
        protected TextView txtCategory;
        protected TextView txtName;
        protected TextView txtAbilitie;
        protected Button seeAudition;

        public EntityViewHolder(View v) {
            super(v);
            this.imRolEntity = (ImageView) v.findViewById(R.id.rol_entity_image);
            this.txtCategory = (TextView) v.findViewById(R.id.rol_entity_category);
            this.txtName = (TextView) v.findViewById(R.id.rol_entity_name);
            this.txtAbilitie = (TextView) v.findViewById(R.id.rol_entity_abilitie);
            this.seeAudition = (Button) v.findViewById(R.id.btn_see_audition);
        }
    }
}