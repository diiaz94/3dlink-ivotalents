package com.threedlink.ivotalents.Adapters;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.threedlink.ivotalents.UploadResources.UploadGalleryFile;
import com.threedlink.ivotalents.UploadResources.UploadPhoto;
import com.threedlink.ivotalents.UploadResources.UploadVideo;
import com.threedlink.ivotalents.UploadResources.UploadVoice;

/**
 * Created by vp50343 on 24/03/2017.
 */


public class UploadResourceSwipeAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public UploadResourceSwipeAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                UploadGalleryFile tab1 = new UploadGalleryFile();
                return tab1;
            case 1:
                UploadPhoto tab2 = new UploadPhoto();
                return tab2;
            case 2:
                UploadVideo tab3 = new UploadVideo();
                return tab3;
            case 3:
                UploadVoice tab4 = new UploadVoice();
                return tab4;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
