package com.example.math;

import java.util.HashMap;

public class CurrentControl {
	private double h = 0.0001;
	private double idref;
	private double iqref;
	private double udref;
	private double uqref;
	private double I_id;
	private double I_iq;
	
	private double kpd;
	private double kid;
	private double rad;
	private double kpq;
	private double kiq;
	private double raq;
	private double umax;
	//private HashMap<String,Double> output;
	
	public CurrentControl(){
		this.udref = 0;
		this.uqref = 0;
		this.I_id = 0;
		this.I_iq = 0;
		umax = 540 / ( Math.sqrt(3.0) );
		calculatePMSMCCVariables();
	}
	
	public void simulateCurrentControl(double idref, double iqref, double wr, double id, double iq){
		double eid = idref - id;
		double eiq = iqref - iq;
		
		
		udref = (kpd * eid) + (kid * I_id) - (wr * PMSM.lq * iq) - rad * id;
		double vd = udref;
		uqref = (kpq * eiq) + (kiq * I_iq) + (wr * PMSM.ld * id) - raq * iq;
		
		double vq = uqref;
		if(vd > umax){
			udref = umax;
		}
		if(vd < -umax){
			udref = -umax;
		}
		if(vq > umax){
			uqref = umax;
		}
		if(vq < -umax){
			uqref = -umax;
		}
		
		I_id += h * (eid + ( udref - vd ) / kpd);
		I_iq += h * (eiq + ( uqref - vq ) / kpq);
	}
	
	private void calculatePMSMCCVariables(){
		double trc = 0.005;
		double alphac = Math.log(9.0)/trc;
		
		kpd = alphac * PMSM.ld;
		kid = alphac * kpd;
		rad = kpd - PMSM.rs;
		
		kpq = alphac * PMSM.lq;
		kiq = alphac * kpq;
		raq = kpq - PMSM.rs;
	}

	public double getIdref() {
		return idref;
	}

	public void setIdref(double idref) {
		this.idref = idref;
	}

	public double getIqref() {
		return iqref;
	}

	public void setIqref(double iqref) {
		this.iqref = iqref;
	}

	public double getUdref() {
		return udref;
	}

	public void setUdref(double udref) {
		this.udref = udref;
	}

	public double getUqref() {
		return uqref;
	}

	public void setUqref(double uqref) {
		this.uqref = uqref;
	}

	public double getI_id() {
		return I_id;
	}

	public void setI_id(double i_id) {
		I_id = i_id;
	}

	public double getI_iq() {
		return I_iq;
	}

	public void setI_iq(double i_iq) {
		I_iq = i_iq;
	}

	public double getKpd() {
		return kpd;
	}

	public void setKpd(double kpd) {
		this.kpd = kpd;
	}

	public double getKid() {
		return kid;
	}

	public void setKid(double kid) {
		this.kid = kid;
	}

	public double getRad() {
		return rad;
	}

	public void setRad(double rad) {
		this.rad = rad;
	}

	public double getKpq() {
		return kpq;
	}

	public void setKpq(double kpq) {
		this.kpq = kpq;
	}

	public double getKiq() {
		return kiq;
	}

	public void setKiq(double kiq) {
		this.kiq = kiq;
	}

	public double getRaq() {
		return raq;
	}

	public void setRaq(double raq) {
		this.raq = raq;
	}
	
}
