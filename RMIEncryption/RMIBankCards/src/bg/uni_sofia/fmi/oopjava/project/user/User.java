package bg.uni_sofia.fmi.oopjava.project.user;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 *
 * @author Dimitar Panayotov
 */
public class User implements Serializable, Comparable<User> {
    private static final long serialVersionUID = 7537241177995906561L;

    private String username;
    private String password;
    private Permission permission;
    private final String KEY;
     
    public User() {
        this("", "", Permission.User);
    }
    
    public User(String username, String password) {
        this(username, password, Permission.User);
    }

    public User(String username, String password, Permission permission) {
        KEY = generateKey();
        this.username = username;
        this.password = password;
        this.permission = permission;
    }
    
    public User(User user){
        this.username = user.username;
        this.password = user.password;
        this.permission = user.permission;
        KEY = generateKey();
    }

    public Permission getPermission() {
        return permission;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int compareTo(User o) {
        if(username.equals(o.username) && password.equals(o.password) && permission.toString().equals(o.permission.toString())){
            return 0;
        }
        return -1;
    }
    
    private String generateKey() {
        String number =  UUID.randomUUID().toString().substring(0, 18);
        return number.replaceAll("-", "");
    }
    
    public final String getUID() throws RemoteException {
        return KEY;
    }
    
    public String toString(){
        return String.format("%s\t%s\t%s", username, "*****", permission.toString());
    }
}