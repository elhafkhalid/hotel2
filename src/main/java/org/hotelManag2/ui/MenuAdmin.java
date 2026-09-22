package org.hotelManag2.ui;

import org.hotelManag2.exception.AuthenticationException;
import org.hotelManag2.model.Room;
import org.hotelManag2.model.User;
import org.hotelManag2.model.enums.RoomType;
import org.hotelManag2.service.AuthService;
import org.hotelManag2.service.RoomService;
import org.hotelManag2.util.InputUtils;
import org.hotelManag2.util.MoneyUtils;

import java.math.BigDecimal;
import java.util.List;

public class MenuAdmin {

    private final AuthService authService;
    private final RoomService roomService;

    public MenuAdmin(AuthService authService,RoomService roomService) {
        this.authService = authService;
        this.roomService = roomService;
    }

    public void start() {
        while (true) {
            System.out.println("=== Espace Admin ===");
            System.out.println("Bienvenue " + authService.getCurrentUser().getFullName());
            System.out.println("1. Mon profil");
            System.out.println("2. Changer mon mot de passe");
            System.out.println("3. Voir toutes les chambres");
            System.out.println("4. Ajouter une chambre");
            System.out.println("5. Modifier une chambre");
            System.out.println("6. Passer en maintenance / disponible");
            System.out.println("7. Se deconnecter");
            int choice = InputUtils.readInt("Votre choix : ");
            switch (choice) {
                case 1 -> showProfile();
                case 2 -> changePassword();
                case 3 -> showAllRooms();
                case 4 -> addRoom();
                case 5 -> updateRoom();
                case 6 -> toggleMaintenance();
                case 7 -> {
                    authService.logout();
                    System.out.println("Deconnexion reussie.");
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void showAllRooms(){
        List<Room> rooms = roomService.findAll();

        for(Room room : rooms){
            System.out.println("Chambre " + room.getRoomNumber() + " - " + room.getType()
                    + " - " + room.getCapacity() + " pers - " + MoneyUtils.format(room.getPricePerNight())
                    + " - " + room.getStatus());
        }
    }


    private void addRoom(){
        showAllRooms();

        String roomNumber = InputUtils.readString("numero de chambre? : ");
        RoomType type = readRoomType();
        int capacity = InputUtils.readInt("capacity? : ");
        BigDecimal price = InputUtils.readMoney("prix par nuit? : ");
        roomService.createRoom(roomNumber,type,capacity,price);
        System.out.println("Chambre ajoutee.");
    }


    private void updateRoom(){
        showAllRooms();

        String roomNumber = InputUtils.readString("numero de chambre? : ");
        RoomType type = readRoomType();
        int capacity = InputUtils.readInt("capacity? : ");
        BigDecimal price = InputUtils.readMoney("prix par nuit? : ");
        roomService.updateRoom(roomNumber,type,capacity,price);
        System.out.println("Chambre modifiee.");
    }


    private void toggleMaintenance(){
        showAllRooms();

        String roomNumber = InputUtils.readString("numero de chambre? : ");
        roomService.toggleMaintenance(roomNumber);
        System.out.println("Statut mis a jour.");
    }


    private RoomType readRoomType(){
        System.out.println("Types possibles : SINGLE, DOUBLE, SUITE");
        while(true){
            try {
                return RoomType.valueOf(InputUtils.readString("Type : ").toUpperCase());
            }catch(IllegalArgumentException e){
                System.out.println("Type inconnu, reessayez.");
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


    private void changePassword() {
        String newPassword = InputUtils.readString("Nouveau mot de passe : ");
        String confirmation = InputUtils.readString("Confirmation : ");
        if (!newPassword.equals(confirmation)) {
            System.out.println("Les mots de passe ne correspondent pas.");
            return;
        }
        try {
            authService.changePassword(newPassword);
            System.out.println("Mot de passe modifie.");
        } catch (AuthenticationException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}