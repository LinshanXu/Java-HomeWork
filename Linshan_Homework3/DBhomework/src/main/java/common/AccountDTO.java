/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author aleks_uuia3ly
 */
public interface AccountDTO extends Remote{
    /**
     * The specified message is received by the client.
     *
     * @param msg The message that shall be received.
     */
    void recvMsg(String msg) throws RemoteException;
}
