package org.xml.demo.ui.figures;

import lombok.Getter;
import lombok.Setter;
import org.xml.demo.ui.states.ApplicationWindowState;

import java.awt.*;

@Setter
@Getter
public abstract class  Figure {   
    
    protected ApplicationWindowState windowState;
    
    protected boolean isPicked;
    
    public abstract void draw(Graphics g, boolean filled);
        
    public abstract boolean belongs(int x, int y);
    
    public void shift(int x1, int y1, int x2, int y2) {        
    }
    
    public void setToPos() {
    }
}
