package org.xml.demo.ui.figures;

import java.awt.*;

public class Rectangle extends Figure {

    private int initialX, initialY;
    
    private int startX;

    private int startY;

    private int width;

    private int height;

    public Rectangle(int startX, int startY, int currentX, int currentY) {
        this.initialX = this.startX = Integer.min(startX, currentX);
        this.initialY = this.startY = Integer.min(startY, currentY);
        this.width = Math.abs(startX - currentX);
        this.height = Math.abs(startY - currentY);
    }

    @Override
    public void draw(Graphics g, boolean filled) {
        if (filled) {
            g.fillRect(startX, startY, width, height);
        } else {
            g.drawRect(startX, startY, width, height);
        }
    }

    @Override
    public boolean belongs(int x, int y) {
        return x >= startX && x <=startX + width && y >= startY && y <= startY + height;
    }

    @Override
    public void shift(int x1, int y1, int x2, int y2) {
        int tmp1 = x2 - x1;
        int tmp2 = y2 - y1;        
        startX = this.initialX + tmp1;
        startY = this.initialY + tmp2;        
    }    

    @Override
    public void setToPos() {
         this.initialX = startX;
         this.initialY = startY;
    }
    
    
}
