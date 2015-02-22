package bg.uni_sofia.fmi.oopjava.project.server;

import bg.uni_sofia.fmi.oopjava.project.user.User;
import bg.uni_sofia.fmi.oopjava.project.user.Users;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Dimitar Panayotov
 */
public interface ServerRemote extends Remote{

    void start() throws RemoteException;

    boolean isAlive() throws RemoteException;

    void restart() throws RemoteException;

    void kill() throws RemoteException;

    void addUser(User user) throws RemoteException;

    Users getAllUsers() throws RemoteException;

    User hasEntry(String username) throws RemoteException;

    void saveUsers(Users users) throws RemoteException;
 
    void storeCards(int sortingOption) throws RemoteException;

    String encrypt(String key, String number, int displacement) throws RemoteException;

    String decrypt(String number) throws RemoteException;
    
    int getEncryptionCount(String card) throws RemoteException;
}
