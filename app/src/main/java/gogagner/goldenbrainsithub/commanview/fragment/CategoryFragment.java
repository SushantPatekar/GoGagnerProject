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

    public CategoryFragment() {
    }

    private final String android_version_names[] = {
            "Foods",
            "Movies",
            "Cloths",
            "Grocery",
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
        CategoryAdapter adapter = new CategoryAdapter(getActivity(), categoryModelArrayList);
        rv.setAdapter(adapter);

        return rv;
    }


    private ArrayList<CategoryModel> prepareData() {

        ArrayList<CategoryModel> android_version = new ArrayList<>();
        for (int i = 0; i < android_version_names.length; i++) {
            CategoryModel CategoryModel = new CategoryModel();
            CategoryModel.setCategory_name(android_version_names[i]);
            CategoryModel.setCategory_image(android_image_urls[i]);
            android_version.add(CategoryModel);
        }
        return android_version;
    }
}
