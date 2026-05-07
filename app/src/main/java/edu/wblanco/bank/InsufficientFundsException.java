/**
 * InsufficientFundsException.java
 * 
 * Description: Custom exception thrown when a withdrawl or transfer 
 *              exceeds the account's available balance.
 * 
 * Author: William Blanco
 * Date: May 2026
 */

package edu.wblanco.bank;
/**
 * Thrown when a withdrawl or transfer is attempted for an amount
 * that exceeds the account's current balance.
 */
public class InsufficientFundsException extends RuntimeException{

    private final double attemptedAmount;
    private final double availableBalance;

    /**
     * Constructs a new exception with details about the failed transaction.
     * 
     * @param attemptedAmount the amount that was requested for withdrawl
     * @param availableBalance the actual balance available at the time of the attempt
     */
    public InsufficientFundsException(double attemptedAmount, double availableBalance){
        super(String.format("Cannot withdraw %.2f: available balance is %.2f",attemptedAmount, availableBalance));
        this.attemptedAmount = attemptedAmount;
        this.availableBalance = availableBalance;
    }

    /**
     * Returns the amount that was requested for withdrawl.
     * 
     * @return the attempted amount to withdrawl
     */
    public double getAttemptedAmount(){
        return attemptedAmount;
    }

    /**
     * Returns the available balance at the time of failed withdrawl.
     * 
     * @return the available balance 
     */
    public double getAvailableBalance(){
        return availableBalance;
    }
}