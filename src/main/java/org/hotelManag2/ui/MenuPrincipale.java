package org.hotelManag2.ui;

import org.hotelManag2.exception.AuthenticationException;
import org.hotelManag2.model.User;
import org.hotelManag2.repository.jdbc.ReservationRepositoryJDBC;
import org.hotelManag2.repository.jdbc.UserRepositoryJDBC;
import org.hotelManag2.repository.jdbc.RoomRepositoryJDBC;
import org.hotelManag2.service.AuthService;
import org.hotelManag2.service.PricingService;
import org.hotelManag2.service.ReservationService;
import org.hotelManag2.service.RoomService;
import org.hotelManag2.util.InputUtils;


public class MenuPrincipale {
    private final AuthService authService;
    private final RoomService roomService;
    private final PricingService pricingService;
    private final ReservationService reservationService;

    public MenuPrincipale(){
        this.authService = new AuthService(new UserRepositoryJDBC());
        this.roomService = new RoomService(new RoomRepositoryJDBC());
        this.pricingService = new PricingService();
        this.reservationService = new ReservationService(new RoomRepositoryJDBC(),new ReservationRepositoryJDBC(),pricingService);
    }

    public void start() {
        while (true) {
            System.out.println("=== Hotel Manager V2 ===");
            System.out.println("1. S'inscrire");
            System.out.println("2. Se connecter");
            System.out.println("3. Quitter");
            int choice = InputUtils.readInt("Votre choix : ");
            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Au revoir.");
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void register(){

        String fullName = InputUtils.readString("votre nom? : ");
        String email = InputUtils.readString("votre email? : ");
        String password = InputUtils.readString("votre pass? : ");

        try {
            User user = authService.register(fullName,email,password);
        }catch (AuthenticationException e){
            System.out.println("erreur!!" + e.getMessage());
        }
    }

    private void login(){
        String email = InputUtils.readString("votre email? : ");
        String pass = InputUtils.readString("votre pass? : ");
        try {
            User user = authService.login(email,pass);

            switch (user.getRole()) {
                case CLIENT -> new MenuClient(authService,roomService,pricingService,reservationService).start();
                case ADMIN -> new MenuAdmin(authService,roomService).start();
            }

        }catch(AuthenticationException e){
            System.out.println("erreur!! : " + e.getMessage());
        }
    }
}
