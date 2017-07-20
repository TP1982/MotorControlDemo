package com.example.Vaadin8MotorControl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.vaadin.hezamu.canvas.Canvas;

public class MyCanvas extends Canvas{
	
	public MyCanvas(){
		super();
	}
	
	public void plot(ArrayList<Double> xList, ArrayList<Double> yList, String color){
		if(xList.size() != yList.size()){
			System.out.println("Arrays/Vectors are different in sizes");
		}else {
			this.beginPath();
			if("blue".equals(color)){
				this.setStrokeStyle("rgb(0, 0, 255)");
			}else if("green".equals(color)){
				this.setStrokeStyle("rgb(0, 255, 0)");
			}else if("red".equals(color)){
				this.setStrokeStyle("rgb(255, 0, 0)");
			}else if("magenta".equals(color)){
				this.setStrokeStyle("rgb(255, 0, 255)");
			}else if("cyan".equals(color)){
				this.setStrokeStyle("rgb(0, 255, 255)");
			}else if("silver".equals(color)){
				this.setStrokeStyle("rgb(192, 192, 192)");
			}
			
			this.setLineCap("butt");
			this.setLineJoin("round");
			
			double xmax = xList.stream().max(Double::compare).get();
			double xmin = xList.stream().min(Double::compare).get();
			double ymax = yList.stream().max(Double::compare).get();
			double ymin = yList.stream().min(Double::compare).get();
			
			double xpixel = 0;
			double ypixel = 0;
			for(int i = 0; i < xList.size(); i++){
				xpixel = floatToPixel(xList.get(i), xmax, xmin, this.getWidth());
				ypixel = floatToPixel(yList.get(i), ymax, ymin, this.getHeight());
				this.lineTo(xpixel, ypixel);
			}
			this.stroke();
		}
	}
	
	public double floatToPixel(double input, double max, double min, double pixelArea){
		double margin = 10;
    	double maxArea = (max - min);
    	double pixel = -1.0*((pixelArea-margin) / maxArea) * input + pixelArea - 5;
    	
    	return pixel;
	}
	
	public void drawGrid(ArrayList<Double> xList, ArrayList<Double> yList, int xTicks, int yTicks){
    	double margin = 10;
    	// First creation of y-axis
		this.beginPath();
    	this.setStrokeStyle("rgb(0,0,0)");
    	this.lineTo(0, 0);
    	this.lineTo(0, this.getHeight());
    	this.stroke();
    	
    	double closestToZero = 1;
		int index = 0;
    	for(int i = 0; i < xList.size(); i++){
    		if(Math.abs(yList.get(i)) < closestToZero){
    			closestToZero = yList.get(i);
    			index = i;
    		}
    	}
    	double yMax = yList.stream().max(Double::compare).get();
    	System.out.println("ymax: " + yMax);
		double yMin = yList.stream().min(Double::compare).get();
		System.out.println("ymin: " + yMin);
		double xMax = xList.stream().max(Double::compare).get();
		System.out.println("xmax: " + xMax);
		double xMin = xList.stream().min(Double::compare).get();
		System.out.println("xmin: " + xMin);
		
		this.beginPath();
		this.setStrokeStyle("rgb(0,0,0)");
		double yPoint = floatToPixel(closestToZero, yMax, yMin, this.getHeight());
		
		System.out.println("nollapiste y-akselilla pixelinä: " + yPoint);
		this.lineTo(0, yPoint);
		this.lineTo(this.getWidth(), yPoint);
		this.stroke();
    	System.out.println("drawGrid(): value of closest to zero: " + closestToZero);
    	System.out.println("drawGrid(): index of the value which is closest to zero: " + index);
    	
    	
    	DecimalFormat df = new DecimalFormat("#.#");
    	df.setRoundingMode(RoundingMode.HALF_UP);
    	// draw x-axis ticks
    	double tickValue = xMax / xTicks;
    	double division = (this.getWidth()-margin) / xTicks;
    	double sum = 0;
    	double sumOfTicks = 0;
    	for(int i=0; i < (xTicks + 1); i++){
    		this.beginPath();
    		this.lineTo(sum, yPoint-5);
    		this.lineTo(sum, yPoint+5);
    		this.fillText(String.valueOf(df.format(sumOfTicks)), sum-5, yPoint-10, 10);
    		this.stroke();
    		sum += division;
    		sumOfTicks += tickValue;
    	}
    	
    	// draw y-axis ticks
    	
    	
    	tickValue = (yMax - yMin) / yTicks;
    	division = (this.getHeight()-margin) / yTicks;
    	sum = this.getHeight()-division;
    	sumOfTicks = 0;
    	for(int i=1; i < (xTicks + 1); i++){
    		sumOfTicks += tickValue;
    		this.beginPath();
    		this.lineTo(0-5, sum);
    		this.lineTo(0+5, sum);
    		this.fillText(String.valueOf(df.format(sumOfTicks)), 6, sum+5, 20);
    		this.stroke();
    		sum -= division;
    		
    	}
    }
}
