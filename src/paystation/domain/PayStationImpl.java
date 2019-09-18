package paystation.domain;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    
    private int insertedSoFar;
    private int timeBought;
    private int totalEarnings=0;
    private int count5= 0 ;
    private int count10 = 0;
    private int count25 = 0;
    
    
    
    protected HashMap<Integer,Integer> coinMap = new HashMap<Integer,Integer>();
    
    
    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: 
                count5++;
                coinMap.put(5, count5);
                break;
            case 10: 
                count10++;
                coinMap.put(10, count10);
                break;
            case 25: 
                count25++;
                coinMap.put(25, count25);
                break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        // reset transaction and return reciept object
        Receipt r = new ReceiptImpl(timeBought);
        totalEarnings+=insertedSoFar;
        reset();
        return r;
    }

    @Override
    public HashMap<Integer,Integer> cancel() {
        //Reset transaction counts and return the coins in play
        if (coinMap.get(5) == null) {
            coinMap.put(5, 0);
        };
        if (coinMap.get(10) == null) {
            coinMap.put(10, 0);
        };
        if (coinMap.get(25) == null) {
            coinMap.put(25, 0);
        };
        
        HashMap<Integer,Integer> tempMap = new HashMap<Integer,Integer>();
        tempMap.put(5,coinMap.get(5));
        tempMap.put(10,coinMap.get(10));
        tempMap.put(25,coinMap.get(25));
        reset();
        return tempMap;
    }
    
    
    private void reset() {
        coinMap.put(5, 0);
        coinMap.put(10, 0);
        coinMap.put(25, 0);
        timeBought = insertedSoFar = 0;
        count5 = 0; count10 = 0; count25 = 0;
    
    }
    
    @Override
    public int empty() {
        //returns the total value of coins entered since last empty()
        int total = totalEarnings;
        totalEarnings = 0;
        reset();
        return total;
    
    
    }
}
