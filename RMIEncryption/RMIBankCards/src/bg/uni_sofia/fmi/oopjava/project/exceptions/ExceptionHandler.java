package bg.uni_sofia.fmi.oopjava.project.exceptions;

import java.rmi.ServerException;

/**
 *
 * @author Dimitar Panayotov
 */
public class ExceptionHandler {

    private static ErrorDialog dialog = null;

    public static void show(Exception e) {
        if (dialog == null) { //only one dialog at a time
            java.awt.EventQueue.invokeLater(() -> {
                dialog = new ErrorDialog(e);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e1) {
                        dialog = null;
                    }
                });
                dialog.setVisible(true);
            });
        }
    }

    public static void serverError() {
        show(new ServerException("There has been a problem connecting to the server!"));
    }
}
