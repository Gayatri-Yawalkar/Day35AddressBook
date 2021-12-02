package com.bridgelabz.addressbook;
public class Contacts {
	public Integer id;
	public String name;
	public String address;
	public String city;
	public String state;
	public String zip;
	public String phoneNum;
	public String email;
	public Contacts(Integer id,String firstLastName,String address,String city,String state,String zip,String phoneNum,String email) {
		this.address=address;
		this.city=city;
		this.state=state;
		this.zip=zip;
	}
	public Contacts(int id, String name, String phoneNum, String email) {
		this.id=id;
		this.name=name;
		this.phoneNum=phoneNum;
		this.email=email;
	}
	public String getFirstLastName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public String getEmail() {
		return email;
	}
	@Override
	public String toString() {
		return "Name="+this.name+",Address="+this.address+",City="+this.city+",State="+this.state+
				",Zip="+this.zip+",Mobile Number="+this.phoneNum+",Email="+this.email;
	}
}
