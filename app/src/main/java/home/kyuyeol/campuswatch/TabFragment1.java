package home.kyuyeol.campuswatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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

public class TabFragment1 extends android.support.v4.app.Fragment {

    private Fragment1RecyclerViewAdapter adapter;
    private static final String server_url = "http://rkswlrbduf.cafe24.com/CampuSwatch/F1/F1.php";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout1, container, false);
        ButterKnife.bind(view);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        final List<Fragment1DataSet> data = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, server_url, (String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++) {
                                Fragment1DataSet dataSet = new Fragment1DataSet();
                                dataSet.name = response.getJSONObject(i).getString("name");
                                dataSet.price = response.getJSONObject(i).getString("price");
                                dataSet.image_name = response.getJSONObject(i).getString("image_name");
                                data.add(dataSet);
                            }
                            adapter = new Fragment1RecyclerViewAdapter(getActivity(), data);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
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
