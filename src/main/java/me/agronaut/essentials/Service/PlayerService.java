package me.agronaut.essentials.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import me.agronaut.essentials.DAO.Player;
import me.agronaut.essentials.Utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.logging.Logger;

public class PlayerService {

    private final Logger logger = Logger.getLogger(PlayerService.class.getName());

    public PlayerService() {
    }

    public Long save(Player player)
    {
        Long res = null;
        Session session = null;
        try
        {
            session = HibernateUtils.getHibernateSession();
            session.persist(player);

            res = player.getId();
        } catch (HibernateException e)
        {
            logger.severe("Hiba a lekerdezsben");
        }
        finally {
            HibernateUtils.closeHibernateSession(session);
        }

        return res;
    }

    public Player getByUsername(String username)
    {
        Player res = null;
        Session session = null;

        try
        {
            session = HibernateUtils.getHibernateSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Player> criteriaQuery = cb.createQuery(Player.class);
            Root<Player> root = criteriaQuery.from(Player.class);

            criteriaQuery.select(root).where(cb.equal(root.get("name"), username));

            Query<Player> result = session.createQuery(criteriaQuery);

            res = result.getSingleResult();
        } catch (HibernateException e)
        {
            logger.severe("Hiba a lekerdezes soran!");
        }

        return res;
    }
}
