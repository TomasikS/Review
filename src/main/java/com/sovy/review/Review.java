/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sovy.review;

import java.io.Serializable;

/**
 *
 * @author stefan.tomasik
 */
public class Review implements Serializable {

    public Review(Long id, String text, int hodnotenie, int idBook, int idUser) {
        this.id = id;
        this.text = text;
        this.hodnotenie = hodnotenie;
        this.idBook = idBook;
        this.idUser = idUser;
    }

    private Long id;
    private String text;
    private int hodnotenie;
    private int idBook;
    private int idUser;

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = Id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getHodnotenie() {
        return hodnotenie;
    }

    public void setHodnotenie(int hodnotenie) {
        this.hodnotenie = hodnotenie;
    }

    public Review() {
    }
;

}
