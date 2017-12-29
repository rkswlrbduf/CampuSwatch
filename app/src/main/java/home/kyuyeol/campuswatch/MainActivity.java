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

    //final static int MAIN_COLOR = Color.parseColor("#EBEBEC");
    int MAIN_COLOR;
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

        MAIN_COLOR = getResources().getColor(R.color.mainColor);

        ButterKnife.bind(this);

        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        window.setStatusBarColor(MAIN_COLOR);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonArray = new JSONArray();

        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image1, "상품 검색")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image2, "원단몰")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image3, "모아보기")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.selfmade_image4, "내 상품")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image5, "더보기")));

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        // 색상 설정 지우기 말것
        //((ImageView)tabLayout.getTabAt(0).getCustomView().findViewById(R.id.icon)).setColorFilter(getResources().getColor(R.color.colorGray),PorterDuff.Mode.SRC_IN);
        //((TextView)tabLayout.getTabAt(0).getCustomView().findViewById(R.id.icon_name)).setTextColor(getResources().getColor(R.color.colorGray));
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                /*ImageView imageView = (ImageView)tab.getCustomView().findViewById(R.id.icon);
                imageView.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
                TextView textView = (TextView)tab.getCustomView().findViewById(R.id.icon_name);
                textView.setTextColor(Color.parseColor("#ffffff"));*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                /*ImageView imageView = (ImageView)tab.getCustomView().findViewById(R.id.icon);
                imageView.setColorFilter(getResources().getColor(R.color.colorGray), PorterDuff.Mode.SRC_IN);
                TextView textView = (TextView)tab.getCustomView().findViewById(R.id.icon_name);
                textView.setTextColor(getResources().getColor(R.color.colorGray));*/
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
//        image.setColorFilter(getResources().getColor(R.color.colorGray), PorterDuff.Mode.SRC_IN);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/NanumBarunGothic.ttf");
        name.setTypeface(typeface);
//        name.setTextColor(getResources().getColor(R.color.colorGray));
        name.setText(string);
        return view;
    }

}
