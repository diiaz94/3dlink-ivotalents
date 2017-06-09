package com.threedlink.ivotalents.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.adapters.ProfileSwipeAdapter;
import com.threedlink.ivotalents.fragments.profiletabs.ProfileAudioList;
import com.threedlink.ivotalents.fragments.profiletabs.ProfileExperience;
import com.threedlink.ivotalents.fragments.profiletabs.ProfilePhotoGrid;
import com.threedlink.ivotalents.fragments.profiletabs.ProfileVideoGrid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


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
    private String mName;
    private String mEmail;
    LinearLayout tabFotos;
    LinearLayout tabAudios;
    LinearLayout tabVideos;
    LinearLayout tabExperiencias;
    LinearLayout tabContentFotos;
    LinearLayout tabContentAudios;
    LinearLayout tabContentVideos;
    LinearLayout tabContentExperiencias;
    TextView tabTextFotos;
    TextView tabTextAudios;
    TextView tabTextVideos;
    TextView tabTextExperiencias;
    private OnFragmentInteractionListener mListener;
    Button logout_btn;
    TextView lblName;
    TextView lblEmail;
    ImageView ic_fb_orange;
    ImageView ic_tw_orange;
    ImageView ic_ig_orange;
    private IvoTalentsApp mApp;
    private String currentTab;
    private LinearLayout currentLayoutContent;

    LinearLayout[] fotoLayouts = new LinearLayout[GRID_SIZE];
    ImageView[] fotoContents = new ImageView[GRID_SIZE];
    private int currentIdx;

    TextView text_bio;
    EditText edit_bio;
    ImageButton ic_edit_bio;
    ImageButton ic_save_bio;
    ImageButton ic_edit_social;
    ImageButton ic_save_social;
    LinearLayout left_info;
    LinearLayout left_info_edit;


    EditText url_social_fb_artist;
    EditText url_social_tw_artist;
    EditText url_social_ig_artist;
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
            mName = getArguments().getString(NAME);
            mEmail = getArguments().getString(EMAIL);
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

        tabContentFotos= (LinearLayout)view.findViewById(R.id.tabContentFotos);
        tabContentFotos.setVisibility(View.VISIBLE);
        tabFotos = (LinearLayout)view.findViewById(R.id.tabFotos);
        tabFotos.setOnClickListener(this);
        tabTextFotos = (TextView)view.findViewById(R.id.tabTextFotos);

        tabContentAudios= (LinearLayout)view.findViewById(R.id.tabContentAudios);
        tabAudios = (LinearLayout)view.findViewById(R.id.tabAudios);
        tabAudios.setOnClickListener(this);
        tabTextAudios = (TextView)view.findViewById(R.id.tabTextAudios);

        tabContentVideos= (LinearLayout)view.findViewById(R.id.tabContentVideos);
        tabVideos = (LinearLayout)view.findViewById(R.id.tabVideos);
        tabVideos.setOnClickListener(this);
        tabTextVideos = (TextView)view.findViewById(R.id.tabTextVideos);

        tabContentExperiencias= (LinearLayout)view.findViewById(R.id.tabContentExperiencias);
        tabExperiencias = (LinearLayout)view.findViewById(R.id.tabExperiencias);
        tabExperiencias.setOnClickListener(this);
        tabTextExperiencias = (TextView)view.findViewById(R.id.tabTextExperiencias);



        //Para que se vean todos los layouts
        LinearLayout datosPrincipales = (LinearLayout)view.findViewById(R.id.datosPrincipales);
        datosPrincipales.setVisibility(View.VISIBLE);
        LinearLayout intereses = (LinearLayout)view.findViewById(R.id.intereses);
        intereses.setVisibility(View.VISIBLE);
        LinearLayout caracteristicas = (LinearLayout)view.findViewById(R.id.caracteristicas);
        caracteristicas.setVisibility(View.VISIBLE);
        LinearLayout caracteristicas2 = (LinearLayout)view.findViewById(R.id.caracteristicas2);
        caracteristicas2.setVisibility(View.VISIBLE);
        LinearLayout tabsSection = (LinearLayout)view.findViewById(R.id.tabsSection);
        tabsSection.setVisibility(View.VISIBLE);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("FOTOS"));
        tabLayout.addTab(tabLayout.newTab().setText("AUDIOS"));
        tabLayout.addTab(tabLayout.newTab().setText("VIDEOS"));
        tabLayout.addTab(tabLayout.newTab().setText("EXPERIENCIA"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);

        List<Fragment> pageFragments = new ArrayList<>();
        pageFragments.add(ProfilePhotoGrid.newInstance(mEmail, ""));
        pageFragments.add(ProfileAudioList.newInstance(mEmail,""));
        pageFragments.add(ProfileVideoGrid.newInstance(mEmail,""));
        pageFragments.add(ProfileExperience.newInstance(mEmail,""));
        final ProfileSwipeAdapter adapter = new ProfileSwipeAdapter
                (getActivity().getSupportFragmentManager(), pageFragments);
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
/*
        for (int i=0;i<this.fotoLayouts.length;i++) {

            fotoLayouts[i] = (LinearLayout) view.findViewById(mApp.getResourcebyname("foto_"+String.valueOf(i+1)));
            fotoContents[i] = (ImageView) view.findViewById(mApp.getResourcebyname("foto_content"+String.valueOf(i+1)));
            fotoLayouts[i].setOnClickListener(this);
        }
        activateTab("tabFotos");
        ScrollView parentScrollView = (ScrollView) view.findViewById(R.id.parentScrollView);
        parentScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event)
            {
                v.findViewById(R.id.childScrollView).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        ScrollView childScrollView = (ScrollView) view.findViewById(R.id.childScrollView);
        childScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event)
            {   //Disallow the touch request for parent scroll on touch of
                //child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
*/
        ic_fb_orange = (ImageView) view.findViewById(R.id.ic_fb_orange);
        ic_fb_orange.setOnClickListener(this);
        ic_tw_orange = (ImageView) view.findViewById(R.id.ic_tw_orange);
        ic_tw_orange.setOnClickListener(this);
        ic_ig_orange = (ImageView) view.findViewById(R.id.ic_ig_orange);
        ic_ig_orange.setOnClickListener(this);



        RelativeLayout myLayout = (RelativeLayout) view.findViewById(R.id.fragment_profile_artist);
        mApp.applyFonts(myLayout);

        ic_edit_social = (ImageButton)view.findViewById(R.id.ic_edit_social);
        ic_edit_social.setOnClickListener(this);
        ic_edit_bio = (ImageButton)view.findViewById(R.id.ic_edit_bio);
        ic_edit_bio.setOnClickListener(this);
        ic_save_social = (ImageButton)view.findViewById(R.id.ic_save_social);
        ic_save_social.setOnClickListener(this);
        ic_save_bio = (ImageButton)view.findViewById(R.id.ic_save_bio);
        ic_save_bio.setOnClickListener(this);

        text_bio = (TextView)view.findViewById(R.id.text_bio);
        edit_bio = (EditText)view.findViewById(R.id.edit_bio);

        left_info = (LinearLayout) view.findViewById(R.id.left_info);
        left_info_edit = (LinearLayout) view.findViewById(R.id.left_info_edit);


        url_social_fb_artist = (EditText) view.findViewById(R.id.url_social_fb_artist);
        url_social_tw_artist = (EditText) view.findViewById(R.id.url_social_tw_artist);
        url_social_ig_artist = (EditText) view.findViewById(R.id.url_social_ig_artist);
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
            case R.id.ic_fb_orange:
                break;
            case R.id.ic_tw_orange:
                break;
            case R.id.ic_ig_orange:
                break;
            case R.id.ic_edit_social:
                editSocialMedia();
                break;
            case R.id.ic_save_social:
                saveSocialmedia();
                break;
            case R.id.ic_edit_bio:
                editBio(v);
                break;
            case R.id.ic_save_bio:
                saveBio(v);
                break;

        }
    }

    private void editBio(View v) {
        edit_bio.setText(text_bio.getText());
        text_bio.setVisibility(View.GONE);
        edit_bio.setVisibility(View.VISIBLE);
        ic_edit_bio.setVisibility(View.GONE);
        ic_save_bio.setVisibility(View.VISIBLE);

    }

    private void saveBio(View v) {
        text_bio.setText(edit_bio.getText());
        text_bio.setVisibility(View.VISIBLE);
        edit_bio.setVisibility(View.GONE);
        ic_edit_bio.setVisibility(View.VISIBLE);
        ic_save_bio.setVisibility(View.GONE);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void editSocialMedia() {
        left_info.setVisibility(View.GONE);
        left_info_edit.setVisibility(View.VISIBLE);
    }

    private void saveSocialmedia() {
        left_info.setVisibility(View.VISIBLE);
        left_info_edit.setVisibility(View.GONE);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);


        ic_fb_orange.setImageDrawable(getResources().getDrawable(url_social_fb_artist.getText().toString().isEmpty()?R.drawable.fb_logo_orange_disabled:R.drawable.fb_logo_orange));
        ic_tw_orange.setImageDrawable(getResources().getDrawable(url_social_tw_artist.getText().toString().isEmpty()?R.drawable.tw_logo_orange_disabled:R.drawable.tw_logo_orange));
        ic_ig_orange.setImageDrawable(getResources().getDrawable(url_social_ig_artist.getText().toString().isEmpty()?R.drawable.ig_logo_orange_disabled:R.drawable.ig_logo_orange));

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
        if(currentTab!=tab){
            LinearLayout layout = null;
            TextView text = null;
            LinearLayout layoutContent = null;
            if(tab.equalsIgnoreCase("tabFotos")){
                layout = tabFotos;
                text = tabTextFotos;
                layoutContent = tabContentFotos;

            }else if(tab.equalsIgnoreCase("tabAudios")){
                layout = tabAudios;
                text = tabTextAudios;
                layoutContent = tabContentAudios;
            }else if(tab.equalsIgnoreCase("tabVideos")){
                layout = tabVideos;
                text = tabTextVideos;
                layoutContent = tabContentVideos;
            }else if(tab.equalsIgnoreCase("tabExperiencias")){
                layout = tabExperiencias;
                text = tabTextExperiencias;
                layoutContent = tabContentExperiencias;
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
            if(layoutContent!=null){
                layoutContent.setVisibility(View.VISIBLE);
            }

            resetCurrentTab();
            currentTab = tab;
            currentLayoutContent = layoutContent;
        }
    }

    private void resetCurrentTab() {
        LinearLayout layout = null;
        TextView text = null;
        LinearLayout layoutContent = null;
        if(currentTab.equalsIgnoreCase("tabFotos")){
            layout = tabFotos;
            text = tabTextFotos;
            layoutContent = tabContentFotos;

        }else if(currentTab.equalsIgnoreCase("tabAudios")){
            layout = tabAudios;
            text = tabTextAudios;
            layoutContent = tabContentAudios;
        }else if(currentTab.equalsIgnoreCase("tabVideos")){
            layout = tabVideos;
            text = tabTextVideos;
            layoutContent = tabContentVideos;
        }else if(currentTab.equalsIgnoreCase("tabExperiencias")){
            layout = tabExperiencias;
            text = tabTextExperiencias;
            layoutContent = tabContentExperiencias;
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
        if(layoutContent!=null){
            layoutContent.setVisibility(View.GONE);
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
