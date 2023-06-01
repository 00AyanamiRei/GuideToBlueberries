package com.ayanami.businesslogiclayer.model;

import com.ayanami.businesslogiclayer.model.interfaces.UserI;
import jakarta.validation.constraints.*;

import java.util.StringJoiner;

/**
* User entity
 */
public class User implements UserI {
    @NotEmpty
    @Size(max = 100)
    private String userName;

    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;

    @NotEmpty
    @Email
    private String mail;

    @NotEmpty
    private  String status;

    public User(String userName, String password, String mail, String status) {
        this.userName = userName;
        this.password = password;
        this.mail = mail;
        this.status = status;
    }

    public static UserBuilderUserName builder() {
        return userName -> password -> mail -> status -> () -> new User(userName, password, mail, status);
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public String getStatus() {return status;}

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setStatus(String status) {this.status = status;}

    public interface UserBuilderUserName {
        UserBuilderPassword userName(String userName);
    }

    public interface UserBuilderPassword {
        UserBuilderMail password(String password);
    }

    public interface UserBuilderMail {
        UserBuilderStatus mail(String mail);
    }

    public interface UserBuilderStatus {
        UserBuilder status(String status);
    }

    public interface UserBuilder{
        User build();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "", "")
                .add(userName)
                .add(password)
                .add(mail)
                .add(status)
                .toString();
    }
}

