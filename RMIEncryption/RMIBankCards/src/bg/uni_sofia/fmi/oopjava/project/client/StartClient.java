package bg.uni_sofia.fmi.oopjava.project.client;

import bg.uni_sofia.fmi.oopjava.project.Application;
import bg.uni_sofia.fmi.oopjava.project.exceptions.ExceptionHandler;
import bg.uni_sofia.fmi.oopjava.project.exceptions.MessageHandler;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Dimitar Panayotov
 */
public class StartClient {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            Application.startClient();
            new Client().start();
        } catch ( NotBoundException | ClassNotFoundException |
                InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ExceptionHandler.show(ex);
        } catch (RemoteException e){
            MessageHandler.showError("Please make sure the server is running and try again!");
        }
    }
}
