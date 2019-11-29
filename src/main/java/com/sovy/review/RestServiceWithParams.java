/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sovy.review;

import java.io.IOException;
import java.sql.SQLException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 *
 * @author stefan.tomasik
 */
public class RestServiceWithParams extends ServerResource {

    Database database = new Database();

    @Get("/find/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Review findData() throws SQLException, IOException, ClassNotFoundException {
        Long idd;
        idd = Long.valueOf(this.getAttribute("id"));
        return database.getZanerById(idd);

    }

    @Put("/update/{id}")
    public void updateData(Review review) throws IOException, ClassNotFoundException, SQLException {
        database.updateText(review);
    }
}
