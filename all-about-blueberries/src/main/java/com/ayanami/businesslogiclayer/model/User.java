package com.ayanami.businesslogiclayer.model;

import com.ayanami.businesslogiclayer.model.interfaces.UserI;
import jakarta.validation.constraints.*;

import java.util.StringJoiner;

public class User implements UserI {
    @NotNull
    @Positive
    private Integer id;

    @NotEmpty
    @Size(max = 100)
    private String userName;

    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;

    @NotEmpty
    @Email
    private String mail;


    public User(int id, String userName, String password, String mail) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.mail = mail;
    }

    public static UserBuilderId builder() {
        return id -> userName -> password -> mail -> () -> new User(id, userName, password, mail);
    }

    @Override
    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public interface UserBuilderId {
        UserBuilderUserName id(int id);
    }

    public interface UserBuilderUserName {
        UserBuilderPassword userName(String userName);
    }

    public interface UserBuilderPassword {
        UserBuilderMail password(String password);
    }

    public interface UserBuilderMail {
        UserBuilder mail(String mail);
    }

    public interface UserBuilder{
        User build();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "", "")
                .add("" + id)
                .add(userName)
                .add(password)
                .add(mail)
                .toString();
    }
}

