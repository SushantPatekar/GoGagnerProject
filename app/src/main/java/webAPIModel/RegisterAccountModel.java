package webAPIModel;

public class RegisterAccountModel {


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

	public String firstName;
    	public String lastName;
    	public String email;
    	public String mobile;
    	public int userType;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int status;
    	public String password;
    	public int localityId;
    	public int cityId;
    	public int stateId;
    	public int countryId;

    	public String aadhaarCardNo;
    	public String dob;
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
/*firstName":"Ashish",
			"lastName":"nair",
			"email":"ashishnair57@gmail.com",
			"aadhaarCardNo":"0000000000",
			"dob":"1987-12-09",
			"occupation":"service",
			"localityId":2,
			"cityId":1,
			"stateId":1,
			"countryId":1
*/

}
