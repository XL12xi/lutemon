package com.example.lutemon;

import java.util.HashMap;

public class Storage {
    private static Storage instance;

    private HashMap<Integer, Lutemon> home = new HashMap<>();
    private HashMap<Integer, Lutemon> training = new HashMap<>();
    private HashMap<Integer, Lutemon> battle = new HashMap<>();
    private int idCounter = 1;

    private Storage() {}

    public static Storage getInstance() {
        if (instance == null) instance = new Storage();
        return instance;
    }

    public void addToHome(Lutemon lutemon) {
        lutemon.setId(idCounter++);
        home.put(lutemon.getId(), lutemon);
    }

    public void moveToTraining(int id) {
        Lutemon lutemon = home.remove(id);
        if (lutemon != null) training.put(id, lutemon);
    }

    public void moveToBattle(int id) {
        Lutemon lutemon = home.remove(id);
        if (lutemon != null) battle.put(id, lutemon);
    }

    public void returnToHome(int id) {
        Lutemon lutemon = training.remove(id);
        if (lutemon == null) lutemon = battle.remove(id);
        if (lutemon != null) home.put(id, lutemon);
    }
    public void removeFromBattleArea(Lutemon lutemon) {
        // remove from battle
        battle.remove(lutemon.getId());

    }

    public void addLutemon(Lutemon lutemon) {
        // add to home
        home.put(lutemon.getId(), lutemon);
    }

    public HashMap<Integer, Lutemon> getHomeLutemons() { return home; }
    public HashMap<Integer, Lutemon> getTrainingLutemons() { return training; }
    public HashMap<Integer, Lutemon> getBattleLutemons() { return battle; }
}

