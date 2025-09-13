package com.gabriel.draw.controller;

import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionController implements ActionListener {
    AppService appService;
    public  ActionController(AppService appService){
        this.appService = appService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String  cmd = e.getActionCommand();
        if(ActionCommand.UNDO.equals(cmd) ){ //undo command
            appService.undo();
        }
        if(ActionCommand.REDO.equals(cmd) ){ //redo command
            appService.redo();
        } //updated to use action command and .equals
        else if(ActionCommand.LINE.equals(cmd)){ //line shape
            appService.setShapeMode( ShapeMode.Line);
        }
        else if(ActionCommand.RECT.equals(cmd)){ //rectangle shape
            appService.setShapeMode( ShapeMode.Rectangle);
        }
        else if(ActionCommand.ELLIPSE.equals(cmd)){ //ellipse shape
            appService.setShapeMode( ShapeMode.Ellipse);
        }
        else if("color".equals(cmd)){ //color menu
            Color selectedColor = JColorChooser.showDialog(null, "Choose a Color", appService.getColor());
            appService.setColor( selectedColor);
        }
}


}
