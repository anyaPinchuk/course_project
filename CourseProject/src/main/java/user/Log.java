package user;

import dao.UserDAO;
import server.Server;
import server.WrongPasswordException;
import user.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ANYA on 21.09.2016.
 */
public class Log{

    public static boolean authorization(User user) throws SQLException{
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.findAll();
        if(user != null) {
            for (User u: users) {
                if (u.getLogin().equals(user.getLogin())){
                    if (u.getPassword().equals(user.getPassword())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean registration(User user) throws SQLException{
        UserDAO userDAO = new UserDAO();
        if(user.getLogin()!=null && user.getPassword()!=null){
            if (!isLoginExist(user.getLogin())) {
                //Server.addUser(user);
                userDAO.insert(user);
                return true;
            }
        }
        return false;
    }

    public static boolean isLoginExist(String login) throws SQLException{
        UserDAO userDAO = new UserDAO();
        boolean isExist = false;
        //if (login!=null && Server.getUsers().containsKey(login))
        if (login!=null && userDAO.findAll().contains(login))
            isExist = true;
        return isExist;
    }
}
