package com.threedlink.ivotalents.Adapters;

/**
 * Created by diiaz94 on 19-01-2017.
 */


import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.threedlink.ivotalents.ReceivedMessages;
import com.threedlink.ivotalents.SentMessages;


public class MessagesSwipeAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MessagesSwipeAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ReceivedMessages tab1 = new ReceivedMessages();
                return tab1;
            case 1:
                SentMessages tab2 = new SentMessages();
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