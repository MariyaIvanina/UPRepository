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
import utilities.MessageExchange;
import dbcontext.BdContext;
import dbcontext.UserRepository;
import entity.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import entity.User;
/**
 *
 * @author Mary
 */
@Controller
@RequestMapping("/")
public class ChatController {
    private List<Message> history = new ArrayList<Message>();
    private int messageSize = 0;
    private MessageExchange messageExchange = new MessageExchange();

    List<Message> getActions(int index) {
        if (index > 0 && index < history.size())
            return history.subList(index, history.size());
        return history;
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
    @RequestMapping(value = "/onlineUser", method = RequestMethod.GET)
    public void getOnlineUsers(HttpServletRequest req,HttpServletResponse resp,Model model) throws IOException
    {
        sendResponse(resp,messageExchange.getServerResponseUser(LoginController.onlineUsers));
    }
    @RequestMapping(value="/msgchat/{text}", method = RequestMethod.GET)
    public void getMessages(@PathVariable("text") String text,HttpServletRequest req,HttpServletResponse resp,ModelMap model) throws IOException, SQLException
    {
        if (text != null) {
            Map<String, String> map = queryToMap(text);
            String token = map.get("token");
            Logger.getLogger(ChatController.class.getName()).info("Get request:token " + token + " recieved");
            if (token != null && !"".equals(token)) {
                int index = messageExchange.getIndex(token);
                if(index == 0)
                {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    List<Message> messages = new BdContext().getMessages();
                    messageSize = messages.size();
                    sendResponse(resp, messageExchange.getServerResponse(messages));
                }
                else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    sendResponse(resp, messageExchange.getServerResponse(getActions(index-messageSize)));
                }
            } 
            
        }
    }
    private void sendResponse(HttpServletResponse resp, String response) throws IOException {
        
        OutputStream os = resp.getOutputStream();
        os.write(response.getBytes("UTF-8"));
        os.flush();
        os.close();
    }
    
    @RequestMapping(value="/chat", method = RequestMethod.POST)
    public void chat(HttpServletRequest req,HttpServletResponse
            resp,ModelMap model) throws IOException, IllegalStateException, ServletException, ParseException
    {
        Message message = null;
        String[] mu = req.getParameterMap().keySet().toArray(new String[req.getParameterMap().keySet().size()]);
        if(mu.length > 0)
            message = new Message(messageExchange.getJSONObject(mu[0]));
        BdContext my = new BdContext();
        try {
            my.insertValue(message);
            message = my.getLastMessage();
        } catch (SQLException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(ChatController.class.getName()).info("Get Message from User "+message.getNickName()+" : " + message.getText() + " " + message.getMessageID() + " " + message.getUserId());
        history.add(message);
    }
    @RequestMapping(value="/changeNick", method = RequestMethod.POST)
    public void changeNick(HttpServletRequest req,HttpServletResponse resp,ModelMap model) throws IOException, IllegalStateException, ServletException, ParseException, SQLException 
    {
        User user = new User();
        String[] mu = req.getParameterMap().keySet().toArray(new String[req.getParameterMap().keySet().size()]);
        if(mu.length > 0)
        {
            System.out.println(mu[0]);
            JSONObject my = messageExchange.getJSONObject(mu[0]);
            user.setUser_id(Integer.parseInt((String)my.get("user_id")));
            user.setUser_name((String)my.get("NickName"));
        }
        Logger.getLogger(ChatController.class.getName()).info("Gnange Nick from User " + user.getUser_name());
        new UserRepository().updateUser(user);
        updateListUser(user);
        history.addAll(new BdContext().getUpdatedMessages(user.getUser_id(),user.getUser_name()));
    }
    @RequestMapping(value="/chat", method = RequestMethod.GET)
    public String toChat(HttpServletRequest req,HttpServletResponse resp,ModelMap model) throws IOException, SQLException 
    {
        String currentuser = getUserEmail(req);
        User user = new UserRepository().getUser(currentuser);
        history = new ArrayList<Message>();
        if(user != null)
        {
            model.addAttribute("username", user.getUser_name());
            model.addAttribute("nickID", user.getUser_id());
        }
        setTheme(req,model);
        return "chat";
    }
    @RequestMapping(value="/msgchat/{text}", method = RequestMethod.DELETE)
    private void doDelete(@PathVariable("text") String text,HttpServletRequest req,HttpServletResponse resp,ModelMap model) throws SQLException
    {
    	if (text != null) {
            Map<String, String> map = queryToMap(text);
            String id = map.get("id");
            Logger.getLogger(ChatController.class.getName()).info("DELETE request:id= "+id+" recieved");
            if (id != null && !"".equals(id)) {
                int index =Integer.parseInt(id);
                history.add(new Message(index));
                new BdContext().deleteMessage(index);
            }
        }
    }
    @RequestMapping(value="/msgchatEdit", method = RequestMethod.POST)
    private void doEdit(HttpServletRequest req,HttpServletResponse resp,ModelMap model) throws ParseException
    {
        Message message = null;
        String[] mu = req.getParameterMap().keySet().toArray(new String[req.getParameterMap().keySet().size()]);
        if(mu.length > 0)
            message = Message.getEditedMessage(messageExchange.getJSONObject(mu[0]));
    	if (message != null) {
            Logger.getLogger(ChatController.class.getName()).info("EDIT request:id= "+message.getMessageID()+"name=" + message.getNickName()+" msg = "+message.getText() +"recieved");
            try {
                new BdContext().updateMessage(message);
                history.add(new BdContext().getMessage(message.getMessageID()));
            } catch (SQLException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    private void updateListUser(User user)
    {
        for(int i = 0; i < LoginController.onlineUsers.size(); i++)
        {
            if(LoginController.onlineUsers.get(i).getUser_id() == user.getUser_id())
            {
                User temp = LoginController.onlineUsers.get(i);
                temp.setUser_name(user.getUser_name());
                LoginController.onlineUsers.set(i, temp);
            }
        }
    
    }

    private String getTheme(HttpServletRequest req)
    {
        Cookie[] cookies = req.getCookies();
        String theme = null;
        if(cookies != null)
        {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("Mytheme") && cookies[i].getValue().isEmpty()!=true) {
                    theme = cookies[i].getValue();
                }
            }
        }
        return theme;
    }
    private String getUserEmail(HttpServletRequest req)
    {
        Cookie[] cookies = req.getCookies();
        String currentuser = null;
        if(cookies != null)
        {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("login") && cookies[i].getValue().isEmpty()!=true) {
                    currentuser = cookies[i].getValue();
                }
            }
        }
        return currentuser;
    }
    private void setTheme(HttpServletRequest req, ModelMap model)
    {
        String theme = getTheme(req);
        if(theme != null)
            model.addAttribute("theme", "/resources/css/"+theme+".css");
        else
            model.addAttribute("theme", "/resources/css/united.css");
    }
}

