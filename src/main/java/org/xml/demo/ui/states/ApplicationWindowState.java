package org.xml.demo.ui.states;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

/**
 * State of application.
 * 
 * @author alitvinov
 */
@Data
@AllArgsConstructor
public class ApplicationWindowState {

    private final ApplicationMode mode;

    private final Color color;
}
