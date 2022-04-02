package me.agronaut.essentials.Service;

import me.agronaut.essentials.DAO.Player;
import me.agronaut.essentials.Utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class PlayerService {
    public PlayerService() {
    }

    public boolean save(Player player)
    {
        boolean res = true;
        Session session = null;
        try
        {
            session = HibernateUtils.getHibernateSession();
            session.save(player);
        } catch (HibernateException e)
        {
            System.err.println("Hiba a lekerdezsben");
            res = false;
        }
        finally {
            if (session != null) {
                HibernateUtils.closeHibernateSession(session);
            }
        }
        return res;
    }
}
