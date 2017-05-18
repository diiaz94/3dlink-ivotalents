package com.threedlink.ivotalents.adapters;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by vp50343 on 24/03/2017.
 */


public class UploadResourceSwipeAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;

    public UploadResourceSwipeAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= fragmentList.size()) {
            return null;
        }
        return fragmentList.get(position);
        /*
        switch (position) {
            case 0:
                UploadGalleryFile tab1 = UploadGalleryFile.newInstance("","");
                return tab1;
            case 1:
                UploadVideo tab2 = UploadVideo.newInstance("","");
                return tab2;
            case 2:
                UploadVideo tab3 = UploadVideo.newInstance("","");
                return tab3;
            case 3:
                UploadVoice tab4 = UploadVoice.newInstance("","");
                return tab4;

            default:
                return null;
        }*/
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
