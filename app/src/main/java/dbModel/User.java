package dbModel;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    public String firstName;
    public String lastName;
    public String email;
    public String mobile;
    public int userType;
    public int status;
    public String password;
    public int localityId;
    public int cityId;
    public int stateId;
    public int countryId;
  public String smallProfileURL;

    public String getSmallProfileURL() {
        return smallProfileURL;
    }

    public void setSmallProfileURL(String smallProfileURL) {
        this.smallProfileURL = smallProfileURL;
    }

    public String getMediumProfileURL() {
        return mediumProfileURL;
    }

    public void setMediumProfileURL(String mediumProfileURL) {
        this.mediumProfileURL = mediumProfileURL;
    }

    public String mediumProfileURL;

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



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getProfileURL() {
        return profileURL;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @PrimaryKey
    @NonNull
    private String id;

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String profileURL;

    public String refreshToken;
    public String accessToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
