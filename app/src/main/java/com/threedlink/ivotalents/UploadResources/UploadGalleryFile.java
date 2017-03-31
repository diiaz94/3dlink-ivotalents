package com.threedlink.ivotalents.UploadResources;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.threedlink.ivotalents.R;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UploadGalleryFile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadGalleryFile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadGalleryFile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageAdapter myImageAdapter;
    private OnFragmentInteractionListener mListener;
    private GridView mGridView;
    private View mProgressView;
    private String targetPath;

    public UploadGalleryFile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadGalleryFile.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadGalleryFile newInstance(String param1, String param2) {
        UploadGalleryFile fragment = new UploadGalleryFile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_gallery_file, container, false);
        mGridView = (GridView) view.findViewById(R.id.gridview);
        mProgressView = view.findViewById(R.id.progress_view);
        initView();
        return view;
    }

    private void initView() {
        if(getActivity()!=null) {
            mGridView.setVisibility(View.VISIBLE);
            myImageAdapter = new ImageAdapter(getActivity().getApplicationContext());
            mGridView.setAdapter(myImageAdapter);
            methodThatStartsTheAsyncTask();

        }
    }
    private void clearView() {
        if(getActivity()!=null) {
            myImageAdapter = null;
            mGridView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //you are visible to user now - so set whatever you need
           initView();
        }
        else {
            //you are no longer visible to the user so cleanup whatever you need
           clearView();
        }
    }


   private void methodThatStartsTheAsyncTask() {
       showProgress(true);
       new Thread(new Runnable() {
           public void run() {
               String ExternalStorageDirectoryPath = Environment
                       .getExternalStorageDirectory()
                       .getAbsolutePath();
               targetPath = ExternalStorageDirectoryPath + "/Pictures/Screenshots";

               getActivity().runOnUiThread(new Runnable() {
                   public void run() {
                       Toast.makeText(getActivity(),targetPath, Toast.LENGTH_LONG).show();
                   }
               });

               File targetDirector = new File(targetPath);
               File[] files = targetDirector.listFiles();
               int MAX_SHOW = 15,count = 0;
               for (File file : files){
                   if(count<MAX_SHOW){
                       myImageAdapter.add(file.getAbsolutePath());
                       count++;
                   }else{
                       break;
                   }
               }
               getActivity().runOnUiThread(new Runnable() {
                   public void run() {
                       showProgress(false);
                   }
               });

           }
       }).start();

   }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mGridView.setVisibility(show ? View.GONE : View.VISIBLE);
            mGridView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mGridView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mGridView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
   public class ImageAdapter extends BaseAdapter {

        private Context mContext;
        ArrayList<String> itemList = new ArrayList<String>();

        public ImageAdapter(Context c) {
            mContext = c;
        }

        void add(String path){
            itemList.add(path);
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            Bitmap bm = getThumbnailBitmap(itemList.get(position),220);

            imageView.setImageBitmap(bm);
            return imageView;
        }
        /**
         * returns the thumbnail image bitmap from the given url
         *
         * @param path
         * @param thumbnailSize
         * @return
         */
        private Bitmap getThumbnailBitmap(final String path, final int thumbnailSize) {
            Bitmap bitmap;
            BitmapFactory.Options bounds = new BitmapFactory.Options();
            bounds.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, bounds);
            if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
                bitmap = null;
            }
            int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                    : bounds.outWidth;
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = originalSize / thumbnailSize;
            bitmap = BitmapFactory.decodeFile(path, opts);
            return bitmap;
        }
        public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {

            Bitmap bm = null;
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(path, options);

            return bm;
        }

        public int calculateInSampleSize(

                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                if (width > height) {
                    inSampleSize = Math.round((float)height / (float)reqHeight);
                } else {
                    inSampleSize = Math.round((float)width / (float)reqWidth);
                }
            }

            return inSampleSize;
        }

    }

}
