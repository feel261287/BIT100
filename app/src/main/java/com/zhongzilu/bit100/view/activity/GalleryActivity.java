package com.zhongzilu.bit100.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.widget.TouchImageView;

/**
 * 图片画廊，用于查看网页上的图片
 * Created by zhongzilu on 2016-09-16.
 */
public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity==>";

    //Extra Tag
    public static final String EXTRA_IMAGES_LIST = "images_list";
    public static final String EXTRA_CURRENT_IMAGE_POSITION = "image_position";

    //Extra Value
    private String[] mList;
    private int mChosePosition;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mViewPager = (ViewPager) findViewById(R.id.container);

        Intent intent = getIntent();
        if (intent != null){
            mList = intent.getStringArrayExtra(EXTRA_IMAGES_LIST);
            mChosePosition = intent.getIntExtra(EXTRA_CURRENT_IMAGE_POSITION, 0);
        }

        // 如果mList为空，则不再进行其他操作
        if (mList == null) {
            LogUtil.d(TAG, "onCreate: mList is null");
            return;
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mList);
        mViewPager.setOffscreenPageLimit(2);
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(mChosePosition, true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String TAG = "PlaceholderFragment==>";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_IMAGE_URL = "image_url";
        private View contentView;

        public PlaceholderFragment() {}

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(String url) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_IMAGE_URL, url);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (contentView == null)
                contentView = inflater.inflate(R.layout.fragment_gallery, container, false);
            return contentView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            TouchImageView imageView = (TouchImageView) view.findViewById(R.id.img_touch_image);
            String url = getArguments().getString(ARG_IMAGE_URL);
            LogUtil.d(TAG, "onViewCreated: url==>" + url);
            Glide.with(getContext())
                    .load(url)
                    .placeholder(R.drawable.image_default)
                    .error(R.drawable.image_default)
                    .into(imageView);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private String[] imgSrc;

        public SectionsPagerAdapter(FragmentManager fm, String[] imgS) {
            super(fm);
            this.imgSrc = imgS;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(imgSrc[position]);
        }

        @Override
        public int getCount() {
            // Show imgSrc length total pages.
            return imgSrc.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }
}