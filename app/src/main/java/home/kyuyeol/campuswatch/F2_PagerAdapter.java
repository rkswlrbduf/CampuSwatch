package home.kyuyeol.campuswatch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by yumin on 2017-12-01.
 */

public class F2_PagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public F2_PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                F2_TabFragment1 tabFragment1 = new F2_TabFragment1();
                return tabFragment1;
            case 1:
                F2_TabFragment2 tabFragment2 = new F2_TabFragment2();
                return tabFragment2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
