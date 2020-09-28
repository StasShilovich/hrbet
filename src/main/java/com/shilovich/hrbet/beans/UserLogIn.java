package com.shilovich.hrbet.beans;

import java.io.Serializable;

public class UserLogIn implements Serializable {
    private transient String serialVersionUID;

    final void setSerialVersionUID(String uid) {
        this.serialVersionUID = uid;
    }
}
