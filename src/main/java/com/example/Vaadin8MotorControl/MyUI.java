/**
 * @author TuomoP
 * @version 0.9
 * 
 */
package com.example.Vaadin8MotorControl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.vaadin.hezamu.canvas.Canvas;

import com.example.math.CurrentControl;
import com.example.math.LPF;
import com.example.math.PMSM;
import com.example.math.SVPWM;
import com.example.math.SpeedControl;
import com.example.math.TaurefToIqref;
import com.example.math.VectorPair;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ClassResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	double timeStep = 0.0001;
	double time = 0.0;
	double maxOfTime = 10.0;
	double minOfTime = 0.0;
	int xAxeTickCount = 8;
	int yAxeTickCount = 8;
	
	SpeedControl speedControl = new SpeedControl();
	TaurefToIqref calculate = new TaurefToIqref();
	CurrentControl currentControl = new CurrentControl();
	SVPWM svpwm = new SVPWM();
	PMSM pmsm = new PMSM();
	LPF dCurrentLPF = new LPF(200.0, timeStep);
	LPF qCurrentLPF = new LPF(200.0, timeStep);
	
	// Lists of simulated values
	ArrayList<Double> timeList = new ArrayList<Double>();
	ArrayList<Double> wrefList = new ArrayList<Double>();
	ArrayList<Double> wList = new ArrayList<Double>();
	ArrayList<Double> iqrefList = new ArrayList<Double>();
	ArrayList<Double> iqList = new ArrayList<Double>();
	ArrayList<Double> idrefList = new ArrayList<Double>();
	ArrayList<Double> idList = new ArrayList<Double>();
	ArrayList<Double> udList = new ArrayList<Double>();
	ArrayList<Double> uqList = new ArrayList<Double>();
	ArrayList<Double> idFilteredList = new ArrayList<Double>();
	ArrayList<Double> iqFilteredList = new ArrayList<Double>();
	
	Label progressLabel = new Label("");
	Label infoLabel = new Label("", ContentMode.HTML);
	MyCanvas canvas = new MyCanvas();
	
	List<VectorPair> listOfVectorPairs = new ArrayList<>();
	ComboBox<VectorPair> plotOptions = new ComboBox<>("Data to plot");
	
	String basepath = VaadinService.getCurrent()
            .getBaseDirectory().getAbsolutePath();
	
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	infoLabel.setValue("This is a demo about control of Permanent Magnet Synchronous Machine"
    			+"<br>" + "*Press run to start simulation"
    			+"<br>" + "*Choose which variables to plot with data to plot"
    			+"<br>" + "*Patience may be required when plotting the results. Simulation time varies from time to time"
    			+"<br>" + "*By changing the control parameters one can see the difference in the plot");
    	infoLabel.addStyleName(ValoTheme.LABEL_COLORED);
    	
    	progressLabel.addStyleName(ValoTheme.LABEL_LARGE);
    	progressLabel.addStyleName(ValoTheme.LABEL_COLORED);
    	Button runButton = new Button("Run");
    	Button clearButton = new Button("Clear");
    	
    	
    	canvas.setHeight("600px");
    	canvas.setWidth("600px");
    	
    	clearButton.addClickListener( e -> {
    		time = 0.0;
    		canvas.clear();
    		listOfVectorPairs.clear();
    		timeList.clear();
    		
    		wrefList.clear();
    		wList.clear();
    		iqrefList.clear();
    		iqList.clear();
    		idrefList.clear();
    		idList.clear();
    		udList.clear();
    		uqList.clear();
    		idFilteredList.clear();
    		iqFilteredList.clear();
    	});

    	runButton.addClickListener( e -> {
    		
    		progressLabel.setValue("Simulation started.");
    		simulate();
    		
    		plotOptions.setItems(listOfVectorPairs);
    		canvas.clear();
			canvas.drawAxes2(timeList, wrefList, wList, 8, 8);
			canvas.plot2(timeList, wrefList, wList, "blue", "green");
    		
    		System.out.println("wrefmin: " + wrefList.stream().min(Double::compare).get());
        	System.out.println("wrefmax: " + wrefList.stream().max(Double::compare).get());
        	System.out.println("wmin: " + wList.stream().min(Double::compare).get());
        	System.out.println("wmax: " + wList.stream().max(Double::compare).get());
        	System.out.println("iqrefmax: " + iqrefList.stream().max(Double::compare).get());
        	System.out.println("iqrefmin: " + iqrefList.stream().min(Double::compare).get());
        	System.out.println("iqmin: " + iqList.stream().min(Double::compare).get());
        	System.out.println("iqmax: " + iqList.stream().max(Double::compare).get());
        	System.out.println("idrefmax: " + idrefList.stream().max(Double::compare).get());
        	System.out.println("idrefmin: " + idrefList.stream().min(Double::compare).get());
        	System.out.println("idmin: " + idList.stream().min(Double::compare).get());
        	System.out.println("idmax: " + idList.stream().max(Double::compare).get());
        	System.out.println("udmax: " + udList.stream().max(Double::compare).get());
        	System.out.println("udmin: " + udList.stream().min(Double::compare).get());
        	System.out.println("uqmin: " + uqList.stream().min(Double::compare).get());
        	System.out.println("uqmax: " + uqList.stream().max(Double::compare).get());

        });
    	
    	plotOptions.addValueChangeListener(e -> {
    		if(plotOptions.getValue().toString().equals("iqref(t) & iq(t)")){
    			canvas.clear();
    			canvas.drawAxes2(timeList, iqrefList, iqList, 8, 8);
    			canvas.plot2(timeList, iqrefList, iqList, "blue", "green");
    		}else if(plotOptions.getValue().toString().equals("idref(t) & id(t)")){
    			canvas.clear();
    			canvas.drawAxes2(timeList, idrefList, idList, 8, 8);
    			canvas.plot2(timeList, idrefList, idList, "blue", "green");

    		}else if(plotOptions.getValue().toString().equals("ud(t) & uq(t)")){
    			canvas.clear();
    			canvas.drawAxes2(timeList, udList, uqList, 8, 8);
    			canvas.plot2(timeList, udList, uqList, "blue", "magenta");

    		}else if(plotOptions.getValue().toString().equals("wref(t) & w(t)")){
    			canvas.clear();
    			canvas.drawAxes2(timeList, wrefList, wList, 8, 8);
    			canvas.plot2(timeList, wrefList, wList, "blue", "green");

    		}else if(plotOptions.getValue().toString().equals("idFiltered(t) & iqFiltered(t)")){
    			canvas.clear();
    			canvas.drawAxes2(timeList, idFilteredList, iqFilteredList, 8, 8);
    			canvas.plot2(timeList, idFilteredList, iqFilteredList, "blue", "green");
    		}
    	});
    	
    	
    	
    	
    	Panel main = new Panel();
    	main.setSizeFull();
    	VerticalLayout mainLayout = new VerticalLayout();
    	mainLayout.setSizeUndefined();
    	final Label header = new Label("Motor Control Demo");
    	header.addStyleName(ValoTheme.LABEL_HUGE);
    	header.addStyleName(ValoTheme.LABEL_COLORED);
    	header.addStyleName("headerStyle");
    	
    	// Radiobutton layout
        HorizontalLayout radiobuttonLayout = new HorizontalLayout();
        
        RadioButtonGroup<String> radioButtons = new RadioButtonGroup<>("Motor type");
        
        radioButtons.setItems("PMSM");
        radioButtons.setSelectedItem("PMSM");
        radioButtons.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
        
        RadioButtonGroup<String> controlMethod = new RadioButtonGroup<>("Control method");
        controlMethod.setItems("Vector control");
        controlMethod.setSelectedItem("Vector control");
        
        radiobuttonLayout.addComponents(radioButtons, controlMethod, runButton, clearButton, progressLabel, plotOptions);
        radiobuttonLayout.setSpacing(true);
        
        HorizontalLayout textfieldsLayout = new HorizontalLayout();
        Parameters parameterLayout = new Parameters(speedControl, currentControl);
        
        // Equations and CanvasArea
        HorizontalLayout equationsAndFigure = new HorizontalLayout();
        VerticalLayout eqs = new VerticalLayout();
        equationsAndFigure.addComponents(eqs, canvas);
        
        Label equations = new Label("Equations");
        equations.addStyleName(ValoTheme.LABEL_LARGE);
        equations.addStyleName(ValoTheme.LABEL_COLORED);
        
        System.out.println(basepath);
        
        Image pmsm = new Image("Equations for PMSM", new ClassResource("/images/PMSM.png"));
        Image sc = new Image("Two degrees of freedom speed control equations", new ClassResource("/images/sc.png"));
        eqs.addComponents(equations, pmsm, sc);
        
        mainLayout.addComponents(header, infoLabel, radiobuttonLayout, parameterLayout, equationsAndFigure);
        main.setContent(mainLayout);
        setContent(main);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
    
    
    public void simulate() {
    	progressLabel.setValue("Simulation in progress please wait...");
		double wref = 0;
		ClassLoader classloader = getClass().getClassLoader();
		File file = new File(classloader.getResource("resources/wref.txt").getFile());
		System.out.println(classloader.getResource("resources/wref.txt").getFile());
    	try {
			
    		FileInputStream fs = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			
			System.out.println("Speed controller proportional gain: " + speedControl.getKp());
			System.out.println("Speed controller integral gain: " + speedControl.getKi());
			System.out.println("Speed controller active damping: " + speedControl.getRa());
			
			for(int i=0; i<100001; i++){
				try {
					wref = Double.parseDouble(br.readLine());
			
					speedControl.simulateSpeedControl(wref,pmsm.getW());
					double iqref = calculate.calculateIqref(speedControl.getTauref());
					
					double filteredId = dCurrentLPF.filter(pmsm.getId());
					double filteredIq = qCurrentLPF.filter(pmsm.getIq());
					//System.out.println("["+filteredId+" "+filteredIq+"]");
					
					//currentControl.simulateCurrentControl(0.0, iqref, pmsm.getW(), pmsm.getId(), pmsm.getIq());
					currentControl.simulateCurrentControl(0.0, iqref, pmsm.getW(), filteredId, filteredIq);
					svpwm.simulateSVPWM(currentControl.getUdref(), currentControl.getUqref(), pmsm.getThetar(), time);
					//pmsm.simulateMotor(currentControl.getUdref(), currentControl.getUqref(), pmsm.getWr());
					pmsm.simulateMotor(svpwm.getUd_pwm(), svpwm.getUq_pwm(), pmsm.getWr());
					
					time += 0.0001;
					
					
					timeList.add(time);
					wrefList.add(wref);
					wList.add(pmsm.getW());
					iqrefList.add(iqref);
					iqList.add(pmsm.getIq());
					idrefList.add(currentControl.getIdref());
					idList.add(pmsm.getId());
					udList.add(currentControl.getUdref());
					uqList.add(currentControl.getUqref());
					idFilteredList.add(filteredId);
					iqFilteredList.add(filteredIq);
					//System.out.println(pmsm.getId());
					
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			
			br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("File not found!");
			e1.printStackTrace();
		} catch (IOException e2){
			e2.printStackTrace();
			System.out.println("Buffered reader failed");
		}
    	
    	progressLabel.setValue("Simulation ready. Waiting to plot results!");
    	
		listOfVectorPairs.add(new VectorPair(wrefList, wList, "wref(t) & w(t)"));
		listOfVectorPairs.add(new VectorPair(iqrefList, iqList, "iqref(t) & iq(t)"));
		listOfVectorPairs.add(new VectorPair(idrefList, idList, "idref(t) & id(t)"));
		listOfVectorPairs.add(new VectorPair(udList, uqList, "ud(t) & uq(t)"));
		listOfVectorPairs.add(new VectorPair(idFilteredList, iqFilteredList, "idFiltered(t) & iqFiltered(t)"));
		
    }
    
}
