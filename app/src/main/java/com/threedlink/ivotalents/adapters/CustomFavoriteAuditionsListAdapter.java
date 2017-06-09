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
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.dtos.RolEntity;

import java.util.List;


/**
 * Created by diiaz94 on 28-05-2017.
 */
public class CustomFavoriteAuditionsListAdapter extends RecyclerView.Adapter<CustomFavoriteAuditionsListAdapter.AuditionViewHolder> {

    private Context context;
    private List<RolEntity> items;
    private IvoTalentsApp mApp;
    private final ImageLoader imageLoader;
    public CustomFavoriteAuditionsListAdapter(Context context, List<RolEntity> items) {
        this.context = context;
        this.items = items;
        mApp = ((IvoTalentsApp) context);
        imageLoader = ((IvoTalentsApp)context.getApplicationContext()).getImageLoader();
    }


    @Override
    public AuditionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_favorite_audition_list, parent, false);

        return new AuditionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomFavoriteAuditionsListAdapter.AuditionViewHolder holder, int position) {
        imageLoader.displayImage(items.get(position).getImgUrl(), holder.imCasting);
        holder.txtName.setText(items.get(position).getName());
        holder.txtCategory.setText(items.get(position).getCategory());
        holder.txtAbilitie.setText(items.get(position).getAbility());
        holder.btnSeeMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View popupView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class AuditionViewHolder  extends RecyclerView.ViewHolder {

        ImageView imCasting;
        TextView txtCategory;
        TextView txtName;
        TextView txtAbilitie;
        TextView btnSeeMore;

        public AuditionViewHolder(View v) {
            super(v);
            this.imCasting = (ImageView) v.findViewById(R.id.casting_image);
            this.txtCategory = (TextView) v.findViewById(R.id.casting_category);
            this.txtName = (TextView) v.findViewById(R.id.rol_entity_name);
            this.txtAbilitie = (TextView) v.findViewById(R.id.rol_entity_abilitie);
            this.btnSeeMore = (Button) v.findViewById(R.id.casting_btn_detail);
        }
    }
}
