package bg.uni_sofia.fmi.oopjava.project.user;
 
/**
 *
 * @author Dimitar Panayotov
 */
public enum Permission {

    Administrator("Administrator"), //can encrypt decrypt + add / remove users
    User("User"), // can encryp decrypt
    None("None"); //can't encrypt decrypt

    private final String text;

    private Permission(String text) {
        this.text = text;
    }
}