package bg.uni_sofia.fmi.oopjava.project.server;

import bg.uni_sofia.fmi.oopjava.project.Application;
import bg.uni_sofia.fmi.oopjava.project.XStreamConverter;
import bg.uni_sofia.fmi.oopjava.project.exceptions.ExceptionHandler;
import bg.uni_sofia.fmi.oopjava.project.user.Permission;
import bg.uni_sofia.fmi.oopjava.project.user.User;
import bg.uni_sofia.fmi.oopjava.project.user.Users;
import bg.uni_sofia.fmi.oopjava.project.windows.ServerWindow;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.converters.extended.NamedMapConverter;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Dimitar Panayotov
 */
public class Server extends UnicastRemoteObject implements ServerRemote {

    private final XStreamConverter xsc = new XStreamConverter();
    private Map<String, List<String>> cards = new HashMap<>();
    private Map<String, Integer> encryptions = new HashMap<>();
    private boolean running;

    /**
     *
     * @throws RemoteException
     */
    public Server() throws RemoteException {
        super();
        initFile();
    }
    
    /**
     * If the users file is empty or does not exist, create a new with a default administrator
     */
    private void initFile(){
        if(!new File(Application.FILE_NAME).exists() || new File(Application.FILE_NAME).length() == 0){
            Users users = new Users();
            users.add(new User("admin", "admin", Permission.Administrator));
            try {
                xsc.add(users, Application.FILE_NAME);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException ex) {
                ExceptionHandler.show(ex);
            }
        }
    }

    @Override
    public void start() throws RemoteException {
        running = true;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerWindow();
            }
        });
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return running;
    }

    @Override
    public void restart() throws RemoteException {
        if(!running){
            running = true;
        }
    }

    @Override
    public void kill() throws RemoteException {
        if(running){
            running = false;
        }
    }

    @Override
    public synchronized void addUser(User user) throws RemoteException {
        try {
            xsc.add(user, Application.FILE_NAME);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException ex) {
            ExceptionHandler.show(ex);
        }
    }

    @Override
    public Users getAllUsers() throws RemoteException {
        Users users = null;
        try {
            users = (Users) xsc.getAllObjects(Users.class, Application.FILE_NAME);
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            ExceptionHandler.show(ex);
        }

        return users;
    }

    /**
     *
     * @param username
     * @return the user which corresponds to the username or null if no such user exists
     * @throws RemoteException
     */
    @Override
    public User hasEntry(String username) throws RemoteException {
        Users users = getAllUsers();
        for (User u : users.getAll()) {
            if (username.equals(u.getUsername())) {
                return u;
            }
        }
        return null;
    }

    @Override
    public synchronized void saveUsers(Users users) throws RemoteException {
        try {
            xsc.toEmptyXMLFile(users, Application.FILE_NAME);
        } catch (IOException ex) {
            ExceptionHandler.show(ex);
        }
    }
    
    @Override
    public String encrypt(String key, String number, int displacement) throws RemoteException {
        StringBuilder builder = new StringBuilder();
        String result;
        for(int i = 0, j=0; i<number.length(); i++){
            char c = number.charAt(i);
            int offset = (displacement + key.charAt(j))%16;
            builder.append((char)(c + offset) % 10);
            j = j++ % key.length();
        }
        
        result = builder.toString();
        
        addCard(number, result);
        increaseEncryptions(number);
        
        return result;
    }

    private void increaseEncryptions(String number){
        Integer val = 0;
        for(Map.Entry<String, Integer> e : encryptions.entrySet()){
            String k = e.getKey();
            if(k.equals(number)){
                val = e.getValue();
                break;
            }
        }
        
        encryptions.put(number, val+1);
    }
    
    private void addCard(String number, String encrypted){
        List<String> current = new ArrayList<>();
        for(Map.Entry<String, List<String>> e : cards.entrySet()){
            if(e.getKey().equals(number)){
                current = e.getValue();
                break;
            }
        }
        current.add(encrypted);
        cards.put(number, current);
    }

    @Override
    public String decrypt(String number) throws RemoteException {
        String alreadyEncrypted = alreadyEncrypted(number);
        if(alreadyEncrypted != null){
            return alreadyEncrypted;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            builder.append((char)(c + 10)%10);
        }
        
        addCard(builder.toString(), number);
        
        return builder.toString();
    }

    private String alreadyEncrypted(String encryption){
        for(Map.Entry<String, List<String>> e : cards.entrySet()){
            String number = e.getKey();
            for(String val : e.getValue()){
                if(val.equals(encryption)){
                    return number;
                }
            }
        }
        
        return null;
    }
    
    @Override
    public void storeCards(int sortingOption) throws RemoteException {
        MapConverter nmc = new NamedMapConverter(xsc.getStream().getMapper(), "card", "plaintext", String.class, "encrypted", String.class);
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose a folder");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setMultiSelectionEnabled(false);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("File (.xml)", ".xml"));
        Integer choice = chooser.showSaveDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            String filename = chooser.getSelectedFile().getAbsoluteFile().toString();
            File cardSave;
            if(filename.endsWith(".xml")){
                cardSave = new File(chooser.getSelectedFile().getAbsoluteFile().toString());
            }else{
                cardSave = new File(chooser.getSelectedFile().getAbsoluteFile() + ((FileNameExtensionFilter) chooser.getFileFilter()).getExtensions()[0]);
            }
            if(cardSave.exists()){
                int overwrite = JOptionPane.showConfirmDialog(null,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
                if(overwrite != JOptionPane.YES_OPTION){
                    chooser.showSaveDialog(null);
                }
            }
            try (Writer writer = new FileWriter(cardSave)) {
                if(sortingOption == 0){
                    xsc.writeMap(mapSortedByKey(), writer, nmc);
                }else if(sortingOption == 1){
                     xsc.writeMap(mapSortedByValue(), writer, nmc);
                }else{
                    ExceptionHandler.show(new IllegalArgumentException("Invalid sorting choice!"));
                }
            } catch (IOException ex) {
                ExceptionHandler.show(ex);
            }
        }
    }

    private Map<String, List<String>> mapSortedByKey() {
        return new TreeMap<>(cards);
    }
        
    private Map<String, List<String>> mapSortedByValue(){
        Map<String, List<String>> result = cards;
        Set<Entry<String, List<String>>> set = result.entrySet();
        List<Entry<String, List<String>>> list = new ArrayList<>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, List<String>>>(){
            @Override
            public int compare(Entry<String, List<String>> o1, Entry<String, List<String>> o2) {
                if(o1.getValue().size()<o2.getValue().size()){
                    return -1;
                }else if(o1.getValue().size()>o2.getValue().size()){
                    return 1;
                }else{
                    int sum = 0;
                    for(int i = 0; i<o1.getValue().size(); i++){
                        sum += o1.getValue().get(i).compareTo(o2.getValue().get(i));
                    }
                    return sum;
                }
            }
        });
        return result;
    }
    
    @Override
    public int getEncryptionCount(String card) throws RemoteException{
        for(Map.Entry<String, Integer> e : encryptions.entrySet()){
            String key = e.getKey();
            if(key.equals(card)){
                return e.getValue();
            }
        }
        return 0;
    }
}
