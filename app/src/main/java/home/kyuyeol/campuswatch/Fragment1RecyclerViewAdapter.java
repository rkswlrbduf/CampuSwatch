package home.kyuyeol.campuswatch;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by yumin on 2017-12-01.
 */

public class Fragment1RecyclerViewAdapter extends RecyclerView.Adapter<Fragment1RecyclerViewAdapter.MyViewHolder> {

    private static final int TYPE_LEFT_HALF = 0;
    private static final int TYPE_RIGHT_HALF = 1;
    private static final int TYPE_LEFT_THIRD = 2;
    private static final int TYPE_MIDDLE_THIRD = 3;
    private static final int TYPE_RIGHT_THIRD = 4;

    @Override
    public int getItemViewType(int position) {
        final int modeEight = position % 9;
        switch (modeEight) {
            case 0:
                return TYPE_LEFT_THIRD;
            case 1:
                return TYPE_MIDDLE_THIRD;
            case 2:
                return TYPE_RIGHT_THIRD;
            case 3:
            case 5:
            case 7:
                return TYPE_LEFT_HALF;
            case 4:
            case 6:
            case 8:
                return TYPE_RIGHT_HALF;
        }
        return -1;
    }

    LayoutInflater inflater;
    List<Fragment1DataSet> data = Collections.emptyList();
    Context context;
    final String image_url = "http://rkswlrbduf.cafe24.com/CampuSwatch/F1/F1_Image/";

    @Override
    public int getItemCount() {
        Log.d("DATA SIZE", String.valueOf(data.size()));
        return data.size();
    }

    public Fragment1RecyclerViewAdapter(Context context, List<Fragment1DataSet> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = inflater.inflate(R.layout.fragment1_recyclerview_row, parent, false);
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final int type = viewType;
                final ViewGroup.LayoutParams lp = view.getLayoutParams();
                if (lp instanceof GridLayoutManager.LayoutParams) {
                    GridLayoutManager.LayoutParams sglp = (GridLayoutManager.LayoutParams) lp;
                    switch (type) {
                        case TYPE_LEFT_THIRD:
                            sglp.width = (parent.getWidth())/3;
                            sglp.height = view.getHeight()*2/3;
                            break;
                        case TYPE_MIDDLE_THIRD:
                            sglp.width = (parent.getWidth())/3;
                            sglp.height = view.getHeight()*2/3;
                            break;
                        case TYPE_RIGHT_THIRD:
                            sglp.width = (parent.getWidth())/3;
                            sglp.height = view.getHeight()*2/3;
                            break;
                        case TYPE_LEFT_HALF:
                            sglp.width = (parent.getWidth()-(sglp.getMarginStart()+sglp.getMarginStart()))/2;
                            break;
                        case TYPE_RIGHT_HALF:
                            sglp.width = (parent.getWidth()-(sglp.getMarginStart()+sglp.getMarginStart()))/2;
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
        Picasso.with(context).load(image_url + data.get(position).image_name + ".png").transform(new CircleTransform(20, 30)).into(holder.image);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView price;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.F1_image);
            name = (TextView) itemView.findViewById(R.id.F1_name);
            price = (TextView) itemView.findViewById(R.id.F1_price);
        }
    }
}
