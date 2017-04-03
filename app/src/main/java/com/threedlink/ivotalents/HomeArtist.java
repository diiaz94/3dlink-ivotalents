package com.threedlink.ivotalents;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.threedlink.ivotalents.Adapters.CustomFollowingViewAdapter;
import com.threedlink.ivotalents.Adapters.CustomRecentCastingsListAdapter;
import com.threedlink.ivotalents.DTO.Casting;
import com.threedlink.ivotalents.DTO.RolEntity;
import com.threedlink.ivotalents.Adapters.CustomRecentViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IvoTalentsApp mApp;

    private ListView recentCastingList;
    private GridView recentProvidersGrid;
    private GridView followersGrid;
    private GridView followedsGrid;

    private OnFragmentInteractionListener mListener;
    private ProgressBar spinner1;
    private LinearLayout recentCastingsContainer;
    private View mView;

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
        // Inflate the layout for this fragment
        if(mView==null) {
            mView = inflater.inflate(R.layout.fragment_home_artist, container, false);

            recentCastingList = (ListView) mView.findViewById(R.id.newCastingsList);
            recentCastingsContainer = (LinearLayout) mView.findViewById(R.id.recent_castings_container);
            spinner1 = (ProgressBar) mView.findViewById(R.id.spinner1);
            recentCastingsContainer.setVisibility(View.GONE);
            spinner1.setVisibility(View.GONE);
            mCastingTask = new CastingsTask();
            mCastingTask.execute((Void) null);

            initGridViewProviders(mView);
            initGridViewFollowers(mView);
            initGridViewFolloweds(mView);


            RelativeLayout myLayout = (RelativeLayout) mView.findViewById(R.id.fragment_home_artist);
            mApp.setFontsOnRelative(myLayout);
        }
        return mView;
    }


    private void initListViewCastings(ArrayList<Casting> list){

        recentCastingList.setAdapter(new CustomRecentCastingsListAdapter(getActivity(),list));

        View item = recentCastingList.getAdapter().getView(0, null, recentCastingList);
        item.measure(0, 0);
        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (3.5 * item.getMeasuredHeight()));
        recentCastingList.setLayoutParams(params);
        //android.widget.LinearLayout.LayoutParams paramsArtists = (LinearLayout.LayoutParams) recentCastingList.getLayoutParams();
        //paramsArtists.height = 200*(recentCastingList.getAdapter().getCount()/3);
        //recentCastingList.setLayoutParams(paramsArtists);

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

    private void initView() {
    }
    private void clearView() {
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void showSpinner1(boolean show){
        spinner1.setVisibility(show?View.VISIBLE:View.GONE);
        recentCastingsContainer.setVisibility(!show?View.VISIBLE:View.GONE);
    }
    private CastingsTask mCastingTask;
    public class CastingsTask extends AsyncTask<Void, Void, ArrayList<Casting>> {

        private String mToken;
        private int mResponseCode;
        private ResponseBody responseError;

        CastingsTask() {

        }
        @Override
        protected void onPreExecute() {
            showSpinner1(true);
        }
        @Override
        protected ArrayList<Casting> doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            Call<ArrayList<Casting>> call = mApp.getApiServiceIntance().castings(mToken);

            try {
                Response<ArrayList<Casting>> response = call.execute();
                mResponseCode = response.code();
                Log.d("mResponseCode::",String.valueOf(mResponseCode));
                //Log.d(" response.body()::", response.errorBody()   );
                if(response.isSuccessful()){//Created
                    ArrayList<Casting> list = response.body();
                    return list;
                }else{
                    responseError = response.errorBody();
                    Log.d("Register","responseError.::"+responseError.string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<Casting> list) {
            mCastingTask = null;
            showSpinner1(false);
            if (list!=null) {
                initListViewCastings(list);
            } else {
                if(mResponseCode==400){

                }else{

                }

            }
        }

        @Override
        protected void onCancelled() {
            mCastingTask  = null;
            showSpinner1(false);
        }
    }
}





