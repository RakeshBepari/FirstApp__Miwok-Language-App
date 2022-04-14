package com.example.miwoklanguageapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SimpleFragmentAdapter extends FragmentPagerAdapter {

    private Context mcontext;

    public SimpleFragmentAdapter(@NonNull FragmentManager fm, int behavior,Context mcontext) {
        super(fm, behavior);
        this.mcontext=mcontext;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return mcontext.getString(R.string.numbers);
        else if (position==1)
            return mcontext.getString(R.string.category_family);
        else if (position==2)
            return mcontext.getString(R.string.colors);
        else
            return mcontext.getString(R.string.phrases);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new NumbersFragment();
        else if (position==1)
            return new FamilyMembersFragment();
        else if (position==2)
            return new ColorsFragments();
        else
            return new PhrasesFragments();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
