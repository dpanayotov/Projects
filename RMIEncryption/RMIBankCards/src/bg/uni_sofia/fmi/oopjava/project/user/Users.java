package bg.uni_sofia.fmi.oopjava.project.user;

import bg.uni_sofia.fmi.oopjava.project.exceptions.InvalidCredentialsException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
 
/**
 *
 * @author Dimitar Panayotov
 */
public class Users implements Serializable {
    private List<User> users = new ArrayList<>();

    public synchronized void add(User user) {
        this.users.add(user);
    }

    public synchronized void addAll(Collection<User> users) {
        this.users.addAll(users);
    }

    public synchronized void addAll(Object users) throws IllegalArgumentException {
        if (users instanceof Users) {
            this.users.addAll(((Users) users).getAll());
        } else {
            throw new IllegalArgumentException("Cannot add object that is not user!");
        }
    }

    public Collection<User> getAll() {
        return this.users;
    }

    public User get(int index) {
        return this.users.get(index);
    }

    public boolean hasAny() {
        return this.users.isEmpty();
    }
    
    public synchronized void remove(User user){
        Iterator<User> i = users.iterator();
        while(i.hasNext()){
            User current = i.next();
            if(current.compareTo(user) == 0){
                i.remove();
                return;
            }
        }
    }
    
    public User getByUsername(String username) throws InvalidCredentialsException{
        Iterator<User> i = users.iterator();
        while(i.hasNext()){
            User current = i.next();
            if(current.getUsername().equals(username)){
                return current;
            }
        }
        
        throw new InvalidCredentialsException("No user found!");
    }
}