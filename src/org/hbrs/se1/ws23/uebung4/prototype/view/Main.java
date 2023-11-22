package org.hbrs.se1.ws23.uebung4.prototype.view;

import org.hbrs.se1.ws23.uebung4.prototype.model.Container;
import org.hbrs.se1.ws23.uebung4.prototype.model.InputDialog;

public class Main {
    public static void main (String[] args) throws Exception {
        // ToDo: Bewertung Exception-Handling (F3, F7)
        Container con = Container.getInstance();
        InputDialog c = new InputDialog();
        c.startEingabe();
    }
}
