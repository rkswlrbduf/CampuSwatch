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

    int IMAGE_COLOR;

    private Fragment1RecyclerViewAdapter adapter;
    private static final String server_url = "http://rkswlrbduf.cafe24.com/Uic/F1/F1.php";
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout1, container, false);
        ButterKnife.bind(view);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        final List<Fragment1DataSet> data = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        IMAGE_COLOR = getResources().getColor(R.color.statusColor);

        imageView = (ImageView) view.findViewById(R.id.icon);
        imageView.setColorFilter(IMAGE_COLOR);

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
                                dataSet.code = response.getJSONObject(i).getString("code");
                                data.add(dataSet);
                            }
                            GridLayoutManager lm = new GridLayoutManager(getActivity().getApplicationContext(),6);
                            lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                @Override
                                public int getSpanSize(int position) {
                                    switch (position) {
                                        case 0:
                                        case 1:
                                        case 2:
                                            return 2;
                                        default:
                                            return 3;
                                    }
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

        /*recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                *//*Bundle bundle = new Bundle();
                bundle.putString("Code", data.get(position).code);
                Log.d("Code", data.get(position).code);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_bottom,R.anim.anim_slide_out_top);*//*
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));*/
        requestQueue.add(jsonObjectRequest);

        return view;
    }

}
