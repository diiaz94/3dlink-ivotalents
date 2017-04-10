package com.threedlink.ivotalents.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.threedlink.ivotalents.R;

import java.util.List;

/**
 * Created by diiaz94 on 06-04-2017.
 */
public class CustomGalleryAdapter extends RecyclerView.Adapter<CustomGalleryAdapter.ViewHolder> {

    private static final String TAG = "CustomGalleryAdapter" ;
    private List<String> items;

    private Context context;

    private DisplayImageOptions options;

    public CustomGalleryAdapter(Context context, List<String> cameraImages) {
        this.context = context;
        items = cameraImages;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .resetViewBeforeLoading(true)
                .build();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_grid_image, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String name = "Nombre no disponible";
        String ext = null;
        try {
            String[] parentArr = items.get(position).split("/");
            name = parentArr[parentArr.length-1];
            String[] arr = name.split("\\.");
            ext = arr[arr.length-1];
        }catch (Exception e){
            Log.e(TAG,"Error");
        }
        holder.txtName.setText(name);

        if(ext!=null && ext.equalsIgnoreCase("mp4")){
            holder.playBtnVideo.setVisibility(View.VISIBLE);
        }else if (ext!=null && ext.equalsIgnoreCase("mp3")||ext.equalsIgnoreCase("m4a")){
            String uri = "@drawable/ic_audio";  // where myresource (without the extension) is the file
            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            Drawable res = context.getResources().getDrawable(imageResource);
            holder.imageView.setImageDrawable(res);
            holder.playBtnVideo.setVisibility(View.GONE);
        }else {
            holder.playBtnVideo.setVisibility(View.GONE);
            ImageLoader.getInstance()
                .displayImage(items.get(position), holder.imageView, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        holder.progressBar.setProgress(0);
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                },  new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        holder.progressBar.setProgress(Math.round(100.0f * current / total));
                    }
                });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView imageView;
        ImageView playBtnVideo;
        ProgressBar progressBar;
        TextView txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            this.txtName = (TextView) itemView.findViewById(R.id.name);
            this.playBtnVideo = (ImageView) itemView.findViewById(R.id.btn_play_video);
        }
    }
}
