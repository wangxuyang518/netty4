package net.gamedo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class User implements Serializable {

    public String username;
    public String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
