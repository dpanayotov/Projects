package bg.uni_sofia.fmi.oopjava.project.client;

import bg.uni_sofia.fmi.oopjava.project.Application;
import bg.uni_sofia.fmi.oopjava.project.exceptions.ExceptionHandler;
import bg.uni_sofia.fmi.oopjava.project.windows.LoginScreen;
import java.awt.EventQueue;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Dimitar Panayotov
 */
public class Client  {
    private static final int WAIT_TIME = 5;
    
    public void start()  {
        checkStatus();
        EventQueue.invokeLater(() -> {
            new LoginScreen();
        });
    }

    private void checkStatus() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    LocateRegistry.getRegistry(Application.PORT).lookup("Server");
                } catch (RemoteException | NotBoundException ex) {
                    ExceptionHandler.show(new ServerException("The server is unavailable at the moment. Please restart the application to see if the problem continues."));
                }
            }
        };
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(r, 0, WAIT_TIME, TimeUnit.SECONDS);
    }
}
