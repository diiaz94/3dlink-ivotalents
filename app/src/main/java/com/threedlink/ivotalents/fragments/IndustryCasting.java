package com.threedlink.ivotalents.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.adapters.CustomFavoriteAuditionsListAdapter;
import com.threedlink.ivotalents.adapters.CustomSwipeAdapterIndustryCastingView;
import com.threedlink.ivotalents.custom.CustomRetrofitCallback;
import com.threedlink.ivotalents.dtos.RolEntity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IndustryCasting.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IndustryCasting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndustryCasting extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IvoTalentsApp mApp;

    @Bind(R.id.recent_auditions_container)
    LinearLayout recentAuditionsContainer;
    @Bind(R.id.recent_auditions_data_info)
    LinearLayout recentAuditionsDataInfo;
    @Bind(R.id.recent_auditions_view_pager)
    ViewPager recentAuditionsViewPager;
    @Bind(R.id.recent_auditions_progress_info)
    LinearLayout recentAuditionsProgressInfo;
    @Bind(R.id.recent_auditions_text_info)
    TextView recentAuditionsTextInfo;
    @Bind(R.id.recent_auditions_spinner)
    ProgressBar recentAuditionsSpinner;


    @Bind(R.id.favorite_auditions_container)
    LinearLayout favoriteAuditionsContainer;
    @Bind(R.id.favorite_auditions_data_info)
    LinearLayout favoriteAuditionsDataInfo;
    @Bind(R.id.favorite_auditions_recycler)
    RecyclerView favoriteAuditionsRecycler;
    @Bind(R.id.favorite_auditions_progress_info)
    LinearLayout favoriteAuditionsProgressInfo;
    @Bind(R.id.favorite_auditions_text_info)
    TextView favoriteAuditionsTextInfo;
    @Bind(R.id.favorite_auditions_spinner)
    ProgressBar favoriteAuditionsSpinner;
    @Bind(R.id.ind_slide_left_arrow)
    ImageView indSlideLeftArrow;
    @Bind(R.id.ind_slide_right_arrow)
    ImageView indSlideRightArrow;

    private OnFragmentInteractionListener mListener;

    public IndustryCasting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IndustryCasting.
     */
    // TODO: Rename and change types and number of parameters
    public static IndustryCasting newInstance(String param1, String param2) {
        IndustryCasting fragment = new IndustryCasting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ((IvoTalentsApp) getActivity().getApplicationContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_industry_casting, container, false);
        ButterKnife.bind(this, v);

        loadRecentAuditions();
        loadFavoriteAuditions();

        return v;
    }


    private void loadRecentAuditions() {
        recentAuditionsContainer.setVisibility(View.VISIBLE);//container
        recentAuditionsDataInfo.setVisibility(View.GONE);//data


        recentAuditionsViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v(null, "TOUCH EVENT"); // handle your fragment number here
                indSlideRightArrow.setVisibility(recentAuditionsViewPager.getCurrentItem()+1<recentAuditionsViewPager.getAdapter().getCount()?View.VISIBLE:View.INVISIBLE);
                indSlideLeftArrow.setVisibility(recentAuditionsViewPager.getCurrentItem()-1>=0?View.VISIBLE:View.INVISIBLE);

                return false;
            }
        });
        recentAuditionsProgressInfo.setVisibility(View.VISIBLE);//progress
        recentAuditionsTextInfo.setText(getString(R.string.load_recents_castings_text));//progress text;

        Call<ArrayList<RolEntity>> castingCall = mApp.getApiServiceIntance().providers("");
        castingCall.enqueue(new CustomRetrofitCallback<ArrayList<RolEntity>>() {
            @Override
            public void handleSuccess(Response response) {
                ArrayList<RolEntity> list = (ArrayList<RolEntity>) response.body();
                recentAuditionsViewPager.setAdapter(new CustomSwipeAdapterIndustryCastingView(getActivity().getApplicationContext(),list));
                recentAuditionsDataInfo.setVisibility(View.VISIBLE);
                recentAuditionsProgressInfo.setVisibility(View.GONE);
            }

            @Override
            public void handleError(Response response) {
                recentAuditionsTextInfo.setText(getString(R.string.error_load_recents_castings_text));
                recentAuditionsSpinner.setVisibility(View.GONE);
            }

        });
    }

    private void loadFavoriteAuditions() {
        favoriteAuditionsContainer.setVisibility(View.VISIBLE);//container
        favoriteAuditionsDataInfo.setVisibility(View.GONE);//data

        LinearLayoutManager llmanager = new GridLayoutManager(getActivity(),2);
        //SnapHelper snapHelper = new LinearSnapHelper();
        favoriteAuditionsRecycler.setLayoutManager(llmanager);
        //snapHelper.attachToRecyclerView(castingsRecycler);
        favoriteAuditionsProgressInfo.setVisibility(View.VISIBLE);//progress
        favoriteAuditionsTextInfo.setText(getString(R.string.load_recents_castings_text));//progress text;

        Call<ArrayList<RolEntity>> castingCall = mApp.getApiServiceIntance().providers("");
        castingCall.enqueue(new CustomRetrofitCallback<ArrayList<RolEntity>>() {
            @Override
            public void handleSuccess(Response response) {
                ArrayList<RolEntity> list = (ArrayList<RolEntity>) response.body();
                favoriteAuditionsRecycler.setAdapter(new CustomFavoriteAuditionsListAdapter(getActivity().getApplicationContext(),list.subList(0,2)));
                favoriteAuditionsDataInfo.setVisibility(View.VISIBLE);
                favoriteAuditionsProgressInfo.setVisibility(View.GONE);
            }

            @Override
            public void handleError(Response response) {
                favoriteAuditionsTextInfo.setText(getString(R.string.error_load_recents_castings_text));
                favoriteAuditionsSpinner.setVisibility(View.GONE);
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
