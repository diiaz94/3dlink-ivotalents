package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;
import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.dtos.RolEntity;
import com.threedlink.ivotalents.viewholders.RolEntityViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diiaz94 on 12-06-2017.
 */


public class MyUserListRecyclerViewAdapter extends RecyclerView.Adapter<RolEntityViewHolder> {

    private final Context ctx;
    private List<RolEntity> items;
    private LayoutInflater inflater;
    private final ImageLoader imageLoader;


    public MyUserListRecyclerViewAdapter(Context ctx, ArrayList<RolEntity> items) {
        this.ctx = ctx;
        inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
        imageLoader = ((IvoTalentsApp)ctx.getApplicationContext()).getImageLoader();
    }


    @Override
    public RolEntityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_following, parent, false);
        return new RolEntityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RolEntityViewHolder holder, int position) {
        holder.setItem(items.get(position));
        // holder.beaconImage.s(mValues.get(position).id);

        imageLoader.displayImage(items.get(position).getImgUrl(), holder.getImRolEntity());
        holder.getTxtCategory().setText(items.get(position).getCategory());
        holder.getTxtName().setText(items.get(position).getName());
        holder.getTxtAbilitie().setText(items.get(position).getAbility());

        holder.getTxtCategory().setTypeface(FontApplyTask.getFont(ctx));
        holder.getTxtName().setTypeface(FontApplyTask.getFontBold(ctx));
        holder.getTxtAbilitie().setTypeface(FontApplyTask.getFont(ctx));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}

