package gogagner.goldenbrainsithub.com;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dbModel.CityModel;
import dbModel.Locality;
import dbModel.LocalityModel;
import dbModel.StateModel;
import gogagner.goldenbrainsithub.model.mobileModel;
import networkcommunication.NetworkCommunicationHelper;
import slidermenu.BaseDrawerActivity;
import utility.Constants;
import utility.Helper;
import webAPIModel.ChangePWDModel;
import webAPIModel.PostAddMedia;
import webAPIModel.PostAddModel;

public class BuyerPostAddActitivity  extends BaseDrawerActivity implements View.OnClickListener {
    public static String TAG = BuyerSellerDashBoardActivity.class.getSimpleName();
Spinner spAddMaster;
Button btnNext_one,btnNext_two,btnNext_three;
ImageView iv_addBaner_one;
RelativeLayout relativeLayout_first,relativeLayout_second,relativeLayout_three;
EditText edDOB;
TextView tvCoinLbl,tvCoinBalance;
Spinner spBuisnessAd,spBuisnessCat,spBuisnessSubCat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_post_add_layout);
        initView();
    }

    private void initView(){
        spAddMaster = findViewById(R.id.spAddMaster);

        spBuisnessAd = findViewById(R.id.spBuisnessAd);
        spBuisnessCat = findViewById(R.id.spBuisnessCat);
        spBuisnessSubCat = findViewById(R.id.spBuisnessSubCat);
        spBuisnessAd.setOnItemSelectedListener(buissness_add);
        spBuisnessCat.setOnItemSelectedListener(buissness_category);

        btnNext_one= findViewById(R.id.btnSaveNext_one);
        btnNext_two = findViewById(R.id.btnSaveNext_two);
        btnNext_three= findViewById(R.id.btnSaveNext_three);
        iv_addBaner_one = findViewById(R.id.imgAddBanner_one);
        edDOB = findViewById(R.id.edDOB);
        edDOB.setHint(getResources().getString(R.string.post_add_date).toUpperCase());
        edDOB.setOnClickListener(this);
        setOccupation(1);
        setbuisness(1);
        setCategory(1);
        setSubCategory(1);
        spAddMaster.setOnItemSelectedListener(onLocationListener);
        btnNext_one.setOnClickListener(this);
        btnNext_two.setOnClickListener(this);
        btnNext_three.setOnClickListener(this);

        relativeLayout_first = findViewById(R.id.relativeLayout_first);
        relativeLayout_second = findViewById(R.id.relativeLayout_second);
        relativeLayout_three = findViewById(R.id. relativeLayout_three);

        String first = "You require";
        String next = "<font color='#000000'> 50 Coins </font>";
        String third =  "to Post this Advertise";
        tvCoinLbl = findViewById(R.id.tvCoinLbl);
        tvCoinBalance = findViewById(R.id. tvCoinBalance);
        tvCoinLbl.setText(Html.fromHtml(first + next+third));
    }
    @Override
    public void onClick(View view) {
switch (view.getId()){
    case R.id.btnSaveNext_one:
         viewSecondContainer();
        break;

    case R.id.btnSaveNext_two:
        viewThirdContainer();
        break;

    case R.id.btnSaveNext_three:
        viewFourContainer();
        break;
    case R.id.edDOB:
        edDOB.setInputType(InputType.TYPE_NULL);
        setDateFromCalender();
        break;
}
    }

    public void viewSecondContainer(){
        relativeLayout_second.setVisibility(View.VISIBLE);
        relativeLayout_first.setVisibility(View.GONE);
        relativeLayout_three.setVisibility(View.GONE);
    }


    public void viewThirdContainer(){
        relativeLayout_second.setVisibility(View.GONE);
        relativeLayout_first.setVisibility(View.GONE);
        relativeLayout_three.setVisibility(View.VISIBLE);

        /*relativeLayout_second.setVisibility(View.VISIBLE);
        relativeLayout_first.setVisibility(View.GONE);
        relativeLayout_three.setVisibility(View.GONE);*/
    }

    public void viewFourContainer(){


    postAddApi();
    }
    @Override
    public void onDrawerProfileClick(View view) {

    }

    private void setOccupation(int mPosition) {

        dbModel.Locality firstLocality;
        ArrayList<Locality> mcityList = new ArrayList<Locality>();
        try {
            firstLocality = new dbModel.Locality();
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


            spAddMaster.setAdapter(new LocationAdapter(getApplicationContext(),
                    R.layout.simple_spinner_white_dropdown, mcityList));
        } catch (Exception e) {

        }


    }

    private void setbuisness(int mPosition) {

        dbModel.Locality firstLocality;
        ArrayList<Locality> mcityList = new ArrayList<Locality>();
        try {


            firstLocality = new dbModel.Locality();
            firstLocality.setId(1);
            firstLocality.setName("Buisness to be Advertise");
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


            spBuisnessAd.setAdapter(new LocationAdapter(getApplicationContext(),
                    R.layout.simple_spinner_white_dropdown, mcityList));
        } catch (Exception e) {

        }


    }

    private void setCategory(int mPosition) {

        dbModel.Locality firstLocality;
        ArrayList<Locality> mcityList = new ArrayList<Locality>();
        try {
            firstLocality = new dbModel.Locality();
            firstLocality.setId(0);
            firstLocality.setName("Category");
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


            spBuisnessCat.setAdapter(new LocationAdapter(getApplicationContext(),
                    R.layout.simple_spinner_white_dropdown, mcityList));
        } catch (Exception e) {

        }


    }

    private void setSubCategory(int mPosition) {

        dbModel.Locality firstLocality;
        ArrayList<Locality> mcityList = new ArrayList<Locality>();
        try {
            firstLocality = new dbModel.Locality();
            firstLocality.setId(0);
            firstLocality.setName("Buisness SubCateogry");
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


            spBuisnessSubCat.setAdapter(new LocationAdapter(getApplicationContext(),
                    R.layout.simple_spinner_white_dropdown, mcityList));
        } catch (Exception e) {

        }


    }
    public class LocationAdapter extends ArrayAdapter<Locality> {
        List<Locality> mList ;
        public LocationAdapter(@NonNull Context context, int resource, @NonNull List<dbModel.Locality> objects) {
            super(context, resource, objects);
            mList = objects;
        }



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
            TextView label= row.findViewById(R.id.idText);
            label.setText(""+mList.get(position).getName());



            return row;
        }
    }

    private int mSelectedLocationId;
    private int mSelectedBuisnessId;
    private int mSourceId;
    private String publishDate;
    private AdapterView.OnItemSelectedListener onLocationListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0 && position==1 ) {
                dbModel.State _state=  new StateModel().getState(getApplication(),position);
                mSelectedLocationId =position;
                iv_addBaner_one.setVisibility(View.VISIBLE);
            }
            else {
                iv_addBaner_one.setVisibility(View.GONE);
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void setDateFromCalender() {
        int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        publishDate = edDOB.getText().toString().trim();
                        btnNext_three.setText("Post Add For 50 Coins");
                        tvCoinLbl.setVisibility(View.VISIBLE);
                        tvCoinBalance.setVisibility(View.VISIBLE);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private AdapterView.OnItemSelectedListener buissness_add = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                spBuisnessCat.setVisibility(View.VISIBLE);
                mSelectedBuisnessId = position ;
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private AdapterView.OnItemSelectedListener buissness_category = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            if (position > 0) {
                spBuisnessSubCat.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    ProgressDialog mProgressDialog;

    public void postAddApi(){
        try{
            if (new Helper().isNetworkAvailable(getApplication())) {
                mProgressDialog = new ProgressDialog(BuyerPostAddActitivity.this);
                Helper.showDialog(mProgressDialog,getResources().getString(R.string.popup_messege));
                String webAPI = Helper.getSharedPrefValStr(BuyerPostAddActitivity.this, Constants.sharedPref.s_BASE_URL)
                        .concat(Constants.webAPI.postAdd);
                String requestBody = createPostAdModel();
                NetworkCommunicationHelper networkCommunicationHelper = new NetworkCommunicationHelper();

                networkCommunicationHelper.sendUserPostRequest(getApplication(), webAPI, requestBody,
                        new NetworkCommunicationHelper.OnResponseReceived() {
                            @Override
                            public void onSuccess(final String res) {
                                try {
                                    new Helper().hideDialog(mProgressDialog);
                                    Toast.makeText(getApplicationContext(), "" + Helper.getServerSuccessMessage(res), Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {

                                }

                            }

                            @Override
                            public void onFailure(final String err) {
                                new Helper().hideDialog(mProgressDialog);
                                Helper.showToast(BuyerPostAddActitivity.this, "" + Helper.getServerMessage(err));
                            }


                        });
            } else {
                Helper.showToast(BuyerPostAddActitivity.this, getResources().getString(R.string.lbl_no_internet));
            }
        }
        catch (Exception e){

        }
    }

    public String createPostAdModel() {
        PostAddModel postAddModel = new PostAddModel();
        PostAddMedia postAddMedia = new PostAddMedia();
        mobileModel mobileModel = new mobileModel();
              mobileModel.setSmall("https://gogagnerbucket.s3.ap-south-1.amazonaws.com/images/profile/mobile/small/12X.jpe");
              mobileModel.setMedium("https://gogagnerbucket.s3.ap-south-1.amazonaws.com/images/profile/mobile/medium/12X.jpeg");
        postAddMedia.setMobileModel(mobileModel);
        postAddModel.setBusinessId(""+mSelectedBuisnessId);
        postAddModel.setLocationId(""+mSelectedLocationId);
        postAddModel.setPublishDate(""+publishDate);
        postAddModel.setSourceId(""+mSourceId);
        postAddModel.setPostAddMedia(postAddMedia);
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        Type type = new TypeToken<PostAddModel>() {
        }.getType();
        return gson.toJson(postAddModel, type);
    }
}
