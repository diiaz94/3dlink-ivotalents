package com.threedlink.ivotalents;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.threedlink.ivotalents.Adapters.CustomFollowingViewAdapter;
import com.threedlink.ivotalents.DTO.RolEntity;
import com.threedlink.ivotalents.Adapters.CustomRecentViewAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeIndustry.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeIndustry#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeIndustry extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IvoTalentsApp mApp;

    private GridView recentArtistsGrid;
    private GridView recentProvidersGrid;
    private GridView followersGrid;
    private GridView followedsGrid;

    private OnFragmentInteractionListener mListener;

    public HomeIndustry() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeIndustry.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeIndustry newInstance(String param1, String param2) {
        HomeIndustry fragment = new HomeIndustry();
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
        final View view =  inflater.inflate(R.layout.fragment_home_industry, container, false);



        initGridViewArtists(view);
        initGridViewProviders(view);
        initGridViewFollowers(view);
        initGridViewFolloweds(view);




        RelativeLayout myLayout = (RelativeLayout) view.findViewById(R.id.fragment_home_industry);
        mApp.setFontsOnRelative(myLayout);

        return view;
    }

    private void initGridViewArtists(View view){
        Resources res = getActivity().getApplicationContext().getResources();
        String[] tempArtistCategories = res.getStringArray(R.array.artist_categories);
        String[] tempArtistNames = res.getStringArray(R.array.artist_names);
        String[] tempArtistAbilities = res.getStringArray(R.array.artist_abilities);
        int[] imageArtists = {R.drawable.talent_1,R.drawable.talent_2,R.drawable.juan_esteban,R.drawable.talent_1,R.drawable.talent_2,R.drawable.juan_esteban};
        ArrayList<RolEntity> listArtists = new ArrayList<RolEntity>();
        for (int i=0; i<6;i++){
            RolEntity profile = new RolEntity(tempArtistCategories[i],tempArtistNames[i],tempArtistAbilities[i],imageArtists[i]);
            listArtists.add(profile);
        }
        recentArtistsGrid = (GridView) view.findViewById(R.id.newArtistsGrid);
        recentArtistsGrid.setAdapter(new CustomRecentViewAdapter(getActivity().getApplicationContext(),listArtists));
        View item = recentArtistsGrid.getAdapter().getView(0, null, recentArtistsGrid);
        item.measure(0, 0);
        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (2.2 * item.getMeasuredHeight()));
        recentArtistsGrid.setLayoutParams(params);

    }

    private void initGridViewProviders(View view) {
        Resources res = getActivity().getApplicationContext().getResources();

        String[] tempProviderCategories = res.getStringArray(R.array.provider_categories);
        String[] tempProviderNames = res.getStringArray(R.array.provider_names);
        String[] tempProviderAbilities = res.getStringArray(R.array.provider_abilities);
        int[] imageProviders = {R.drawable.talent_1,R.drawable.talent_2,R.drawable.juan_esteban};
        ArrayList<RolEntity> listProviders = new ArrayList<RolEntity>();
        for (int i=0; i<3;i++){
            RolEntity provider = new RolEntity(tempProviderCategories[i],tempProviderNames[i],tempProviderAbilities[i],imageProviders[i]);
            listProviders.add(provider);
        }


        recentProvidersGrid = (GridView) view.findViewById(R.id.newProvidersGrid);
        recentProvidersGrid.setAdapter(new CustomRecentViewAdapter(getActivity().getApplicationContext(),listProviders));
        View item = recentProvidersGrid.getAdapter().getView(0, null, recentProvidersGrid);
        item.measure(0, 0);
        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (1.2 * item.getMeasuredHeight()));
        recentProvidersGrid.setLayoutParams(params);

    }

    private void initGridViewFollowers(View view) {
        Resources res = getActivity().getApplicationContext().getResources();
        String[] tempFollowerCategories = res.getStringArray(R.array.follower_categories);
        String[] tempFollowerNames = res.getStringArray(R.array.follower_names);
        String[] tempFollowerAbilities = res.getStringArray(R.array.follower_abilities);
        int[] imageFollowers = {R.drawable.bg_gray_following,R.drawable.bg_gray_following};
        ArrayList<RolEntity> listFollowers = new ArrayList<RolEntity>();
        for (int i=0; i<2;i++){
            RolEntity follower = new RolEntity(tempFollowerCategories[i],tempFollowerNames[i],tempFollowerAbilities[i],imageFollowers[i]);
            listFollowers.add(follower);
        }
        followersGrid = (GridView) view.findViewById(R.id.followersGrid);
        followersGrid.setAdapter(new CustomFollowingViewAdapter(getActivity().getApplicationContext(),listFollowers));
        android.widget.LinearLayout.LayoutParams paramsFollowers = (LinearLayout.LayoutParams) followersGrid.getLayoutParams();
        paramsFollowers.height = 250*(followersGrid.getAdapter().getCount()/2);
        followersGrid.setLayoutParams(paramsFollowers);
    }

    private void initGridViewFolloweds(View view) {
        Resources res = getActivity().getApplicationContext().getResources();
        String[] tempFollowedCategories = res.getStringArray(R.array.followed_categories);
        String[] tempFollowedNames = res.getStringArray(R.array.followed_names);
        String[] tempFollowedAbilities = res.getStringArray(R.array.followed_abilities);
        int[] imageFolloweds = {R.drawable.bg_gray_following,R.drawable.bg_gray_following};
        ArrayList<RolEntity> listFolloweds = new ArrayList<RolEntity>();
        for (int i=0; i<2;i++){
            RolEntity followed = new RolEntity(tempFollowedCategories[i],tempFollowedNames[i],tempFollowedAbilities[i],imageFolloweds[i]);
            listFolloweds.add(followed);
        }
        followedsGrid = (GridView) view.findViewById(R.id.followedsGrid);
        followedsGrid.setAdapter(new CustomFollowingViewAdapter(getActivity().getApplicationContext(),listFolloweds));
        android.widget.LinearLayout.LayoutParams paramsFolloweds = (LinearLayout.LayoutParams) followedsGrid.getLayoutParams();
        paramsFolloweds.height = 250*(followedsGrid.getAdapter().getCount()/2);
        followedsGrid.setLayoutParams(paramsFolloweds);
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}





