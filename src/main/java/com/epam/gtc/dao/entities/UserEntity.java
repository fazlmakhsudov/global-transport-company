package com.epam.gtc.dao.entities;

import com.epam.gtc.utils.BuilderField;
import com.epam.gtc.utils.BuilderFieldConstant;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User entity
 *
 * @author Fazliddin Makhsudov
 */
public class UserEntity implements Serializable {
    private static final long serialVersionUID = -6889036256149495388L;
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    @BuilderField(transferTo = BuilderFieldConstant.ENUM_FROM_ID, crossQueryName = "role")
    private int roleId;
    @BuilderField(transferTo = BuilderFieldConstant.LOCALDATE_FROM_TIMESTAMP)
    private Timestamp createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "name='" + name + '\'' +
                ", \nsurname='" + surname + '\'' +
                ", \nemail='" + email + '\'' +
                ", \npassword='" + password + '\'' +
                ", \nroleId=" + roleId +
                ", \ncreatedDate=" + createdDate +
                '}';
    }
}
