package com.threedlink.ivotalents.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.threedlink.ivotalents.DTO.Casting;
import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.ViewHolders.CastingViewHolder;
import com.threedlink.ivotalents.ViewHolders.RolEntityViewHolder;

import java.util.ArrayList;

/**
 * Created by diiaz94 on 17-01-2017.
 */
public class CustomRecentCastingsListAdapter extends BaseAdapter {
    private FragmentActivity activity;
    private Context ctx;
    private ArrayList<Casting> list;
    private IvoTalentsApp mApp;

    public CustomRecentCastingsListAdapter(FragmentActivity activity, ArrayList<Casting> list) {
        this.ctx = activity.getApplicationContext();
        this.list = list;
        mApp = ((IvoTalentsApp) ctx);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.list.size();
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
        CastingViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_casting_list, parent, false);
            holder = new CastingViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (CastingViewHolder) row.getTag();
        }
        // holder.imRolEntity.setImageResource(list.get(position).getImageId());
        holder.getTxtCategory().setText(list.get(position).getCategory());
        holder.getTxtDescription().setText(list.get(position).getDescription());
        holder.getTxtExpiration().setText(list.get(position).getExpiration());

        holder.getTxtCategory().setTypeface(mApp.getFont());
        holder.getTxtDescription().setTypeface(mApp.getFont());
        holder.getTxtExpiration().setTypeface(mApp.getFont());

        holder.getBtnSeeMore().setOnClickListener(new View.OnClickListener() {
            public void onClick(View popupView) {
                Fragment fragment = null;
                fragment = com.threedlink.ivotalents.Casting.newInstance("param1","param2");
                mApp.loadFragment(com.threedlink.ivotalents.Casting.newInstance("param1","param2"));
            }
        });;
        //Log.e("GETVIEW HEIGHT::", String.valueOf(row.getLayoutParams().height));

        return row;

    }
}
