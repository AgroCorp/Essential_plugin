package me.agronaut.essentials.Service;

import me.agronaut.essentials.DAO.Groups;
import me.agronaut.essentials.Utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class GroupService {
    public boolean save(Groups group)
    {
        Session session  = null;
        boolean res = true;
        try{
            session = HibernateUtils.getHibernateSession();

            session.save(group);

        } catch (HibernateException e) {
            System.err.println("Hiba az adat olvasasa soran");
            res = false;
        } finally {
            HibernateUtils.closeHibernateSession(session);
        }
        return res;
    }
}
