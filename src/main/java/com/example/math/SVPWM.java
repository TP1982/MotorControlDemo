package com.example.math;

public class SVPWM {
	private double Udc = 600.0; //
	private double u_peak = (2/3) * Udc;
	private double fsw = 1000.0;	// alkup. 2000
	private double Tsw = 1/fsw;
	private double u_switch = 0.5*Udc;
	private double u_inv = 3/(2*Udc);
	private double ud_pwm;
	private double uq_pwm;
	private double u_alpha_ref;
	private double u_beta_ref;
	private double theta;
	private double ua;
	private double ub;
	private double uc;
	
	public SVPWM(){
		ud_pwm = 0;
		uq_pwm = 0;
		u_alpha_ref = 0;
		u_beta_ref = 0;
		theta = 0;
		ua = 0;
		ub = 0;
		uc = 0;
	}
	
	public void simulateSVPWM(double udref, double uqref, double thetar, double time){
		double sector, t0, t1, t2, ua, ub, uc, U_alpha, U_beta;
		ua = 0; ub = 0; uc = 0;
		
		u_alpha_ref = Math.cos(thetar) * udref - Math.sin(thetar) * uqref;
		u_beta_ref = Math.cos(thetar) * uqref + Math.sin(thetar) * udref;
		double timer = time - Math.floor(fsw*time)/fsw;
		
		theta_pwm();
		
		// SECTOR 1
		   if (theta >= 0 && theta < Math.PI/3) {
		     sector = 1;
		     t1 = (u_inv*(u_alpha_ref - u_beta_ref/Math.sqrt(3)))*Tsw;
		     t2 = (u_inv*2/Math.sqrt(3)*u_beta_ref)*Tsw;
		     t0 = Tsw - t1 - t2;
		     
		     if (timer <= t1){
		       ua = 0.5*Udc;
		       ub = -0.5*Udc;
		       uc = -0.5*Udc;
		     }
		     if (timer > t1 && timer <= t1+t2){
		       ua = 0.5*Udc;
		       ub = 0.5*Udc;
		       uc = -0.5*Udc;
		     }
		     if (timer > t1+t2){
		       ua = 0.5*Udc;
		       ub = ua;
		       uc = ub;
		     }

		   }
		   //**************************************************************
		   // SECTOR 2
		   if (theta >= (Math.PI/3) && theta < (Math.PI*2/3) ) {
		     sector = 2;
		     t1 = (u_inv*(u_alpha_ref + (1/Math.sqrt(3)*u_beta_ref)))*Tsw;
		     t2 = (u_inv*(-u_alpha_ref + (1/Math.sqrt(3)*u_beta_ref)))*Tsw;
		     t0 = Tsw - t1 - t2;
		     
		     if (timer <= t1){
		       ua = 0.5*Udc;
		       ub = 0.5*Udc;
		       uc = -0.5*Udc;
		     }
		     if (timer > t1 && timer <= t1+t2){
		       ua = -0.5*Udc;
		       ub = 0.5*Udc;
		       uc = -0.5*Udc;
		     }
		     if (timer > t1+t2){
		       ua = 0.5*Udc;
		       ub = ua;
		       uc = ub;
		     }
		   }
		   //***************************************************************
		   // SECTOR 3
		   if (theta >= Math.PI*2/3 && theta < Math.PI) {
		     sector = 3;
		     t1 = (u_inv*(2/Math.sqrt(3))*u_beta_ref)*Tsw;
		     t2 = (u_inv*(-u_alpha_ref - (1/Math.sqrt(3))*u_beta_ref))*Tsw;
		     t0 = Tsw - t1 - t2;
		     
		     if (timer <= t1){
		       ua = -0.5*Udc;
		       ub = 0.5*Udc;
		       uc = -0.5*Udc;
		     }
		     if (timer > t1 && timer <= t1+t2){
		       ua = -0.5*Udc;
		       ub = 0.5*Udc;
		       uc = 0.5*Udc;
		     }
		     if (timer > t1+t2){
		       ua = 0.5*Udc;
		       ub = ua;
		       uc = ub;
		     }
		   }
		   //****************************************************************
		   // SECTOR 4
		   if (theta >= Math.PI && theta < (4*Math.PI/3) ) {
		     sector = 4;
		     t1 = (u_inv*(-u_alpha_ref + (1/Math.sqrt(3))*u_beta_ref))*Tsw;
		     t2 = (-u_inv*(2/Math.sqrt(3))*u_beta_ref)*Tsw;
		     t0 = Tsw - t1 - t2;
		     
		     if (timer <= t1){
		       ua = -0.5*Udc;
		       ub = 0.5*Udc;
		       uc = 0.5*Udc;
		     }
		     if (timer > t1 && timer <= t1+t2){
		       ua = -0.5*Udc;
		       ub = -0.5*Udc;
		       uc = 0.5*Udc;
		     }
		     if (timer > t1+t2){
		       ua = -0.5*Udc;
		       ub = ua;
		       uc = ub;
		     }
		   }
		   // *****************************************************************
		   // SECTOR 5
		   if (theta >= 4*Math.PI/3 && theta < 5*Math.PI/3) {
		     sector = 5;
		     t1 = (u_inv*(-u_alpha_ref - (1/Math.sqrt(3))*u_beta_ref))*Tsw;
		     t2 = (u_inv*(u_alpha_ref - (1/Math.sqrt(3))*u_beta_ref))*Tsw;
		     t0 = Tsw - t1 - t2;
		     
		     if (timer <= t1){
		       ua = -0.5*Udc;
		       ub = -0.5*Udc;
		       uc = 0.5*Udc;
		     }
		     if (timer > t1 && timer <= t1+t2){
		       ua = 0.5*Udc;
		       ub = -0.5*Udc;
		       uc = 0.5*Udc;
		     }
		     if (timer > t1+t2){
		       ua = 0.5*Udc;
		       ub = ua;
		       uc = ub;
		     }
		   }
		   //
		   //*********************************************************************
		   // SECTOR 6
		   if (theta >= 5*Math.PI/3 && theta < 2*Math.PI) {
		     sector = 6;
		     t1 = ( u_inv * ( (-Math.sqrt(3)/2) * u_beta_ref) ) *Tsw;
		     t2 = ( u_inv * ( u_alpha_ref + (1/Math.sqrt(3)) * u_beta_ref) ) *Tsw;
		     t0 = Tsw - t1 - t2;
		     
		     if ( timer <= t1){
		       ua = 0.5*Udc;
		       ub = -0.5*Udc;
		       uc = 0.5*Udc;
		     }
		     if ( timer > t1 && timer <= (t1 + t2) ){
		       ua = 0.5*Udc;
		       ub = -0.5*Udc;
		       uc = -0.5*Udc;
		     }
		     if ( timer > (t1 + t2) ){
		       ua = 0.5*Udc;
		       ub = ua;
		       uc = ub;
		     }
		   }
		   U_alpha = ua - 0.5*ub - 0.5*uc;
		   U_beta = Math.sqrt(3)*0.5*ub - Math.sqrt(3)*0.5*uc;
		   ud_pwm = Math.cos(thetar)*U_alpha + Math.sin(thetar)*U_beta;
		   uq_pwm = Math.cos(thetar)*U_beta - Math.sin(thetar)*U_alpha;
		
	}
	
	public void theta_pwm(){
		
		if (u_beta_ref > 0 && u_alpha_ref > 0){
		  theta = Math.atan(u_beta_ref/u_alpha_ref);
		}
		else if (u_beta_ref > 0 && u_alpha_ref < 0){
		  theta = Math.PI + Math.atan(u_beta_ref/u_alpha_ref);
		}
		else if (u_beta_ref < 0 && u_alpha_ref < 0){
		  theta = Math.PI + Math.atan(u_beta_ref/u_alpha_ref);
		}
		else if (u_beta_ref < 0 && u_alpha_ref > 0){
		  theta = Math.PI*2 + Math.atan(u_beta_ref/u_alpha_ref);
		}
	}
	
	public void voltageLimit(){
		if (u_alpha_ref > u_peak){
		     u_alpha_ref = u_peak;
		   }
		   if (u_alpha_ref < -u_peak){
		     u_alpha_ref = -u_peak;
		   }
		   if (u_beta_ref > u_peak){
		     u_beta_ref = u_peak;
		   }
		   if (u_beta_ref < -u_peak){
		     u_beta_ref = -u_peak;
		   }
		   else {
		     u_alpha_ref = u_alpha_ref;
		     u_beta_ref = u_beta_ref;
		   }
	}

	public double getUdc() {
		return Udc;
	}

	public void setUdc(double udc) {
		Udc = udc;
	}

	public double getU_peak() {
		return u_peak;
	}

	public void setU_peak(double u_peak) {
		this.u_peak = u_peak;
	}

	public double getFsw() {
		return fsw;
	}

	public void setFsw(double fsw) {
		this.fsw = fsw;
	}

	public double getTsw() {
		return Tsw;
	}

	public void setTsw(double tsw) {
		Tsw = tsw;
	}

	public double getU_switch() {
		return u_switch;
	}

	public void setU_switch(double u_switch) {
		this.u_switch = u_switch;
	}

	public double getU_inv() {
		return u_inv;
	}

	public void setU_inv(double u_inv) {
		this.u_inv = u_inv;
	}

	public double getUd_pwm() {
		return ud_pwm;
	}

	public void setUd_pwm(double ud_pwm) {
		this.ud_pwm = ud_pwm;
	}

	public double getUq_pwm() {
		return uq_pwm;
	}

	public void setUq_pwm(double uq_pwm) {
		this.uq_pwm = uq_pwm;
	}

	public double getU_alpha_ref() {
		return u_alpha_ref;
	}

	public void setU_alpha_ref(double u_alpha_ref) {
		this.u_alpha_ref = u_alpha_ref;
	}

	public double getU_beta_ref() {
		return u_beta_ref;
	}

	public void setU_beta_ref(double u_beta_ref) {
		this.u_beta_ref = u_beta_ref;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getUa() {
		return ua;
	}

	public void setUa(double ua) {
		this.ua = ua;
	}

	public double getUb() {
		return ub;
	}

	public void setUb(double ub) {
		this.ub = ub;
	}

	public double getUc() {
		return uc;
	}

	public void setUc(double uc) {
		this.uc = uc;
	}
	
	
}
