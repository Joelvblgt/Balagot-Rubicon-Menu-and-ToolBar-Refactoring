package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawingToolBar extends JToolBar {
    public DrawingToolBar(ActionListener actionListener){
        JButton button = new JButton();
        button.setActionCommand(actionCommand.);
        button.setToolTipText(toolTipText);
        button.addActionListener(actionListener);
    }
}
