package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DrawingMenuBar extends JMenuBar {



    public DrawingMenuBar( ActionListener actionListener ){
        super();
        ActionController actionController = (ActionController) actionListener;

        JMenuItem lineMenuItem = new JMenuItem("Line");
        JMenuItem rectangleMenuItem = new JMenuItem("Rectangle");
        JMenuItem ellipseMenuItem = new JMenuItem("Ellipse");
        // pinasok

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        JMenuItem redoMenuItem = new JMenuItem("Redo");
        JMenuItem colorMenuItem = new JMenuItem("Color");
        JMenuItem selectMenuItem = new JMenuItem("Select");

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        add(editMenu);
        //undo
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoMenuItem.addActionListener(actionListener); // use actionListener because DMB extends JMB not implements ActionListeners
        undoMenuItem.setActionCommand(ActionCommand.UNDO);
        actionController.setUndoMenuItem(undoMenuItem);
        editMenu.add(undoMenuItem);
        //redo
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        redoMenuItem.addActionListener(actionListener);
        redoMenuItem.setActionCommand(ActionCommand.REDO);
        actionController.setRedoMenuItem(redoMenuItem);
        editMenu.add(redoMenuItem);

        JMenu drawMenu = new JMenu("Draw");
        drawMenu.setMnemonic(KeyEvent.VK_D);
        add(drawMenu); //


        editMenu.addSeparator();
        //select
        selectMenuItem.setActionCommand(ActionCommand.SELECT);
        selectMenuItem.addActionListener(actionListener);
        selectMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        editMenu.add(selectMenuItem);

        //line
        drawMenu.add(lineMenuItem);
        lineMenuItem.setActionCommand(ActionCommand.LINE);
        lineMenuItem.addActionListener(actionListener);

        //rectangle
        drawMenu.add(rectangleMenuItem);
        rectangleMenuItem.addActionListener(actionListener);
        rectangleMenuItem.setActionCommand(ActionCommand.RECT);
        //ellipse
        drawMenu.add(ellipseMenuItem);
        ellipseMenuItem.addActionListener(actionListener);
        ellipseMenuItem.setActionCommand(ActionCommand.ELLIPSE);

        //properties
        JMenu propMenu = new JMenu("Properties");
        propMenu.setMnemonic(KeyEvent.VK_P);
        //Color Menu
        propMenu.add(colorMenuItem);
        this.add(propMenu);
        colorMenuItem.addActionListener(actionListener);
        colorMenuItem.setActionCommand("color");
    }


}

