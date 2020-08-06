package com.ntdat.chatapp.data;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int TEXT_MESSAGE = 0;
    public static final int IMAGE_MESSAGE = 1;
    public static final int FILE_MESSAGE = 2;

    private String sender;
    private String recipient;
    private int messageType;
    private String message;

    public Message() {}

    public Message(String sender, String recipient, int messageType, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.messageType = messageType;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
