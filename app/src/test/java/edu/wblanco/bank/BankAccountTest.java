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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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
class Withdrawals {

    @Test
    @DisplayName("decreases the balance by the withdrawn amount")
    void decreasesBalanceByWithdrawnAmount() {
        account.withdraw(200.00);
        assertEquals(300.00, account.getBalance());
    }

    @Test
    @DisplayName("throws InsufficientFundsException when amount exceeds balance")
    void throwsInsufficientFundsExceptionWhenOverdrawn() {
        assertThrows(InsufficientFundsException.class,
            () -> account.withdraw(INITIAL_BALANCE + 1));
    }

    @Test
    @DisplayName("does not change the balance when an overdraft is attempted")
    void doesNotModifyBalanceOnFailedWithdrawal() {
        try {
            account.withdraw(INITIAL_BALANCE + 1);
        } catch (InsufficientFundsException ignored) {}
        assertEquals(INITIAL_BALANCE, account.getBalance());
    }

    @Test
    @DisplayName("throws IllegalArgumentException when withdrawal amount is negative")
    void rejectsNegativeWithdrawal() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10.00));
    }
}

@Nested
@DisplayName("when transferring funds")
class Transfers {

    private BankAccount destinationAccount;

    @BeforeEach
    void createDestinationAccount() {
        destinationAccount = new BankAccount("ACC-999", 0.00);
    }

    @Test
    @DisplayName("decreases source balance and increases destination balance by transfer amount")
    void movesMoneyBetweenAccounts() {
        account.transfer(100.00, destinationAccount);

        assertEquals(400.00, account.getBalance());
        assertEquals(100.00, destinationAccount.getBalance());
    }

    @Test
    @DisplayName("leaves both accounts unchanged when source has insufficient funds")
    void leavesAccountsUnchangedOnFailedTransfer() {
        assertThrows(InsufficientFundsException.class,
            () -> account.transfer(INITIAL_BALANCE + 1, destinationAccount));

        assertEquals(INITIAL_BALANCE, account.getBalance());
        assertEquals(0.00, destinationAccount.getBalance());
    }
}
  @Nested
@DisplayName("hasBalance()")
class HasBalance {

    @Test
    @DisplayName("returns true when balance is greater than zero")
    void returnsTrueWhenBalanceIsPositive() {
        assertTrue(account.hasBalance());
    }

    @Test
    @DisplayName("returns false when balance is zero")
    void returnsFalseWhenBalanceIsZero() {
        BankAccount emptyAccount = new BankAccount("ACC-000", 0.00);
        assertFalse(emptyAccount.hasBalance());
    }
}

@Nested
@DisplayName("transaction logging")
class Logging {

    private BankAccount loggedAccount;

    @BeforeEach
    void createAccountWithLogger() {
        loggedAccount = new BankAccount(ACCOUNT_ID, INITIAL_BALANCE, mockLogger);
    }

    @Test
    @DisplayName("calls logger at least once after a deposit")
    void invokesLoggerOnDeposit() {
        loggedAccount.deposit(50.00);
        verify(mockLogger, atLeastOnce()).log(anyString(), anyDouble(), anyDouble());
    }

    @Test
    @DisplayName("calls logger at least once after a withdrawal")
    void invokesLoggerOnWithdrawal() {
        loggedAccount.withdraw(50.00);
        verify(mockLogger, atLeastOnce()).log(anyString(), anyDouble(), anyDouble());
    }

    @Test
    @DisplayName("does not call logger when no transaction occurs")
    void doesNotInvokeLoggerWithoutTransaction() {
        verifyNoInteractions(mockLogger);
    }

    @Test
    @DisplayName("does not call logger when a withdrawal fails")
    void doesNotInvokeLoggerOnFailedWithdrawal() {
        try {
            loggedAccount.withdraw(INITIAL_BALANCE + 1);
        } catch (InsufficientFundsException ignored) {}
        verifyNoInteractions(mockLogger);
    }
}
}
