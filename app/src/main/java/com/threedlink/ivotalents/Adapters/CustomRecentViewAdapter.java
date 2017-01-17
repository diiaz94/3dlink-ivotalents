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

    class ViewHolder {
        ImageView imRolEntity;
        TextView txtCategory;
        TextView txtName;
        TextView txtAbilitie;

        public ViewHolder(View v) {
            this.imRolEntity = (ImageView) v.findViewById(R.id.rol_entity_image);
            this.txtCategory = (TextView) v.findViewById(R.id.rol_entity_category);
            this.txtName = (TextView) v.findViewById(R.id.rol_entity_name);
            this.txtAbilitie = (TextView) v.findViewById(R.id.rol_entity_abilitie);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_rol_entity_recent, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.imRolEntity.setImageResource(list.get(position).getImageId());
        holder.txtCategory.setText(list.get(position).getCategory());
        holder.txtName.setText(list.get(position).getName());
        holder.txtAbilitie.setText(list.get(position).getAbility());
        //Log.e("GETVIEW HEIGHT::", String.valueOf(row.getLayoutParams().height));
        holder.txtCategory.setTypeface(mApp.getFont());
        holder.txtName.setTypeface(mApp.getFontBold());
        holder.txtAbilitie.setTypeface(mApp.getFont());
        return row;
    }
}