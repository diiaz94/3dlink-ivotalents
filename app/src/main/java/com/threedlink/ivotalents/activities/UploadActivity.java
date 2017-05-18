package com.threedlink.ivotalents.activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.fragments.UploadResource;
import com.threedlink.ivotalents.adapters.UploadResourceSwipeAdapter;
import com.threedlink.ivotalents.uploadresources.UploadFromCamera;
import com.threedlink.ivotalents.uploadresources.UploadGalleryFile;
import com.threedlink.ivotalents.uploadresources.UploadPhoto;
import com.threedlink.ivotalents.uploadresources.UploadVideo;
import com.threedlink.ivotalents.uploadresources.UploadVoice;

import java.util.ArrayList;
import java.util.List;

public class UploadActivity extends AppCompatActivity implements UploadResource.OnFragmentInteractionListener,
        UploadGalleryFile.OnFragmentInteractionListener,
        UploadPhoto.OnFragmentInteractionListener,
        UploadVideo.OnFragmentInteractionListener,
        UploadVoice.OnFragmentInteractionListener,
        UploadFromCamera.OnFragmentInteractionListener{
    private UploadResourceSwipeAdapter adapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        List<Fragment> pageFragments = new ArrayList<>();
        pageFragments.add(UploadGalleryFile.newInstance("", ""));
        //pageFragments.add(UploadPhoto.newInstance("", ""));
        //pageFragments.add(UploadVideo.newInstance("", ""));
        pageFragments.add(UploadFromCamera.newInstance("",""));
        pageFragments.add(UploadVoice.newInstance("",""));
        adapter = new UploadResourceSwipeAdapter(getSupportFragmentManager(),pageFragments);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("GALERIA"));
        //tabLayout.addTab(tabLayout.newTab().setText("FOTO"));
        //tabLayout.addTab(tabLayout.newTab().setText("VIDEO"));
        tabLayout.addTab(tabLayout.newTab().setText("CAMARA"));
        tabLayout.addTab(tabLayout.newTab().setText("AUDIO"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);

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



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
