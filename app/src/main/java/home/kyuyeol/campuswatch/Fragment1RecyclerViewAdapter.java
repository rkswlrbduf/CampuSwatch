package home.kyuyeol.campuswatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
    private static final int TYPE_LEFT_HALF = 3;
    private static final int TYPE_RIGHT_HALF = 4;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LayoutInflater inflater;
    List<Fragment1DataSet> data = Collections.emptyList();
    Context context;
    final String image_url = "http://rkswlrbduf.cafe24.com/Uic/F1/F1_Image/";

    String json;
    JSONArray jsonArray;

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_LEFT_THIRD;
            case 1:
                return TYPE_MIDDLE_THIRD;
            case 2:
                return TYPE_RIGHT_THIRD;
        }
        int mode = position % 2;
        switch (mode) {
            case 0:
                return TYPE_RIGHT_HALF;
            case 1:
                return TYPE_LEFT_HALF;
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

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final int type = viewType;
                final ViewGroup.LayoutParams lp = view.getLayoutParams();
                if (lp instanceof GridLayoutManager.LayoutParams) {
                    GridLayoutManager.LayoutParams sglp = (GridLayoutManager.LayoutParams) lp;
                    switch (type) {
                        case TYPE_LEFT_THIRD:
                            sglp.width = parent.getWidth() / 3;
                            sglp.height = sglp.width + view.findViewById(R.id.F1).getHeight();
                            view.setLayoutParams(sglp);
                            view.setPadding(10, 0, 10, 0);
                            break;
                        case TYPE_MIDDLE_THIRD:
                            sglp.width = parent.getWidth() / 3;
                            sglp.height = sglp.width + view.findViewById(R.id.F1).getHeight();
                            view.setLayoutParams(sglp);
                            view.setPadding(10, 0, 10, 0);
                            break;
                        case TYPE_RIGHT_THIRD:
                            sglp.width = parent.getWidth() / 3;
                            sglp.height = sglp.width + view.findViewById(R.id.F1).getHeight();
                            view.setLayoutParams(sglp);
                            view.setPadding(10, 0, 10, 0);
                            break;
                        case TYPE_LEFT_HALF:
                            sglp.width = parent.getWidth() / 2;
                            sglp.height = sglp.width + view.findViewById(R.id.F1).getHeight();
                            view.setLayoutParams(sglp);
                            view.setPadding(10, 0, 5, 0);
                            break;
                        case TYPE_RIGHT_HALF:
                            sglp.width = parent.getWidth() / 2;
                            sglp.height = sglp.width + view.findViewById(R.id.F1).getHeight();
                            view.setLayoutParams(sglp);
                            view.setPadding(5, 0, 10, 0);
                            break;
                    }
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
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.optString(i).equals(data.get(position).code)) {
                holder.likeButton.setLiked(true);
            }
        }
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_LEFT_THIRD:
            case TYPE_MIDDLE_THIRD:
            case TYPE_RIGHT_THIRD:
                holder.name.setTextSize(11);
                holder.price.setTextSize(11);
                break;
            case TYPE_LEFT_HALF:
            case TYPE_RIGHT_HALF:
                holder.name.setTextSize(13);
                holder.price.setTextSize(13);
                break;
        }
        Picasso.with(context).load(image_url + data.get(position).image_name + ".png").placeholder(R.drawable.progress_animation).transform(new CircleTransform(15, 0, 0, 0, 0)).into(holder.image);
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
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if (jsonArray.optString(i).equals(data.get(getAdapterPosition()).code)) {
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
