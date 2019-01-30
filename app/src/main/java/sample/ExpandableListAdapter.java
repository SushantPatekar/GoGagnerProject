package sample;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import gogagner.goldenbrainsithub.com.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<ExpandedMenuModel> mListDataHeader; // header titles

    // child data in format of header title, child title
    private HashMap<ExpandedMenuModel, List<String>> mListDataChild;
    ExpandableListView expandList;

    public ExpandableListAdapter(Context context, List<ExpandedMenuModel> listDataHeader, HashMap<ExpandedMenuModel, List<String>> listChildData, ExpandableListView mView) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.expandList = mView;
    }

    @Override
    public int getGroupCount() {
        int i = mListDataHeader.size();
        Log.d("GROUPCOUNT", String.valueOf(i));
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        /*int childCount = 0;
        //if (groupPosition != 2) {
        if (groupPosition != mListDataHeader.size()-1) {
            childCount = this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                    .size();
        }
        return childCount;*/

        List childList = mListDataChild.get(mListDataHeader.get(groupPosition));
        if (childList != null && ! childList.isEmpty()) {
            return childList.size();
        }
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.d("CHILD", mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition).toString());
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition);
     // return 1;

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandedMenuModel headerTitle = (ExpandedMenuModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.sample_listheader, null);
        }


        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.submenu);

        ImageView arrowicon = (ImageView) convertView
                .findViewById(R.id.arrowicon);

       /* if (getChildrenCount(groupPosition)==0) {
            arrowicon.setVisibility(View.GONE);
        }
        else {
            arrowicon.setVisibility(View.VISIBLE);
        }
        int imageResourceId = isExpanded ? R.drawable.ic_img_avatar
                : R.drawable.ic_category_search;
        arrowicon.setImageResource(imageResourceId);*/

        ImageView headerIcon = (ImageView) convertView.findViewById(R.id.iconimage);
        lblListHeader.setText(headerTitle.getIconName());
        headerIcon.setImageResource(headerTitle.getIconImg());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
       // final String childText = (String) getChild(groupPosition, childPosition);

        final  String childText = "This";
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.sample_list_submenu, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu);

        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}