package com.gabriel.draw.controller;

import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Line;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Shape;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

public class DrawingController  implements MouseListener, MouseMotionListener {
    private Point end;
    final private DrawingView drawingView;
    Shape currentShape;
    private final AppService appService;
    //
    private Shape selectedShape;
    private Point dragStartPoint;
    private Point originalShapeLocation;
    private Point originalShapeEnd;
    private String activeResizeHandle;

     public DrawingController(AppService appService, DrawingView drawingView){
       this.appService = appService;
         this.drawingView = drawingView;
         drawingView.addMouseListener(this);
         drawingView.addMouseMotionListener(this);
     }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point start = e.getPoint();
        //
        if(appService.getDrawMode() == DrawMode.Select){
            Drawing drawing = (Drawing) appService.getModel();

            // First check if clicking on a resize handle of already selected shape
            if(selectedShape != null) {
                activeResizeHandle = getResizeHandle(selectedShape, start);
                if(activeResizeHandle != null) {
                    // User clicked on a resize handle
                    appService.setDrawMode(DrawMode.Scaling);
                    dragStartPoint = start;
                    originalShapeLocation = new Point(selectedShape.getLocation());
                    originalShapeEnd = new Point(selectedShape.getEnd());
                    return;
                }
            }

            // If not on a handle, check if clicking on a shape to select it
            selectedShape = null;
            for(int i = drawing.getShapes().size() - 1; i >= 0; i--){
                Shape shape = drawing.getShapes().get(i);
                if(shape.contains(start)){
                    selectedShape = shape;
                    drawing.selectShape(shape);
                    dragStartPoint = start;
                    originalShapeLocation = new Point(shape.getLocation());
                    originalShapeEnd = new Point(shape.getEnd());
                    break;
                }
            }

            if (selectedShape == null){
                drawing.clearSelection();
            }

            drawingView.repaint();
            return;
        }



        if(appService.getDrawMode() == DrawMode.Idle) {

            switch (appService.getShapeMode()){
                case Line:  currentShape = new Line(start, start);
                    currentShape.setColor(appService.getColor());
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,false );
                    appService.setDrawMode(DrawMode.MousePressed);
                    break;
                case Rectangle:
                    currentShape = new Rectangle(start, start);
                    currentShape.setColor(appService.getColor());
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,false );
                    appService.setDrawMode(DrawMode.MousePressed);
                    break;
                case  Ellipse:
                    currentShape = new Ellipse(start, start);
                    currentShape.setColor(appService.getColor());
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,false );
                    appService.setDrawMode(DrawMode.MousePressed);
                    break;
                default:
                    return;
            }


        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
         // Handle end of the scale
        if(appService.getDrawMode() == DrawMode.Scaling){
            appService.setDrawMode(DrawMode.Select);
            activeResizeHandle = null;
            return;
        }
         if(appService.getDrawMode() == DrawMode.MousePressed){
             end = e.getPoint();
             currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,true );
             appService.scale(currentShape,end);
             currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,false );
             appService.create(currentShape);
             appService.setDrawMode(DrawMode.Idle);
           }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
         //
        if(appService.getDrawMode() == DrawMode.Scaling && selectedShape != null && activeResizeHandle != null){
            Point currentPoint = e.getPoint();

            int minX = Math.min(originalShapeLocation.x, originalShapeEnd.x);
            int minY = Math.min(originalShapeLocation.y, originalShapeEnd.y);
            int maxX = Math.max(originalShapeLocation.x, originalShapeEnd.x);
            int maxY = Math.max(originalShapeLocation.y, originalShapeEnd.y);

            // Adjust based on handle
            switch (activeResizeHandle){
                case "TOP_LEFT":
                    minX = currentPoint.x;
                    minY = currentPoint.y;
                    break;
                case "TOP_RIGHT":
                    maxX = currentPoint.x;
                    minY = currentPoint.y;
                    break;
                case "BOTTOM_LEFT":
                    minX = currentPoint.x;
                    maxY = currentPoint.y;
                    break;
                case "BOTTOM_RIGHT":
                    maxX = currentPoint.x;
                    maxY = currentPoint.y;
                    break;
                case "LEFT":
                    minX = currentPoint.x;
                    break;
                case "RIGHT":
                    maxX = currentPoint.x;
                    break;
                case "TOP":
                    minY = currentPoint.y;
                    break;
                case "BOTTOM":
                    maxY = currentPoint.y;
                    break;
            }

            // Always set location to top-left, end to bottom-right
            selectedShape.setLocation(new Point(minX, minY));
            selectedShape.setEnd(new Point(maxX, maxY));


            drawingView.repaint();
            return;

        }
        if(appService.getDrawMode() == DrawMode.Select && selectedShape != null){
            Point currentPoint = e.getPoint();

            // calculation for the distance mouse moved
            int dx = currentPoint.x - dragStartPoint.x;
            int dy = currentPoint.y - dragStartPoint.y;

            //calc new shape location
            Point newLocation = new Point(originalShapeLocation.x + dx, originalShapeLocation.y + dy);
            Point newEnd = new Point(originalShapeEnd.x + dx, originalShapeEnd.y + dy);

            // shape movement
            selectedShape.setLocation(newLocation);
            selectedShape.setEnd((newEnd));

            drawingView.repaint();
            return;
        }

        if(appService.getDrawMode() == DrawMode.MousePressed) {
                end = e.getPoint();
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,true );
                appService.scale(currentShape,end);
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,true );
           }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    private static final int HANDLE_SIZE = 8;

     //
    private String getResizeHandle(Shape shape, Point point){
        int x1 = Math.min(shape.getLocation().x, shape.getEnd().x);
        int y1 = Math.min(shape.getLocation().y, shape.getEnd().y);
        int x2 = Math.max(shape.getLocation().x, shape.getEnd().x);
        int y2 = Math.max(shape.getLocation().y, shape.getEnd().y);

        //corner
        if (isNearPoint(point, x1, y1)) return "TOP_LEFT";
        if (isNearPoint(point, x2, y1)) return "TOP_RIGHT";
        if (isNearPoint(point, x1, y2)) return "BOTTOM_LEFT";
        if (isNearPoint(point, x2, y2)) return "BOTTOM_RIGHT";

        //edges
        if (isNearVerticalEdge(point, x1, y1, y2)) return "LEFT";
        if (isNearVerticalEdge(point, x2, y1, y2)) return "RIGHT";
        if (isNearHorizontalEdge(point, y1, x1, x2)) return "TOP";
        if (isNearHorizontalEdge(point, y2, x1, x2)) return "BOTTOM";

        return null;
    }
    private boolean isNearPoint(Point p, int x, int y) {
        return Math.abs(p.x - x) <= HANDLE_SIZE && Math.abs(p.y - y) <= HANDLE_SIZE;
    }

    private boolean isNearVerticalEdge(Point p, int x, int y1, int y2) {
        return Math.abs(p.x - x) <= HANDLE_SIZE && p.y >= y1 && p.y <= y2;
    }

    private boolean isNearHorizontalEdge(Point p, int y, int x1, int x2) {
        return Math.abs(p.y - y) <= HANDLE_SIZE && p.x >= x1 && p.x <= x2;
    }

}
