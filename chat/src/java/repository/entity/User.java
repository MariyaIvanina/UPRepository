/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository.entity;

/**
 *
 * @author Mary
 */
import java.sql.Date;
import org.json.simple.JSONObject;

/**
 *
 * @author Mary
 */
public class User {
    private int user_id;
    private String user_name;
    private String email;
    private String password;
    private String member_since;

    public User()
    {
    
    }
    public User(String name, String email, String password, String toString) {
        this.user_name = name;
        this.email = email;
        this.password = password;
        this.member_since = toString;
    }
	public String toString()
	{
		JSONObject js=new JSONObject();
		js.put("user_id", getUser_id());
		js.put("user_name", getUser_name());
		js.put("password", getPassword());
		js.put("email", getEmail());
		return js.toJSONString();
	}
    
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getMember_since() {
        return member_since;
    }

  
    public void setMember_since(String member_since) {
        this.member_since = member_since;
    }

    
    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
