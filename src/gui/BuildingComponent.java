package gui;

import javax.swing.JComponent;
import java.util.ArrayList;
import java.awt.Rectangle;


public class BuildingComponent {

    private Rectangle rectangle;
    private int no; //may be used
    private JComponent jComponent;

    public BuildingComponent(JComponent component, Rectangle rect) {
        jComponent = component;
        rectangle = rect;
    }

    public void setjComponent(JComponent jComponent) {
        this.jComponent = jComponent;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public JComponent getjComponent() {
        return jComponent;
    }

    public JComponent getComponent() {
        return jComponent;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setComponent(JComponent comp) {
        jComponent = comp;
    }
}
