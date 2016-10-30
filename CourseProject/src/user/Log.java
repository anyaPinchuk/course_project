package user;

import server.Server;
import server.WrongPasswordException;
import user.User;

/**
 * Created by ANYA on 21.09.2016.
 */
public class Log{

    public static boolean authorization(User user){
        return user != null && Server.getUsers().containsKey(user.getLogin()) && user.equals(Server.getUsers().get(user.getLogin()));
    }

    public static boolean registration(User user){
        if(user.getLogin()!=null && user.getPassword()!=null){
            if (!isLoginExist(user.getLogin())) {
                Server.addUser(user);
                return true;
            }
        }
        return false;
    }

    public static boolean isLoginExist(String login){
        boolean isExist = false;
        if (login!=null && Server.getUsers().containsKey(login))
            isExist = true;
        return isExist;
    }
}
