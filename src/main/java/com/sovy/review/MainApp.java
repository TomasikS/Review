/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sovy.review;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author stefan.tomasik
 */
 
public class MainApp extends Application {

    

    static final int port = 8182;
    static final Router router = new Router();
    static Context ctx;


  
    public static void main(String[] args) throws ClassNotFoundException {
  
        
        
           
    /*String driver = "org.postgresql.Driver";
    Class.forName(driver);
    String dbURL = "jdbc:postgresql://localhost:5432/bookstore";
    String dbUsername = "postgres";
    String dbPassword = "postgres";

    java.util.Properties connProperties = new java.util.Properties();
    connProperties.put(DATABASE_USER, dbUsername);
    connProperties.put(DATABASE_PASSWORD, dbPassword);*/
        
        
        try {
            org.restlet.Application myApp = new org.restlet.Application() {

                @Override
                public org.restlet.Restlet createInboundRoot() {

                    ctx.getParameters().add("port", Integer.toString(port));
                    router.setContext(ctx);
                    return router;
                }
            ;
            };
         Component component = new Component();

            component.getDefaultHost().attach("/all/", RestService.class);
            component.getDefaultHost().attach("/add/", RestService.class);
            component.getDefaultHost().attach("/del/", RestService.class);
            component.getDefaultHost().attach("/update/{id}", RestServiceWithParams.class);
            component.getDefaultHost().attach("/find/{id}", RestServiceWithParams.class);
            new Server(Protocol.HTTP, port, component).start();
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



}
