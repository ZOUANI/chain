/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Client;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author moulaYounes
 */
@Stateless
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "chainingProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private Long generateId() {
        Long maxId = (Long) em.createQuery("SELECT MAX(cl.id) FROM Client cl").getSingleResult();
        return (maxId == null ? 1l : maxId + 1);
    }

    @Override
    public void create(Client client) {
        client.setId(generateId());
        client.setBloquer(false);
        client.setDetailBloquage("");
        super.create(client);
    }

    public ClientFacade() {
        super(Client.class);
    }

}
