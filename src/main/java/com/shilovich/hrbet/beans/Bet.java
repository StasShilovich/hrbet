package com.shilovich.hrbet.beans;

import java.io.Serializable;
import java.util.Date;

public class Bet implements Serializable {
    private transient String serialVersionUID;
    private Long id;
    private Boolean status;
    private UserAuthorized userAuthorized;
    private Date date;
    private


    public Bet() {
    }

    final void setSerialVersionUID(String uid) {
        this.serialVersionUID = uid;
    }
}
