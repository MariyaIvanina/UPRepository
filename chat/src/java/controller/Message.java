/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

/**
 *
 * @author Mary
 */
import java.io.Serializable;
import java.util.Date;

import org.json.simple.JSONObject;


public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	private static int sid=0;
	private int messageID;
	private String NickName;
	private String Text;
	private String addedDate;
        public Message()
        {
            
        }
	public Message(JSONObject obj)
	{
            if((String)obj.get("messageID")!= null)
            {
		messageID=Integer.parseInt((String)obj.get("messageID"));
            }
		NickName=(String) obj.get("NickName");
		Text=(String) obj.get("Text");
		addedDate=(String) obj.get("addedDate");
	}
	Message(int id)
	{
		messageID=id;
		Text="";
		NickName="Deleter";
		addedDate="now";
	}
	public String toString()
	{
		JSONObject js=new JSONObject();
		js.put("messageID", getMessageID());
		js.put("NickName", getNickName());
		js.put("Text", getText());
		js.put("addedDate", getAddedDate());
		return js.toJSONString();
	}
        public static Message getEditedMessage(JSONObject obj)
	{
		Message res=new Message();
		res.messageID=Integer.parseInt((String)obj.get("messageID"));
		res.NickName=(String) obj.get("NickName");
		res.Text=(String) obj.get("Text");
		res.addedDate=(String) obj.get("addedDate");
		return res;
	}

    /**
     * @return the messageID
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * @param messageID the messageID to set
     */
    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    /**
     * @return the NickName
     */
    public String getNickName() {
        return NickName;
    }

    /**
     * @param NickName the NickName to set
     */
    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    /**
     * @return the Text
     */
    public String getText() {
        return Text;
    }

    /**
     * @param Text the Text to set
     */
    public void setText(String Text) {
        this.Text = Text;
    }

    /**
     * @return the addedDate
     */
    public String getAddedDate() {
        return addedDate;
    }

    /**
     * @param addedDate the addedDate to set
     */
    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }
}

