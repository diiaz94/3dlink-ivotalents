package com.threedlink.ivotalents.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;
import com.threedlink.ivotalents.dtos.Casting;
import com.threedlink.ivotalents.viewholders.MessageViewHolder;

import java.util.ArrayList;

/**
 * Created by diiaz94 on 28-02-2017.
 */
public class CustomParticipationsListAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<Casting> list;
    private IvoTalentsApp mApp;

    public CustomParticipationsListAdapter(Context ctx, ArrayList<Casting> list) {
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
        MessageViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_received_message_list, parent, false);
            holder = new MessageViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (MessageViewHolder) row.getTag();
        }
        // holder.imRolEntity.setImageResource(list.get(position).getImageId());
        holder.getTxtName().setText(list.get(position).getCategory());
        holder.getTxtResume().setText(list.get(position).getDescription());

        holder.getTxtName().setTypeface(FontApplyTask.getFontBold(ctx));
        holder.getTxtResume().setTypeface(FontApplyTask.getFont(ctx));

        //Log.e("GETVIEW HEIGHT::", String.valueOf(row.getLayoutParams().height));

        return row;

    }
}
