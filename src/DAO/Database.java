/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Node;
import model.Request;
import model.Tenant;

/**
 *
 * @author yanne
 */
public class Database {
    public static CustomHashTable<String, Node> nodes = new CustomHashTable<>();
    public static CustomHashTable<String, Tenant> tenants = new CustomHashTable<>();
    public static CustomHashTable<Integer, Request> requests = new CustomHashTable<>();
        
        public static int numberOfnodes=0;
        

       public static CustomHashTable<String, Node> searchNodes(String key) {
    CustomHashTable<String, Node> result = new CustomHashTable<>();
    nodes.forEachValue(node -> {
        if (node.getRoomNumber().toLowerCase().contains(key.toLowerCase()) ||
            node.getType().toLowerCase().contains(key.toLowerCase())) {
            result.put(node.getRoomNumber(), node); // Assuming Node has a getNodeID method
        }
    });
    return result;
}

      public static CustomHashTable<String, Node> searchNodes2(String key) {
    CustomHashTable<String, Node> result = new CustomHashTable<>();
    nodes.forEachValue(node -> {
        if (node.getRoomNumber().toLowerCase().contains(key.toLowerCase()) ||
            node.getAvailability().toLowerCase().contains(key.toLowerCase()) ||
            node.getType().toLowerCase().contains(key.toLowerCase()) ||
            String.valueOf(node.getPrice()).toLowerCase().contains(key.toLowerCase())) {
            result.put(node.getRoomNumber(), node);
        }
    });
    return result;
}

       
    public static CustomHashTable<Integer, Request> searchRequest(String key) {
    CustomHashTable<Integer, Request> result = new CustomHashTable<>();
    requests.forEachValue(request -> {
        if (request.getRoomNumber().toLowerCase().contains(key.toLowerCase()) ||
            request.getName().toLowerCase().contains(key.toLowerCase()) ||
            String.valueOf(request.getRequestId()).toLowerCase().contains(key.toLowerCase())) {
            result.put(request.getRequestId(), request); // Assuming Request has a getRequestId method
        }
    });
    return result;
}

        public static CustomHashTable<String, Tenant> searchTenantsByName(String key) {
        CustomHashTable<String, Tenant> result = new CustomHashTable<>();
        tenants.forEachValue(tenant -> {
            if (tenant.getName().toLowerCase().contains(key.toLowerCase())) {
                result.put(tenant.getTenantID(), tenant); // Assuming Tenant has a getTenantID method
            }
        });
        return result;
    }

            public static CustomHashTable<String, Tenant> searchTenant(String key) {
        CustomHashTable<String, Tenant> result = new CustomHashTable<>();
        tenants.forEachValue(tenant -> {
            if (tenant.getName().toLowerCase().contains(key.toLowerCase())) {
                result.put(tenant.getTenantID(), tenant); // Use Tenant's unique identifier as key
            }
        });
        return result;
    }

      public static Node findNode(String roomNumber) {
        return nodes.get(roomNumber);
    }
        
        public static List<Node> findAvailableNode() {
        List<Node> availableNodes = new ArrayList<>();
        nodes.forEachValue(node -> {
            if ("Available".equalsIgnoreCase(node.getAvailability())) {
                availableNodes.add(node);
            }
        });
        return availableNodes;
    }
    
      //DATABASE MYSQL  
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/login";
    static final String USER = "root";
    static final String PASS = "yannenzo";
    
    public static void loadNodesFromDB() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //STEP 3: Open a connection

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query

            stmt = conn.createStatement();
            String sql = "SELECT * FROM node";
            //STEP 5: Extract data from result set
            try (ResultSet rs = stmt.executeQuery(sql)) {
                //STEP 5: Extract data from result set
                while (rs.next()) {
                    //Retrieve by column name
                    String roomNum = rs.getString("roomNumber");
                    String type = rs.getString("type");
                    int price = rs.getInt("price");
                    String availability = rs.getString("availability");
                    String location = rs.getString("location");
                    String category = rs.getString("category");
                    
                    Node node = new Node(roomNum, type, price, availability, location, category);
                    nodes.put(node.getRoomNumber(), node);
                    numberOfnodes++;
                   
                }
                //STEP 6: Clean-up environment
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void saveNode(Node node) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //STEP 3: Open a connection

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String updateSql = "insert into node(roomNumber, type, price, availability, location, category) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement updateStatement = conn.prepareStatement(updateSql);

            updateStatement.setString(1, node.getRoomNumber());
            updateStatement.setString(2, node.getType());
            updateStatement.setInt(3, node.getPrice());
            updateStatement.setString(4, node.getAvailability());
            updateStatement.setString(5, node.getLocation());
            updateStatement.setString(6, node.getCategory());

            // Execute the update statement
            updateStatement.executeUpdate();
            // Close the resources
            updateStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateNode(String roomNumber, Node node) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //STEP 3: Open a connection

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String updateSql = "UPDATE node SET type = ?, price = ?, availability = ?, location = ?, category = ? WHERE roomNumber = ?";
            try (PreparedStatement updateStatement = conn.prepareStatement(updateSql)) {
                updateStatement.setString(1, node.getType());
                updateStatement.setInt(2, node.getPrice());
                updateStatement.setString(3, node.getAvailability());
                updateStatement.setString(4, node.getLocation());
                updateStatement.setString(5, node.getCategory());
                updateStatement.setString(6, node.getRoomNumber());

                // Execute the update statement
                updateStatement.executeUpdate();
                
                updateStatement.close();
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void deleteNode(String roomNumber) {
    Connection conn = null;
    PreparedStatement deleteStatement = null;
    
    try {
        // Register JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        // Open a connection
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        
        // Create the SQL statement
        String deleteSql = "DELETE FROM node WHERE roomNumber = ?";
        
        // Prepare the statement
        deleteStatement = conn.prepareStatement(deleteSql);
        deleteStatement.setString(1, roomNumber);
        
        // Execute the delete statement
        deleteStatement.executeUpdate();
        
        // Close the statement
        deleteStatement.close();
        
        // Close the connection
        conn.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    public static void changeNodeAvailability(String nodeID, String availability) {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
        //STEP 2: Register JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        //STEP 3: Open a connection
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        
        //STEP 4: Prepare the SQL statement for updating node availability
        String sql = "UPDATE node SET availability = ? WHERE roomNumber = ?";
        stmt = conn.prepareStatement(sql);
        
        // Set the values for the prepared statement
        stmt.setString(1, availability);
        stmt.setString(2, nodeID);
        
        //STEP 5: Execute the SQL statement to update node availability
        int rowsUpdated = stmt.executeUpdate();
        
        //STEP 6: Clean-up environment
        stmt.close();
        conn.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    public static void loadTenantFromDB() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //STEP 3: Open a connection

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query

            stmt = conn.createStatement();
            String sql = "SELECT * FROM tenant";
            //STEP 5: Extract data from result set
            try (ResultSet rs = stmt.executeQuery(sql)) {
                //STEP 5: Extract data from result set
                while (rs.next()) {
                    //Retrieve by column name
                    int tenantID = rs.getInt("tenantID");
                    String name = rs.getString("name");
                    String gender = rs.getString("gender");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    Date dateOfBirth = rs.getDate("dateOfBirth");
                    Date dateOfEntry = rs.getDate("dateOfEntry");
                    String nodeID = rs.getString("nodeID");
                    Tenant tenant = new Tenant(String.valueOf(tenantID), name, gender, dateOfBirth, phoneNumber, email, dateOfEntry, nodeID);
                
                // Add the tenant to the CustomHashTable
                tenants.put(String.valueOf(tenantID), tenant);
                   
                }
                //STEP 6: Clean-up environment
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static Tenant findTenantByNodeID(String nodeID) {
        final Tenant[] foundTenant = new Tenant[1]; // Array to hold the found tenant

        tenants.forEachValue(tenant -> {
            if (tenant.getNodeID().equals(nodeID)) {
                foundTenant[0] = tenant;
            }
        });

        return foundTenant[0];
    }

    public static void createTenant(Tenant newTenant) {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
        //STEP 2: Register JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        //STEP 3: Open a connection
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        
        //STEP 4: Prepare the SQL statement for inserting a new tenant
        String sql = "INSERT INTO tenant (tenantID, name, gender, dateOfBirth, phoneNumber, email, dateOfEntry, nodeID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql);
        
        // Set the values for the prepared statement
        stmt.setString(1, newTenant.getTenantID());
        stmt.setString(2, newTenant.getName());
        stmt.setString(3, newTenant.getGender());
        stmt.setDate(4, new java.sql.Date(newTenant.getDateOfBirth().getTime()));
        stmt.setString(5, newTenant.getPhoneNumber());
        stmt.setString(6, newTenant.getEmail());
        stmt.setDate(7, new java.sql.Date(newTenant.getDateOfEntry().getTime()));
        stmt.setString(8, newTenant.getNodeID());
        
        //STEP 5: Execute the SQL statement to insert the new tenant
        stmt.executeUpdate();
        
        //STEP 6: Clean-up environment
        stmt.close();
        conn.close();
        
        // Print a success message or perform any other necessary actions
        System.out.println("New tenant created successfully!");

    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    public static void deleteTenant(String tenantID ) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "DELETE FROM tenant WHERE tenantID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, tenantID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public static void createRequest(Request request) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO request ( name, gender, dateOfBirth, phoneNumber, email, timeOfRequest, roomNumber, requestDescription) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, request.getName());
            statement.setString(2, request.getGender());
            // Convert LocalDate to java.sql.Date
            statement.setDate(3, Date.valueOf(request.getDateOfBirth()));
            statement.setString(4, request.getPhoneNumber());
            statement.setString(5, request.getEmail());
            statement.setTimestamp(6, Timestamp.valueOf(request.getTimeOfRequest()));
            statement.setString(7, request.getRoomNumber());
            statement.setString(8, request.getRequestDescription());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all requests from the "request" table
    public static void loadRequestFromDB() {
       
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM request";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int requestID = resultSet.getInt("requestID");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                LocalDate dateOfBirth = resultSet.getDate("dateOfBirth").toLocalDate();
                String phoneNumber = resultSet.getString("phoneNumber");
                String email = resultSet.getString("email");
                LocalDateTime timeOfRequest = resultSet.getTimestamp("timeOfRequest").toLocalDateTime();
                String roomNumber = resultSet.getString("roomNumber");
                String requestDescription = resultSet.getString("requestDescription");

                Request request = new Request(requestID, name, gender, dateOfBirth, phoneNumber, email, timeOfRequest, roomNumber, requestDescription);
                requests.put(requestID, request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a specific request by its ID
    public static void deleteRequest(int requestId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "DELETE FROM request WHERE requestID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, requestId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void loadData() {
        numberOfnodes=0;
         requests.clear();
         nodes.clear();
         requests.clear();
        loadNodesFromDB();
        loadTenantFromDB();
        loadRequestFromDB();
    }
    
    
}
