package org.xml.demo.ui;

import lombok.Setter;
import org.xml.demo.ui.decorators.FilledDecorator;
import org.xml.demo.ui.decorators.IDecorator;
import org.xml.demo.ui.figures.Figure;
import org.xml.demo.ui.figures.Line;
import org.xml.demo.ui.figures.Rectangle;
import org.xml.demo.ui.states.ApplicationMode;
import org.xml.demo.ui.states.ApplicationWindowState;
import org.xml.demo.ui.states.IApplicationWindowStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.List;
import org.xml.demo.ui.decorators.PickedDecorator;

public class GraphicArea extends JComponent {

    private int gridStep = 10;

    private boolean isMousePressed = false;

    private int startX, startY, currentX, currentY;

    private List<Figure> figures = new LinkedList<>();

    @Setter
    private IApplicationWindowStateManager manager;

    public GraphicArea() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                isMousePressed = true;
                currentX = startX = e.getX();
                currentY = startY = e.getY();
                if (manager.provideState().getMode() == ApplicationMode.SELECT_ELEMENT
                        || manager.provideState().getMode() == ApplicationMode.FILL_ELEMENT) {
                    Figure previousActive = null;
                    for (Figure f: figures) {
                        if (f.belongs(currentX, currentY)) {
                            f.setPicked(true);
                            if (previousActive != null) {
                                previousActive.setPicked(false);
                            }
                            previousActive = f;
                        }                        
                    };
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isMousePressed=false;
                Figure figure = drawFromState(null, false);
                if (figure != null) {
                    figures.add(figure);
                }        
                figures.forEach(f -> {
                    f.setPicked(false);
                });
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        drawGrid(g);

        //draw existing figures        
        for (Figure f: figures) {
            IDecorator decorator = createDecorator(manager.provideState());
            decorator.doDecorate(f, g);            
        }

        if (isMousePressed) {
            drawFromState(g, true);
        }
    }
        
    private Figure drawFromState(Graphics g, boolean doDecorate) {
        ApplicationWindowState state = manager.provideState();
        IDecorator decorator = createDecorator(state);
        Figure f = null;
        switch (state.getMode()) {
            case DRAW_RECTANGLE:
                f = createRectangle();
                break;
            case DRAW_LINE:
                f = createLine();
                break;
            case DRAW_CIRCLE:
                break;
        }
        if (f != null) {
            f.setWindowState(state);
        }
        if (f != null && decorator!=null && doDecorate) {
            decorator.doDecorate(f, g);
        }
        return f;
    }

    private Figure createRectangle() {
        return new Rectangle(startX, startY, currentX, currentY);
    }

    private Figure createLine() {
        return new Line(startX, startY, currentX, currentY);
    }

    private IDecorator createDecorator(ApplicationWindowState state) {
        if (state.getMode() != ApplicationMode.SELECT_ELEMENT && state.getMode() != ApplicationMode.FILL_ELEMENT || !isMousePressed
        ) {
            return new FilledDecorator(isMousePressed);
        } else if (state.getMode() == ApplicationMode.SELECT_ELEMENT) {            
            return new PickedDecorator(startX, startY, currentX, currentY);
        } else if (state.getMode() == ApplicationMode.FILL_ELEMENT) {            
            return new PickedDecorator(startX, startY, currentX, currentY) {
                @Override
                public void changeTargetAttrs(Figure target) {                    
                    target.getWindowState().setColor(state.getColor());
                }                
            };
        } else {
            return null;
        }
    }

    private void drawGrid(Graphics g) {
        for (int i =0 ; i<getHeight(); i+= gridStep) {
            g.drawLine(0, i, getWidth(), i);
        }
        for (int i =0 ; i<getWidth(); i+= gridStep) {
            g.drawLine(i, 0, i, getHeight());
        }
    }
}
