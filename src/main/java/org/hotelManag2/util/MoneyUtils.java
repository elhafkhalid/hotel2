package org.hotelManag2.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtils {
    private MoneyUtils(){

    }
    public static BigDecimal amount(String value){

        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
    }
    public static String format(BigDecimal amount){
        return amount.setScale(2,RoundingMode.HALF_UP).toPlainString();
    }
}
