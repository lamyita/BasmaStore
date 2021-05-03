package com.example.demo.entites;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity(name="users")
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6949828312801015558L;

	@Id  //prim key
	@GeneratedValue /// auto incrmt
	private long id;
	@Column(nullable=false, length=50) ////not null
	private String userId;
	
	@Column(nullable=false, length=50) ////not null
	private String firstName;
	@Column(nullable=false, length=50) ////not null
	private String lastName;
	@Column(nullable=false, length=120, unique=true)
	private String email;
	@Column(nullable= false)
	private String encryptedPassword;
	@Column(nullable=true)
	private String emailVeificationToken;
	///	@Column(columnDefinition="boolen defult false") ////discrption
	@Column(nullable= false)
	private Boolean emailVerificationStatus; 
	
	
	////chaque objet user peut avoir plusr adresses 
	@OneToMany(mappedBy="user", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<AddressEntity> addresses;	
	
	@OneToOne(mappedBy="user",fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private ContactEntity contact;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="users")
	private Set<GroupEntity> groups = new HashSet<>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
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
	@Column(nullable=true)
	private Boolean admin = false;
	
	
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public String getEmailVeificationToken() {
		return emailVeificationToken;
	}
	public void setEmailVeificationToken(String emailVeificationToken) {
		this.emailVeificationToken = emailVeificationToken;
	}
	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}
	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}
	public List<AddressEntity> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressEntity> addresses) {
		this.addresses = addresses;
	}
	public ContactEntity getContact() {
		return contact;
	}
	public void setContact(ContactEntity contact) {
		this.contact = contact;
	}
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	
	
}
