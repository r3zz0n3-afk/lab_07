package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        //fail("To be implemented");
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        int mount = 100;
        bankAccount.deposit(mRossi.getUserID(), mount);
        assertEquals(bankAccount.getBalance(),mount);
        int transaction = bankAccount.getTransactionsCount();
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(
        mount - (StrictBankAccount.MANAGEMENT_FEE + transaction * StrictBankAccount.TRANSACTION_FEE)
        , bankAccount.getBalance()
        );
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        double amount = -100;
        double balanceBeforeDraw = bankAccount.getBalance();
        int transactionBeforeDraw = bankAccount.getTransactionsCount();
        try {
            bankAccount.withdraw(mRossi.getUserID(),amount);
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            assertEquals(balanceBeforeDraw, bankAccount.getBalance());// not modify the balance
            assertEquals(transactionBeforeDraw, bankAccount.getTransactionsCount());// not modify the transactions count
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        double amount = bankAccount.getBalance() + 1;
        double balanceBeforeDraw = bankAccount.getBalance();
        int transactionBeforeDraw = bankAccount.getTransactionsCount();
        try {
            bankAccount.withdraw(mRossi.getUserID(),amount);
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            assertEquals(balanceBeforeDraw, bankAccount.getBalance());// not modify the balance
            assertEquals(transactionBeforeDraw, bankAccount.getTransactionsCount());// not modify the transactions count
        }
    }
}
