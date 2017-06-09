package com.threedlink.ivotalents.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.adapters.CustomFollowingViewAdapter;
import com.threedlink.ivotalents.adapters.CustomRecentCastingsListAdapter;
import com.threedlink.ivotalents.custom.CustomRetrofitCallback;
import com.threedlink.ivotalents.dtos.Casting;
import com.threedlink.ivotalents.dtos.RolEntity;
import com.threedlink.ivotalents.adapters.CustomRecentViewAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeArtist.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeArtist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeArtist extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "HomeArtist";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IvoTalentsApp mApp;

    @Bind(R.id.castings_container)
    LinearLayout castingsContainer;
    @Bind(R.id.castings_data_info)
    LinearLayout castingsDataInfo;
    @Bind(R.id.castings_recycler)
    RecyclerView castingsRecycler;
    @Bind(R.id.castings_progress_info)
    LinearLayout castingsProgressInfo;
    @Bind(R.id.castings_text_info)
    TextView castingsTextInfo;
    @Bind(R.id.castings_spinner)
    ProgressBar castingsSpinner;

    @Bind(R.id.providers_container)
    LinearLayout providersContainer;
    @Bind(R.id.providers_data_info)
    LinearLayout providersDataInfo;
    @Bind(R.id.providers_recycler)
    RecyclerView providersRecycler;
    @Bind(R.id.providers_progress_info)
    LinearLayout providersProgressInfo;
    @Bind(R.id.providers_text_info)
    TextView providersTextInfo;
    @Bind(R.id.providers_spinner)
    ProgressBar providersSpinner;


    private RecyclerView recentProvidersGrid;
    private GridView followersGrid;
    private GridView followedsGrid;

    private OnFragmentInteractionListener mListener;



    public HomeArtist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeArtist.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeArtist newInstance(String param1, String param2) {
        HomeArtist fragment = new HomeArtist();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  v = inflater.inflate(R.layout.fragment_home_artist, container, false);
        ButterKnife.bind(this, v);

        loadRecentCastings();
        loadProviders();
        //initGridViewProviders(v);
        initGridViewFollowers(v);
        initGridViewFolloweds(v);


        RelativeLayout myLayout = (RelativeLayout) v.findViewById(R.id.fragment_home_artist);
        //mApp.setFontsOnRelative(myLayout);

        return v;
    }

    private void loadRecentCastings(){
        castingsContainer.setVisibility(View.VISIBLE);//container
        castingsDataInfo.setVisibility(View.GONE);//data
        LinearLayoutManager llmanager = new LinearLayoutManager(getActivity());
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        castingsRecycler.setLayoutManager(llmanager);
        castingsProgressInfo.setVisibility(View.VISIBLE);//progress
        castingsTextInfo.setText(getString(R.string.load_recents_castings_text));//progress text;

        Call<ArrayList<Casting>> castingCall = mApp.getApiServiceIntance().castings("");
        castingCall.enqueue(new CustomRetrofitCallback<ArrayList<Casting>>(getActivity()) {
            @Override
            protected void handleSuccess(Response response) {
                ArrayList<Casting> list = (ArrayList<Casting>) response.body();
                castingsRecycler.setAdapter(new CustomRecentCastingsListAdapter(getActivity().getApplicationContext(),list));
                castingsDataInfo.setVisibility(View.VISIBLE);
                castingsProgressInfo.setVisibility(View.GONE);
            }

            @Override
            public void failure(Throwable error) {
                castingsTextInfo.setText(getString(R.string.error_load_recents_castings_text));
                castingsSpinner.setVisibility(View.GONE);
            }

        });
    }
    private void loadProviders(){
        providersContainer.setVisibility(View.VISIBLE);//container
        providersDataInfo.setVisibility(View.GONE);//data
        LinearLayoutManager llmanager = new GridLayoutManager(getActivity(),3);
        providersRecycler.setLayoutManager(llmanager);
        providersProgressInfo.setVisibility(View.VISIBLE);//progress
        providersTextInfo.setText(getString(R.string.load_recents_providers_text));//progress text;

        Call<ArrayList<RolEntity>> providersCall = mApp.getApiServiceIntance().providers("");
        providersCall.enqueue(new CustomRetrofitCallback<ArrayList<RolEntity>>(getActivity()) {
            @Override
            protected void handleSuccess(Response response) {
                ArrayList<RolEntity> list = (ArrayList<RolEntity>) response.body();
                providersRecycler.setAdapter(new CustomRecentViewAdapter(getActivity().getApplicationContext(),list));
                providersDataInfo.setVisibility(View.VISIBLE);
                providersProgressInfo.setVisibility(View.GONE);
            }

            @Override
            public void failure(Throwable error) {
                providersTextInfo.setText(getString(R.string.error_load_recents_providers_text));
                providersSpinner.setVisibility(View.GONE);
            }

        });
    }

    private void initListViewCastings(ArrayList<Casting> list){
        /*LinearLayoutManager llmanager = new LinearLayoutManager(getActivity());
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recentCastingList.setLayoutManager(llmanager);
        recentCastingList.setAdapter(new CustomRecentCastingsListAdapter(getActivity().getApplicationContext(),list));


        View item = recentCastingList.getAdapter().getView(0, null, recentCastingList);
        item.measure(0, 0);
        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (3.5 * item.getMeasuredHeight()));
        recentCastingList.setLayoutParams(params);
        //android.widget.LinearLayout.LayoutParams paramsArtists = (LinearLayout.LayoutParams) recentCastingList.getLayoutParams();
        //paramsArtists.height = 200*(recentCastingList.getAdapter().getCount()/3);
        //recentCastingList.setLayoutParams(paramsArtists);
*/
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


        recentProvidersGrid = (RecyclerView) view.findViewById(R.id.newProvidersGrid);
        //recentProvidersGrid.setHasFixedSize(true);
        LinearLayoutManager llmanager = new GridLayoutManager(getActivity(),3);
        recentProvidersGrid.setLayoutManager(llmanager);
        recentProvidersGrid.setAdapter(new CustomRecentViewAdapter(getActivity().getApplicationContext(),listProviders));

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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //clearView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //you are visible to user now - so set whatever you need
            //initView();
        }
        else {
            //you are no longer visible to the user so cleanup whatever you need
            //clearView();
        }
    }

    private void initView() {
    }
    private void clearView() {
        this.recentProvidersGrid = null;
        this.followedsGrid = null;
        this.followersGrid = null;
        System.gc();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
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





