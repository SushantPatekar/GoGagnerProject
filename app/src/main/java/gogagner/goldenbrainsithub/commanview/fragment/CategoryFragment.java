package gogagner.goldenbrainsithub.commanview.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import gogagner.goldenbrainsithub.adapter.CategoryAdapter;
import gogagner.goldenbrainsithub.adapter.RecyclerTouchListener;
import gogagner.goldenbrainsithub.adapter.SpacesItemDecoration;
import gogagner.goldenbrainsithub.com.BuyerSellerCategoryActivity;
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
            "data:image/jpeg;base64,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",
           "https://static.toiimg.com/photo/65989074.cms",
            "http://www.wbais.net/uploaded/news_images/GALA/clothes-sale1.jpg",
            "data:image/jpeg;base64,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",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
           // "http://api.learn2crack.com/android/images/marshmallow.png"
            " R.drawable.ic_img_avatar"
    };
    ArrayList<CategoryModel> categoryModelArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_category_fragment, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recycler_category_fragment);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        categoryModelArrayList= prepareData();
        CategoryAdapter adapter = new CategoryAdapter(getActivity(), categoryModelArrayList);
        rv.setAdapter(adapter);


        rv.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CategoryModel movie = categoryModelArrayList.get(position);

                getActivity().startActivity(new Intent(getActivity(),
                        BuyerSellerCategoryActivity.class));
                //Toast.makeText(getActivity(), movie.getCategory_name() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return rootView;


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
