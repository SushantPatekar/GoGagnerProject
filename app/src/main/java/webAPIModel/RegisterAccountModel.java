package webAPIModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import gogagner.goldenbrainsithub.model.MediaModel;

public class RegisterAccountModel implements Serializable {


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLocalityId() {
        return localityId;
    }

    public void setLocalityId(int localityId) {
        this.localityId = localityId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("email")
    public String email;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("userType")
    public int userType;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @SerializedName("status")
    public int status;
    @SerializedName("password")
    public String password;
    @SerializedName("localityId")
    public int localityId;
    @SerializedName("cityId")
    public int cityId;
    @SerializedName("stateId")
    public int stateId;
    @SerializedName("countryId")
    public int countryId;
    @SerializedName("aadhaarCardNo")
    public String aadhaarCardNo;
    @SerializedName("dob")
    public String dob;
    @SerializedName("occupation")
    public String occupation;

    public String getAadhaarCardNo() {
        return aadhaarCardNo;
    }

    public void setAadhaarCardNo(String aadhaarCardNo) {
        this.aadhaarCardNo = aadhaarCardNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @SerializedName("media")
    public SignupMedia mediaModel;

    public SignupMedia getMediaModel() {
        return mediaModel;
    }

    public void setMediaModel(SignupMedia mediaModel) {
        this.mediaModel = mediaModel;
    }
	/*"media":{
		"web": {},
		"mobile": {
			"medium": "https://gogagnerbucket.s3.ap-south-1.amazonaws.com/images/profile/mobile/medium/12X.jpeg",
					"small": "https://gogagnerbucket.s3.ap-south-1.amazonaws.com/images/profile/mobile/small/12X.jpeg"
		}
	}*/

}
