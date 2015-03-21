/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class registrationController {
    
   Calendar c = Calendar.getInstance();
   SimpleDateFormat all = new SimpleDateFormat("yyyy-MM-dd");
   String curStringDate = all.format(c.getTime());
   
   //Date curDate = new Date();
   //String curStringDate = new SimpleDateFormat("yyyy-MM-dd").format(curDate); 
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
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
            model.addAttribute("theme", "/resources/css/"+theme+".css");
        else 
             model.addAttribute("theme", "/resources/css/united.css");
        return new ModelAndView("registration", "person", new Person());
              
    }
   @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String processSubmit(
            @ModelAttribute("person") Person person,
            BindingResult result,HttpServletRequest req,HttpServletResponse resp, Model model) throws IOException {
        
        
        new PersonValidator().validate(person, result);
	if (result.hasErrors()) {
            String theme = getTheme(req);
            if(theme != null)
                model.addAttribute("theme", "/resources/css/"+theme+".css");
            else 
                 model.addAttribute("theme", "/resources/css/united.css");
            return "registration";
        }
        else
        {
            new UserRepository().insertUser(new User(person.getName(), person.getEmail(), person.getPassword(), curStringDate.toString()));
            resp.sendRedirect("home");
            return "home";
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

