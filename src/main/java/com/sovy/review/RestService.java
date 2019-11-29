/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sovy.review;

import java.io.IOException;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 *
 * @author stefan.tomasik
 */
public class RestService extends ServerResource{
    
   
    Database database = new Database();
    
    @Post("/add/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void insertData(Review review) throws SQLException, IOException, ClassNotFoundException {
      database.insertData(review);
    }

 
    @Delete("/del/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void removeData(Review review) throws SQLException, IOException, ClassNotFoundException {
       database.remove(review);
    }
}
