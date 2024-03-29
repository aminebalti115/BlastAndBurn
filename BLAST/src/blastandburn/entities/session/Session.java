/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.entities.session;

import java.sql.Timestamp;

/**
 *
 * @author user
 */
public class Session {
    private int sessionId;
    private int coachid;
    private int userId;
    private String title;
    private String description;
    private boolean isDeleted;
    private int numOfDays;
    private Timestamp deletedAt;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private int price;


     public Session(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Session() {
    }

    

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }


    

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCoachid() {
        return coachid;
    }

    public void setCoachid(int coachid) {
        this.coachid = coachid;
    }

    @Override
    public String toString() {
        return "Session{" + "coachid=" + coachid + ", userId=" + userId + ", title=" + title + ", description=" + description + ", isDeleted=" + isDeleted + ", numOfDays=" + numOfDays + ", deletedAt=" + deletedAt + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", price=" + price + '}';
    }
    

   
    

}
