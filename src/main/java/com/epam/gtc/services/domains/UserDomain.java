package com.epam.gtc.services.domains;

import com.epam.gtc.dao.entities.constants.Role;
import com.epam.gtc.utils.BuilderField;
import com.epam.gtc.utils.BuilderFieldConstant;

import java.time.LocalDateTime;

/**
 * User domain
 *
 * @author Fazliddin Makhsudov
 */
public class UserDomain {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    @BuilderField(transferTo = BuilderFieldConstant.ID, crossQueryName = "role")
    @BuilderField(transferTo = BuilderFieldConstant.NAME, crossQueryName = "role")
    private Role role;
    @BuilderField(transferTo = BuilderFieldConstant.TIMESTAMP)
    private LocalDateTime createdDate;
    private String banned;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }
}
