package com.HenrysGroceries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.Banner;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class HenrysGroceriesApplication implements CommandLineRunner {



	private static Logger LOG = LoggerFactory
			.getLogger(HenrysGroceriesApplication.class);

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HenrysGroceriesApplication.class);
	}

	private static List<Stock> stocks = new ArrayList<Stock>();

	public static void main(String[] args) {

		LOG.info("STARTING THE APPLICATION");
		SpringApplication app = new SpringApplication(HenrysGroceriesApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);

		stocks.add(new Stock("soup", "tin", 0.65));
		stocks.add(new Stock("bread", "loaf", 0.80));
		stocks.add(new Stock("milk", "bottle", 1.30));
		stocks.add(new Stock("apples", "single", 0.10));

		System.out.println("############################################");
		System.out.println("Product		" + "Unit		" + "COST		");
		System.out.println("############################################");

		stocks.forEach( a -> {
			System.out.println(a.product+"		"+ a.unit+"			"+ a.cost);

		});
		System.out.println("############################################");

		LocalDate today = LocalDate.now();
		System.out.println("Today's Local date : " + today);

		LocalDate appleFromDate = today.plus(3, ChronoUnit.DAYS);
		LocalDate appleToDate = LocalDate.of(2019, Month.JULY, 31);
		LocalDate breadToDate = today.plus(6, ChronoUnit.DAYS);

		System.out.println("##########################################################################################");
		System.out.println("								TODAY DiSCOUNT : "+ today +"								  ");


		System.out.println("##########################################################################################");
		System.out.println("the offer													valid from			valid to		");
		System.out.println("##########################################################################################");
		System.out.println("Buy 2 tins of soup and get a loaf of bread half price		yesterday			for 7 days		");
		System.out.println("Apples have a 10% discount									from 3 days hence	until the end of the following month");

		System.out.println("##########################################################################################");

		// create a scanner so we can read the command-line input

		Scanner scanner = new Scanner(System.in);


		stocks.forEach( a -> {
			//  prompt for the user's name
			System.out.print("Did you want to buy "+ a.product+ "(y/n)");

			// get their input as a String
			String answer = scanner.next();

			double price = 0;
			boolean offerFlog = false;

			if(answer.equalsIgnoreCase("Y")){
				System.out.println("How many "+ a.product + " want to buy");
				int quantiy = scanner.nextInt();
				if(a.product.equalsIgnoreCase("apples") && today.isAfter(appleFromDate) && today.isBefore(appleToDate)) {
					a.setTotalPrice(a.totalPrice  + (a.cost - ( a.cost * 0.1)) * quantiy);
				} else if(a.product.equalsIgnoreCase("soup")){
					if(quantiy >= 2 && today.isBefore(breadToDate))
						offerFlog = true;
					a.setTotalPrice(a.totalPrice  + (a.cost * quantiy));
				} else if(a.product.equalsIgnoreCase("bread")) {
					if(offerFlog)
						a.setTotalPrice(a.totalPrice  + (a.cost - ( a.cost * 0.5))* quantiy);
					else
						a.setTotalPrice(a.totalPrice  + (a.cost * quantiy));
				} else if(a.product.equalsIgnoreCase("milk")) {
					a.setTotalPrice(a.totalPrice  + (a.cost * quantiy));
				}

			}
		});

		// prompt for their age
		System.out.println("Total cost :  "+ Stock.totalPrice);

		// get the age as an int

		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("EXECUTING : command line runner");

		for (int i = 0; i < args.length; ++i) {
			LOG.info("args[{}]: {}", i, args[i]);
		}

	}
}

class Stock {

	String product ;
	String unit;
	double cost;
	static double totalPrice;

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Stock(String product, String unit, double cost){
		this.product = product;
		this.unit = unit;
		this.cost = cost;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}



}
