package org.openbakery.timetracker.service;

import org.openbakery.timetracker.data.User;
import org.openbakery.timetracker.service.exception.LoginFailedException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rene
 * Date: 24.11.11
 * Time: 08:21
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {
    public User login(String username, String password) throws LoginFailedException;
    public void logoff(User user);
    public void store(User user);
    public void delete(User user);
    public List<User> getAllUsers();
    public List<User> getAllActiveUsers();

}
