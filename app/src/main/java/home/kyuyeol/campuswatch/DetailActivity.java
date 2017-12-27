package home.kyuyeol.campuswatch;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by yumin on 2017-12-18.
 */

public class DetailActivity extends AppCompatFont {

    @BindView(R.id.detail_main_image)
    ImageView mainImage;
    @BindView(R.id.detail_code)
    TextView code;
    @BindView(R.id.detail_name)
    TextView name;
    @BindView(R.id.detail_description)
    TextView description;
    @BindView(R.id.detail_price)
    TextView price;
    @BindView(R.id.detail_delivery)
    TextView delivery;
    @BindView(R.id.detail_total)
    TextView total;
    @BindView(R.id.detail_product_image)
    ImageView productImage;

    private int sum = 0;

    private String server_url = "http://rkswlrbduf.cafe24.com/Uic/Detail/F1.php";
    private final String image_url = "http://rkswlrbduf.cafe24.com/Uic/Detail/F1_Image/";

    final static int MAIN_COLOR = Color.parseColor("#EBEBEC");

    GradientDrawable gradientDrawable = new GradientDrawable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NanumBarunGothic.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.detail_activity);

        ButterKnife.bind(this);

        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(Color.parseColor("#ffffff"));

        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        window.setStatusBarColor(MAIN_COLOR);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();

        server_url += "?Code=";
        server_url += bundle.getString("Code");

        Log.d("SERVER", server_url);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, server_url, (String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            name.setText(response.getJSONObject(0).getString("name"));
                            description.setText(response.getJSONObject(0).getString("description"));
                            price.setText(response.getJSONObject(0).getString("price"));
                            delivery.setText(String.valueOf(2500));
                            sum = Integer.parseInt(price.getText().toString()) + Integer.parseInt(delivery.getText().toString());
                            total.setText(String.valueOf(sum));
                            code.setText(response.getJSONObject(0).getString("code"));
                            Picasso.with(getApplicationContext()).load(image_url + response.getJSONObject(0).getString("main_image") + ".png").placeholder(gradientDrawable).transform(new CircleTransform(10, 0, 0, 0, 0)).into(mainImage);
                            Picasso.with(getApplicationContext()).load(image_url + response.getJSONObject(0).getString("product_image") + ".png").placeholder(gradientDrawable).into(productImage);
                            Log.d("TAG", response.getJSONObject(0).getString("product_image"));
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

    }


}
