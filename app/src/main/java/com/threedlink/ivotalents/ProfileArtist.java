package com.threedlink.ivotalents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileArtist.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileArtist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileArtist extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Button logout_btn;
    TextView lblName;
    TextView lblEmail;

    private IvoTalentsApp mApp;
    public ProfileArtist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Parameter 1.
     * @param email Parameter 2.
     * @return A new instance of fragment ProfileArtist.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileArtist newInstance(String name, String email) {
        ProfileArtist fragment = new ProfileArtist();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ((IvoTalentsApp) getActivity().getApplicationContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(NAME);
            mParam2 = getArguments().getString(EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_profile_artist, container, false);
        logout_btn = (Button)view.findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(this);
        lblName = (TextView) view.findViewById(R.id.lblName);
        lblEmail = (TextView) view.findViewById(R.id.lblEmail);
        //Manejo de tabs

        TabHost tabs = (TabHost)view.findViewById(R.id.tabHost);
        tabs.setup();

        TabHost.TabSpec spec;

        spec = tabs.newTabSpec("Fotos");
        spec.setContent(R.id.tab_fotos);
        spec.setIndicator("Fotos");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("Audios");
        spec.setContent(R.id.tab_fotos);
        spec.setIndicator("Audios");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("Videos");
        spec.setContent(R.id.tab_fotos);
        spec.setIndicator("Videos");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("Experiencia");
        spec.setContent(R.id.tab_fotos);
        spec.setIndicator("Experiencia");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        String name = getArguments().getString(NAME);
        String email = getArguments().getString(EMAIL);
        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));
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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.logout_btn:
                mApp.logout(v);
                break;
        }
    }


}
