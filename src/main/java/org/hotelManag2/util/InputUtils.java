package org.hotelManag2.util;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class InputUtils {
    private static final Scanner SCANNER = new Scanner(System.in);
    private InputUtils(){

    }

    public static String readString(String msg){
        System.out.print(msg);
        return SCANNER.nextLine().trim();
    }

    public static int readInt(String msg){
        while(true){
            try {
                String input = readString(msg);
                return Integer.parseInt(input);

            } catch(NumberFormatException e){
                System.out.println("Saisie invalide, entrer un nombre valid");
            }
        }
    }

    public static BigDecimal readMoney(String msg){
        while(true){
            try {
                String amount = readString(msg);
                return MoneyUtils.amount(amount);
            }catch(NumberFormatException e) {
                System.out.println("Saisie invalude, entrer un montant");
            }

        }
    }

    public static LocalDate readDate(String msg){
        while(true){
            try{
                String date = readString(msg);
                return DateUtils.parse(date);
            }catch(DateTimeException e){
                System.out.println("date invalud,format attendu : JJ/DD/YYYY");
            }
        }
    }
}
