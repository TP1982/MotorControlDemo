package com.example.math;

import java.util.ArrayList;

public class VectorPair {
	private int sizeOfRef;
	private int sizeOfMeas;
	private double minOfRef;
	private double maxOfRef;
	private double minOfMeas;
	private double maxOfMeas;
	private ArrayList<Double> reference;
	private ArrayList<Double> measured;
	private String name;
	
	public VectorPair(ArrayList<Double> reference, ArrayList<Double> measured, String name){
		this.name = name;
		this.reference = reference;
		this.measured = measured;
		sizeOfRef = reference.size();
		sizeOfMeas = measured.size();
		minOfRef = reference.stream().min(Double::compare).get();
		maxOfRef = reference.stream().max(Double::compare).get();
		minOfMeas = measured.stream().min(Double::compare).get();
		maxOfMeas = measured.stream().max(Double::compare).get();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSizeOfRef() {
		return sizeOfRef;
	}

	public void setSizeOfRef(int sizeOfRef) {
		this.sizeOfRef = sizeOfRef;
	}

	public int getSizeOfMeas() {
		return sizeOfMeas;
	}

	public void setSizeOfMeas(int sizeOfMeas) {
		this.sizeOfMeas = sizeOfMeas;
	}

	public double getMinOfRef() {
		return minOfRef;
	}

	public void setMinOfRef(double minOfRef) {
		this.minOfRef = minOfRef;
	}

	public double getMaxOfRef() {
		return maxOfRef;
	}

	public void setMaxOfRef(double maxOfRef) {
		this.maxOfRef = maxOfRef;
	}

	public double getMinOfMeas() {
		return minOfMeas;
	}

	public void setMinOfMeas(double minOfMeas) {
		this.minOfMeas = minOfMeas;
	}

	public double getMaxOfMeas() {
		return maxOfMeas;
	}

	public void setMaxOfMeas(double maxOfMeas) {
		this.maxOfMeas = maxOfMeas;
	}

	public ArrayList<Double> getReference() {
		return reference;
	}

	public void setReference(ArrayList<Double> reference) {
		this.reference = reference;
	}

	public ArrayList<Double> getMeasured() {
		return measured;
	}

	public void setMeasured(ArrayList<Double> measured) {
		this.measured = measured;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
