/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.startup;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import client.view.StreamHandler;
import common.FileCatalog;


public class Main {
     public static void main(String[] args) {
        try {
            FileCatalog server = (FileCatalog) Naming.lookup("filesystem");
            new StreamHandler().start(server);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println("Could not start client.");
        }
    }
}
