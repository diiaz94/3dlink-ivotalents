package com.threedlink.ivotalents.fragments.profiletabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.custom.CustomRetrofitCallback;
import com.threedlink.ivotalents.dtos.MediaResource;
import com.threedlink.ivotalents.utils.Util;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfilePhotoGrid.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilePhotoGrid#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilePhotoGrid extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EMAIL = "email";
    private static final String ARG_PARAM2 = "param2";
    private static final int PHOTO_LIMIT_COUNT = 9;

    // TODO: Rename and change types of parameters
    private String mEmail;
    private String mParam2;

    @Bind(R.id.photo_grid_container)
    LinearLayout photoGridContainer;
    @Bind(R.id.photo_grid_data_info)
    LinearLayout photoGridDataInfo;
    @Bind(R.id.photo_grid_view)
    GridView photoGridView;
    @Bind(R.id.photo_grid_progress_info)
    LinearLayout photoGridProgressInfo;
    @Bind(R.id.photo_grid_text_info)
    TextView photoGridTextInfo;
    @Bind(R.id.photo_grid_spinner)
    ProgressBar photoGridSpinner;

    @Bind(R.id.pagination_container)
    LinearLayout paginationContainer;
    @Bind(R.id.upload_image_to_profile)
    LinearLayout uploadImageToProfile;

    private IvoTalentsApp mApp;
    private Util util;
    private OnFragmentInteractionListener mListener;
    private ArrayList<MediaResource> mList;
    private int mRealSize;

    public ProfilePhotoGrid() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param email Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilePhotoGrid.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilePhotoGrid newInstance(String email, String param2) {
        ProfilePhotoGrid fragment = new ProfilePhotoGrid();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmail = getArguments().getString(ARG_EMAIL);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mApp = ((IvoTalentsApp) getActivity().getApplicationContext());
        util = new Util();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_photo_grid, container, false);
        ButterKnife.bind(this, v);

        boolean isMyOwnProfile = mApp.getEntitySession().getEmail().equalsIgnoreCase(mEmail);
        uploadImageToProfile.setVisibility(isMyOwnProfile?View.VISIBLE:View.GONE);
        loadGridViewData();
        
        return v;
    }

    private void loadGridViewData() {
        photoGridContainer.setVisibility(View.VISIBLE);//container
        photoGridDataInfo.setVisibility(View.GONE);//data
        photoGridProgressInfo.setVisibility(View.VISIBLE);//progress
        photoGridTextInfo.setText(getString(R.string.load_photo_grid_text));//progress text;
        Call<ArrayList<MediaResource>> imagesCall = mApp.getApiServiceIntance().mediaResources("","");
        imagesCall.enqueue(new CustomRetrofitCallback<ArrayList<MediaResource>>(getActivity()) {
            @Override
            protected void handleSuccess(Response response) {
                ArrayList<MediaResource> list = (ArrayList<MediaResource>) response.body();
                Iterator<MediaResource> it = list.iterator();
                while (it.hasNext()) {
                    if (it.next().getType() != MediaResource.MediaResourceType.PHOTO) {
                        it.remove();
                    }
                }
                mRealSize = list.size();
                mList = completeList(list);
                
                util.setPagination(paginationContainer,mList,mRealSize,photoGridView,PHOTO_LIMIT_COUNT,R.drawable.simple_point,R.drawable.selected_point_orange);
                if(photoGridView!=null){

                    photoGridDataInfo.setVisibility(View.VISIBLE);
                    photoGridProgressInfo.setVisibility(View.GONE);
                }
            }

            @Override
            public void failure(Throwable error) {
                photoGridTextInfo.setText(getString(R.string.error_load_recents_castings_text));
                photoGridSpinner.setVisibility(View.GONE);
            }

        });
    }

    private ArrayList<MediaResource> completeList(ArrayList<MediaResource> list) {
        int remaining = list.size()%PHOTO_LIMIT_COUNT;
        if(remaining!=0){
            for (int i = 0; i < PHOTO_LIMIT_COUNT-remaining; i++) {
                list.add(new MediaResource());
            }
        }
        return list;
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
}
