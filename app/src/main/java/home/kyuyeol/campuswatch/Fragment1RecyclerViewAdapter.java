package home.kyuyeol.campuswatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yumin on 2017-12-01.
 */

public class Fragment1RecyclerViewAdapter extends RecyclerView.Adapter<Fragment1RecyclerViewAdapter.MyViewHolder> {

    private static final int TYPE_LEFT_THIRD = 0;
    private static final int TYPE_MIDDLE_THIRD = 1;
    private static final int TYPE_RIGHT_THIRD = 2;
    private static final int TYPE_FULL = 4;

    GradientDrawable gradientDrawable = new GradientDrawable();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<String> heartList = new ArrayList<String>();
    LayoutInflater inflater;
    List<Fragment1DataSet> data = Collections.emptyList();
    Context context;
    final String image_url = "http://rkswlrbduf.cafe24.com/Uic/F1/F1_Image/";

    String json;
    JSONArray jsonArray;

    @Override
    public int getItemViewType(int position) {
        final int modeEight = position % 3;
        switch (modeEight) {
            case 0:
                return TYPE_LEFT_THIRD;
            case 1:
                return TYPE_MIDDLE_THIRD;
            case 2:
                return TYPE_RIGHT_THIRD;
            /*case 3:
            case 5:
            case 7:
                return TYPE_LEFT_HALF;
            case 4:
            case 6:
            case 8:
                return TYPE_RIGHT_HALF;*/
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        Log.d("DATA SIZE", String.valueOf(data.size()));
        return data.size();
    }

    public Fragment1RecyclerViewAdapter(Context context, List<Fragment1DataSet> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = inflater.inflate(R.layout.fragment1_recyclerview_row, parent, false);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(Color.parseColor("#ffffff"));

        json = preferences.getString("heart", null);

        try {
            jsonArray = new JSONArray(json);
        } catch(JSONException e) {
            e.printStackTrace();
            jsonArray = new JSONArray();
        }

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final int type = viewType;
                final ViewGroup.LayoutParams lp = view.getLayoutParams();
                if (lp instanceof GridLayoutManager.LayoutParams) {
                    GridLayoutManager.LayoutParams sglp = (GridLayoutManager.LayoutParams) lp;
                    int ori_width = view.getWidth();
                    int ori_height = view.getHeight();
                    switch (type) {
                        case TYPE_LEFT_THIRD:
                            sglp.width = (parent.getWidth()) / 3;
                            sglp.height = sglp.width + view.findViewById(R.id.F1_name).getHeight() + view.findViewById(R.id.F1).getHeight();
                            view.setPadding(30, 15, 10, 0);
                            Log.d("WIDTH", String.valueOf(sglp.width) + " " + String.valueOf(ori_height) + " " + String.valueOf(ori_width) + " " + String.valueOf(sglp.height));
                            break;
                        case TYPE_MIDDLE_THIRD:
                            sglp.width = (parent.getWidth()) / 3;
                            sglp.height = sglp.width + view.findViewById(R.id.F1_name).getHeight() + view.findViewById(R.id.F1).getHeight();
                            view.setPadding(10, 15, 10, 0);
                            Log.d("WIDTH", String.valueOf(sglp.width) + " " + String.valueOf(ori_height) + " " + String.valueOf(ori_width) + " " + String.valueOf(sglp.height));
                            break;
                        case TYPE_RIGHT_THIRD:
                            sglp.width = (parent.getWidth()) / 3;
                            sglp.height = sglp.width + view.findViewById(R.id.F1_name).getHeight() + view.findViewById(R.id.F1).getHeight();
                            view.setPadding(10, 15, 30, 0);
                            Log.d("WIDTH", String.valueOf(sglp.width) + " " + String.valueOf(ori_height) + " " + String.valueOf(ori_width) + " " + String.valueOf(sglp.height));
                            break;
                    }
                    view.setLayoutParams(sglp);
                    /*final StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) ((RecyclerView) parent).getLayoutManager();
                    lm.invalidateSpanAssignments();*/
                }
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(data.get(position).name);
        holder.price.setText(data.get(position).price);
        for(int i = 0;i<jsonArray.length();i++) {
            if(jsonArray.optString(i).equals(data.get(position).code)) {
                holder.likeButton.setLiked(true);
            }
        }
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_LEFT_THIRD:
                Picasso.with(context).load(image_url + data.get(position).image_name + ".png").placeholder(gradientDrawable).transform(new CircleTransform(10, 0, 30, 0, 0)).into(holder.image);
                break;
            case TYPE_MIDDLE_THIRD:
                Picasso.with(context).load(image_url + data.get(position).image_name + ".png").placeholder(gradientDrawable).transform(new CircleTransform(10, 0, 30, 0, 0)).into(holder.image);
                break;
            case TYPE_RIGHT_THIRD:
                Picasso.with(context).load(image_url + data.get(position).image_name + ".png").placeholder(gradientDrawable).transform(new CircleTransform(10, 0, 30, 0, 0)).into(holder.image);
                break;
            case TYPE_FULL:
                Picasso.with(context).load(image_url + data.get(position).image_name + ".png").placeholder(gradientDrawable).transform(new CircleTransform(10, 30, 30, 30, 0)).into(holder.image);
                break;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView price;
        LikeButton likeButton;

        public MyViewHolder(final View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.F1_image);
            name = (TextView) itemView.findViewById(R.id.F1_name);
            price = (TextView) itemView.findViewById(R.id.F1_price);
            likeButton = (LikeButton) itemView.findViewById(R.id.f1_heart);

            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    String json = preferences.getString("heart", null);
                    JSONArray jsonArray = new JSONArray();
                    if (json != null) {
                        try {
                            jsonArray = new JSONArray(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    jsonArray.put(data.get(getAdapterPosition()).code);
                    editor.putString("heart", jsonArray.toString());
                    editor.apply();
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    String json = preferences.getString("heart", null);
                    JSONArray jsonArray = new JSONArray();
                    if (json != null) {
                        try {
                            jsonArray = new JSONArray(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for(int i = 0;i<jsonArray.length();i++) {
                        if(jsonArray.optString(i).equals(data.get(getAdapterPosition()).code)) {
                            jsonArray.remove(i);
                        }
                    }
                    editor.putString("heart", jsonArray.toString());
                    editor.apply();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    Bundle bundle = new Bundle();
                    bundle.putString("Code", data.get(getAdapterPosition()).code);
                    Log.d("Code", data.get(getAdapterPosition()).code);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
                }
            });
        }
    }
}
