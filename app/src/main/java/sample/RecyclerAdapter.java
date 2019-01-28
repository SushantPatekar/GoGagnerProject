package sample;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import gogagner.goldenbrainsithub.com.R;


public class RecyclerAdapter extends ExpandableRecyclerViewAdapter<OSViewHolder, PhoneViewHolder> {

    private Activity activity;

    public RecyclerAdapter(Activity activity, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.activity = activity;
    }

    @Override
    public OSViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sample_group_view_holder, parent, false);

        return new OSViewHolder(view);
    }

    @Override
    public PhoneViewHolder onCreateChildViewHolder(ViewGroup parent, final int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sample_child_view_holder, parent, false);

        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(PhoneViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Phone phone = ((MobileOS) group).getItems().get(childIndex);
        holder.onBind(phone,group);
    }

    @Override
    public void onBindGroupViewHolder(OSViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }
}