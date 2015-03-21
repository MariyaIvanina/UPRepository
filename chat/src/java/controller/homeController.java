/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
public class homeController{
    
    
   @RequestMapping(value = "/index", method = RequestMethod.GET)
        public ModelAndView welcome(HttpServletRequest req,HttpServletResponse resp,ModelMap model) {
        
        List<User> result = new UserRepository().getUsers();
        PageableData<User> t = new PageableData<User>(result,1,3);
        String table = PageHelper.PageLinks(t.getPageNo(), t.getCountPage(), "/Chat/index/");
        model.addAttribute("Users",t);
        model.addAttribute("table",table);
        String currentuser = getUserEmail(req);
        String theme = getTheme(req);
        model.addAttribute("currentuser",currentuser);
        if(theme != null)
            model.addAttribute("theme", "/resources/css/"+theme+".css");
        else 
             model.addAttribute("theme", "/resources/css/united.css");
        model.addAttribute("cook", theme);
        return new ModelAndView("index",model);
    }
    
    @RequestMapping(value="/index/{name}", method = RequestMethod.GET)
    public String welcomeName(@PathVariable("name") String name,HttpServletRequest req,HttpServletResponse resp, ModelMap model) {
 
        List<User> result = new UserRepository().getUsers();
        PageableData<User> t = new PageableData<User>(result,Integer.parseInt(name),3);
        String table = PageHelper.PageLinks(t.getPageNo(), t.getCountPage(), "/Chat/index/");
        model.addAttribute("Users",t);
        model.addAttribute("table",table);
        String currentuser = getUserEmail(req);
        String theme = getTheme(req);
        model.addAttribute("currentuser",new UserRepository().getUser(currentuser).getUser_name());
         if(theme != null)
            model.addAttribute("theme", "/resources/css/"+theme+".css");
        else 
             model.addAttribute("theme", "/resources/css/united.css");
        return "index";
    }
    
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest req,HttpServletResponse resp,ModelMap model) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat all= new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss",java.util.Locale.ENGLISH);
        String curStringDate = all.format(c.getTime());
        String currentuser = getUserEmail(req);
        String theme = getTheme(req);
        User user = new UserRepository().getUser(currentuser);
        if(user != null)
        {
            model.addAttribute("currentuser",user.getUser_name());
            addUsertoList(user);
        }
        else
            model.addAttribute("currentuser",null);
        model.addAttribute("datetime", curStringDate);
        if(theme != null)
            model.addAttribute("theme", "/resources/css/"+theme+".css");
        else 
             model.addAttribute("theme", "/resources/css/united.css");
        
        return new ModelAndView("home",model);
    }
    /*@RequestMapping(value="/home/{lan}", method = RequestMethod.GET)
    public void homeLang(@PathVariable("lan") String lan,HttpServletRequest req,HttpServletResponse resp,ModelMap model) throws IOException {
        
        Cookie cookie = new Cookie("defaultLocale","de");
        resp.addCookie(cookie);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat all = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss",java.util.Locale.ENGLISH);
        String curStringDate = all.format(c.getTime());
        model.addAttribute("datetime", curStringDate);
        resp.sendRedirect("home");      
    }*/
    @RequestMapping(value="/home/{lan}", method = RequestMethod.GET)
    public String homeLang(@PathVariable("lan") String lan,HttpServletRequest req,HttpServletResponse resp,ModelMap model) throws IOException {
        
        Calendar c = Calendar.getInstance();
        SimpleDateFormat all= new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss",java.util.Locale.ENGLISH);
        String curStringDate = all.format(c.getTime());
        Cookie[] cookies = req.getCookies();
        String currentuser = getUserEmail(req);
        String theme = getTheme(req);
        
        model.addAttribute("currentuser",new UserRepository().getUser(currentuser).getUser_name());
        model.addAttribute("datetime", curStringDate);
        if(lan != null)
            model.addAttribute("theme", "/resources/css/"+lan+".css");
        else 
             model.addAttribute("theme", "/resources/css/united.css");
        Cookie cookie = new Cookie("Mytheme",lan);
        cookie.setPath("/");
        resp.addCookie(cookie);
        return "home";
    }
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest req,HttpServletResponse resp,ModelMap model) throws IOException 
    {
        String currentuser = getUserEmail(req);
        Cookie cookie = new Cookie("login","");
        resp.addCookie(cookie);
        deleteUserFromList(new UserRepository().getUser(currentuser));
        resp.sendRedirect("home");
    }
    private void deleteUserFromList(User user)
    {
        for(int i = 0; i < loginController.onlineUsers.size(); i++)
        {
            if(loginController.onlineUsers.get(i).getUser_id() == user.getUser_id())
            {
                loginController.onlineUsers.remove(i);
                break;
            }
        }
    }
    private void addUsertoList(User user)
    {
        boolean isFound = false;
        for(int i = 0; i < loginController.onlineUsers.size(); i++)
        {
            if(loginController.onlineUsers.get(i).getUser_id() == user.getUser_id())
            {
                isFound = true;
                break;
            }
        }
        if(!isFound)
            loginController.onlineUsers.add(user);
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

