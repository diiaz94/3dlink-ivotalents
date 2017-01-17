package com.threedlink.ivotalents.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.threedlink.ivotalents.DTO.RolEntity;
import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.ViewHolders.RolEntityViewHolder;

import java.util.ArrayList;

/**
 * Created by diiaz94 on 17-01-2017.
 */

public class CustomRecentViewAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<RolEntity> list;
    private IvoTalentsApp mApp;

    public CustomRecentViewAdapter(Context ctx, ArrayList<RolEntity> list) {
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
            row = inflater.inflate(R.layout.item_rol_entity_recent, parent, false);
            holder = new RolEntityViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (RolEntityViewHolder) row.getTag();
        }
        holder.getImRolEntity().setImageResource(list.get(position).getImageId());
        holder.getTxtCategory().setText(list.get(position).getCategory());
        holder.getTxtName().setText(list.get(position).getName());
        holder.getTxtAbilitie().setText(list.get(position).getAbility());

        holder.getTxtCategory().setTypeface(mApp.getFont());
        holder.getTxtName().setTypeface(mApp.getFontBold());
        holder.getTxtAbilitie().setTypeface(mApp.getFont());
        return row;
    }
}