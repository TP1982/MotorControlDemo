package com.example.math;
// digital Low Pass Filter

public class LPF {
	private double output;
	private double fc;
	private double omega_c;
	private double Ts;
	
	public LPF(double cutoffFrequency, double Ts){
		output = 0;
		fc = cutoffFrequency;
		omega_c = Math.PI*2*cutoffFrequency;
		this.Ts = Ts;
	}
	
	public double filter(double input){
		output = (output + Ts*omega_c*input) / (1 + Ts*omega_c);
		return output;
	}

	public double getFc() {
		return fc;
	}

	public void setFc(double fc) {
		this.fc = fc;
	}

	public double getTs() {
		return Ts;
	}

	public void setTs(double ts) {
		Ts = ts;
	}
	
	
}
