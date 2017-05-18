package com.threedlink.ivotalents.uploadresources;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.threedlink.ivotalents.adapters.CustomGalleryAdapter;
import com.threedlink.ivotalents.dtos.MediaResource;
import com.threedlink.ivotalents.previews.PhotoPreview;
import com.threedlink.ivotalents.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private OnFragmentInteractionListener mListener;
    private GridView mGridView;
    private View mProgressView;
    private String targetPath;
    private ProgressBar spinner;
    private UploadGalleryFileTask mUploadGalleryFileTask;
    private View mView;
    private ArrayList<MediaResource> mData;
    private CustomGalleryAdapter mAdapter;

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
    protected RecyclerView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fr_image_grid, container, false);
        initView();



/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
*/

        return mView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //you are visible to user now - so set whatever you need
           // initView();
        }
        else {
            //you are no longer visible to the user so cleanup whatever you need
            //clearView();
        }
    }

    private void clearView() {
        gridView = null;
        spinner = null;
        gridView = null;
        spinner = null;
        mUploadGalleryFileTask= null;
        System.gc();
    }

    private void initView() {
        if(mView!=null){
            gridView = (RecyclerView) mView.findViewById(R.id.grid);
            LinearLayoutManager llmanager = new GridLayoutManager(getActivity(),3);
            gridView.setLayoutManager(llmanager);
            spinner = (ProgressBar) mView.findViewById(R.id.spinner);
            gridView.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            mUploadGalleryFileTask= new UploadGalleryFileTask();
            mUploadGalleryFileTask.execute((Void) null);
            mData = new ArrayList<MediaResource>();
            mAdapter = new CustomGalleryAdapter(getActivity().getApplicationContext(),mData);
            gridView.setAdapter(mAdapter);
            mAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("DemoRecView", "Pulsado el elemento " + gridView.getChildPosition(v));
                    displayPreview( gridView.getChildPosition(v));

                }
            });

        }
    }

    private void displayPreview(int position) {
        MediaResource mr = mData.get(position);
        switch (mr.getType()){
            case PHOTO:
                Intent intent = PhotoPreview.newIntentPhoto(getActivity(), mr.getPath());
                startActivity(intent);
                break;
        }

    }

    public static final String CAMERA_IMAGE_BUCKET_NAME =
            Environment.getExternalStorageDirectory().toString()
                    + "/DCIM/Camera";
    public static final String CAMERA_IMAGE_BUCKET_ID =
            getBucketId(CAMERA_IMAGE_BUCKET_NAME);

    /**
     * Matches code in MediaProvider.computeBucketValues. Should be a common
     * function.
     */
    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }

    public static List<String> getCameraImages(Context context) {
        final String[] projection = { MediaStore.Images.Media.DATA };
        final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        final String[] selectionArgs = { CAMERA_IMAGE_BUCKET_ID };
        final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        ArrayList<String> result = new ArrayList<String>(cursor.getCount());
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                final String data = cursor.getString(dataColumn);
                result.add("file:///"+data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public  ArrayList<MediaResource> getFiles(Context context){
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Files.getContentUri("external");
        String[] projection =  {
                                MediaStore.Files.FileColumns.DATA,
                                MediaStore.Files.FileColumns._ID,
                                MediaStore.Files.FileColumns.DATE_MODIFIED
                                };
        String selection =  MediaStore.Files.FileColumns.MIME_TYPE + "=? or "+
                            MediaStore.Files.FileColumns.MIME_TYPE + "=? or "+
                            MediaStore.Files.FileColumns.MIME_TYPE + "=? or "+
                            MediaStore.Files.FileColumns.MIME_TYPE + "=? or "+
                            MediaStore.Files.FileColumns.MIME_TYPE + "=? ";

        String[] selectionArgs = {
                                    MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpeg"),
                                    MimeTypeMap.getSingleton().getMimeTypeFromExtension("png"),
                                    MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3"),
                                    MimeTypeMap.getSingleton().getMimeTypeFromExtension("m4a"),
                                    MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp4")
                                };
        //String[] selectionArgs = {MediaStore.Files.FileColumns.MEDIA_TYPE_NONE};
        String sortOrder = MediaStore.Images.Media.DATE_MODIFIED+ " DESC";
        Cursor cursor = cr.query(uri, projection, selection, selectionArgs, sortOrder);
        ArrayList<MediaResource> result = new ArrayList<MediaResource>(cursor.getCount());
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            final int dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED);
            do {
                final String data = cursor.getString(dataColumn);
                final String date = cursor.getString(dateColumn);
                result.add(new MediaResource("file:///"+data,date));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    private ArrayList<MediaResource> getListFiles(File parentDir) {
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                mData.addAll(getListFiles(file));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.swap(mData);
                    }
                });

            } else {
                if(file.getName().endsWith("jpg") ||
                   file.getName().endsWith("mp4") ||
                   file.getName().endsWith("mp3") ||
                   file.getName().endsWith("m4a")){
                    //mData.add(new MediaResource("file:///"+file.getPath()));
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.swap(mData);
                        }
                    });
                }
            }
        }
        return mData;
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




    private void showSpinner(boolean show){
        spinner.setVisibility(show?View.VISIBLE:View.GONE);
        gridView.setVisibility(!show?View.VISIBLE:View.GONE);
    }

    public class UploadGalleryFileTask extends AsyncTask<Void, Void, ArrayList<MediaResource>> {


        UploadGalleryFileTask() {

        }
        @Override
        protected void onPreExecute() {
            showSpinner(true);
        }
        @Override
        protected ArrayList<MediaResource> doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            mData = new ArrayList<MediaResource>();
            //return getListFiles(new File(Environment.getExternalStorageDirectory().toString()));
            return getFiles(getContext());

        }

        @Override
        protected void onPostExecute(ArrayList<MediaResource> list) {
            mUploadGalleryFileTask = null;
            showSpinner(false);
            mData = list;
            mAdapter.swap(list);
        }

        @Override
        protected void onCancelled() {
            mUploadGalleryFileTask  = null;
            showSpinner(false);
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //clearView();
    }
}
