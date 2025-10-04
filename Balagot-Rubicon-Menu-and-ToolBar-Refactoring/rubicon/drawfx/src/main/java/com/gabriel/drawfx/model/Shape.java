package com.gabriel.drawfx.model;

import com.gabriel.drawfx.service.RendererService;
import lombok.Data;

import java.awt.*;
@Data
public abstract class Shape {
    int id;
    private Point location;
    private Point end;
    private Color color;
    private Color fill;
    private RendererService rendererService;
    public Shape(Point location){
        this.setLocation(location);
        this.setEnd(location);

  }
    public boolean contains(Point point) {
        // Get the bounding rectangle
        int x = Math.min(location.x, end.x);
        int y = Math.min(location.y, end.y);
        int width = Math.abs(end.x - location.x);
        int height = Math.abs(end.y - location.y);

        // Check if point is inside the bounding rectangle
        return point.x >= x && point.x <= x + width &&
                point.y >= y && point.y <= y + height;
    }

}
