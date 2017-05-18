package com.threedlink.ivotalents.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.threedlink.ivotalents.adapters.ParticipationsSwipeAdapter;
import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.dtos.RolEntity;
import com.threedlink.ivotalents.utils.Util;
import com.threedlink.ivotalents.utils.enums.Rol;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Participations.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Participations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Participations extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IvoTalentsApp mApp;
    private OnFragmentInteractionListener mListener;
    private ParticipationsSwipeAdapter adapter;
    private Util util;
    private RolEntity entity;

    public Participations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Participations.
     */
    // TODO: Rename and change types and number of parameters
    public static Participations newInstance(String param1, String param2) {
        Participations fragment = new Participations();
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
        util = new Util();
        mApp = ((IvoTalentsApp) getActivity().getApplicationContext());
        entity = mApp.getEntitySession();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_participations, container, false);

        List<Fragment> pageFragments = new ArrayList<>();
        pageFragments.add(ParticipationsStarted.newInstance("", ""));
        pageFragments.add(ParticipationsFinished.newInstance("",""));
        adapter = new ParticipationsSwipeAdapter(getActivity().getSupportFragmentManager(),pageFragments);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        String[] arrayTitleTabs = getTitlesTabs();
        tabLayout.addTab(tabLayout.newTab().setText(arrayTitleTabs[0]));
        tabLayout.addTab(tabLayout.newTab().setText(arrayTitleTabs[1]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setCurrentItem(Integer.parseInt(mParam1));


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

    public String[] getTitlesTabs() {
        String[] arrayTitleTabs = new String[2];
        if(Rol.valueOf(entity.getRole()) == Rol.ARTISTA){
            Resources res = getActivity().getApplicationContext().getResources();
            String[] abilities_to_auditions = res.getStringArray(R.array.abilities_to_auditions);
            String[] abilities_to_castings= res.getStringArray(R.array.abilities_to_castings);

            if(util.indexOf(abilities_to_auditions,entity.getAbility()) !=-1 ){
                arrayTitleTabs[0] = getString(R.string.label_auditions_participations_text);
                arrayTitleTabs[1] = arrayTitleTabs[0]+" TERMINADOS";
            }
            if(util.indexOf(abilities_to_castings,entity.getAbility()) !=-1 ){
                arrayTitleTabs[0] = getString(R.string.label_castings_participations_text);
                arrayTitleTabs[1] = arrayTitleTabs[0]+" TERMINADOS";
            }
        }else{
            arrayTitleTabs[0] = getString(R.string.label_both_participations_text);
            arrayTitleTabs[1] = arrayTitleTabs[0] + " TERMINADOS";
        }
        return arrayTitleTabs;
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
