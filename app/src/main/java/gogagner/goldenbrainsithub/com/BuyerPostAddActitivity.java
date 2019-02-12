package gogagner.goldenbrainsithub.com;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dbModel.Locality;
import slidermenu.BaseDrawerActivity;

public class BuyerPostAddActitivity  extends BaseDrawerActivity implements View.OnClickListener {
    public static String TAG = BuyerSellerDashBoardActivity.class.getSimpleName();
Spinner spAddMaster;
Button btnNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_post_add_layout);
        initView();
    }

    private void initView(){
        spAddMaster = (Spinner)findViewById(R.id.spAddMaster);
        btnNext=(Button) findViewById(R.id.btnSaveNext_one);
        setOccupation(1);
    }
    @Override
    public void onClick(View view) {

    }



    @Override
    public void onDrawerProfileClick(View view) {

    }

    private void setOccupation(int
                                       mPosition){


        dbModel.Locality firstLocality;
        ArrayList<Locality> mcityList = new ArrayList<Locality>();
        try{
            firstLocality= new dbModel.Locality();
            firstLocality.setId(0);
            firstLocality.setName("Advertise Location");
            firstLocality.setSlug(getResources().getString(R.string.locality));
            mcityList.add(firstLocality);

            firstLocality = new dbModel.Locality();
            firstLocality.setId(1);
            firstLocality.setName("Home Screen");
            firstLocality.setSlug(getResources().getString(R.string.locality));
            mcityList.add(firstLocality);
            firstLocality = new dbModel.Locality();
            firstLocality.setId(2);
            firstLocality.setName("Category Screen");
            firstLocality.setSlug(getResources().getString(R.string.locality));
            mcityList.add(firstLocality);

            firstLocality = new dbModel.Locality();
            firstLocality.setId(3);
            firstLocality.setName("Product Screen");
            firstLocality.setSlug(getResources().getString(R.string.locality));
            mcityList.add(firstLocality);


            spAddMaster.setAdapter(new LocalityAdapter(getApplicationContext(),
                    R.layout.simple_spinner_white_dropdown,mcityList));
        }
        catch (Exception e){

        }



    }
    public class LocalityAdapter extends ArrayAdapter<Locality> {
        List<Locality> mList ;
        public LocalityAdapter(@NonNull Context context, int resource, @NonNull List<dbModel.Locality> objects) {
            super(context, resource, objects);
            mList = objects;
        }

       /* public StateAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }*/

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.simple_spinner_white_dropdown, parent, false);
            TextView label=(TextView)row.findViewById(R.id.idText);
            label.setText(""+mList.get(position).getName());



            return row;
        }
    }
}
