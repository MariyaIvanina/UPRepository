/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.sql.SQLException;

/**
 *
 * @author Mary
 */
public interface IMyService {
    public Object service(Object [] args) throws SQLException;
    public void serviceForInserting(Object [] args) throws SQLException;
    public void serviceForUpdating(Object [] args) throws SQLException;
    public void serviceForUpdatingDeleting(Object [] args) throws SQLException;
    public void serviceForDeleting(Object[] args) throws SQLException;
    public void serviceForUpdatingNickNamesForMessages(Object [] args) throws SQLException;
    public Object serviceForMessages(Object [] args) throws SQLException;
}
