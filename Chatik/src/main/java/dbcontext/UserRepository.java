/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbcontext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.User;

/**
 *
 * @author Mary
 */
public class UserRepository {

    ConnectionPool connectionPool;
    public UserRepository()
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BdContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        connectionPool =  ConnectionPool.getInstance();
    }
    public List<User> getUsers() throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<User> list = new ArrayList<User>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select user_id, user_name, password, email from users " +
                    "order by user_id");
        while (rs.next()) {
                User r = new User();
                r.setUser_id(rs.getInt("user_id"));
                r.setUser_name(rs.getString("user_name"));
                r.setPassword(rs.getString("password"));
                r.setEmail(rs.getString("email"));
                list.add(r);
                
            }
        connection.commit();
        connectionPool.returnToPoll(connection);
        return list;
    }
    public void insertUser(User user) throws SQLException
    {
        Connection connection = connectionPool.getConnection();
        List<User> list = new ArrayList<User>();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (user_name,email, password) VALUES ( ?, ?, ?)");
        statement.setString(1,user.getUser_name());
        statement.setString(2, user.getEmail() );
        statement.setString(3, user.getPassword());
        statement.executeUpdate();
        connection.commit();
       connectionPool.returnToPoll(connection);
    }
    public void deleteUser(int id) throws SQLException
    {
        Connection connection = connectionPool.getConnection();
        Statement statement = null;
        PreparedStatement statement1 = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
        statement1.setInt(1,id);
        statement1.executeUpdate();
        connection.commit();
        connectionPool.returnToPoll(connection);
    }
    public void updateUser(User user) throws SQLException
    {
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET user_name = ? WHERE user_id = ?");
        statement1.setString(1,user.getUser_name());
        statement1.setInt(2, user.getUser_id());
        statement1.execute();
        connection.commit();
        connectionPool.returnToPoll(connection);
    }
    public User getUser(String email) throws SQLException
    {
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement1 = connection.prepareStatement("select user_id, user_name, password, email from users WHERE email = ?");
        statement1.setString(1,email);
        ResultSet rs = statement1.executeQuery();
        User r = new User();
        if(rs.next()) {
                r = new User();
                r.setUser_id(rs.getInt("user_id"));
                r.setUser_name(rs.getString("user_name"));
                r.setPassword(rs.getString("password"));
                r.setEmail(rs.getString("email"));
            }
        connection.commit();
        connectionPool.returnToPoll(connection);
        return r;
    
    }
}
