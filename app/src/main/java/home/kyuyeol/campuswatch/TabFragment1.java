package home.kyuyeol.campuswatch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import butterknife.ButterKnife;

/**
 * Created by yumin on 2017-12-01.
 */

public class TabFragment1 extends android.support.v4.app.Fragment {

    final static int MAIN_COLOR = Color.parseColor("#EBEBEC");

    private Fragment1RecyclerViewAdapter adapter;
    private static final String server_url = "http://rkswlrbduf.cafe24.com/CampuSwatch/F1/F1.php";
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout1, container, false);
        ButterKnife.bind(view);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        final List<Fragment1DataSet> data = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        imageView = (ImageView) view.findViewById(R.id.icon);
        imageView.setColorFilter(MAIN_COLOR);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, server_url, (String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Fragment1DataSet dataSet = new Fragment1DataSet();
                                dataSet.name = response.getJSONObject(i).getString("name");
                                dataSet.price = response.getJSONObject(i).getString("price");
                                dataSet.image_name = response.getJSONObject(i).getString("image_name");
                                data.add(dataSet);
                            }
                            GridLayoutManager lm = new GridLayoutManager(getActivity(), 3);
                            lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                @Override
                                public int getSpanSize(int position) {
                                    return 1;
                                }
                            });
                            adapter = new Fragment1RecyclerViewAdapter(getActivity(), data);
                            recyclerView.setLayoutManager(lm);
                            recyclerView.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("Index", position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_bottom,R.anim.anim_slide_out_top);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        requestQueue.add(jsonObjectRequest);

        return view;
    }

}
