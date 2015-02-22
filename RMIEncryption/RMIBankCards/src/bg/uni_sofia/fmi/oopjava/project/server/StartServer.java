package bg.uni_sofia.fmi.oopjava.project.server;

import bg.uni_sofia.fmi.oopjava.project.Application;
import bg.uni_sofia.fmi.oopjava.project.exceptions.ExceptionHandler;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Dimitar Panayotov
 */
public class StartServer {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            Application.startServer();
            Application.getServer().start();
        } catch (RemoteException | AlreadyBoundException ex) {
            ExceptionHandler.show(ex);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ExceptionHandler.show(ex);
        }
    }
}
