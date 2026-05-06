/**
 * BankAccount.java
 * 
 * Description: Represents a bank that supports deposits, withdrawals, transfers,
 *              and optional transaction logging.
 * 
 * Author: William Blanco
 * 
 * Date: May 2026
 */

package edu.wblanco.bank;

/**
 * Represents a bank account that supports deposits, withdrawals, and optional transaction logging.
 * 
 * All monetary amounts must be positive. Withdrawals that exceed that
 * the current balance throw {@link InsufficientFundsException}
 *     
 */
public class BankAccount{

    private final String accountId;
    private double balance;
    private final TransactionLogger logger;

    /**
     * Creates a new account with a starting balance and no transaction logging.
     * 
     * @param accountID unique identifier for account
     * @param initialBalance starting balance; must be non-negative
     * @throws IllegalArgumentException if initialBalance is negative
     */

    public BankAccount(String accountId, double initialBalance){
        this(accountId, initialBalance,null);
    }

    /**
     * 
     * @param accountId unique identifier for account
     * @param initialBalance starting balance; must be non-negative
     * @param logger records each completed transaction; may be null
     * @throws IllegalArgumentException if initialBalance is negative
     */
    public BankAccount(String accountId, double initialBalance, TransactionLogger logger){
        if(initialBalance < 0){
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.accountId = accountId;
        this.balance = initialBalance;
        this.logger = logger;
    }

    /**
     * Deposits the specified amount into the account.
     * 
     * @param amount the amount to deposit; must be positive
     * @throws IllegalArgumentException if amount is zero or negative
     */
    public void deposit(double amount){
        requirePositiveAmount(amount);
        balance += amount;
        logIfEnabled("DEPOSIT", amount);
    }


    private void requirePositiveAmount(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be positive, your amount is: " + amount);
        }
    }

    private void logIfEnabled(String transactionType, double amount){
        if(logger != null){
            logger.log(transactionType, amount, balance);
        }
    }

    /**
     * Withdraws the specified amount from the account.
     * 
     * @param amount the amount to withdraw; must be positive
     * @throws IllegalArgumentException if amount is zero or negative
     * @throws InsufficientFundsException use if it exceeds the accounts current balance
     */

    public void withdraw(double amount){
        requirePositiveAmount(amount);
        if (amount > balance){
            throw new InsufficientFundsException(amount,balance);
        }
        balance -= amount;
        logIfEnabled("WITHDRAWAL", amount);
    }

    /**
     * 
     * @param amount the amount to transfer; must be positive
     * @param destination the account receive the funds
     * @throws IllegalArgumentException if amount is zero or negative
     * @throws InsufficientFundsException use if it exceeds the accounts current balance
     */
    public void transfer(double amount, BankAccount destination){
        withdraw(amount);
        destination.deposit(amount);
    }

    /**
     * Returns the unique identifier for this account.
     * 
     * @return the account ID
     */
    public String getAccountId(){
        return accountId;
    }
    /**
     * Returns the current balance of the account.
     * 
     * @return the current balance
     */
    public double getBalance(){
        return balance;
    }

    /**
     * Returns true if the account has a balance greater than zero.
     * 
     * @return true if balance is positive, false otherwise 
     */
    public boolean hasBalance(){
        return balance > 0;
    }

}
