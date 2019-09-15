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
    
    
    //Will fix the comment soon ( Dawud Baig)
    private Map<Integer,Integer> coinMap = new HashMap<Integer,Integer>();

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: break;
            case 10: break;
            case 25: break;
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
        totalCollected+=insertedSoFar;
        reset();
        return r;
    }

    @Override
    public Map<Integer,Integer> cancel() {
        //Reset transaction counts and return the coins in play
        Map<Integer,Integer> tempMap = coinMap;
        reset();
        return tempMap;
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
        coinMap=new HashMap<Integer, Integer>();
    
    }
    
    @Override
    public int empty() {
        //returns the total value of coins entered since last empty()
        int totalEarnings = insertedSoFar;
        reset();
        return totalEarnings;
    
    
    }
}
