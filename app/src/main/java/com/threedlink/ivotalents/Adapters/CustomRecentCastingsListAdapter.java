package com.threedlink.ivotalents.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.threedlink.ivotalents.DTO.Casting;
import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.UploadActivity;
import com.threedlink.ivotalents.UploadResource;
import com.threedlink.ivotalents.ViewHolders.CastingViewHolder;
import com.threedlink.ivotalents.ViewHolders.RolEntityViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diiaz94 on 17-01-2017.
 */
public class CustomRecentCastingsListAdapter extends RecyclerView.Adapter<CustomRecentCastingsListAdapter.CastingViewHolder> {
    private Context context;
    private List<Casting> items;
    private IvoTalentsApp mApp;

    public CustomRecentCastingsListAdapter(Context context, List<Casting> items) {
        this.context = context;
        this.items = items;
        mApp = ((IvoTalentsApp) context);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(CustomRecentCastingsListAdapter.CastingViewHolder itemsViewHolder, int i) {
        itemsViewHolder.imCasting.setImageResource(items.get(i).getImageId());
        itemsViewHolder.txtExpiration.setText(items.get(i).getExpiration());
        itemsViewHolder.txtCategory.setText(items.get(i).getCategory());
        itemsViewHolder.txtDescription.setText(items.get(i).getDescription());
        itemsViewHolder.btnSeeMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View popupView) {
                Log.d("onBindViewHolder", "onClick ");
                Intent intent = new Intent(context,UploadActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public CastingViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_casting_list, viewGroup, false);

        return new CastingViewHolder(itemView);
    }

    public static class CastingViewHolder  extends RecyclerView.ViewHolder {

        ImageView imCasting;
        TextView txtCategory;
        TextView txtExpiration;
        TextView txtDescription;
        TextView btnSeeMore;

        public CastingViewHolder(View v) {
            super(v);
            this.imCasting = (ImageView) v.findViewById(R.id.casting_image);
            this.txtCategory = (TextView) v.findViewById(R.id.casting_category);
            this.txtDescription = (TextView) v.findViewById(R.id.casting_description);
            this.txtExpiration = (TextView) v.findViewById(R.id.casting_expiration);
            this.btnSeeMore = (Button) v.findViewById(R.id.casting_btn_detail);
        }
    }
}
