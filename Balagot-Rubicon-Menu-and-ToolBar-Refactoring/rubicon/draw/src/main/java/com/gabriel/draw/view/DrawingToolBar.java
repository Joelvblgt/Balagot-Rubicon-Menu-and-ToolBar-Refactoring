package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;

import javax.swing.*;
import java.awt.event.ActionListener;

public class DrawingToolBar extends JToolBar {
    public DrawingToolBar(ActionListener actionListener){
        //cast to ActionController to access setter methods
        ActionController actionController = (ActionController) actionListener;

        //undo
        JButton undoButton = new JButton();
        undoButton.setIcon(new ImageIcon(getClass().getResource("/com/gabriel/draw/view/undo.jpg")));
        undoButton.setActionCommand(ActionCommand.UNDO);
        undoButton.setToolTipText("Undo");
        undoButton.addActionListener(actionListener);
        actionController.setUndoButton(undoButton);
        add(undoButton);


        //redo
        JButton redoButton = new JButton();
        redoButton.setIcon(new ImageIcon(getClass().getResource("/com/gabriel/draw/view/redo.png")));
        redoButton.setActionCommand(ActionCommand.REDO);
        redoButton.setToolTipText("Redo");
        redoButton.addActionListener(actionListener);
        actionController.setRedoButton(redoButton);
        add(redoButton);

        //line
        JToggleButton lineButton = new JToggleButton();
        lineButton.setIcon(new ImageIcon(getClass().getResource("/com/gabriel/draw/view/Line.png")));
        lineButton.setActionCommand(ActionCommand.LINE);
        lineButton.setToolTipText("Draw Line");
        lineButton.addActionListener(actionListener);

        add(lineButton);

        //rectangle
        JToggleButton rectangleButton = new JToggleButton();
        rectangleButton.setIcon(new ImageIcon(getClass().getResource("/com/gabriel/draw/view/Rectangle.png")));
        rectangleButton.setActionCommand(ActionCommand.RECT);
        rectangleButton.setToolTipText("Draw Rectangle");
        rectangleButton.addActionListener(actionListener);
        add(rectangleButton);

        //ellipse
        JToggleButton ellipseButton = new JToggleButton();
        ellipseButton.setIcon(new ImageIcon(getClass().getResource("/com/gabriel/draw/view/ellipse.png")));
        ellipseButton.setActionCommand(ActionCommand.ELLIPSE);
        ellipseButton.setToolTipText("Draw Ellipse");
        ellipseButton.addActionListener(actionListener);
        add(ellipseButton);

        //color
        JButton colorButton = new JButton();
        colorButton.setIcon(new ImageIcon(getClass().getResource("/com/gabriel/draw/view/Platte.png")));
        colorButton.setActionCommand("color");
        colorButton.setToolTipText("Choose a Color");
        colorButton.addActionListener(actionListener);
        add(colorButton);




    }
}
