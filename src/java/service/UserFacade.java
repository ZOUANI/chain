/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author moulaYounes
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {


    @PersistenceContext(unitName = "chainingProjectPU")
    private EntityManager em;

    public int seConnnecter(User user) {
        if (user == null || user.getLogin() == null) {
            return -5;
        } else {
            User loadedUser = find(user.getLogin());
            if (loadedUser == null) {
                return -4;
            } else if (!loadedUser.getPassword().equals(user.getPassword())) {
                return -3;
            } else if (loadedUser.getBlocked() == 1) {
                return -2;
            } else {
                user.setBlocked(loadedUser.getBlocked());
                return 1;
            }
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

}
