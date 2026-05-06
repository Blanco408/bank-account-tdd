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
        @DisplayName("accepts zero ad a valid initial balance")
        void acceptsZeroInitalBalance(){
            BankAccount emptyAccount = new BankAccount("AAC-003",0.00);
            assertEquals(0.00, emptyAccount.getBalance());
        }
    }



}
