package com.gabriel.draw.controller;

import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.service.AppService;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionController implements ActionListener {
    AppService appService;

    // Setter methods for enable/disable functionality
    @Setter
    private JButton undoButton;
    @Setter
    private JButton redoButton;
    @Setter
    private JMenuItem undoMenuItem;
    @Setter
    private JMenuItem redoMenuItem;

    public  ActionController(AppService appService){
        this.appService = appService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String  cmd = e.getActionCommand();
        if(ActionCommand.UNDO.equals(cmd) ){ //undo command
            appService.undo();
        }
        else if(ActionCommand.REDO.equals(cmd) ){ //redo command
            appService.redo();
        } //updated to use action command and .equals
        else if(ActionCommand.LINE.equals(cmd)){ //line shape
            appService.setShapeMode( ShapeMode.Line);
            appService.setDrawMode(DrawMode.Idle);
        }
        else if(ActionCommand.RECT.equals(cmd)){ //rectangle shape
            appService.setShapeMode( ShapeMode.Rectangle);
            appService.setDrawMode(DrawMode.Idle);
        }
        else if(ActionCommand.ELLIPSE.equals(cmd)){ //ellipse shape
            appService.setShapeMode( ShapeMode.Ellipse);
            appService.setDrawMode(DrawMode.Idle);
        }
        else if(ActionCommand.SELECT.equals(cmd)){
            appService.setDrawMode(DrawMode.Select);
        }
        else if("color".equals(cmd)){ //color menu
            Color selectedColor = JColorChooser.showDialog(null, "Choose a Color", appService.getColor());
            appService.setColor( selectedColor);
        }

        updateButtonStates();
}


//for button states
    private void updateButtonStates() {
        boolean canUndo = CommandService.canUndo();
        boolean canRedo = CommandService.canRedo();

        if (undoButton != null) undoButton.setEnabled(canUndo);
        if (redoButton != null) redoButton.setEnabled(canRedo);
        if (undoMenuItem != null) undoMenuItem.setEnabled(canUndo);
        if (redoMenuItem != null) redoMenuItem.setEnabled(canRedo);
    }
    // calls when app starts (initializes button states)
    public void initializeButtonStates() {
        updateButtonStates();
    }

}
