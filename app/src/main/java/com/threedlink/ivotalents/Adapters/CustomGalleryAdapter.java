package com.threedlink.ivotalents.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.threedlink.ivotalents.dtos.MediaResource;
import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diiaz94 on 06-04-2017.
 */
public class CustomGalleryAdapter extends RecyclerView.Adapter<CustomGalleryAdapter.ViewHolder> implements View.OnClickListener{

    private static final String TAG = "CustomGalleryAdapter" ;
    private final ImageLoader imageLoader;
    private List<MediaResource> items;

    private Context context;
    private View.OnClickListener listener;
    public void swap(ArrayList<MediaResource> items){

        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
    public CustomGalleryAdapter(Context context, List<MediaResource> items) {
        this.context = context;
        this.items = items;
        imageLoader = ((IvoTalentsApp)context.getApplicationContext()).getImageLoader();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_grid_image, parent, false);
        itemView.setOnClickListener(this);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.setIsRecyclable(false);
        try {
            switch (items.get(position).getType()){

                case VIDEO:
                    holder.playBtnVideo.setVisibility(View.VISIBLE);
                    imageLoader.displayImage(items.get(position).getPath(), holder.imageView);
                    break;
                case PHOTO:
                    holder.playBtnVideo.setVisibility(View.GONE);
                    imageLoader.displayImage(items.get(position).getPath(), holder.imageView);
                    //holder.imageView.setImageBitmap(imageLoader.loadImageSync(items.get(position)));
                    // (new ThumbnailImage(items.get(position),holder.imageView)).execute();

                    //imageLoader.displayImage(items.get(position).getPath(),, holder.imageView);
                   /* imageLoader
                            .displayImage(items.get(position).getPath(), holder.imageView,null, new SimpleImageLoadingListener() {
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
                            });*/
                    break;
                case AUDIO:
                    String uri = "@drawable/ic_audio";  // where myresource (without the extension) is the file
                    int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
                    Drawable res = context.getResources().getDrawable(imageResource);
                    holder.imageView.setImageDrawable(res);
                    holder.playBtnVideo.setVisibility(View.GONE);
                    break;
            }
            holder.txtName.setText(items.get(position).getDate());
        }catch (Exception e){
            Log.e("ERROR BINDVIEW",e.getMessage());
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
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
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

    public class ThumbnailImage   extends AsyncTask<Void, Void, Bitmap> {

        String uri;
        ImageView imageView;

        ThumbnailImage(String uri, ImageView imageView){
            this.uri = uri;
            this.imageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(Void... params) {
            return imageLoader.loadImageSync(uri);
        }
        @Override
        protected void onPostExecute(final Bitmap bm) {
            imageView.setImageBitmap(bm);
            try {
                this.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }


    }
}

