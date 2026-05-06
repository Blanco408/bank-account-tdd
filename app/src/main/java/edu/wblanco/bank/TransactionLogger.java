/**
 * TransactionLogger.java
 * 
 * Description: Interface defining the contract for recording bank account 
 *              transactions to an external destination
 * 
 * Author: William Blanco
 * Date: May 2026
 */

package edu.wblanco.bank;


public interface TransactionLogger{
  /**
   * Records a completed transaction with its details. 
   * @param type the type of transaction
   * @param amount the monetary amount involved in the transaction
   * @param balance the account balance after the transaction completed 
   */
    void log(String type, double amount, double balance);
}