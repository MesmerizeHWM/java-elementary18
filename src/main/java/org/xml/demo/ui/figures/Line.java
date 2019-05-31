package org.xml.demo.ui.figures;

import lombok.Data;

import java.awt.*;

@Data
public class Line extends Figure {

    private final int startX, startY, endX, endY;

    @Override
    public void draw(Graphics g, boolean filled) {
        g.drawLine(startX, startY, endX, endY);
    }

    @Override
    public boolean belongs(int x, int y) {
        return Math.abs(getLineLength(startX, startY, x, y) + getLineLength(x, y, endX, endY)  - getLineLength(startX, startY, endX, endY)) <=1;
    }
    
    private int getLineLength(int x1, int y1, int x2, int y2) {        
        return (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
    
}
