package com.example.math;

public class TaurefToIqref {
	private double iqref;
	
	public TaurefToIqref() {
		this.iqref = 0.0;
	}
	
	public double calculateIqref(double tauref){
		iqref = (2.0 / (3.0 * PMSM.polepairs * PMSM.psiPM)) * tauref;
		return iqref;
	}

	public double getIqref() {
		return iqref;
	}

	public void setIqref(double iqref) {
		this.iqref = iqref;
	}
	
}
