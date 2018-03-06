/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author 330085
 */
@Entity
@Table(name = "app_user")
@NamedQueries({
    @NamedQuery(name = "AppUser.findAll", query = "SELECT a FROM AppUser a")})
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "sso_id")
    private String ssoId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "password")
    private String password;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "first_name")
    private String firstName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "last_name")
    private String lastName;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "email")
    private String email;
    
    @ManyToMany
    @JoinTable(name = "app_user_user_profile", 
            joinColumns = {
                @JoinColumn(name = "user_id", referencedColumnName = "id")
            }, 
            inverseJoinColumns = {
                @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
            }
    )    
    private List<UserProfile> userProfileList;

    public AppUser() {
    }

    public AppUser(Integer id) {
        this.id = id;
    }

    public AppUser(Integer id, String ssoId, String password, String firstName, String lastName, String email) {
        this.id = id;
        this.ssoId = ssoId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserProfile> getUserProfileList() {
        return userProfileList;
    }

    public void setUserProfileList(List<UserProfile> userProfileList) {
        this.userProfileList = userProfileList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.web.spring.AppUser[ id=" + id + " ]";
    }
    
}
