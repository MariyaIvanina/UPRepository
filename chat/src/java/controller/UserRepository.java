/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import repository.entity.User;

/**
 *
 * @author Mary
 */
public class UserRepository {
    public List<User> getUsers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<User>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0707vfvf1986");
            statement = connection.createStatement();
            rs = statement.executeQuery("select user_id, user_name, password, email from users " +
                    "order by user_id");
            while (rs.next()) {
                User r = new User();
                r.setUser_id(rs.getInt("user_id"));
                r.setUser_name(rs.getString("user_name"));
                r.setPassword(rs.getString("password"));
                r.setEmail(rs.getString("email"));
                list.add(r);
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error: " + ex.getMessage());
            }
        }
        return list;
    }
    public void insertUser(User user)
    {
        Connection connection = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<User>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0707vfvf1986");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (user_name,email, password) VALUES ( ?, ?, ?)");
            statement.setString(1,user.getUser_name());
            statement.setString(2, user.getEmail() );
            statement.setString(3, user.getPassword());
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }
    public void deleteUser(int id)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Message> list = new ArrayList<Message>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0707vfvf1986");
            PreparedStatement statement1 = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
            statement1.setInt(1,id);
            statement1.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }
    void updateUser(User user)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Message> list = new ArrayList<Message>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0707vfvf1986");
            PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET user_name = ? WHERE user_id = ?");
            statement1.setString(1,user.getUser_name());
            statement1.setInt(2, user.getUser_id());
            statement1.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }
        public User getUser(String email)
        {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        User r = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0707vfvf1986");
            PreparedStatement statement1 = connection.prepareStatement("select user_id, user_name, password, email from users WHERE email = ?");
            statement1.setString(1,email);
            rs = statement1.executeQuery();
            
            if(rs.next()) {
                r = new User();
                r.setUser_id(rs.getInt("user_id"));
                r.setUser_name(rs.getString("user_name"));
                r.setPassword(rs.getString("password"));
                r.setEmail(rs.getString("email"));
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Error SQL execution: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error: " + ex.getMessage());
            }
        }
        return r;
    
    }
}
