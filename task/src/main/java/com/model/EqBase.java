package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EqBase {

	@Id
	@Column(name="id")
	private int id;
	@Column(name="eqSentence")
	private String eqSentence;
	@Column(name="eqRoot")
	private double eqRoot;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEqSentence() {
		return eqSentence;
	}
	
	public void setEqSentence(String eqSentence) {
		this.eqSentence = eqSentence;
	}
	
	public double getEqRoot() {
		return eqRoot;
	}
	
	public void setEqRoot(double eqRoot) {
		this.eqRoot = eqRoot;
	}

}
