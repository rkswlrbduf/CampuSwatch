package home.kyuyeol.campuswatch;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yumin on 2017-12-01.
 */

public class TabFragment2 extends android.support.v4.app.Fragment {

    private Fragment2RecyclerViewAdapter adapter;
    private static final String server_url = "http://rkswlrbduf.cafe24.com/CampuSwatch/F2/F2.php";

    TabLayout tabLayout;
    ViewPager viewPager;

    int SELECTED_COLOR;
    int UNSELECTED_COLOR;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_layout2, container, false);
        ButterKnife.bind(container);

        SELECTED_COLOR = getResources().getColor(R.color.tabColor);
        UNSELECTED_COLOR = getResources().getColor(R.color.f2UnseletedTab);

        tabLayout = (TabLayout)view.findViewById(R.id.F2_Tab);
        viewPager = (ViewPager)view.findViewById(R.id.F2_view_pager);
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getActivity(), "원단 공장 랭킹")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(InflateTab(getActivity(), "즐겨찾기")));

        ((TextView)tabLayout.getTabAt(0).getCustomView().findViewById(R.id.icon_name)).setTextColor(SELECTED_COLOR);

        F2_PagerAdapter pagerAdapter = new F2_PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                TextView textView = (TextView)tab.getCustomView().findViewById(R.id.icon_name);
                textView.setTextColor(SELECTED_COLOR);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = (TextView)tab.getCustomView().findViewById(R.id.icon_name);
                textView.setTextColor(UNSELECTED_COLOR);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    public View InflateTab(Context context, String string) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.f2_content, null);
        view.setPadding(15,15,15,15);
        TextView name = (TextView)view.findViewById(R.id.icon_name);
        name.setTextColor(UNSELECTED_COLOR);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/NanumBarunGothic.ttf");
        name.setTypeface(typeface);
        name.setText(string);
        return view;
    }
}
