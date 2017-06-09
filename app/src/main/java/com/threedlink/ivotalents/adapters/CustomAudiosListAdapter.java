package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;
import com.threedlink.ivotalents.dtos.MediaResource;
import com.threedlink.ivotalents.viewholders.AudiosViewHolder;

import java.util.ArrayList;

/**
 * Created by diiaz94 on 02-06-2017.
 */
public class CustomAudiosListAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<MediaResource> list;
    private IvoTalentsApp mApp;
    private int btnPlayDrawable;
    private int btnPauseDrawable;

    public CustomAudiosListAdapter(Context ctx, ArrayList<MediaResource> list,int btnPlayDrawable,int btnPauseDrawable) {
        this.ctx = ctx;
        this.list = list;
        this.btnPlayDrawable = btnPlayDrawable;
        this.btnPauseDrawable = btnPauseDrawable;
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
        AudiosViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.player_audio_view, parent, false);
            holder = new AudiosViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (AudiosViewHolder) row.getTag();
        }
        holder.getRecordReproductor().setBackgroundResource(position%2==0?R.color.ivo_gray_grid:R.color.ivo_gray_light);

        holder.getPlayRecordBtn().setBackgroundResource(btnPlayDrawable);
        holder.getPauseRecordBtn().setBackgroundResource(btnPauseDrawable);

        holder.getTxtName().setText(list.get(position).getName());
        holder.getTxtName().setTypeface(FontApplyTask.getFontBold(ctx));


        final AudiosViewHolder finalHolder = holder;
        holder.getPlayRecordBtn().setOnClickListener(new View.OnClickListener() {
            public void onClick(View popupView) {
                finalHolder.getPlayRecordBtn().setVisibility(View.GONE);
                finalHolder.getPauseRecordBtn().setVisibility(View.VISIBLE);
            }
        });
        holder.getPauseRecordBtn().setOnClickListener(new View.OnClickListener() {
            public void onClick(View popupView) {
                finalHolder.getPauseRecordBtn().setVisibility(View.GONE);
                finalHolder.getPlayRecordBtn().setVisibility(View.VISIBLE);
            }
        });

        return row;

    }


}
