package gogagner.goldenbrainsithub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gogagner.goldenbrainsithub.com.GlideApp;
import gogagner.goldenbrainsithub.com.R;
import gogagner.goldenbrainsithub.model.AddModel;

public class CategoryBannerAdapter extends RecyclerView.Adapter<CategoryBannerAdapter.ViewHolder> {
    private ArrayList<AddModel> android;
    private Context context;

    public CategoryBannerAdapter(Context context, ArrayList<AddModel> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public CategoryBannerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_categry_slider, viewGroup, false);
        return new CategoryBannerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryBannerAdapter.ViewHolder viewHolder, int i) {

        GlideApp.with(context)
                .load(android.get(i).getAdd_image())
                .placeholder(R.mipmap.go_ganer_app_icon)
                .error(R.mipmap.go_ganer_app_icon)
                //.override(138, 138)
                .centerCrop()
                .into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_android;
        private ImageView img_android;
        public ViewHolder(View view) {
            super(view);

            //tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView) view.findViewById(R.id.img_android);
        }
    }

}