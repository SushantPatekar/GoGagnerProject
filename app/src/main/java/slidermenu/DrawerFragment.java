package slidermenu;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import gogagner.goldenbrainsithub.com.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utility.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment implements View.OnClickListener {


    public DrawerFragment() {
        // Required empty public constructor
    }

    private static String TAG = DrawerFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    public ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private ArrayList<String> titles = null;
    private FragmentDrawerListener drawerListener;

    private BaseDrawerActivity mDrawerActivity;
    private DrawerEventsCallback mDrawerEventsCallback;
    private Toolbar mToolbar;
    private View layout;
    private TextView appVersion, tv_app_name;
    private ImageView ivUserProfile;
    private int[] icons = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mDrawerActivity = (BaseDrawerActivity) activity;
        try {
            mDrawerEventsCallback = (DrawerEventsCallback) activity;
        } catch (ClassCastException e) {
            throw new RuntimeException(activity.getClass().getSimpleName() + " must implement DrawerEventsCallback");
        }
    }


    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.nav_drawer_labels)));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        if (layout == null) {
            layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
            initializeViews();
            setAdapter();
        }
        return layout;
    }

    public List<DrawerModel> getData() {
        List<DrawerModel> data = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            DrawerModel navItem = new DrawerModel();
            navItem.setTitle(titles.get(i));
            if (i >= icons.length) {
                navItem.setImage(icons[0]);
            } else {
                navItem.setImage(icons[i]);
            }
            data.add(navItem);
        }
        return data;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
//        this.mToolbar = toolbar;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                mDrawerEventsCallback.onOpen();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                mDrawerEventsCallback.onClose();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }

    private void initializeViews() {
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        appVersion = (TextView) layout.findViewById(R.id.tvAppVersion);
        appVersion.setText("Version : " + Helper.getVersion(getActivity()));
        tv_app_name = layout.findViewById(R.id.tv_app_name);
        ivUserProfile = layout.findViewById(R.id.iv_logo);
        tv_app_name.setOnClickListener(this);
        ivUserProfile.setOnClickListener(this);
    }

    private void setAdapter() {
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void setUserName(String userName) {
        tv_app_name.setText(userName);
    }

    public void setProfilePic(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            Glide.with(getActivity()).load(imagePath).apply(RequestOptions.circleCropTransform()).into(ivUserProfile);
            ivUserProfile.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        } else {
            Glide.with(this).load(R.drawable.ic_launcher_background).apply(RequestOptions.circleCropTransform()).into(ivUserProfile);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_app_name:
                drawerListener.onDrawerProfileClick(v);
                mDrawerLayout.closeDrawer(containerView);
                break;

            case R.id.iv_logo:
                drawerListener.onDrawerProfileClick(v);
                mDrawerLayout.closeDrawer(containerView);
                break;
        }
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);

        void onDrawerProfileClick(View view);
    }

    public interface DrawerEventsCallback {
        void onOpen();

        void onClose();
    }
}