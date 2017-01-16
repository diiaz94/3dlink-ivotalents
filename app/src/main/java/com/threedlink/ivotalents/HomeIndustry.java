package com.threedlink.ivotalents;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

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

    private GridView newArtistsGrid;

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
        final View view =  inflater.inflate(R.layout.fragment_dashboard_artist, container, false);
        newArtistsGrid = (GridView) view.findViewById(R.id.newArtistsGrid);

        return view;
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


class Artist{
    String name;
    String category;
    String ability;
    int imageId;
    public Artist(String name, String category, String ability,int imageId) {
        this.name = name;
        this.category = category;
        this.ability = ability;
        this.imageId = imageId;
    }


}

class RecenArtist extends BaseAdapter{
    private Context ctx;
    private ArrayList<Artist> list;

    public RecenArtist(Context ctx) {
        this.ctx = ctx;
        list = new ArrayList<Artist>();
        Resources res = ctx.getResources();
        String[] tempArtistNames = res.getStringArray(R.array.artist_names);
        int[] image_resources = {R.drawable.juan_esteban,R.drawable.juan_esteban,R.drawable.juan_esteban};
        for (int i=0; i<3;i++){
            Artist artist = new Artist(tempArtistNames[i],tempArtistNames[i],tempArtistNames[i],image_resources[i]);
            list.add(artist);
        }

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}