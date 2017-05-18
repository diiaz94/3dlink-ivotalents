package com.threedlink.ivotalents.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by diiaz94 on 28-02-2017.
 */
public class ParticipationsSwipeAdapter  extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    int mNumOfTabs;

    public ParticipationsSwipeAdapter(FragmentManager fm,List<Fragment> fragmentList) {
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
                ParticipationsStarted tab1 = new ParticipationsStarted();
                return tab1;
            case 1:
                ParticipationsFinished tab2 = new ParticipationsFinished();
                return tab2;

            default:
                return null;
        }*/
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
