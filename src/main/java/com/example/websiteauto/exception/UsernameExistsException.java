package com.example.websiteauto.exception;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String username) {super("Username already exists: " + username);
    }
}
