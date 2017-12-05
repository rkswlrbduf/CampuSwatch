package home.kyuyeol.campuswatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import me.grantland.widget.AutofitHelper;

/**
 * Created by yumin on 2017-12-01.
 */

public class TabFragment3 extends android.support.v4.app.Fragment {

    RecyclerView categoryRecyclerView;
    RecyclerView bannerRecyclerView;
    Fragment3CategoryRecyclerViewAdapter categoryAdapter;
    Fragment3BannerRecyclerViewAdapter bannerAdapter;
    private static final String category_server_url = "http://rkswlrbduf.cafe24.com/CampuSwatch/F3/F3_Category/F3_Category.php";
    private static final String banner_server_url = "http://rkswlrbduf.cafe24.com/CampuSwatch/F3/F3_Banner/F3_Banner.php";
    List<Fragment3CategoryDataSet> categoryData = new ArrayList<>();
    List<Fragment3BannerDataSet> bannerData = new ArrayList<>();

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout3, container, false);

        textView = (TextView)view.findViewById(R.id.textview);
        AutofitHelper.create(textView);

        categoryRecyclerView = (RecyclerView)view.findViewById(R.id.f3_category_recylcerview);
        bannerRecyclerView = (RecyclerView)view.findViewById(R.id.f3_banner_recyclerview);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequestCategory = new JsonArrayRequest(Request.Method.GET, category_server_url, (String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0;i<response.length();i++) {
                                Fragment3CategoryDataSet dataSet = new Fragment3CategoryDataSet();
                                dataSet.name = response.getJSONObject(i).getString("name");
                                dataSet.image_name = response.getJSONObject(i).getString("image_name");
                                categoryData.add(dataSet);
                            }
                            categoryAdapter = new Fragment3CategoryRecyclerViewAdapter(getActivity(), categoryData);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        JsonArrayRequest jsonArrayRequestBanner = new JsonArrayRequest(Request.Method.GET, banner_server_url, (String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0;i<response.length();i++) {
                                Fragment3BannerDataSet dataSet = new Fragment3BannerDataSet();
                                dataSet.image_name = response.getJSONObject(i).getString("image_name");
                                bannerData.add(dataSet);
                            }
                            bannerAdapter = new Fragment3BannerRecyclerViewAdapter(getActivity(), bannerData);
                            StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                            bannerRecyclerView.setLayoutManager(lm);
                            bannerRecyclerView.setAdapter(bannerAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequestCategory);
        requestQueue.add(jsonArrayRequestBanner);

        return view;
    }
}
