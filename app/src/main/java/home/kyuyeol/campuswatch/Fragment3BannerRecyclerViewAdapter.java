package home.kyuyeol.campuswatch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by yumin on 2017-12-01.
 */

public class Fragment3BannerRecyclerViewAdapter extends RecyclerView.Adapter<Fragment3BannerRecyclerViewAdapter.MyViewHolder> {

    private static final int TYPE_FULL = 0;
    private static final int TYPE_LEFT_HALF = 1;
    private static final int TYPE_RIGHT_HALF = 2;

    @Override
    public int getItemViewType(int position) {
        final int modeEight = position % 8;
        switch (modeEight) {
            case 0:
            case 5:
                return TYPE_LEFT_HALF;
            case 1:
            case 6:
                return TYPE_RIGHT_HALF;
        }
        return TYPE_FULL;
    }

    LayoutInflater inflater;
    List<Fragment3BannerDataSet> data = Collections.emptyList();
    Context context;
    final String image_url = "http://rkswlrbduf.cafe24.com/CampuSwatch/F3/F3_Banner/F3_Banner_Image/";
    @Override
    public int getItemCount() {
        Log.d("DATA SIZE", String.valueOf(data.size())); return data.size();
    }

    public Fragment3BannerRecyclerViewAdapter(Context context, List<Fragment3BannerDataSet> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = inflater.inflate(R.layout.fragment3_banner_recyclerview_row, parent, false);
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final int type = viewType;
                final ViewGroup.LayoutParams lp = view.getLayoutParams();
                if(lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams)lp;
                    switch (type) {
                        case TYPE_FULL:
                            sglp.setFullSpan(true);
                            sglp.setMargins(50,25,50,0);
                            break;
                        case TYPE_LEFT_HALF:
                            sglp.setFullSpan(false);
                            sglp.width = view.getWidth();
                            sglp.setMargins(50,25,10,0);
                            break;
                        case TYPE_RIGHT_HALF:
                            sglp.setFullSpan(false);
                            sglp.setMargins(10,25,50,0);
                            sglp.width = view.getWidth();
                            break;
                    }
                    view.setLayoutParams(sglp);
                    final StaggeredGridLayoutManager lm =  (StaggeredGridLayoutManager)((RecyclerView) parent).getLayoutManager();
                    lm.invalidateSpanAssignments();
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
        Picasso.with(context).load(image_url+data.get(position).image_name + ".png").placeholder(R.mipmap.ic_launcher_round).into(holder.image);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.F3_banner_image);
        }
    }
}
