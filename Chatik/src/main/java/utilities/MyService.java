/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author Mary
 */
public class MyService implements IMyService {

    @Override
    public Object service(Object [] args) throws SQLException{
        PreparedStatement statement= MyThreadLocal.getConnection().prepareStatement((String)args[0]);
        for(int i=1;i<args.length;i++)
            statement.setString(i,(String)args[i] );

        return statement.executeQuery();
    }
    @Override
    public void serviceForInserting(Object [] args) throws SQLException{
        PreparedStatement statement= MyThreadLocal.getConnection().prepareStatement((String)args[0]);
        for(int i=1;i<4;i++)
            statement.setString(i,(String)args[i] );
        for(int i = 4; i < 6; i++)
        {
            statement.setInt(i, (Integer)args[i]);
        }
        statement.executeUpdate();
    }
    @Override
    public void serviceForUpdating(Object [] args) throws SQLException{
        PreparedStatement statement= MyThreadLocal.getConnection().prepareStatement((String)args[0]);
        statement.setString(1,(String)args[1] );
        statement.setInt(2,(Integer)args[2] );
        statement.executeUpdate();
    }
    @Override
    public void serviceForUpdatingNickNamesForMessages(Object [] args) throws SQLException{
        PreparedStatement statement= MyThreadLocal.getConnection().prepareStatement((String)args[0]);
        statement.setString(1,(String)args[1] );
        statement.setInt(2,(Integer)args[2] );
        statement.executeUpdate();
    }

    @Override
    public Object serviceForMessages(Object[] args) throws SQLException {
        PreparedStatement statement= MyThreadLocal.getConnection().prepareStatement((String)args[0]);
        statement.setInt(1,(Integer)args[1] );
        return statement.executeQuery();
    }

    @Override
    public void serviceForDeleting(Object [] args) throws SQLException{
        PreparedStatement statement= MyThreadLocal.getConnection().prepareStatement((String)args[0]);
        statement.setInt(1,(Integer)args[1] );
        statement.executeUpdate();
    }
}
