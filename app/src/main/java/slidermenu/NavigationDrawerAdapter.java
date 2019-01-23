package slidermenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import gogagner.goldenbrainsithub.com.R;

import java.util.List;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    private List<DrawerModel> data ;
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<DrawerModel> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DrawerModel current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.title.setTag(R.string.drawer_text_tag,current.getTitle());
        holder.mDrawerImageView.setImageDrawable(context.getDrawable(current.getImage()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RelativeLayout rlMainView;
        ImageView mDrawerImageView;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.nav_title);
            rlMainView = (RelativeLayout) itemView.findViewById(R.id.rl_main_view);
            view = itemView;
            mDrawerImageView= (ImageView) itemView.findViewById(R.id.iv_drawer_icon);
        }
    }
}
