package com.threedlink.ivotalents.fragments.profiletabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.custom.CustomRetrofitCallback;
import com.threedlink.ivotalents.dtos.MediaResource;
import com.threedlink.ivotalents.utils.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileVideoGrid.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileVideoGrid#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileVideoGrid extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EMAIL = "email";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mEmail;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    @Bind(R.id.video_slider_scroll)
    HorizontalScrollView videoSliderScroll;

    @Bind(R.id.video_slider_container)
    LinearLayout videoSliderContainer;

    @Bind(R.id.upload_video_to_profile)
    LinearLayout uploadVideoToProfile;

    private IvoTalentsApp mApp;
    private Util util;

     public ProfileVideoGrid() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param email Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileVideoGrid.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileVideoGrid newInstance(String email, String param2) {
        ProfileVideoGrid fragment = new ProfileVideoGrid();
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
        View v = inflater.inflate(R.layout.fragment_profile_video_grid, container, false);
        ButterKnife.bind(this, v);

        boolean isMyOwnProfile = mApp.getEntitySession().getEmail().equalsIgnoreCase(mEmail);
        uploadVideoToProfile.setVisibility(isMyOwnProfile?View.VISIBLE:View.GONE);
        loadGridViewData();



        return v;
    }

    private void loadGridViewData() {
        Call<ArrayList<MediaResource>> imagesCall = mApp.getApiServiceIntance().images("","");
        imagesCall.enqueue(new CustomRetrofitCallback<ArrayList<MediaResource>>(getActivity()) {
            @Override
            protected void handleSuccess(Response response) {
                ArrayList<MediaResource> list = (ArrayList<MediaResource>) response.body();
                Iterator<MediaResource> it = list.iterator();
                while (it.hasNext()) {
                    if (it.next().getType() != MediaResource.MediaResourceType.VIDEO) {
                        it.remove();
                    }
                }
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                for (int i = 0; i < list.size(); i++) {

                    View itemView = inflater.inflate(R.layout.item_grid_video,videoSliderContainer,false);
                    videoSliderContainer.addView(itemView);

                }
            }

            @Override
            public void failure(Throwable error) {

            }

        });

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
