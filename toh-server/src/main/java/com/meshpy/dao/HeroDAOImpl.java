package com.meshpy.dao;

import com.meshpy.entity.Hero;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HeroDAOImpl implements HeroDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Hero> getHeroes() {

        // get the current Hibernate session
        Session session = sessionFactory.getCurrentSession();

        // create a query and sort by last name
        Query<Hero> query = session.createQuery("SELECT FROM heroes ORDER BY name", Hero.class);

        // execute query and get result list;
        List<Hero> heroes = query.getResultList();

        return heroes;
    }

    @Override
    public void addHero(Hero hero) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(hero);
    }

    @Override
    public List<Hero> searchHeroes(String heroName) {

        Session session = sessionFactory.getCurrentSession();
        Query query;

        if (heroName != null && heroName.trim().length() > 0) {
            query = session.createQuery("SELECT FROM heroes WHERE LOWER(name) LIKE :heroName", Hero.class);
            query.setParameter("heroName", "%" + heroName.toLowerCase() + "%");
        } else {
            query= session.createQuery("SELECT FROM heroes", Hero.class);
        }

        List<Hero> heroes = query.getResultList();

        return heroes;
    }

    @Override
    public Hero getHero(int id) {

        Session session = sessionFactory.getCurrentSession();
        Hero hero = session.get(Hero.class, id);
        return hero;
    }

    @Override
    public void deleteHero(int id) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM heroes WHERE id=:heroId");
        query.setParameter("heroId", id);
        query.executeUpdate();
    }
}
