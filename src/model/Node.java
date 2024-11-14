/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yanne
 */
public class Node implements Serializable{
    String roomNumber;
    String type;
    int price;
    String availability;
    String location;
    String category;
    public Node() {
    }

    public Node(String roomNumber, String type, int price, String availability, String location, String category) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.availability = availability;
        this.location = location;
        this.category = category;
    }
    
    private StringProperty tenant;

public String getTenant() {
    return tenant.get();
}

public StringProperty tenantProperty() {
    return tenant;
}

public void setTenant(String tenant) {
    this.tenant.set(tenant);
}


    private String tenantName;
 public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

   

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.roomNumber);
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
        final Node other = (Node) obj;
        if (!Objects.equals(this.roomNumber, other.roomNumber)) {
            return false;
        }
        return true;
    }

        
    
    
    
}
