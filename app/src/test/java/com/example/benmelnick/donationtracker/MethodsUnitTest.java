package com.example.benmelnick.donationtracker;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("ALL")
public class MethodsUnitTest {
    @Test
    public void testPasswordInvalid() {
        assertTrue(LoginActivity.isPasswordInvalid(null));
        assertTrue(LoginActivity.isPasswordInvalid(""));
        assertFalse(LoginActivity.isPasswordInvalid("abx7YTxO"));
    }

    @Test
    public void testEmailInvalid() {
        assertTrue(LoginActivity.isEmailInvalid(null));
        assertTrue(LoginActivity.isEmailInvalid(""));
        assertFalse(LoginActivity.isEmailInvalid("example@example.com"));
    }
}