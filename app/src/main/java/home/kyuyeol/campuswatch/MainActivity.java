package home.kyuyeol.campuswatch;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatFont {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NotoSansCJKkr-Medium.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#a62121"));

        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image1, "상품")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image2, "원단몰")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image3, "모아보기")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image4, "내 상품")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image5, "설정")));
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    public static View InflateTab(Context context, int drawable, String string) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.activity_content, null);
        view.setPadding(15,15,15,15);

        ImageView icon = (ImageView)view.findViewById(R.id.icon);
        TextView name = (TextView)view.findViewById(R.id.icon_name);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/NotoSansMonoCJKkr-Bold.otf");
        name.setTypeface(typeface);
        icon.setImageResource(drawable);
        name.setText(string);
        return view;
    }
}
