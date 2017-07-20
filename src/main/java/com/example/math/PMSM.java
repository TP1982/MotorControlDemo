package com.example.math;

import java.util.HashMap;

public class PMSM {
	private double psiD;
	private double psiQ;
	public static double psiPM = 1.03;
	public static double rs = 0.62;
	public static double polepairs = 7;
	private double id;
	private double iq;
	private double wr;		// electrical angular speed
	private double w;		// mechanical angular speed
	public static double ld = 0.0211;
	public static double lq = 0.0211;
	public static double viscousDamping = 6.7;
	public static double Jtot = 2.0;
	private double electricalTorque;
	private double loadTorque;
	private double h;
	private double helperConstant;
	
	public PMSM(){
		this.psiD = 0;
		this.psiQ = 0;
		this.id = 0;
		this.iq = 0;
		this.electricalTorque = 0;
		this.loadTorque = 0.0;
		this.wr = 0;
		this.w = 0;
		this.h = 0.0001;
		
		this.helperConstant = 1 / (1 + this.h * (viscousDamping / Jtot));
	}
	
	public void simulateMotor(double ud, double uq, double wr){
		psiD += h * ( ud - rs * id + wr * psiQ );
		psiQ += h * ( uq - rs * iq - wr * psiD );
		
		id = ( psiD - psiPM ) / ld;
		iq = psiQ / lq;
		
		electricalTorque = 1.5 * polepairs * ( psiD * iq - psiQ * id );
		
		// mechanical equations
		w = helperConstant * (w + (h / Jtot) * (electricalTorque - loadTorque));
		this.wr = polepairs * w;
		
	}

	public double getPsiD() {
		return psiD;
	}

	public void setPsiD(double psiD) {
		this.psiD = psiD;
	}

	public double getPsiQ() {
		return psiQ;
	}

	public void setPsiQ(double psiQ) {
		this.psiQ = psiQ;
	}

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

	public double getIq() {
		return iq;
	}

	public void setIq(double iq) {
		this.iq = iq;
	}

	public double getWr() {
		return wr;
	}

	public void setWr(double wr) {
		this.wr = wr;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public static double getLd() {
		return ld;
	}

	public static void setLd(double ld) {
		PMSM.ld = ld;
	}

	public static double getLq() {
		return lq;
	}

	public static void setLq(double lq) {
		PMSM.lq = lq;
	}

	public double getElectricalTorque() {
		return electricalTorque;
	}

	public void setElectricalTorque(double electricalTorque) {
		this.electricalTorque = electricalTorque;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getHelperConstant() {
		return helperConstant;
	}

	public void setHelperConstant(double helperConstant) {
		this.helperConstant = helperConstant;
	}
	
}
