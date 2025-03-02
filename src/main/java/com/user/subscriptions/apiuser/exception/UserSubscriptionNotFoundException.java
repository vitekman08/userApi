package com.user.subscriptions.apiuser.exception;

public class UserSubscriptionNotFoundException extends RuntimeException{
    public UserSubscriptionNotFoundException(String message) {
        super(message);
    }
}
