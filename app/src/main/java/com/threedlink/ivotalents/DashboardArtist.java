package com.threedlink.ivotalents;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.widget.LinearLayout.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardArtist.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardArtist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardArtist extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IvoTalentsApp mApp;
    private OnFragmentInteractionListener mListener;
    private LinearLayout paginator_swipe_artist_recent;

    public DashboardArtist() {
        // Required empty public constructor
    }

    ViewPager viewPager;
    CustomSwipeAdapterArtist adapter;
    ViewPager viewPagerArtistRecent;
    CustomSwipeAdapterArtistRecent adapterArtistRecent;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardArtist.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardArtist newInstance(String param1, String param2) {
        DashboardArtist fragment = new DashboardArtist();
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
        viewPager = (ViewPager) view.findViewById(R.id.view_pager_artist);
        viewPager.setVisibility(View.VISIBLE);
        adapter = new CustomSwipeAdapterArtist(getActivity().getApplicationContext());
        viewPager.setAdapter(adapter);

        viewPagerArtistRecent = (ViewPager) view.findViewById(R.id.view_pager_artist_recent);
        viewPagerArtistRecent.setVisibility(View.VISIBLE);
        adapterArtistRecent = new CustomSwipeAdapterArtistRecent(getActivity().getApplicationContext());
        viewPagerArtistRecent.setAdapter(adapterArtistRecent);

        paginator_swipe_artist_recent = (LinearLayout) view.findViewById(R.id.paginator_swipe_artist_recent);
        ImageButton paginator_swipe_artist_recent_template = (ImageButton) view.findViewById(R.id.paginator_swipe_artist_recent_template);
        ViewGroup.LayoutParams params = paginator_swipe_artist_recent_template.getLayoutParams();
        if (adapterArtistRecent.getCount()>1) {
            for (int i = 0; i < adapterArtistRecent.getCount(); i++) {
                ImageButton b = new ImageButton(getActivity().getApplicationContext());
                b.setLayoutParams(params);
                b.setBackgroundColor(getResources().getColor(R.color.transparent));
                b.setImageResource(i==0?R.drawable.selected_point_orange:R.drawable.simple_point_white);
                b.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //Log.e("AQUI","b.getLayoutParams().width::"+String.valueOf(b.getLayoutParams().width));
                //Log.e("AQUI","b.getLayoutParams().height::"+String.valueOf(b.getLayoutParams().height));
                b.setTag(String.valueOf(i));
                b.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        int point_selected = Integer.parseInt(v.getTag().toString());
                        for (int i = 0; i < adapterArtistRecent.getCount(); i++) {
                            ImageButton b =(ImageButton) paginator_swipe_artist_recent.getChildAt(i);
                            b.setImageResource(i==point_selected?R.drawable.selected_point_orange:R.drawable.simple_point_white);
                        }
                        viewPagerArtistRecent.setCurrentItem(point_selected);
                    }
                });



                paginator_swipe_artist_recent.addView(b);
            }
        }


        RelativeLayout myLayout = (RelativeLayout) view.findViewById(R.id.fragment_dashboard_artist);
        mApp.setFontsOnRelative(myLayout);

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
