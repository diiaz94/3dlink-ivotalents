package com.threedlink.ivotalents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.lang.reflect.Field;


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
    private static final int GRID_SIZE = 9;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout tabFotos;
    LinearLayout tabAudios;
    LinearLayout tabVideos;
    LinearLayout tabExperiencias;
    TextView tabTextFotos;
    TextView tabTextAudios;
    TextView tabTextVideos;
    TextView tabTextExperiencias;
    private OnFragmentInteractionListener mListener;
    Button logout_btn;
    TextView lblName;
    TextView lblEmail;

    private IvoTalentsApp mApp;
    private String currentTab;
    private LinearLayout currentFotoLayout;
    private ImageView currentFotoContent;
    LinearLayout[] fotoLayouts = new LinearLayout[GRID_SIZE];
    ImageView[] fotoContents = new ImageView[GRID_SIZE];
    private int currentIdx;

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
        currentTab = "tabFotos";
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

        tabFotos = (LinearLayout)view.findViewById(R.id.tabFotos);
        tabFotos.setOnClickListener(this);
        tabTextFotos = (TextView)view.findViewById(R.id.tabTextFotos);

        tabAudios = (LinearLayout)view.findViewById(R.id.tabAudios);
        tabAudios.setOnClickListener(this);
        tabTextAudios = (TextView)view.findViewById(R.id.tabTextAudios);

        tabVideos = (LinearLayout)view.findViewById(R.id.tabVideos);
        tabVideos.setOnClickListener(this);
        tabTextVideos = (TextView)view.findViewById(R.id.tabTextVideos);

        tabExperiencias = (LinearLayout)view.findViewById(R.id.tabExperiencias);
        tabExperiencias.setOnClickListener(this);
        tabTextExperiencias = (TextView)view.findViewById(R.id.tabTextExperiencias);

        //Para que se vean todos los layouts
        LinearLayout datosPrincipales = (LinearLayout)view.findViewById(R.id.datosPrincipales);
        datosPrincipales.setVisibility(View.VISIBLE);
        LinearLayout intereses = (LinearLayout)view.findViewById(R.id.intereses);
        datosPrincipales.setVisibility(View.VISIBLE);
        LinearLayout caracteristicas = (LinearLayout)view.findViewById(R.id.caracteristicas);
        datosPrincipales.setVisibility(View.VISIBLE);
        LinearLayout caracteristicas2 = (LinearLayout)view.findViewById(R.id.caracteristicas2);
        datosPrincipales.setVisibility(View.VISIBLE);

        for (int i=0;i<this.fotoLayouts.length;i++) {

            fotoLayouts[i] = (LinearLayout) view.findViewById(mApp.getResourcebyname("foto_"+String.valueOf(i+1)));
            fotoContents[i] = (ImageView) view.findViewById(mApp.getResourcebyname("foto_content"+String.valueOf(i+1)));
            fotoLayouts[i].setOnClickListener(this);
        }
        /*
        LinearLayout foto_1 = (LinearLayout)view.findViewById(R.id.foto_1);
        foto_1.setOnClickListener(this);
        LinearLayout foto_2 = (LinearLayout)view.findViewById(R.id.foto_2);
        foto_2.setOnClickListener(this);
        LinearLayout foto_3 = (LinearLayout)view.findViewById(R.id.foto_3);
        foto_3.setOnClickListener(this);
        LinearLayout foto_4 = (LinearLayout)view.findViewById(R.id.foto_4);
        foto_4.setOnClickListener(this);
        LinearLayout foto_5 = (LinearLayout)view.findViewById(R.id.foto_5);
        foto_5.setOnClickListener(this);
        LinearLayout foto_6 = (LinearLayout)view.findViewById(R.id.foto_6);
        foto_6.setOnClickListener(this);
        LinearLayout foto_7 = (LinearLayout)view.findViewById(R.id.foto_7);
        foto_7.setOnClickListener(this);
        LinearLayout foto_8 = (LinearLayout)view.findViewById(R.id.foto_8);
        foto_8.setOnClickListener(this);
        LinearLayout foto_9 = (LinearLayout)view.findViewById(R.id.foto_9);
        foto_9.setOnClickListener(this);
        */
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
            case R.id.tabFotos:
                activateTab("tabFotos");
                break;
            case R.id.tabAudios:
                activateTab("tabAudios");
                break;
            case R.id.tabVideos:
                activateTab("tabVideos");
                break;
            case R.id.tabExperiencias:
                activateTab("tabExperiencias");
                break;
            case R.id.foto_1:
                activateFotoOver(0);
                break;
            case R.id.foto_2:
                activateFotoOver(1);
                break;
            case R.id.foto_3:
                activateFotoOver(2);
                break;
            case R.id.foto_4:
                activateFotoOver(3);
                break;
            case R.id.foto_5:
                activateFotoOver(4);
                break;
            case R.id.foto_6:
                activateFotoOver(5);
                break;
            case R.id.foto_7:
                activateFotoOver(6);
                break;
            case R.id.foto_8:
                activateFotoOver(7);
                break;
            case R.id.foto_9:
                activateFotoOver(8);
                break;

        }
    }

    private void activateFotoOver(int idx) {
        currentIdx = idx;
        for (int i = 0;i<GRID_SIZE;i++){
            if(i==idx){
                fotoLayouts[i].setBackgroundColor(getResources().getColor(R.color.ivo_green));
                fotoContents[i].setVisibility(View.VISIBLE);
            }else{
                fotoLayouts[i].setBackgroundColor(getResources().getColor(R.color.ivo_gray_grid));
                fotoContents[i].setVisibility(View.GONE);
            }
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        fotoLayouts[currentIdx].setBackgroundColor(getResources().getColor(R.color.ivo_gray_grid));
                        fotoContents[currentIdx].setVisibility(View.GONE);
                    }
                },
                100);
    }

    private void activateTab(String tab){
        LinearLayout layout = null;
        TextView text = null;
        if(tab.equalsIgnoreCase("tabFotos")){
            layout = tabFotos;
            text = tabTextFotos;

        }else if(tab.equalsIgnoreCase("tabAudios")){
            layout = tabAudios;
            text = tabTextAudios;
        }else if(tab.equalsIgnoreCase("tabVideos")){
            layout = tabVideos;
            text = tabTextVideos;
        }else if(tab.equalsIgnoreCase("tabExperiencias")){
            layout = tabExperiencias;
            text = tabTextExperiencias;
        }
        if(layout!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackground(getResources().getDrawable(R.drawable.border_orange_shape_solid));
            }else{
                layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_orange_shape_solid));
            }

        }
        if(text!=null){
            text.setTextColor(getResources().getColor(R.color.white));
        }
        resetCurrentTab();
        currentTab = tab;
    }

    private void resetCurrentTab() {
        LinearLayout layout = null;
        TextView text = null;
        if(currentTab.equalsIgnoreCase("tabFotos")){
            layout = tabFotos;
            text = tabTextFotos;
        }else if(currentTab.equalsIgnoreCase("tabAudios")){
            layout = tabAudios;
            text = tabTextAudios;
        }else if(currentTab.equalsIgnoreCase("tabVideos")){
            layout = tabVideos;
            text = tabTextVideos;
        }else if(currentTab.equalsIgnoreCase("tabExperiencias")){
            layout = tabExperiencias;
            text = tabTextExperiencias;
        }
        if(layout!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackground(getResources().getDrawable(R.drawable.border_orange_shape));
            }else{
                layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_orange_shape));
            }
        }
        if(text!=null){
            text.setTextColor(getResources().getColor(R.color.ivo_orange));
        }

    }
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
