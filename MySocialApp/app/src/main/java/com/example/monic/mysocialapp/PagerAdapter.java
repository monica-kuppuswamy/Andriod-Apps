package com.example.monic.mysocialapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by monic on 11/18/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numberOfTabs;

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                FriendsFragment tab1 = new FriendsFragment();
                return tab1;
            case 1:
                AddFriendsFragment tab2 = new AddFriendsFragment();
                return tab2;
            case 2:
                FriendRequestFragment tab3 = new FriendRequestFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
