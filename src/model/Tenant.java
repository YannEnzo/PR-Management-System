/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author jmato
 */
public class Tenant {
    private String tenantID;
    private String name;
    private String gender;       //create an enum so gender can only take certain values
    private Date dateOfBirth;
    private String phoneNumber;
    private String email;
    private Date dateOfEntry;
    private String nodeID;   //roomNumber from node

    public Tenant() {
    }

    public Tenant(String tenantID, String name, String gender, Date dateOfBirth, String phoneNumber, String email, Date dateOfEntry, String nodeID) {
        this.tenantID = tenantID;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfEntry = dateOfEntry;
        this.nodeID = nodeID;
    }
    public Tenant( String name, String gender, Date dateOfBirth, String phoneNumber, String email, Date dateOfEntry, String nodeID) {

        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfEntry = dateOfEntry;
        this.nodeID = nodeID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.tenantID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tenant other = (Tenant) obj;
        if (!Objects.equals(this.tenantID, other.tenantID)) {
            return false;
        }
        return true;
    }
    
    
}
