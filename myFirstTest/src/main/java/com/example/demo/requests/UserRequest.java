package com.example.demo.requests;

import java.util.List;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {

	@NotNull(message="ce champ ne doit etre null!")
	@Size(min=3, message="Ce champ doit avoir au mois 3 Caracters !")
	private String firstName;
	
	@NotNull(message="ce champ ne doit etre null!")
	@Size(min=3, message="Ce champ doit avoir au mois 3 Caracters !")
	private String lastName;
	
	@NotNull(message="ce champ ne doit etre null!")
	@Email 
	private String email;
	
	@NotNull(message="ce champ ne doit etre null!")
    @Size(min=8, max=12,message="Ce champ doit avoir ente8  et 12 Caracters !")
//	@Pattern(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$, message)
	@Pattern(regexp="(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",message="ce password doit avoir des lettres en Maj et Minsc et numbers")
	private String password;
	
	
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	private List<AddressRequest> addresses;
	
	private ContactRequest contact;
	private Boolean admin = false;

	

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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<AddressRequest> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressRequest> addresses) {
		this.addresses = addresses;
	}
	
	public ContactRequest getContact() {
		return contact;
	}
	public void setContact(ContactRequest contact) {
		this.contact = contact;
	}

	
	
	

}
