package gogagner.goldenbrainsithub.commanview.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddFragment extends Fragment {
    String[] strings = {"1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7"};

    public AddFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = new RecyclerView(getActivity());
       // rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),0,false));
        //recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        rv.setAdapter(new SimpleRVAdapter(strings));
        return rv;
    }

    /**
     * A Simple Adapter for the RecyclerView
     */
    public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
        private String[] dataSource;
        public SimpleRVAdapter(String[] dataArgs){
            dataSource = dataArgs;
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = new TextView(parent.getContext());
            SimpleViewHolder viewHolder = new SimpleViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            holder.textView.setText(dataSource[position]);
            holder.textView.setTextSize(20);

        }

        @Override
        public int getItemCount() {
            return dataSource.length;
        }
    }

    /**
     * A Simple ViewHolder for the RecyclerView
     */
    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
