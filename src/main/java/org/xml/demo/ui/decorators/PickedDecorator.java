package org.xml.demo.ui.decorators;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import lombok.Data;
import org.xml.demo.ui.figures.Figure;

/**
 * PickedDecorator.
 *
 * @author alitvinov
 */
@Data
public class PickedDecorator extends FilledDecorator {

    protected final int x1, y1, x2, y2;

    public PickedDecorator(int x1, int y1, int x2, int y2) {
        super(true);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void doDecorate(Figure target, Graphics g) {        
        if (target.isPicked() && target.belongs(x2, y2)) {
            changeTargetAttrs(target);
        }
        super.doDecorate(target, g);
    }

    public void changeTargetAttrs(Figure target) {        
        target.shift(x1, y1, x2, y2);
    }
}
