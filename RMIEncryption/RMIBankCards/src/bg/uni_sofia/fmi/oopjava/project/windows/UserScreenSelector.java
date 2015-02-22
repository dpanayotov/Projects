package bg.uni_sofia.fmi.oopjava.project.windows;

import bg.uni_sofia.fmi.oopjava.project.user.Permission;
import bg.uni_sofia.fmi.oopjava.project.user.User;
import java.awt.EventQueue;

/**
 *
 * @author Dimitar Panayotov
 */
public class UserScreenSelector {
    
    public UserScreenSelector(final User user){
        EventQueue.invokeLater(() -> {
            if(user.getPermission() == Permission.Administrator){
                 new AdminScreen(user);
            }else{
                new UserScreen(user);
            }
        });
    }
}
