/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbcontext;

/**
 *
 * @author Mary
 */
//import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.cglib.proxy.Proxy;

/**
 *
 * @author Mary
 */
public class ConnectionPool {
    static final ConnectionPool instance=new ConnectionPool();
    public List<Connection> connections;
    
    public static ConnectionPool getInstance()
    {
        return instance;
    }
    private ConnectionPool()
    {
        connections = new LinkedList<Connection>();
        for(int i = 0; i < 20; i++)
        {
            try {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0707vfvf1986");
                connection.setAutoCommit(false);
                connections.add(connection);
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public synchronized Connection getConnection()
    {
        if(connections.isEmpty()) {
           
            try {
                Thread.currentThread().wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Connection connection = connections.remove(0);
        return connection;
    }
    
    public synchronized void returnToPoll(Connection conn) {
        connections.add(conn);
        notifyAll();
    }
    
    /*{
        IMyService myServiceProxy = (IMyService) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{IMyService.class}, new MyInvocationHandler());
    }*/
}

