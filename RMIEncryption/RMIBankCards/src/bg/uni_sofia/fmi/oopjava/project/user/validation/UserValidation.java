package bg.uni_sofia.fmi.oopjava.project.user.validation;

import bg.uni_sofia.fmi.oopjava.project.exceptions.InvalidCredentialsException;
import bg.uni_sofia.fmi.oopjava.project.user.Permission;

/**
 *
 * @author Dimitar Panayotov
 */
public interface UserValidation {

    default boolean validateUsername(String username) {
        return username.matches("^[a-z0-9_-]{3,16}$");
    }

    default boolean validatePassword(String password) {
        return password.matches("^[a-z0-9_-]{5,20}$");
    }

    default boolean validatePermission(Permission permission) {
        return permission == Permission.Administrator || permission == Permission.User || permission == Permission.None;
    }

    default boolean validatePermission(String permission) {
        String caseInsensitive = permission.toLowerCase();

        return caseInsensitive.equals("administrator") || 
                caseInsensitive.equals("user") || caseInsensitive.equals("none");
    }

    default void validateUser(String username, String password, Permission permission) throws InvalidCredentialsException {
        validateUser(username, password, permission.toString());
    }

    default void validateUser(String username, String password, String permission) throws InvalidCredentialsException {
        if (!validateUser(username, password)) {
            throw new InvalidCredentialsException("Username must be [3,16] characters and password must be [5,20] characters!");
        }
        
        if(!validatePermission(permission)){
            throw new InvalidCredentialsException("Invalid permission level!");
        }
    }

    default boolean validateUser(String username, String password) throws InvalidCredentialsException {
        return validatePassword(password) && validateUsername(username);
    }
}
