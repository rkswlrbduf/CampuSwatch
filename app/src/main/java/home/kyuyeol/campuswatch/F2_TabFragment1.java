package home.kyuyeol.campuswatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class F2_TabFragment1 extends android.support.v4.app.Fragment {

    private Fragment2RecyclerViewAdapter adapter;
    private static final String server_url = "http://rkswlrbduf.cafe24.com/CampuSwatch/F2/F2.php";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f2_tab_layout1, container, false);
        ButterKnife.bind(view);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        final List<Fragment2DataSet> data = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, server_url, (String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++) {
                                Fragment2DataSet dataSet = new Fragment2DataSet();
                                dataSet.name = response.getJSONObject(i).getString("name");
                                dataSet.number = response.getJSONObject(i).getString("number");
                                dataSet.image_name = response.getJSONObject(i).getString("image_name");
                                data.add(dataSet);
                            }
                            adapter = new Fragment2RecyclerViewAdapter(getActivity(), data);
                            recyclerView.setAdapter(adapter);
                            recyclerView.addItemDecoration(new DeviderItemDecoration(getActivity(), DeviderItemDecoration.VERTICAL_LIST));
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        return view;
    }
}
