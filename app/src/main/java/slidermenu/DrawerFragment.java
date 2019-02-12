package slidermenu;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import gogagner.goldenbrainsithub.com.GlideApp;
import gogagner.goldenbrainsithub.com.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import sample.ExpandedMenuModel;
import utility.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment implements View.OnClickListener {


    public DrawerFragment() {
        // Required empty public constructor
    }

    private static String TAG = DrawerFragment.class.getSimpleName();

    //private RecyclerView recyclerView;
    public ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
   // private NavigationDrawerAdapter adapter;
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


    //Initialize Expandable List
    ExpandableListView expandableListView;
    BuyerSellerListAdapter buyerSellerListAdapter;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    private ArrayList<String>  Buyertitles =null;
    private int []BuyerIcon = {R.drawable.ic_menu_home,R.drawable.ic_menu_category,R.drawable.ic_menu_favourite,
    R.drawable.ic_menu_order,R.drawable.ic_menu_earnings,R.drawable.ic_menu_profile,R.drawable.ic_menu_switch_seller,
            R.drawable.ic_menu_post_ad,R.drawable.ic_menu__post_status,R.drawable.ic_menu_coin_help,R.drawable.ic_menu_coin_help,
            R.drawable.ic_menu_card,R.drawable.ic_menu_rate,R.drawable.ic_menu_about,R.drawable.ic_menu_support,
            R.drawable.ic_menu_terms_and_conditions,R.drawable.ic_menu_privacy_policy,
            R.drawable.ic_menu_logout};
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
        Buyertitles = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.buyer_menu)));
    }

    //TODO 1) Initialize Expandable ListView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        if (layout == null) {
            layout = inflater.inflate(R.layout.buyer_navigation_drawer, container, false);
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
        expandableListView = (ExpandableListView) layout.findViewById(R.id.expnadableList);
        appVersion = (TextView) layout.findViewById(R.id.tvAppVersion);
        appVersion.setText("Version : " + Helper.getVersion(getActivity()));
        tv_app_name = layout.findViewById(R.id.tv_app_name);
        ivUserProfile = layout.findViewById(R.id.ivAvatar);
        String imageUrl = getURLForResource(R.drawable.go_ganer_app_icon_round);
        setProfilePic(imageUrl);
      //  tv_app_name.setOnClickListener(this);
      //  ivUserProfile.setOnClickListener(this);
    }

    //todo 2) implement touch click
    private void setAdapter() {
        prepareListData();
        buyerSellerListAdapter = new BuyerSellerListAdapter(getActivity(), listDataHeader, listDataChild, expandableListView);
        expandableListView.setAdapter(buyerSellerListAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListViewGroupClickListenr(getActivity(), expandableListView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

//if(position!=2)
                drawerListener.onGroupItemSelected(view,position);
                drawerListener.onDrawerItemSelected(view, position);
               // mDrawerLayout.closeDrawer(containerView);
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
      /*  if (imagePath != null && !imagePath.isEmpty()) {
            Glide.with(getActivity()).load(imagePath).apply(RequestOptions.circleCropTransform()).into(ivUserProfile);
            ivUserProfile.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        } else {*/

            Glide.with(this).load(R.drawable.go_ganer_app_icon_round).apply(RequestOptions.circleCropTransform())
                    .into(ivUserProfile);
        //}
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
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

        public RecyclerTouchListener(Context context,
                                     final RecyclerView recyclerView,
                                     final ClickListener clickListener) {
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


    static class ExpandableListViewGroupClickListenr implements  ExpandableListView.OnGroupClickListener{
        private ClickListener clickListener;
        private ExpandableListView expandableListView;
        public ExpandableListViewGroupClickListenr(Context context,
                                                   final ExpandableListView expandableListView,
                                                   final ClickListener clickListener) {

          this.clickListener=clickListener;
          this.expandableListView=expandableListView;
        }

        @Override
        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
            clickListener.onClick(view,i);
            return true;
        }
    }
    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);

        void onGroupItemSelected(View view,int groupPosition);

        void onDrawerProfileClick(View view);
    }

    public interface DrawerEventsCallback {
        void onOpen();

        void onClose();
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();





        // Adding child data
        List<String> heading1 = new ArrayList<String>();
        heading1.add("Submenu of item 1");

        List<String> heading2 = new ArrayList<String>();
        heading2.add("Category 1");
        heading2.add("Category 2");
        heading2.add("Category 3");

        for (int i = 0; i < Buyertitles.size(); i++) {
            ExpandedMenuModel item = new ExpandedMenuModel();
            item.setIconName(Buyertitles.get(i));
           // item.setIconImg(R.drawable.ic_menu_favourite);
            item.setIconImg(BuyerIcon[i]);
            listDataHeader.add(item);}



        //listDataChild.put(listDataHeader.get(0), heading1);
        listDataChild.put(listDataHeader.get(1), heading2);
    }
}
