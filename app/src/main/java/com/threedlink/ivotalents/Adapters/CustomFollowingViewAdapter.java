package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.threedlink.ivotalents.asynctasks.FontApplyTask;
import com.threedlink.ivotalents.dtos.RolEntity;
import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.viewholders.RolEntityViewHolder;

import java.util.ArrayList;

/**
 * Created by diiaz94 on 17-01-2017.
 */

public class CustomFollowingViewAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<RolEntity> list;
    private IvoTalentsApp mApp;
    public CustomFollowingViewAdapter(Context ctx, ArrayList<RolEntity> list) {
        this.ctx = ctx;
        this.list = list;
        mApp = ((IvoTalentsApp) ctx);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RolEntityViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_following, parent, false);
            holder = new RolEntityViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (RolEntityViewHolder) row.getTag();
        }
       // holder.imRolEntity.setImageResource(list.get(position).getImageId());
        holder.getTxtCategory().setText(list.get(position).getCategory());
        holder.getTxtName().setText(list.get(position).getName());
        holder.getTxtAbilitie().setText(list.get(position).getAbility());

        holder.getTxtCategory().setTypeface(FontApplyTask.getFont(ctx));
        holder.getTxtName().setTypeface(FontApplyTask.getFontBold(ctx));
        holder.getTxtAbilitie().setTypeface(FontApplyTask.getFont(ctx));

        //Log.e("GETVIEW HEIGHT::", String.valueOf(row.getLayoutParams().height));

        return row;
    }
}