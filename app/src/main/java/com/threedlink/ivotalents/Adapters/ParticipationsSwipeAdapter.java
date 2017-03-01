package com.threedlink.ivotalents.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.threedlink.ivotalents.Auditions;
import com.threedlink.ivotalents.Castings;

/**
 * Created by diiaz94 on 28-02-2017.
 */
public class ParticipationsSwipeAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ParticipationsSwipeAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Castings tab1 = new Castings();
                return tab1;
            case 1:
                Auditions tab2 = new Auditions();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
