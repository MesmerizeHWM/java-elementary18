package org.xml.demo.ui.decorators;

import org.xml.demo.ui.figures.Figure;

import java.awt.*;
import lombok.Data;
import org.xml.demo.ui.figures.Line;

@Data
public class FilledDecorator implements IDecorator {

    private final boolean isMousePressed;

    @Override
    public void doDecorate(Figure target, Graphics g) {
        if (!isMousePressed) {
            target.setToPos();
        }
        if (target instanceof Line) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(10));
        }
        g.setColor(target.getWindowState().getColor());
        target.draw(g, true);
    }

}
