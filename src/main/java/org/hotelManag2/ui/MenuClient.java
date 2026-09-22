package org.hotelManag2.ui;

import org.hotelManag2.dto.AvailableRoomDTO;
import org.hotelManag2.dto.RoomSearchCriteria;
import org.hotelManag2.exception.AuthenticationException;
import org.hotelManag2.exception.BusinessException;
import org.hotelManag2.model.Room;
import org.hotelManag2.model.User;
import org.hotelManag2.service.AuthService;
import org.hotelManag2.service.RoomService;
import org.hotelManag2.util.InputUtils;
import org.hotelManag2.util.MoneyUtils;

import java.time.LocalDate;
import java.util.List;

public class MenuClient {
    private final AuthService authService;
    private final RoomService roomService;

    public MenuClient(AuthService authService,RoomService roomService){
        this.authService = authService;
        this.roomService = roomService;
    }

    public void start() {
        while (true) {
            System.out.println("=== Espace Client ===");
            System.out.println("Bienvenue " + authService.getCurrentUser().getFullName());
            System.out.println("1. Mon profil");
            System.out.println("2. Changer mon mot de passe");
            System.out.println("3. Voir les chambres");
            System.out.println("4. Rechercher une chambre disponible");
            System.out.println("3. Se deconnecter");
            int choice = InputUtils.readInt("Votre choix : ");
            switch (choice) {
                case 1 -> showProfile();
                case 2 -> changePassword();
                case 3 -> showRooms();
                case 4 -> searchRooms();
                case 5 -> {
                    authService.logout();
                    System.out.println("Deconnexion reussie.");
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void showProfile() {
        User user = authService.getCurrentUser();
        System.out.println("--- Profil ---");
        System.out.println("Nom : " + user.getFullName());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Role : " + user.getRole());
    }

    private void changePassword(){
        String newPassword = InputUtils.readString("nv mot de passe? : ");
        String confirmation = InputUtils.readString("confirmation? : ");

        if(!newPassword.equals(confirmation)) {
            System.out.println("Les mots de passe ne correspondent pas.");
            return;
        };

        try{
            authService.changePassword(newPassword);
            System.out.println("Mot de passe modifie.");
        }catch(AuthenticationException e){
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void showRooms(){
        List<Room> rooms = roomService.listAvailable();
        if(rooms.isEmpty()) {
            System.out.println("aucun chambre exist ");
            return;
        }
        for(Room room : rooms){
            System.out.println("Chambre " + room.getRoomNumber() + " - " + room.getType()
                    + " - " + room.getCapacity() + " pers - " + MoneyUtils.format(room.getPricePerNight()));
        }
    }


    private void searchRooms(){
        LocalDate checkIn = InputUtils.readDate("date d'arrive? (JJ/MM/AAAA) : ");
        LocalDate checkOut = InputUtils.readDate("date dpart? (JJ/MM/AAAA) : ");

        int guests = InputUtils.readInt("nombre de voyageurs? : ");

        try {
            List<AvailableRoomDTO> results = roomService.serchAvailable(new RoomSearchCriteria(checkIn,checkOut,guests));

            if (results.isEmpty()) {
                System.out.println("Aucune chambre disponible pour ces dates.");
                return;
            }

            for(AvailableRoomDTO room : results){
                System.out.println("Chambre " + room.getRoomNumber() + " - " + room.getType()
                        + " - " + room.getCapacity() + " pers - " + MoneyUtils.format(room.getPricePerNight()));
            }


        } catch(BusinessException e){
            System.out.println("Erreur : " + e.getMessage());
        }

    }

}
