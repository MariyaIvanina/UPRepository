/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.sql.Connection;

/**
 *
 * @author Mary
 */
public class MyThreadLocal {
    public static ThreadLocal<Connection> myThreadLocal = new ThreadLocal<Connection>();
    
    public static void setConnection( Connection con)
    {
        myThreadLocal.set(con);
        
    }
    public static Connection getConnection()
    {
        return myThreadLocal.get();
    }
}
