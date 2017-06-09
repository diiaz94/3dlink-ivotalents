package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.threedlink.ivotalents.asynctasks.FontApplyTask;
import com.threedlink.ivotalents.dtos.Contact;
import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.viewholders.ContactViewHolder;

import java.util.ArrayList;

/**
 * Created by diiaz94 on 29-01-2017.
 */
public class CustomContactMessageListAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<Contact> list;
    private IvoTalentsApp mApp;

    public CustomContactMessageListAdapter(Context ctx, ArrayList<Contact> list) {
        this.ctx = ctx;
        this.list = list;
        mApp = ((IvoTalentsApp) ctx);
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
        ContactViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_contact_message_list, parent, false);
            holder = new ContactViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ContactViewHolder) row.getTag();
        }
        // holder.imRolEntity.setImageResource(list.get(position).getImageId());
        holder.getTxtName().setText(list.get(position).getName());
        holder.getTxtSector().setText(list.get(position).getSector());

        holder.getTxtName().setTypeface(FontApplyTask.getFontBold(ctx));
        holder.getTxtSector().setTypeface(FontApplyTask.getFont(ctx));

        //Log.e("GETVIEW HEIGHT::", String.valueOf(row.getLayoutParams().height));

        return row;

    }

}
