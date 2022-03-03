/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.iservices.session;

import blastandburn.entities.session.Session;
import java.util.List;

/**
 *
 * @author fatma
 */
public interface ISessionService {
     public void createSession(Session s);
        public void modifierSession(Session s,int i);
        public void SupprimerSession(int i);
        public List<Session> listSesion();
    
    
}
