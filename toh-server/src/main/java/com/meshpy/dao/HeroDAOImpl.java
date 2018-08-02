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
        Query<Hero> query = session.createQuery("FROM heroes ORDER BY name", Hero.class);

        // execute query and get result list;
        List<Hero> heroes = query.getResultList();

        return heroes;
    }

    @Override
    public void addHero(Hero hero) {

    }

    @Override
    public List<Hero> searchHeroes(String heroName) {
        return null;
    }

    @Override
    public Hero getHero(int id) {
        return null;
    }

    @Override
    public void deleteHero(int id) {

    }
}
