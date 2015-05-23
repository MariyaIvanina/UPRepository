/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import dbcontext.ConnectionPool;
import static utilities.MyThreadLocal.myThreadLocal;
import java.lang.reflect.Method;
import java.sql.Connection;
//import org.springframework.cglib.proxy.InvocationHandler;
import java.lang.reflect.InvocationHandler;
 /*
 * @author Mary
 */
public class MyInvocationHandler implements InvocationHandler {
    ConnectionPool pool=ConnectionPool.getInstance();
    IMyService myServiceOriginal = new MyService();

    public MyInvocationHandler() {
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connection conn = pool.getConnection();

        MyThreadLocal.setConnection(conn);
        Object result;
        try {
            result = method.invoke(myServiceOriginal, args);
            MyThreadLocal.getConnection().commit();

            return result;
        } catch (Throwable t) {
            MyThreadLocal.getConnection().rollback();
            throw t;
        } finally {
            pool.returnToPoll(conn);
        }

    }

}