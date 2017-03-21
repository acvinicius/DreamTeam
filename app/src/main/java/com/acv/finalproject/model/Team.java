package com.acv.finalproject.model;

import android.media.Image;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vinicius.castro on 14/03/2017.
 */

public class Team {

    private long id;
    private String name;
    private Image shield;
    private String email;
    private List<Player> players;

    public Team() {
    }

    public Team(long id, String name, Image shield, String email, List<Player> players) {
        this.id = id;
        this.name = name;
        this.shield = shield;
        this.email = email;
        this.players = players;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getShield() {
        return shield;
    }

    public void setShield(Image shield) {
        this.shield = shield;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}
