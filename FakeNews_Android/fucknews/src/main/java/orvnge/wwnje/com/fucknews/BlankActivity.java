package orvnge.wwnje.com.fucknews;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.orvnge.xutils.MyFragment;
import com.orvnge.xutils.TextProvider;

import java.util.ArrayList;
import java.util.List;

public class BlankActivity extends FragmentActivity implements TextProvider {

    private Button mAdd;
    private Button mRemove;

    public List<String> Frags;
    public List<String> FragsURL;


    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager mPager;
    private MyPagerAdapter mAdapter;

    private ArrayList<String> mEntries = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        mToolbar = (Toolbar) findViewById(R.id.blank_toolbar);
        mToolbar.setTitle(R.string.app_name);

        mEntries.add("pos 1");
        mEntries.add("pos 2");

        mAdd = (Button) findViewById(R.id.blank_add);
        mRemove = (Button) findViewById(R.id.blank_remove);
        mPager = (ViewPager) findViewById(R.id.blank_pager);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewItem();
            }
        });

        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCurrentItem();
            }
        });

        mAdapter = new MyPagerAdapter(this.getSupportFragmentManager(), this);

        mPager.setAdapter(mAdapter);

    }

    private void addNewItem() {
        mEntries.add("new item");
        mAdapter.notifyDataSetChanged();
        mAdapter.notifyChangeInPosition(1);
    }

    private void removeCurrentItem() {
        int position = mPager.getCurrentItem();
        mEntries.remove(position);
        mAdapter.notifyDataSetChanged();
        mAdapter.notifyChangeInPosition(1);
    }

    @Override
    public String getTextForPosition(int position) {
        return mEntries.get(position);
    }
    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public Fragment getTrag(int position) {
        return null;
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        private TextProvider mProvider;
        private long baseId = 0;

        public MyPagerAdapter(FragmentManager fm, TextProvider provider) {
            super(fm);
            this.mProvider = provider;
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.newInstance(mProvider.getTextForPosition(position));
        }

        @Override
        public int getCount() {
            return mProvider.getCount();
        }


        //this is called when notifyDataSetChanged() is called
        @Override
        public int getItemPosition(Object object) {
            // refresh all fragments when data set changed
            return PagerAdapter.POSITION_NONE;
        }


        @Override
        public long getItemId(int position) {
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        /**
         * Notify that the position of a fragment has been changed.
         * Create a new ID for each position to force recreation of the fragment
         * @param n number of items which have been changed
         */
        public void notifyChangeInPosition(int n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }

    }

}

