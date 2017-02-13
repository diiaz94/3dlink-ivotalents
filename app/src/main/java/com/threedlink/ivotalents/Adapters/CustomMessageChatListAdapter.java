package com.threedlink.ivotalents.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.threedlink.ivotalents.DTO.Message;
import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.ViewHolders.MessageViewChatHolder;
import com.threedlink.ivotalents.ViewHolders.MessageViewHolder;

import java.util.ArrayList;

/**
 * Created by vp50343 on 13/02/2017.
 */
public class CustomMessageChatListAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<Message> list;
    private IvoTalentsApp mApp;

    public CustomMessageChatListAdapter(Context ctx, ArrayList<Message> list) {
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
        MessageViewChatHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //row = inflater.inflate(list.get(position).getType().equalsIgnoreCase("enviado")layout.ite, parent, false);
            holder = new MessageViewChatHolder(row);
            row.setTag(holder);
        } else {
            holder = (MessageViewHolder) row.getTag();
        }
        // holder.imRolEntity.setImageResource(list.get(position).getImageId());
        holder.getTxtName().setText(list.get(position).getName());
        holder.getTxtResume().setText(list.get(position).getResume());

        holder.getTxtName().setTypeface(mApp.getFontBold());
        holder.getTxtResume().setTypeface(mApp.getFont());

        //Log.e("GETVIEW HEIGHT::", String.valueOf(row.getLayoutParams().height));

        return row;

    }
}
