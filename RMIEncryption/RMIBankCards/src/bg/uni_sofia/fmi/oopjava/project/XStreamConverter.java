package bg.uni_sofia.fmi.oopjava.project;

import bg.uni_sofia.fmi.oopjava.project.user.User;
import bg.uni_sofia.fmi.oopjava.project.user.Users;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dimitar Panayotov
 */
public class XStreamConverter {
    private XStream xStream = null;

    public XStreamConverter() {
        xStream = new XStream(new StaxDriver());
        loadSettings();
    }

    /**
     * Load settings for file manipulation with users
     */
    private void loadSettings() {
        xStream.alias("user", User.class);
        xStream.alias("users", Users.class);
        xStream.addImplicitCollection(Users.class, "users", User.class);
    }
    
    /**
     * Writes the object to a file with the specified name
     * @param obj
     * @param file
     * @throws IOException
     */
    public void toEmptyXMLFile(Object obj, String file) throws IOException {
        new File(file).delete();
        try (FileWriter writer = new FileWriter(file)) {
            xStream.toXML(obj, writer);
        }
    }

    /**
     * Read users from a file with the specified name.
     * @param type
     * @param file
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object getAllObjects(Class type, String file) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(!new File(file).exists()){
            throw new FileNotFoundException("File " + file + " does not exist!");
        }
        Object result = type.newInstance();
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuffer buff = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            buff.append(line);
        }
        
        if (result instanceof Users) {
            ((Users) result).addAll(xStream.fromXML(buff.toString()));
        }

        return result;
    }
    
    /**
     * Write cards with the specified conversion to the file used by the writer.
     * @param map
     * @param writer
     * @param converter
     */
    public void writeMap(Map<String, List<String>> map, Writer writer, MapConverter converter){
        xStream.alias("root", map.getClass());
        xStream.registerConverter(converter);
        xStream.toXML(map, writer);
    }
    
    /**
     * Adds all the objects from the collection to the a file with the specified name.
     * @param obj
     * @param file
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void addAll(Collection<Object> obj, String file) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException{
        for(Object o : obj){
            add(o, file);
        }
    }

    /**
     * Adds an object to a file with the specified name.
     * If the object's type is User the content of the file is read, saved to
     * a Users object, the new Users is added to the Users and then the Users are
     * saved back into the file.
     * @param obj
     * @param file
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void add(Object obj, String file) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        if (new File(file).length() == 0) {
            if (obj instanceof User) {
                Users users = new Users();
                users.add((User) obj);
                toEmptyXMLFile(users, file);
            }
            return;
        }

        if (obj instanceof User) {
            Users fromFile = (Users) getAllObjects(Users.class, file);
            fromFile.add((User) obj);
            toEmptyXMLFile(fromFile, file);
        }
    }
    
    /**
     *
     * @return
     */
    public XStream getStream(){
        return this.xStream;
    }
}
