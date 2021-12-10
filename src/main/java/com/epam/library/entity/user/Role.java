package com.epam.library.entity.user;

public enum Role {
    ADMIN(1), LIBRARIAN(2), READER(3);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
