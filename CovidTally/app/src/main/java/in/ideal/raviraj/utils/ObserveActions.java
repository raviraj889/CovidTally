package in.ideal.raviraj.utils;

import java.util.Observable;

public class ObserveActions extends Observable {
    private static final ObserveActions ourInstance = new ObserveActions();

    private ObserveActions() {
    }

    public static ObserveActions getInstance() {
        return ourInstance;
    }

    public void update(Object o){
        setChanged();
        notifyObservers(o);
    }
}
