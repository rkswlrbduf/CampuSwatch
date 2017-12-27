package home.kyuyeol.campuswatch;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatFont {

    final static int MAIN_COLOR = Color.parseColor("#EBEBEC");
    final static int BLACK_COLOR = Color.parseColor("#000000");

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NanumBarunGothic.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        window.setStatusBarColor(MAIN_COLOR);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonArray = new JSONArray();

        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image1, "상품 검색")));
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.image1).setText("상품 검색"));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image2, "원단몰")));
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.image2).setText("원단몰"));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image3, "모아보기")));
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.image3).setText("모아보기"));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.selfmade_image4, "내 상품")));
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.image4).setText("내 상품"));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image5, "더보기")));
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.image5).setText("더보기"));
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);


        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        ((ImageView)tabLayout.getTabAt(0).getCustomView().findViewById(R.id.icon)).setColorFilter(MAIN_COLOR,PorterDuff.Mode.SRC_IN);
        ((TextView)tabLayout.getTabAt(0).getCustomView().findViewById(R.id.icon_name)).setTextColor(MAIN_COLOR);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                ImageView imageView = (ImageView)tab.getCustomView().findViewById(R.id.icon);
                imageView.setColorFilter(MAIN_COLOR, PorterDuff.Mode.SRC_IN);
                TextView textView = (TextView)tab.getCustomView().findViewById(R.id.icon_name);
                textView.setTextColor(MAIN_COLOR);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView imageView = (ImageView)tab.getCustomView().findViewById(R.id.icon);
                imageView.setColorFilter(BLACK_COLOR, PorterDuff.Mode.SRC_IN);
                TextView textView = (TextView)tab.getCustomView().findViewById(R.id.icon_name);
                textView.setTextColor(BLACK_COLOR);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    public View InflateTab(Context context, int drawable, String string) {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_content, null);
        TextView name = (TextView)view.findViewById(R.id.icon_name);
        ImageView image = (ImageView)view.findViewById(R.id.icon);
        image.setImageResource(drawable);
        image.setColorFilter(BLACK_COLOR, PorterDuff.Mode.SRC_IN);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/NanumBarunGothic.ttf");
        name.setTypeface(typeface);
        name.setTextColor(BLACK_COLOR);
        name.setText(string);
        return view;
    }

}
