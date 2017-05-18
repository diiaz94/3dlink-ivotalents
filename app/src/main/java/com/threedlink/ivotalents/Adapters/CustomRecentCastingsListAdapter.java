package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.threedlink.ivotalents.dtos.Casting;
import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.activities.UploadActivity;

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
    public CastingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_casting_list, parent, false);

        return new CastingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomRecentCastingsListAdapter.CastingViewHolder holder, int position) {
        holder.imCasting.setImageResource(items.get(position).getImageId());
        holder.txtExpiration.setText(items.get(position).getExpiration());
        holder.txtCategory.setText(items.get(position).getCategory());
        holder.txtDescription.setText(items.get(position).getDescription());
        holder.btnSeeMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View popupView) {
                Log.d("onBindViewHolder", "onClick ");
                Intent intent = new Intent(context,UploadActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
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
