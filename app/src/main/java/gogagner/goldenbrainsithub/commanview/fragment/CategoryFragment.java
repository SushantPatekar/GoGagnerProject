package gogagner.goldenbrainsithub.commanview.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import gogagner.goldenbrainsithub.adapter.CategoryAdapter;
import gogagner.goldenbrainsithub.adapter.SpacesItemDecoration;
import gogagner.goldenbrainsithub.com.R;
import gogagner.goldenbrainsithub.model.CategoryModel;

public class CategoryFragment extends Fragment {
    String[] strings = {"1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7"};

    public CategoryFragment() {}

    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };
    private final String android_image_urls[] = {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
            "http://api.learn2crack.com/android/images/marshmallow.png"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = new RecyclerView(getActivity());
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        //TODO

        ArrayList<CategoryModel> categoryModelArrayList = prepareData();
        CategoryAdapter adapter = new CategoryAdapter(getActivity(),categoryModelArrayList);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.size_3);
        rv.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv.setAdapter(adapter);

        return rv;
    }

    /**
     * A Simple Adapter for the RecyclerView
     */
    public class SimpleRVAdapter extends RecyclerView.Adapter<AddFragment.SimpleViewHolder> {
        private String[] dataSource;
        public SimpleRVAdapter(String[] dataArgs){
            dataSource = dataArgs;
        }

        @Override
        public AddFragment.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = new TextView(parent.getContext());
            AddFragment.SimpleViewHolder viewHolder = new AddFragment.SimpleViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(AddFragment.SimpleViewHolder holder, int position) {
            holder.textView.setText(dataSource[position]);
        }

        @Override
        public int getItemCount() {
            return dataSource.length;
        }
    }



    private ArrayList<CategoryModel> prepareData(){

        ArrayList<CategoryModel> android_version = new ArrayList<>();
        for(int i=0;i<android_version_names.length;i++){
            CategoryModel CategoryModel = new CategoryModel();
            CategoryModel.setCategory_name(android_version_names[i]);
            CategoryModel.setCategory_image(android_image_urls[i]);
            android_version.add(CategoryModel);
        }
        return android_version;
    }
}
