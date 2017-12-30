package home.kyuyeol.campuswatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Collections;
import java.util.List;

/**
 * Created by yumin on 2017-12-01.
 */

public class Fragment4RecyclerViewAdapter extends RecyclerView.Adapter<Fragment4RecyclerViewAdapter.MyViewHolder> {

    private static final int TYPE_LEFT_THIRD = 0;
    private static final int TYPE_MIDDLE_THIRD = 1;
    private static final int TYPE_RIGHT_THIRD = 2;

    LayoutInflater inflater;
    List<Fragment4DataSet> data = Collections.emptyList();
    Context context;
    final String image_url = "http://rkswlrbduf.cafe24.com/Uic/F1/F1_Image/";

    @Override
    public int getItemViewType(int position) {
        int mode = position % 3;
        switch (mode) {
            case 0:
                return TYPE_LEFT_THIRD;
            case 1:
                return TYPE_MIDDLE_THIRD;
            case 2:
                return TYPE_RIGHT_THIRD;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        Log.d("DATA SIZE", String.valueOf(data.size()));
        return data.size();
    }

    public Fragment4RecyclerViewAdapter(Context context, List<Fragment4DataSet> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = inflater.inflate(R.layout.fragment4_recyclerview_row, parent, false);

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
                            sglp.height = sglp.width + view.findViewById(R.id.F4).getHeight();
                            view.setLayoutParams(sglp);
                            break;
                        case TYPE_MIDDLE_THIRD:
                            sglp.width = parent.getWidth() / 3;
                            sglp.height = sglp.width + view.findViewById(R.id.F4).getHeight();
                            view.setLayoutParams(sglp);
                            break;
                        case TYPE_RIGHT_THIRD:
                            sglp.width = parent.getWidth() / 3;
                            sglp.height = sglp.width + view.findViewById(R.id.F4).getHeight();
                            view.setLayoutParams(sglp);
                            break;
                    }
                    view.setPadding(10,0,10,30);
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
        Picasso.with(context).load(image_url + data.get(position).image_name + ".png").placeholder(R.mipmap.ic_launcher_round).into(holder.image);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView price;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.F4_image);
            name = (TextView) itemView.findViewById(R.id.F4_name);
            price = (TextView)itemView.findViewById(R.id.F4_price);
        }
    }
}
