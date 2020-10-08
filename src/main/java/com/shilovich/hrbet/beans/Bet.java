package com.shilovich.hrbet.beans;

import java.io.Serializable;
import java.util.Date;

public class Bet implements Serializable {
    private static final long serialVersionUID = -7442278899832735176L;

    private Long id;
    private Boolean status;
    private UserAuthorized userAuthorized;
    private Date date;

    public Bet() {
    }

}
