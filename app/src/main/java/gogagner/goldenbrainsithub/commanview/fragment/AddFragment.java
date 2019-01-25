package gogagner.goldenbrainsithub.commanview.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import gogagner.goldenbrainsithub.adapter.AdViewAdapter;
import gogagner.goldenbrainsithub.adapter.CategoryAdapter;
import gogagner.goldenbrainsithub.model.AddModel;
import gogagner.goldenbrainsithub.model.CategoryModel;

public class AddFragment extends Fragment {

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

    public AddFragment() {
    }

    RecyclerView rv;
    public int position = 0;
    Timer timer;
    ArrayList<AddModel> addModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rv = new RecyclerView(getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
        addModelArrayList = prepareData();
        final AdViewAdapter adapter = new AdViewAdapter(getActivity(), addModelArrayList);
        rv.setAdapter(adapter);

        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, 2000);
        return rv;
    }

    private class RemindTask extends TimerTask {
        @Override
        public void run() {
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        if(addModelArrayList!=null ){
                            if (position == addModelArrayList.size()) {
                                position = 0;
                            } else {
                                position++;
                            }
                            rv.smoothScrollToPosition(position);
                        }
                    }
                    catch (Exception e){

                    }
                }
            });
        }
    }


    private ArrayList<AddModel> prepareData() {

        ArrayList<AddModel> android_version = new ArrayList<>();
        for (int i = 0; i < android_version_names.length; i++) {
            AddModel addModel = new AddModel();
            addModel.setAdd_name(android_version_names[i]);
            addModel.setAdd_image(android_image_urls[i]);
            android_version.add(addModel);
        }
        return android_version;
    }
}
