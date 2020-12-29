package com.staxrt.tutorial.data;

import java.util.Date;

public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    protected UserDto() { }
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

}
