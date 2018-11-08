package com.example.benmelnick.donationtracker;

import android.content.Context;
import android.text.Editable;
import android.widget.EditText;
import static org.mockito.Mockito.*;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testEditableLength() {
        Context context = mock(Context.class);

        EditText mockEditText = new EditText(context);
        mockEditText.setText("Test text");

        Editable editable = mockEditText.getText();

        assertTrue(editable.length() > 0);
    }

    @Test
    public void testEditableEquality() {
        Context context = mock(Context.class);

        EditText mockEditTextA = new EditText(context);
        mockEditTextA.setText("Test text");

        EditText mockEditTextB = new EditText(context);
        mockEditTextB.setText("Test text");

        assertEquals(mockEditTextA, mockEditTextB);

        mockEditTextB.setText("Now changed");
        assertNotEquals(mockEditTextA, mockEditTextB);
    }
}