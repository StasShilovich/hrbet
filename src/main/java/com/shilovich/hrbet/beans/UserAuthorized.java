package com.shilovich.hrbet.beans;

import java.io.Serializable;

public class UserAuthorized implements Serializable {
    private transient String serialVersionUID;

    final void setSerialVersionUID(String uid) {
        this.serialVersionUID = uid;
    }
}
