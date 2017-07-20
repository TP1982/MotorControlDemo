package com.example.math;

import java.util.HashMap;

public class SpeedControl {
	private double h = 0.0001;
	private double wref;
	private double tauref;
	private double Ispeed;
	private double kp;
	private double ki;
	private double ra;

	
	public SpeedControl(){
		
		calculatePMSMSCVariables();
	}
	
	public double simulateSpeedControl(double wref, double w){
		double e = PMSM.polepairs * (wref - w);
		
		Ispeed += h * e;
		
		tauref = (kp * e) + (ki * Ispeed) - (ra * w);
		
		return tauref;
	}
	
	private void calculatePMSMSCVariables(){
		double trc = 0.003;
		double alphac = Math.log(9.0)/trc;
		
		kp = alphac * PMSM.Jtot / PMSM.polepairs;
		ki = alphac * kp;
		ra = alphac * PMSM.Jtot - PMSM.viscousDamping;
	}

	public double getWref() {
		return wref;
	}

	public void setWref(double wref) {
		this.wref = wref;
	}

	public double getTauref() {
		return tauref;
	}

	public void setTauref(double tauref) {
		this.tauref = tauref;
	}

	public double getIspeed() {
		return Ispeed;
	}

	public void setIspeed(double ispeed) {
		Ispeed = ispeed;
	}

	public double getKp() {
		return kp;
	}

	public void setKp(double kp) {
		this.kp = kp;
	}

	public double getKi() {
		return ki;
	}

	public void setKi(double ki) {
		this.ki = ki;
	}

	public double getRa() {
		return ra;
	}

	public void setRa(double ra) {
		this.ra = ra;
	}
	
	
}
