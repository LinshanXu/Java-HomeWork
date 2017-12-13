/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import model.*;
/**
 *
 * @author Rsheep
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class ConvertDAO {
    @PersistenceContext(unitName = "convertPU")
    private EntityManager em;
    public ConvertData findRateByType(String tp){
        ConvertData Convert = em.find(ConvertData.class, tp);
        if(Convert==null){
            throw new EntityNotFoundException("Don't support such convert " + tp);
        }
        return Convert;
    }
    
}
