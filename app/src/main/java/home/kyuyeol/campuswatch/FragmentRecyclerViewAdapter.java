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
import java.util.zip.Inflater;

/**
 * Created by yumin on 2017-12-01.
 */

public class FragmentRecyclerViewAdapter extends RecyclerView.Adapter<FragmentRecyclerViewAdapter.MyViewHolder> {

    LayoutInflater inflater;
    List<Fragment1DataSet> data = Collections.emptyList();
    Context context;
    final String image_url = "http://rkswlrbduf.cafe24.com/CampuSwatch/F1/F1_Image/";
    @Override
    public int getItemCount() {
        Log.d("DATA SIZE", String.valueOf(data.size())); return data.size();
    }

    public FragmentRecyclerViewAdapter(Context context, List<Fragment1DataSet> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(data.get(position).name);
        holder.price.setText(data.get(position).price);
        Picasso.with(context).load(image_url+data.get(position).image_name + ".png").transform(new CircleTransform(20,30)).into(holder.image);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView price;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.F1_image);
            name = (TextView) itemView.findViewById(R.id.F1_name);
            price = (TextView) itemView.findViewById(R.id.F1_price);
        }
    }
}
