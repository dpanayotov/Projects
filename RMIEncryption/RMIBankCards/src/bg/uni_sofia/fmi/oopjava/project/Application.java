package bg.uni_sofia.fmi.oopjava.project;

import bg.uni_sofia.fmi.oopjava.project.exceptions.ExceptionHandler;
import bg.uni_sofia.fmi.oopjava.project.server.ServerRemote;
import bg.uni_sofia.fmi.oopjava.project.server.Server;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Dimitar Panayotov
 */
public final class Application {

    //Valid number: 4589826367187613

    /**
     * Name of the file in which all the users are stored.
     */
    
    public static final String FILE_NAME = "users.xml";
    
    /**
     *  Port on which the server is working.
     */
    
    private static final String SERVICE_NAME = "Server";
    public static final int PORT = 12345;
    private static ServerRemote server;
    private static Registry registry;

    public static void startClient() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(PORT);
        server = (ServerRemote) registry.lookup(SERVICE_NAME);
    }

    public static void startServer() throws RemoteException, AlreadyBoundException {
        server = new Server();
        registry = LocateRegistry.createRegistry(PORT);
        registry.bind(SERVICE_NAME, server);
    }
    
    /**
     * Restarts the server. Used to change the status in the server panel.
     * @throws RemoteException
     */
    public static void restartServer() throws RemoteException{
        server.restart();
        registry.rebind(SERVICE_NAME, server);
        UnicastRemoteObject.exportObject(server, PORT);
    }

    /**
     * Closes the connection to the client and kills the server.
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */
    public static void closeConnection() throws RemoteException, NotBoundException, MalformedURLException {
        try {
            if (server.isAlive()) {
                registry.unbind(SERVICE_NAME);
                server.kill();
                UnicastRemoteObject.unexportObject(server, true);
            }
        } catch (NoSuchObjectException ex) {
            ExceptionHandler.show(ex);
        }
    }

    /**
     * Returns a reference to the server through which all the main functionalities are performed.
     * @return server reference
     */
    public static ServerRemote getServer() {
        return server;
    }
}
