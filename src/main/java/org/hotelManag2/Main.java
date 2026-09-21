package org.hotelManag2;

import org.hotelManag2.util.InputUtils;

public class Main {
    public static void main(String[] args) {
        while(true) {
            showMenu();
            int choix = InputUtils.readInt("votre choix? : ");
            switch(choix){
                case 1 -> register();
                case 2 -> login();
                case 3 -> {
                    System.out.println("fin du programme");
                    return;
                }
                default -> System.out.println("choix invalide");
            }
        }
    }

    private static void showMenu() {
        System.out.println("=== Hotel Manager V2 ===");
        System.out.println("1. S'inscrire");
        System.out.println("2. Se connecter");
        System.out.println("3. Quitter");
    }

    private static void register() {

    }

    private static void login() {

    }
}