/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityNotFoundException;
import integration.*;
import model.*;

/**
 *
 * @author Rsheep
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class CurrencyConvert {
    @EJB ConvertDAO conDB;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public convertDTO findConvert(String tp){
        return conDB.findRateByType(tp);
    }
}
