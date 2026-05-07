/**
 * BankAccountTest.java
 * 
 * Description: Unit tests for the BankAccount class using JUnit 5 and Mockito.
 *              Tests are written following test-driven development principles.
 * 
 * Author: William Blanco
 * Date: May 2026
 */

package edu.wblanco.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("BankAccount")
class BankAccountTest{

    private static final String ACCOUNT_ID = "ACC-001";
    private static final double INITIAL_BALANCE = 500.00;

    private BankAccount account;

    @Mock
    private TransactionLogger mockLogger;

    @BeforeEach
    void createAccountWithInitialBalance(){
        account = new BankAccount(ACCOUNT_ID, INITIAL_BALANCE);
    }

    @Nested
    @DisplayName("when constructed")
    class Construction{

        @Test
        @DisplayName("Stored the account ID provided")
        void storesAccountId(){
            assertEquals(ACCOUNT_ID,account.getAccountId());
        }

        @Test
        @DisplayName("starts with the initial balance provided")
        void startsWithInitialBalance(){
            assertEquals(INITIAL_BALANCE, account.getBalance());
        }

        @Test
        @DisplayName("throws IllegalArgumentException when initial balance is negative")
        void rejectsNegativeInitialBalance(){
            assertThrows(IllegalArgumentException.class, () -> new BankAccount("AAC-002", -1.00));
        }
        
        @Test
        @DisplayName("accepts zero as a valid initial balance")
        void acceptsZeroInitalBalance(){
            BankAccount emptyAccount = new BankAccount("AAC-003",0.00);
            assertEquals(0.00, emptyAccount.getBalance());
        }
    }
    
    @Nested
    @DisplayName("when depositing ")
    class Deposits {

        @Test
        @DisplayName("increase the balance for the deposited amout")
        void balanceIncrease(){
            account.deposit(100.00);
            assertEquals(600.00, account.getBalance());
        }

        @Test
        @DisplayName("throws IllegalArgumentException when deposit amount is negative")
        void rejectNegativeDeposit(){
            assertThrows(IllegalArgumentException.class, () -> {
                account.deposit(-50.00);
            });
        }
    }

    @Nested
    @DisplayName("when withdrawing")
    class Withdrawls{

        @Test
        @DisplayName("decrease the balance when withdraw")
        void balanceDecrease(){
            account.withdraw(200.00);
            assertEquals(300.00, account.getBalance());
        }

        @Test
        @DisplayName("throw InsuffiencentFund")
        void throwInsufficientFundsExceptionForOverDraw(){
            assertThrows(InsufficientFundsException.class, () -> account.withdraw(INITIAL_BALANCE + 1));
        }

        @Test
        @DisplayName("throw IllegalArgumentException")
        void rejectNegativeWithdraw(){
            assertThrows(IllegalArgumentException.class, () -> {
                account.withdraw(-1.00);
            });
        }
    }

}
