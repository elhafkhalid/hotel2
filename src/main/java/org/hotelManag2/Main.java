package org.hotelManag2;

import org.hotelManag2.config.DatabaseInitializer;
import org.hotelManag2.ui.MenuPrincipale;

public class Main {
    public static void main(String[] args) {
        //DatabaseInitializer.initialize();
        new MenuPrincipale().start();
    }
}
