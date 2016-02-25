/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.dto;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Entity  
@Table(name= "User")  
public class UserDTO {
    
    @Id @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;
    
    @Column(name="deleted")
    private String deleted;
    
    @Column(name="datejoin")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateJoin;
    
    @Column(name="dateleft")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateLeft;
    
    @Column(name="created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;
    
    @Column(name="modified")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="Title_id")
    private TitleDTO title;
    
    
    @Column(name="approver")
    private int approver;
    
    @Column(name="login")
    private String login;
    
    @Transient
    private boolean isManager;

    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }
    
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public int getApprover() {
        return approver;
    }

    public void setApprover(int approver) {
        this.approver = approver;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Date getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(Date dateJoin) {
        this.dateJoin = dateJoin;
    }

    public Date getDateLeft() {
        return dateLeft;
    }

    public void setDateLeft(Date dateLeft) {
        this.dateLeft = dateLeft;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public TitleDTO getTitle() {
        return title;
    }

    public void setTitle(TitleDTO title) {
        this.title = title;
    }


}
