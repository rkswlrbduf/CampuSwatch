package home.kyuyeol.campuswatch;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumin on 2017-12-01.
 */

public class TabFragment4 extends android.support.v4.app.Fragment {

    private Fragment4RecyclerViewAdapter adapter;
    private static final String server_url = "http://rkswlrbduf.cafe24.com/Uic/F1/F1.php";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    RecyclerView recyclerView;
    List<Fragment4DataSet> data;
    RequestQueue requestQueue;

    String json;
    JSONArray jsonArray;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout4, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());

        json = preferences.getString("heart", null);

        if (json != null) {
            try {
                jsonArray = new JSONArray(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            jsonArray = new JSONArray();
        }

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, server_url, (String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Fragment4DataSet dataSet = new Fragment4DataSet();
                                dataSet.name = response.getJSONObject(i).getString("name");
                                dataSet.price = response.getJSONObject(i).getString("price");
                                dataSet.image_name = response.getJSONObject(i).getString("image_name");
                                dataSet.code = response.getJSONObject(i).getString("code");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    if (jsonArray.optString(i).equals(dataSet.code)) {
                                        data.add(dataSet);
                                        Log.d("TAG", "TAG");
                                    }
                                }
                            }
                            GridLayoutManager lm = new GridLayoutManager(getActivity().getApplicationContext(), 3);
                            lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                @Override
                                public int getSpanSize(int position) {
                                    return 1;
                                }
                            });
                            adapter = new Fragment4RecyclerViewAdapter(getActivity(), data);
                            recyclerView.setLayoutManager(lm);
                            recyclerView.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);

        return view;
    }
}
