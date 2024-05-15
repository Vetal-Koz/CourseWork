package org.example.style;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomTextField extends JTextField {
    public CustomTextField(String text) {
        super(text);
        setupFocusListener();
    }

    private void setupFocusListener() {
        Border orangeBorder = BorderFactory.createLineBorder(Color.ORANGE, 1);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                UIManager.getBorder("TextField.border");
            }

            @Override
            public void focusLost(FocusEvent e) {
                
            }
        });
    }
}
