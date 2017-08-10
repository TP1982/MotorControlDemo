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
				xpixel = floatToPixelNormal(xList.get(i), xmax, xmin, this.getWidth());
				ypixel = floatToPixel(yList.get(i), ymax, ymin, this.getHeight());
				this.lineTo(xpixel, ypixel);
			}
			this.stroke();
		}
	}
	
	public void plot2(ArrayList<Double> xList, ArrayList<Double> yList, ArrayList<Double> yList2, String color1, String color2){
		if(xList.size() != yList.size() || xList.size() != yList2.size()){
			System.out.println("Arrays/Vectors are different in sizes");
		}else {
			double valueArea = 0;
			double valueAreaMax1 = yList.stream().max(Double::compare).get() - yList.stream().min(Double::compare).get();
	    	double valueAreaMax2 = yList2.stream().max(Double::compare).get() - yList2.stream().min(Double::compare).get();
			double yMax = 0.0;
			double yMin = 0.0;
			double xMax = 0.0;
			double xMin = 0.0;
			xMax = xList.stream().max(Double::compare).get();
			System.out.println("xmax: " + xMax);
			xMin = xList.stream().min(Double::compare).get();
			System.out.println("xmin: " + xMin);
			
	    	if(valueAreaMax1 < valueAreaMax2){
	    		valueArea = valueAreaMax2;
	    		yMax = yList2.stream().max(Double::compare).get();
		    	System.out.println("ymax: " + yMax);
				yMin = yList2.stream().min(Double::compare).get();
				System.out.println("ymin: " + yMin);
				
			}else {
				valueArea = valueAreaMax1;
				yMax = yList.stream().max(Double::compare).get();
		    	System.out.println("ymax: " + yMax);
				yMin = yList.stream().min(Double::compare).get();
				System.out.println("ymin: " + yMin);
				
			}
			this.beginPath();
			if("blue".equals(color1)){
				this.setStrokeStyle("rgb(0, 0, 255)");
			}else if("green".equals(color1)){
				this.setStrokeStyle("rgb(0, 255, 0)");
			}else if("red".equals(color1)){
				this.setStrokeStyle("rgb(255, 0, 0)");
			}else if("magenta".equals(color1)){
				this.setStrokeStyle("rgb(255, 0, 255)");
			}else if("cyan".equals(color1)){
				this.setStrokeStyle("rgb(0, 255, 255)");
			}else if("silver".equals(color1)){
				this.setStrokeStyle("rgb(192, 192, 192)");
			}
			
			this.setLineCap("butt");
			this.setLineJoin("round");
			
			
			double xpixel = 0;
			double ypixel = 0;
			for(int i = 0; i < xList.size(); i++){
				xpixel = floatToPixelNormal(xList.get(i), xMax, xMin, this.getWidth());
				ypixel = floatToPixel(yList.get(i), yMax, yMin, this.getHeight());
				this.lineTo(xpixel, ypixel);
			}
			this.stroke();
			
			//---------------------------------------------------
			this.beginPath();
			if("blue".equals(color2)){
				this.setStrokeStyle("rgb(0, 0, 255)");
			}else if("green".equals(color2)){
				this.setStrokeStyle("rgb(0, 255, 0)");
			}else if("red".equals(color2)){
				this.setStrokeStyle("rgb(255, 0, 0)");
			}else if("magenta".equals(color2)){
				this.setStrokeStyle("rgb(255, 0, 255)");
			}else if("cyan".equals(color2)){
				this.setStrokeStyle("rgb(0, 255, 255)");
			}else if("silver".equals(color2)){
				this.setStrokeStyle("rgb(192, 192, 192)");
			}
			this.setLineCap("butt");
			this.setLineJoin("round");
			
			
			xpixel = 0;
			ypixel = 0;
			for(int i = 0; i < xList.size(); i++){
				xpixel = floatToPixelNormal(xList.get(i), xMax, xMin, this.getWidth());
				ypixel = floatToPixel(yList2.get(i), yMax, yMin, this.getHeight());
				this.lineTo(xpixel, ypixel);
			}
			this.stroke();
		}
	}
	
	public double floatToPixel(double input, double max, double min, double pixelArea){
		double margin = 40;
    	double maxArea = (max - min);
    	double AreaOfPixels = pixelArea - 2 * margin;
    	double pixel = 0;
    	
    	pixel = -1.0*(input / maxArea) * AreaOfPixels + AreaOfPixels + ((min / maxArea) * AreaOfPixels) + margin;
    	
    	return pixel;
	}
	
	public double floatToPixelNormal(double input, double max, double min, double pixelArea){
		double margin = 40;
    	double maxArea = (max - min);
    	double AreaOfPixels = pixelArea - 2 * margin;
    	double pixel = 0;
    	
    	pixel = (input / maxArea) * AreaOfPixels - ( (min / maxArea) * AreaOfPixels) + margin;
    	
    	return pixel;
	}
	
	
	public void drawAxes(ArrayList<Double> xList, ArrayList<Double> yList, int xTicks, int yTicks){
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
		
		//double xaxepositionInPixel = Math.abs(yMin) / (yMax - yMin) * this.getHeight();
		double xaxepositionInPixel = floatToPixel(0, yMax, yMin, this.getHeight());
		this.beginPath();
		this.setStrokeStyle("rgb(0,0,0)");
		double yPoint = floatToPixel(closestToZero, yMax, yMin, this.getHeight());
		
		//yPoint = -xaxepositionInPixel + this.getHeight() - margin/2;
		
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
    	double division = (this.getWidth() - margin) / xTicks;
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
    	division = (this.getHeight() - margin) / yTicks;
    	sum = this.getHeight() - division;
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
	
	private int closestToZeroIndex(ArrayList<Double> list){
        int closestToZero = 0;
        if(!list.isEmpty()){
            double zero = Math.abs(list.get(0));
            for(int i = 0; i < list.size(); i++){
                if(Math.abs(list.get(i)) < zero){
                    zero = Math.abs(list.get(i));
                    closestToZero = i;
                }
            }
        }

        return closestToZero;
    }
	
	public void drawAxes2(ArrayList<Double> xList, ArrayList<Double> yList, ArrayList<Double> yList2, int xTicks, int yTicks){
    	double valueArea = 0;
		double valueAreaMax1 = yList.stream().max(Double::compare).get() - yList.stream().min(Double::compare).get();
    	double valueAreaMax2 = yList2.stream().max(Double::compare).get() - yList2.stream().min(Double::compare).get();
		double yMax = 0.0;
		double yMin = 0.0;
		double xMax = 0.0;
		double xMin = 0.0;
		xMax = xList.stream().max(Double::compare).get();
		System.out.println("xmax: " + xMax);
		double xMaxPixel = floatToPixelNormal(xMax, xMax, xMin, this.getWidth());
		xMin = xList.stream().min(Double::compare).get();
		System.out.println("xmin: " + xMin);
		double xMinPixel = floatToPixelNormal(xMin, xMax, xMin, this.getWidth());
		
    	if(valueAreaMax1 < valueAreaMax2){
    		valueArea = valueAreaMax2;
    		yMax = yList2.stream().max(Double::compare).get();
	    	System.out.println("ymax: " + yMax);
			yMin = yList2.stream().min(Double::compare).get();
			System.out.println("ymin: " + yMin);
			
		}else {
			valueArea = valueAreaMax1;
			yMax = yList.stream().max(Double::compare).get();
	    	System.out.println("ymax: " + yMax);
			yMin = yList.stream().min(Double::compare).get();
			System.out.println("ymin: " + yMin);
			
		}
    	double yMaxPixel = floatToPixel(yMax, yMax, yMin, this.getHeight());
		double yMinPixel = floatToPixel(yMin, yMax, yMin, this.getHeight());
    	
    	double xdivision = (0.5f*(xMax - xMin) ) / xTicks;
        System.out.println("drawTicks -> Stepsize of x: " + xdivision);
        double ydivision = (0.5f*(yMax - yMin) ) / yTicks;
        System.out.println("drawTicks -> Stepsize of y: " + ydivision);
        double xdivPixel = (0.5f*(xMaxPixel - xMinPixel)) / xTicks;
        System.out.println("drawTicks -> Stepsize of x in pixels: " + xdivPixel);
        double ydivPixel = (0.5f*(yMinPixel - yMaxPixel)) / yTicks;
        System.out.println("drawTicks -> Stepsize of y in pixels: " + ydivPixel);
    	
    	int tmp = closestToZeroIndex(xList);
    	double yaxepositionInPixel = floatToPixelNormal(xList.get(tmp), xMax, xMin, this.getWidth());
		double margin = 10;
    	// First creation of y-axis
		this.beginPath();
    	this.setStrokeStyle("rgb(0,0,0)");
    	this.lineTo(yaxepositionInPixel, 0);
    	this.lineTo(yaxepositionInPixel, this.getHeight());
    	this.stroke();

    	int tmp2 = closestToZeroIndex(yList);
		double xaxepositionInPixel = floatToPixel(yList.get(tmp2), yMax, yMin, this.getHeight());
		
		this.beginPath();
		this.setStrokeStyle("rgb(0,0,0)");
		double yPoint = xaxepositionInPixel;
		System.out.println("yPoint: " + yPoint);
		this.lineTo(0, xaxepositionInPixel);
		this.lineTo(this.getWidth(), xaxepositionInPixel);
		this.stroke();
    	
    	DecimalFormat df = new DecimalFormat("#.#");
    	df.setRoundingMode(RoundingMode.HALF_UP);
    	
    	// draw x-axis ticks to positive side
    	double xvalueinit = xMax / xTicks;
    	
    	double sum = 0;
    	double sumOfTicks = 0;
    	double ypixelinit = floatToPixelNormal(yList.get(tmp2), yMax, yMin, this.getHeight());
    	double xpixelinit = floatToPixelNormal(xList.get(tmp), xMax, xMin, this.getWidth());
    	System.out.println("xpixelinit: " + xpixelinit);
    	if(xMin >= 0){
    		xpixelinit = floatToPixelNormal(xList.get(tmp), xMax, xMin, this.getWidth());
    		xvalueinit = xList.get(tmp);
    		for(int i=0; i < 3*xTicks; i++){
        		xpixelinit += xdivPixel;
        		xvalueinit += xdivision;
        		this.beginPath();
        		this.lineTo(xpixelinit, yPoint-5);
        		this.lineTo(xpixelinit, yPoint+5);
        		this.fillText(String.valueOf(df.format(xvalueinit)), xpixelinit, yPoint+20, 10);
        		this.stroke();
        		
        	}
    	}else{
    		xpixelinit = floatToPixelNormal(xList.get(tmp), xMax, xMin, this.getWidth());
    		xvalueinit = xList.get(tmp);
    		for(int i=0; i < 3*xTicks; i++){
        		xpixelinit += xdivPixel;
        		xvalueinit += xdivision;
        		this.beginPath();
        		this.lineTo(xpixelinit, yPoint-5);
        		this.lineTo(xpixelinit, yPoint+5);
        		this.fillText(String.valueOf(df.format(xvalueinit)), xpixelinit, yPoint+20, 10);
        		this.stroke();
        		
        	}
    		xpixelinit = floatToPixelNormal(xList.get(tmp), xMax, xMin, this.getWidth());
    		xvalueinit = xList.get(tmp);
    		for(int i=0; i < 3*xTicks; i++){
        		xpixelinit -= xdivPixel;
        		xvalueinit -= xdivision;
        		this.beginPath();
        		this.lineTo(xpixelinit, yPoint-5);
        		this.lineTo(xpixelinit, yPoint+5);
        		this.fillText(String.valueOf(df.format(xvalueinit)), xpixelinit, yPoint+20, 10);
        		this.stroke();
        		
        	}
    	}
    	
    	
    	// draw y-axis ticks
    	ypixelinit = floatToPixel(yList.get(tmp2), yMax, yMin, this.getHeight());
    	
    	double yvalueinit = yList.get(tmp2);
    	if(yMin >= 0){
    		xpixelinit = floatToPixelNormal(xList.get(tmp), xMax, xMin, this.getWidth());
        	for(int i=1; i < (3*yTicks); i++){
        		ypixelinit -= ydivPixel;
        		yvalueinit += ydivision;
        		this.beginPath();
        		this.lineTo(xpixelinit-5, ypixelinit);
        		this.lineTo(xpixelinit+5, ypixelinit);
        		this.fillText(String.valueOf(df.format(yvalueinit)), xpixelinit+10, ypixelinit+5, 20);
        		this.stroke();
        		sum -= ydivision;
        		
        	}
    	}else{
    		xpixelinit = floatToPixelNormal(xList.get(tmp), xMax, xMin, this.getWidth());
    		yvalueinit = yList.get(tmp2);
    		ypixelinit = floatToPixel(yList.get(tmp2), yMax, yMin, this.getHeight());
        	for(int i=1; i < (3*yTicks); i++){
        		ypixelinit -= ydivPixel;
        		yvalueinit += ydivision;
        		this.beginPath();
        		this.lineTo(xpixelinit-5, ypixelinit);
        		this.lineTo(xpixelinit+5, ypixelinit);
        		this.fillText(String.valueOf(df.format(yvalueinit)), xpixelinit+10, ypixelinit+5, 20);
        		this.stroke();
        		sum -= ydivision;
        		
        	}
        	xpixelinit = floatToPixelNormal(xList.get(tmp), xMax, xMin, this.getWidth());
    		yvalueinit = yList.get(tmp2);
    		ypixelinit = floatToPixel(yList.get(tmp2), yMax, yMin, this.getHeight());
        	for(int i=1; i < (3*yTicks); i++){
        		ypixelinit += ydivPixel;
        		yvalueinit -= ydivision;
        		this.beginPath();
        		this.lineTo(xpixelinit-5, ypixelinit);
        		this.lineTo(xpixelinit+5, ypixelinit);
        		this.fillText(String.valueOf(df.format(yvalueinit)), xpixelinit+10, ypixelinit+5, 20);
        		this.stroke();
        		sum -= ydivision;
        		
        	}
    	}
    	
    }
	private double getMaxIndexromArrayList(ArrayList<Float> list){
        float max = 0;
        int maxIndex = 0;
        if(!list.isEmpty()){
            for(int i = 0; i<list.size(); i++){
                if(list.get(i) > max){
                    max = list.get(i);
                    maxIndex = i;
                }
            }
        }
        return maxIndex;

    }

    private double getMinIndexFromArrayList(ArrayList<Float> list){
        double min = 10.0f;
        int minIndex = 0;
        if(!list.isEmpty()){
            for(int i = 0; i < list.size(); i++){
                if(list.get(i) < min){
                    min = list.get(i);
                    minIndex = i;
                }
            }
        }

        return minIndex;
    }
}
