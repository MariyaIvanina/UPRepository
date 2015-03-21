/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author Mary
 */
public class BdContext {
    public Message getLastMessage()
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        Message r = new Message();
        try {
            /*Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdatabase?zeroDateTimeBehavior=convertToNull", "root", "0707vfvf1986");*/
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0707vfvf1986");
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT messageID, NickName, Text, addedDate FROM messages WHERE messageID = (SELECT MAX(messageID) FROM messages)");
            if (rs.next()) {
                
                r.setMessageID(rs.getInt("messageID"));
                r.setNickName(rs.getString("NickName"));
                r.setText(rs.getString("Text"));
                r.setAddedDate(rs.getString("addedDate"));
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
        public List<Message> getMessages() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Message> list = new ArrayList<Message>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0707vfvf1986");
            statement = connection.createStatement();
            rs = statement.executeQuery("select messageID, NickName, Text, addedDate from messages " +
                    "order by messageID");
            while (rs.next()) {
                Message r = new Message();
                r.setMessageID(rs.getInt("messageID"));
                r.setNickName(rs.getString("NickName"));
                r.setText(rs.getString("Text"));
                r.setAddedDate(rs.getString("addedDate"));
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
    public void insertValue(Message msg)
    {
        Connection connection = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0707vfvf1986");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO messages (NickName,Text, addedDate) VALUES ( ?, ?, ?)");
            statement.setString(1,msg.getNickName());
            statement.setString(2, msg.getText() );
            statement.setString(3, msg.getAddedDate());
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
    public void deleteMessage(int id)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Message> list = new ArrayList<Message>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0707vfvf1986");
            PreparedStatement statement1 = connection.prepareStatement("DELETE FROM messages WHERE messageID = ?");
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
    void updateMessage(Message msg)
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Message> list = new ArrayList<Message>();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0707vfvf1986");
            PreparedStatement statement1 = connection.prepareStatement("UPDATE messages SET Text = ? WHERE messageID = ?");
            statement1.setString(1,msg.getText());
            statement1.setInt(2, msg.getMessageID());
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
}
