package org.hotelManag2.config;

import org.hotelManag2.exception.BusinessException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static volatile DatabaseConfig instance;
    private final String url;
    private final String user;
    private final String password;

    private DatabaseConfig(){
        try(InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("db.properties")){
            if(input == null) {
                throw new BusinessException("Fichier db.properties introuvable");
            }

            Properties props = new Properties();
            props.load(input);

            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.password");

        }catch(IOException e){
            throw new BusinessException("lecture de db.properties echoué");
        }
    }

    public static DatabaseConfig getInstance(){
       if(instance == null){
           synchronized (DatabaseConfig.class){
               if(instance == null){
                   instance = new DatabaseConfig();
               }
           }
       }

       return instance;
    }

    public String getUrl(){
        return this.url;
    }

    public String getUser(){
        return this.user;
    }

    public String getPassword(){
        return this.password;
    }
}
