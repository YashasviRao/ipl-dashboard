package com.example.ipldashboard.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private long totalMatches;
    private long totalWon;

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

    public long getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(long totalMatches) {
        this.totalMatches = totalMatches;
    }

    public long getTotalWon() {
        return totalWon;
    }

    public void setTotalWon(long totalWon) {
        this.totalWon = totalWon;
    }

    public Team(String name, long totalMatches) {
        this.name = name;
        this.totalMatches = totalMatches;
    }

    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name + ", totalMatches=" + totalMatches + ", totalWon=" + totalWon + "]";
    }

}
