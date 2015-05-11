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

package dbcontext;

import utilities.IMyService;
import utilities.MyInvocationHandler;
import entity.Message;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.cglib.proxy.Proxy;

/**
 *
 * @author Mary
 */
public class BdContext {
    ThreadLocal x;
    IMyService myServiceProxy = (IMyService) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{IMyService.class}, new MyInvocationHandler());
    public BdContext()
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BdContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Message getLastMessage() throws SQLException
    {

        Message r = new Message();
        ResultSet rs = (ResultSet)
        myServiceProxy.service(new Object[]{"SELECT * FROM messages order by messageID desc limit 1"});
        if (rs.next()) {
                
                r.setMessageID(rs.getInt("messageID"));
                r.setNickName(rs.getString("NickName"));
                r.setText(rs.getString("Text"));
                r.setAddedDate(rs.getString("addedDate"));
                r.setUserId(rs.getInt("userId"));
                r.setModification(rs.getInt("modification"));
            }
       
        return r;
    }
    public int getLastModification() throws SQLException
    {

        int r = 0;
        ResultSet rs = (ResultSet)
                myServiceProxy.service(new Object[]{"SELECT * FROM messages order by modification desc limit 1"});
        if (rs.next()) {

            r = rs.getInt("modification");
        }

        return r;
    }
    public List<Message> getMessagesByModification(int index) throws SQLException {
        List<Message> list = new ArrayList<Message>();
        ResultSet rs =(ResultSet) myServiceProxy.serviceForMessages(new Object[]{"select * from messages where modification > ? order by modification", index});
        while (rs.next()) {
            Message r = new Message();
            r.setMessageID(rs.getInt("messageID"));
            r.setNickName(rs.getString("NickName"));
            r.setText(rs.getString("Text"));
            r.setAddedDate(rs.getString("addedDate"));
            r.setUserId(rs.getInt("userId"));
            r.setModification(rs.getInt("modification"));
            list.add(r);
            System.out.println(r.getMessageID() + ":" + r.getNickName()+ " " + r.getText() + " " + r.getAddedDate().toString());
        }

        return list;
    }
    public List<Message> getMessages() throws SQLException {
        List<Message> list = new ArrayList<Message>();
        ResultSet rs =(ResultSet) myServiceProxy.service(new Object[]{"select messageID, NickName, Text, addedDate, userId, modification from messages order by messageID"});
        while (rs.next()) {
                Message r = new Message();
                r.setMessageID(rs.getInt("messageID"));
                r.setNickName(rs.getString("NickName"));
                r.setText(rs.getString("Text"));
                r.setAddedDate(rs.getString("addedDate"));
                r.setUserId(rs.getInt("userId"));
                r.setModification(rs.getInt("modification"));
                list.add(r);
                System.out.println(r.getMessageID() + ":" + r.getNickName()+ " " + r.getText() + " " + r.getAddedDate().toString());
        }   
       
        return list;
    }
    public void insertValue(Message msg) throws SQLException
    {
        myServiceProxy.serviceForInserting(new Object[]{"INSERT INTO messages (NickName,Text, addedDate, userId, modification) VALUES ( ?, ?, ?, ?, ?)",msg.getNickName(),msg.getText(),msg.getAddedDate(), msg.getUserId(), msg.getModification()});
        
    }
    public void updateDeletedMessage (int id, int modification) throws SQLException
    {
        myServiceProxy.serviceForUpdatingDeleting(new Object[]{"UPDATE messages SET Text = ?, modification = ? WHERE messageID = ?","",modification,id});
    }
    public void deleteMessage(int id) throws SQLException
    {
        myServiceProxy.serviceForDeleting(new Object[]{"DELETE FROM messages WHERE messageID = ?",id});
    }
    public void updateMessage(Message msg) throws SQLException
    {
        myServiceProxy.serviceForUpdatingDeleting(new Object[]{"UPDATE messages SET Text = ?, modification = ? WHERE messageID = ?",msg.getText(),msg.getModification(), msg.getMessageID()});
    }
    public List<Message> getUpdatedMessages(int userId, String nickName, int modification) throws SQLException
    {
        myServiceProxy.serviceForUpdatingDeleting(new Object[]{"UPDATE messages SET NickName = ?, modification = ? WHERE userId = ?",nickName,modification,userId});
        List<Message> list = new ArrayList<Message>();
        ResultSet rs =(ResultSet) myServiceProxy.serviceForMessages(new Object[]{"select messageID, NickName, Text, addedDate, modification from messages where userId = ?", userId});
        while (rs.next()) {
            Message r = new Message();
            r.setMessageID(rs.getInt("messageID"));
            r.setNickName(rs.getString("NickName"));
            r.setText(rs.getString("Text"));
            r.setAddedDate(rs.getString("addedDate"));
            r.setModification(rs.getInt("modification"));
            list.add(r);
            System.out.println(r.getMessageID() + ":" + r.getNickName()+ " " + r.getText() + " " + r.getAddedDate().toString());
        }
        return list;
    }
    public Message getMessage(int messageId) throws SQLException
    {
        Message message = new Message();
        ResultSet rs =(ResultSet) myServiceProxy.serviceForMessages(new Object[]{"select messageID, NickName, Text, addedDate, modification from messages where messageId = ?", messageId});
        while (rs.next()) {

            message.setMessageID(rs.getInt("messageID"));
            message.setNickName(rs.getString("NickName"));
            message.setText(rs.getString("Text"));
            message.setAddedDate(rs.getString("addedDate"));
            message.setModification(rs.getInt("modification"));
            System.out.println(message.getMessageID() + ":" + message.getNickName()+ " " + message.getText() + " " + message.getAddedDate().toString());
        }
        return message;
    }
}
