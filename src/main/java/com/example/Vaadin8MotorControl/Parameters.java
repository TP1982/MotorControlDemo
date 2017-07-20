package com.example.Vaadin8MotorControl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.example.math.CurrentControl;
import com.example.math.SpeedControl;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class Parameters extends CustomComponent{
	public Parameters(SpeedControl speedcontrol, CurrentControl currentcontrol){
		
		TextField speedControlKP = new TextField("SpeedControl kp");
		TextField speedControlKI = new TextField("SpeedControl ki");
		TextField speedControlActiveDamping = new TextField("SpeedControl Ra");
		TextField currentControlKPD = new TextField("CurrentControl kpd");
		TextField currentControlKID = new TextField("CurrentControl kid");
		TextField currentControlRAD = new TextField("CurrentControl Rad");
		TextField currentControlKPQ = new TextField("CurrentControl kpq");
		TextField currentControlKIQ = new TextField("CurrentControl kiq");
		TextField currentControlRAQ = new TextField("CurrentControl Raq");
		
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("###.##", dfs);
    	
		speedControlKP.setValue(String.valueOf(df.format(speedcontrol.getKp())));
		speedControlKI.setValue(String.valueOf(df.format(speedcontrol.getKi())));
		speedControlActiveDamping.setValue(String.valueOf(df.format(speedcontrol.getRa())));
		
		currentControlKPD.setValue(String.valueOf(df.format(currentcontrol.getKpd())));
		currentControlKID.setValue(String.valueOf(df.format(currentcontrol.getKid())));
		currentControlRAD.setValue(String.valueOf(df.format(currentcontrol.getRad())));
		
		currentControlKPQ.setValue(String.valueOf(df.format(currentcontrol.getKpq())));
		currentControlKIQ.setValue(String.valueOf(df.format(currentcontrol.getKiq())));
		currentControlRAQ.setValue(String.valueOf(df.format(currentcontrol.getRaq())));
		
		HorizontalLayout textfields = new HorizontalLayout();
		textfields.addStyleName("parametersStyle");
		
		textfields.addComponents(speedControlKP,speedControlKI, speedControlActiveDamping, currentControlKPD,
				currentControlKID,currentControlRAD, currentControlKPQ, currentControlKIQ, currentControlRAQ);
		
		speedControlKP.addValueChangeListener(event -> {
			if(!event.getValue().isEmpty()){
				double temp = Double.valueOf(event.getValue());
				speedcontrol.setKp(temp);
			}
			
		});
		
		speedControlActiveDamping.addValueChangeListener(event ->{
			if(!event.getValue().isEmpty()){
				double temp = Double.valueOf(event.getValue());
				speedcontrol.setRa(temp);
			}
			
		});
		
		speedControlKI.addValueChangeListener(event -> {
			if(!event.getValue().isEmpty()){
				double temp = Double.valueOf(event.getValue());
				speedcontrol.setKi(temp);
			}
		});
		
		currentControlKPD.addValueChangeListener(event -> {
			if(!event.getValue().isEmpty()){
				double temp = Double.valueOf(event.getValue());
				currentcontrol.setKpd(temp);
			}
		});
		
		currentControlKID.addValueChangeListener(event -> {
			if(!event.getValue().isEmpty()){
				double temp = Double.valueOf(event.getValue());
				currentcontrol.setKid(temp);
			}
		});
		
		currentControlRAD.addValueChangeListener(event -> {
			if(!event.getValue().isEmpty()){
				double temp = Double.valueOf(event.getValue());
				currentcontrol.setRad(temp);
			}
		});
		
		currentControlKPQ.addValueChangeListener(event -> {
			if(!event.getValue().isEmpty()){
				double temp = Double.valueOf(event.getValue());
				currentcontrol.setKpq(temp);
			}
		});
		
		currentControlKIQ.addValueChangeListener(event -> {
			if(!event.getValue().isEmpty()){
				double temp = Double.valueOf(event.getValue());
				currentcontrol.setKiq(temp);
			}
		});
		
		currentControlRAQ.addValueChangeListener(event -> {
			if(!event.getValue().isEmpty()){
				double temp = Double.valueOf(event.getValue());
				currentcontrol.setRaq(temp);
			}
		});
		
		setCompositionRoot(textfields);
	}
}
