package ru.gold.ordance.board.core.entity;

public enum Role {
    ANONYMOUS("ANONYMOUS"),
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
