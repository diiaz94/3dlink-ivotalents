package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.dtos.MediaResource;

import java.util.List;

/**
 * Created by diiaz94 on 18-05-2017.
 */
public class ProfilePhotosGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<MediaResource> items;
    private final ImageLoader imageLoader;
    public ProfilePhotosGridAdapter(Context c, List<MediaResource> mList) {
        mContext = c;
        items = mList;
        imageLoader = ((IvoTalentsApp)c.getApplicationContext()).getImageLoader();
    }

    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public MediaResource getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_grid_image, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        if (items.get(position).getPath()!=null)
            imageLoader.displayImage(items.get(position).getPath(), holder.imageView);
        holder.txtName.setVisibility(View.GONE);
        //Log.e("GETVIEW HEIGHT::", String.valueOf(row.getLayoutParams().height));

        return row;
    }

    public static class ViewHolder  {
        ImageView imageView;
        ImageView playBtnVideo;
        ProgressBar progressBar;
        TextView txtName;

        public ViewHolder(View itemView) {
            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            this.txtName = (TextView) itemView.findViewById(R.id.name);
            this.playBtnVideo = (ImageView) itemView.findViewById(R.id.btn_play_video);
        }
    }
}
