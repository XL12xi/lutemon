package com.example.lutemon;

public enum LutemonColor {
    WHITE("White", 5, 4, 20),
    GREEN("Green", 6, 3, 19),
    PINK("Pink", 7, 2, 18),
    ORANGE("Orange", 8, 1, 17),
    BLACK("Black", 9, 0, 16);

    public final String name;
    public final int attack;
    public final int defense;
    public final int maxHealth;

    LutemonColor(String name, int attack, int defense, int maxHealth) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.maxHealth = maxHealth;
    }
}
