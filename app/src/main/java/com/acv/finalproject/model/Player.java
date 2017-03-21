package com.acv.finalproject.model;

import java.io.Serializable;

/**
 * Created by vinicius.castro on 14/03/2017.
 */

public class Player {

    private long id;
    private long teamID;
    private String name;
    private String phone;
    private String email;
    private String position;
    private Integer number;

    public Player() {
    }

    public Player(long id, long teamID, String name, String phone, String email, String position, Integer number) {
        this.id = id;
        this.teamID = teamID;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTeamID() {
        return teamID;
    }

    public void setTeamID(long teamID) {
        this.teamID = teamID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", teamID=" + teamID +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", number=" + number +
                '}';
    }
}
