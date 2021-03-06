/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface FileCatalog extends Remote{
    
    public long login(String username, String password, AccountDTO remoteNode) throws RemoteException;
    
    public void logout(long userID) throws RemoteException;
    
    public boolean register(String username, String password) throws RemoteException;
    
    public boolean unregister(String username, String password) throws RemoteException;
    
    public boolean uploadFile(long userID, String fileName, String filePath, boolean privacy, boolean readable, boolean writeable, boolean notification) throws RemoteException; 
    
    public boolean updateFileName(long userID, String fileName, String newName) throws RemoteException;
    
    public boolean updateFilePath(long userID, String fileName, String filePath) throws RemoteException;
    
    public boolean updateFilePrivacy(long userID, String fileName, boolean privacy) throws RemoteException;
    
    public boolean updateFileReadability(long userID, String fileName, boolean readable) throws RemoteException;
    
    public boolean updateFileWriteability(long userID, String fileName, boolean writeable) throws RemoteException;
    
    public boolean updateFileNotifications(long userID, String fileName, boolean notification) throws RemoteException;
    
    public ArrayList listFiles(long userID) throws RemoteException;
    
    public String readFile(long userID, String fileName) throws RemoteException;
    
    public String writeFile(long userID, String fileName) throws RemoteException;
    
    public void deleteFile(long userID, String filename) throws RemoteException;
    
}
