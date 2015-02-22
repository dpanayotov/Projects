/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.uni_sofia.fmi.oopjava.project;

import bg.uni_sofia.fmi.oopjava.project.client.StartClient;
import bg.uni_sofia.fmi.oopjava.project.server.StartServer;

/**
 *
 * @author Dimitar Panayotov
 */
public class Main {
    
    public static void main(String[] args) {
        if(args.length > 0){
            if(args[0].equals("Server")){
                StartServer.main(args);
            }else if(args[0].equals("Client")){
                StartClient.main(args);
            }
        }else{
            StartServer.main(args);
        }
    }
    
}
