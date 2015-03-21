/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository.methods;

/**
 *
 * @author Mary
 */

import repository.entity.User;
import java.util.List;
import org.hibernate.Session;
import repository.utils.HibernateUtil;
/**
 *
 * @author Mary
 */
public class Repository {
   public static void addUser(String name,String email,String password, String datetime) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User r = new User();
        r.setUser_name(name);
        r.setPassword(password);
        r.setEmail(email);
        r.setMember_since(datetime);
        session.save(r);
        session.getTransaction().commit();
    }
    public static User GetUserByEmail(String email)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User prof = (User)session.createQuery("From User as user where user.email =\'"+ email+"\'").uniqueResult();
        session.getTransaction().commit();
        return prof;
    }
     public static User GetUser(int ID) {
        /*Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Profession prof = (Profession)session.load(Profession.class,ID);
        session.getTransaction().commit();
        return prof;*/
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User prof = (User)session.load(User.class,ID);
        session.getTransaction().commit();
        return prof;
    }
 
    // Метод возвращает список профессий
    public static List<User> listUsers() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<User> result = session.createQuery("from User order by user_id").list();
        session.getTransaction().commit();
        return result;
    }
 
    // Метод удаляет по очереди все записи, которые ему переданы в виде списка
    public static void deleteUsers(List<User> result) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for(User p : result) {
            System.out.println("Delete:"+p.getUser_id()+":"+p.getUser_name());
            session.delete(p);
            //session.flush();
        }
        session.getTransaction().commit();
    }
 
    // Методу удаляет одну запись
    public static void deleteEntity(Object o) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(o);
        session.flush();
        session.getTransaction().commit();
    }
    
}
