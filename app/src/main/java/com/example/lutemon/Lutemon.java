package com.example.lutemon;

public class Lutemon {
    private int id;
    private String name;
    private LutemonColor color;
    private int attack, defense, maxHealth, currentHealth, experience;
    private int winCount = 0;
    private int loseCount = 0;

    public Lutemon(String name, LutemonColor color) {
        this.name = name;
        this.color = color;
        this.experience = 0;

        switch (color) {
            case WHITE: attack = 5; defense = 4; maxHealth = 20; break;
            case GREEN: attack = 6; defense = 3; maxHealth = 19; break;
            case PINK: attack = 7; defense = 2; maxHealth = 18; break;
            case ORANGE: attack = 8; defense = 1; maxHealth = 17; break;
            case BLACK: attack = 9; defense = 0; maxHealth = 16; break;
        }
        currentHealth = maxHealth;
    }

    // === ID ===
    public void setId(int id) { this.id = id; }
    public int getId() { return id; }

    // === Base info ===
    public String getName() { return name; }
    public LutemonColor getColor() { return color; }
    public String getColorName() { return color.name(); }
    public int getDefense() { return defense; }
    public int getMaxHealth() { return maxHealth; }
    public int getCurrentHealth() { return currentHealth; }
    public int getExperience() { return experience; }

    // === Combat logic ===
    public int getBaseAttack() { return attack; }
    public int getTotalAttack() { return attack + experience; }

    public void gainExperience() {
        experience++;
    }

    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) currentHealth = 0;
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public void resetHealth() {
        currentHealth = maxHealth;
    }

    // === Win/Loss stats ===
    public void addWin() {
        winCount++;
    }

    public void addLoss() {
        loseCount++;
    }

    public int getWins() { return winCount; }
    public int getLosses() { return loseCount; }

    // === Optional display ===
    public String getStats() {
        return name + " (" + color.name() + ") - ATK: " + getTotalAttack() +
                ", DEF: " + defense +
                ", HP: " + currentHealth + "/" + maxHealth +
                ", XP: " + experience;
    }
}
