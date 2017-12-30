package moneycalculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MoneyCalculator {

    public static void main(String[] args) throws Exception {
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.execute();
    }
    
    private double amount, exchangeRate;
    private String currencyFrom, curencyTo;
    
    private void execute() throws Exception {
        input();
        process();
        output();
    }
    
    private void input() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an amount: ");
        amount = Double.parseDouble(scanner.next());
        
        System.out.print("Enter source currency: ");
        currencyFrom = scanner.next();
        
        System.out.print("Enter destination currency: ");
        curencyTo = scanner.next();
    }
    
    private void process() throws Exception {
        exchangeRate = getExchangeRate(currencyFrom, curencyTo);
    }

    private void output() {
        System.out.println(amount + " " + currencyFrom + " are equal to " + 
                amount * exchangeRate + " " + curencyTo);
    }
    
    private static double getExchangeRate (String from, String to) throws Exception {
        URL url = new URL("http://api.fixer.io/latest?base=" + from + "&symbols=" + to);
        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
        conection.setRequestMethod("GET");
        InputStreamReader input = new InputStreamReader(conection.getInputStream());
        try (BufferedReader reader = new BufferedReader(input)) {
            String line = reader.readLine();
            line = line.substring(line.indexOf(to)+5, line.indexOf("}"));
            return Double.parseDouble(line);
        }
    }

}