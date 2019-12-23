package gui;

import java.util.ArrayList;
import javax.swing.JComponent;
import java.awt.Rectangle;

public class ComponentsList extends ArrayList<BuildingComponent> {
    private JComponent jC;
    private Rectangle r;

    public JComponent getjC() {
        return jC;
    }

    public void add(JComponent jC, Rectangle r) {
        super.add(new BuildingComponent(jC, r));
    }

    public void setjC(JComponent jC) {
        this.jC = jC;
    }

    public Rectangle getR() {
        return r;
    }

    public void setR(Rectangle r) {
        this.r = r;
    }
}
