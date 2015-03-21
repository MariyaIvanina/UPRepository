/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import repository.entity.User;
import repository.methods.Repository;

/**
 *
 * @author Mary
 */
@Controller
@RequestMapping("/")
public class loginController{
   static List<User> onlineUsers = new ArrayList<User>();
    //Use onSubmit instead of doSubmitAction
    //when you need access to the Request, Response, or BindException objects
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView processRegister(HttpServletRequest req,HttpServletResponse resp,Model model) {
        
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
        if(theme != null)
        {
            model.addAttribute("theme", "/resources/css/"+theme+".css");
        }
        else 
        {
             model.addAttribute("theme", "/resources/css/united.css");
        }
        return new ModelAndView("login", "name", new Name());
              
    }
   @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processSubmit(
            @ModelAttribute("name") Name name,
            BindingResult result, HttpServletRequest req,HttpServletResponse resp,Model model) throws IOException {
        
        
        new NameValidator().validate(name, result);
	if (result.hasErrors()) {
            String theme = getTheme(req);
            if(theme != null)
                model.addAttribute("theme", "/resources/css/"+theme+".css");
            else 
                 model.addAttribute("theme", "/resources/css/united.css");
            return "login";
        }
        else
        {
            Cookie cookie = new Cookie("login",name.getEmail());
            cookie.setMaxAge(10000);
            resp.addCookie(cookie);
            User user = new UserRepository().getUser(name.getEmail());
            onlineUsers.add(user);
            resp.sendRedirect("chat");
            return "chat";
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
}
