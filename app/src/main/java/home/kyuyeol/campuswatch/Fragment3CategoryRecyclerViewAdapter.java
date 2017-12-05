package home.kyuyeol.campuswatch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by yumin on 2017-12-01.
 */

public class Fragment3CategoryRecyclerViewAdapter extends RecyclerView.Adapter<Fragment3CategoryRecyclerViewAdapter.MyViewHolder> {

    LayoutInflater inflater;
    List<Fragment3CategoryDataSet> data = Collections.emptyList();
    Context context;
    final String image_url = "http://rkswlrbduf.cafe24.com/CampuSwatch/F3/F3_Category/F3_Category_Image/";
    @Override
    public int getItemCount() {
        Log.d("DATA SIZE", String.valueOf(data.size())); return data.size();
    }

    public Fragment3CategoryRecyclerViewAdapter(Context context, List<Fragment3CategoryDataSet> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment3_category_recyclerview_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(data.get(position).name);
        Picasso.with(context).load(image_url+data.get(position).image_name + ".png").placeholder(R.mipmap.ic_launcher_round).into(holder.image);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.F3_category_image);
            name = (TextView) itemView.findViewById(R.id.F3_category_name);
        }
    }
}
