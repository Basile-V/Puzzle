package sample.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GridObservable extends Grid{
    private PropertyChangeSupport support;

    public GridObservable(int n, int m) {
        super(n, m);
        support = new PropertyChangeSupport(this);
    }

    @Override
    public boolean setPositionAgent(int x, int y, Agent a){
        int[] oldPos = a.getPos().clone();
        boolean r = super.setPositionAgent(x, y, a);
        int[] newPos = a.getPos().clone();
        support.firePropertyChange("agentMoved", oldPos, newPos);
        return r;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
}
