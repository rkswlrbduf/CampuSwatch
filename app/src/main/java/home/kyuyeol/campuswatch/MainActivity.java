package home.kyuyeol.campuswatch;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#a62121"));

        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image1, "상품")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image2, "쇼핑몰")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image3, "모아보기")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image4, "내 상품")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getApplicationContext(), R.drawable.image5, "설정")));

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
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
        view.setPadding(35,35,35,35);

        ImageView icon = (ImageView)view.findViewById(R.id.icon);
        TextView name = (TextView)view.findViewById(R.id.icon_name);
        icon.setImageResource(drawable);
        name.setText(string);
        return view;
    }
}
