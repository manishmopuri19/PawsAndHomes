package com.PawsAndHomes.Backend.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.persistence.*;
@Entity
@Table(name="ngo")
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Ngo {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column(nullable = false)
	 private String Orgname;
	 
	 @Column(nullable = false, unique = true)
	 private String email;
	 private String mobile;
	 private String location;
	 
	 @Column(nullable = false)
	 private String password;

	 @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL)
	 private List<Pet> pets;

	 @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL)
	 private List<Document> documents;

	 public Ngo(String Orgname,String email,String mobile,String location,String password){
		this.Orgname=Orgname;
		this.email=email;
		this.mobile=mobile;
		this.location=location;
		this.password=password;
	 }

	 public long getId() {
		return id;
	 }

	 public void setId(long id) {
		this.id = id;
	 }

	 public String getOrgname() {
		return Orgname;
	 }

	 public void setOrgname(String orgname) {
		Orgname = orgname;
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

	 public String getLocation() {
		return location;
	 }

	 public void setLocation(String location) {
		this.location = location;
	 }

	 public String getPassword() {
		return password;
	 }

	 public void setPassword(String password) {
		this.password = password;
	 }
	 
	 
}
