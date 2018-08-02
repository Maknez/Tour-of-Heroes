package com.meshpy.service;

import com.meshpy.dao.HeroDAO;
import com.meshpy.entity.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HeroServiceImpl implements HeroService {

    @Autowired
    private HeroDAO heroDAO;

    @Override
    @Transactional
    public List<Hero> getHeroes() {
        return heroDAO.getHeroes();
    }

    @Override
    @Transactional
    public void addHero(Hero hero) {
        heroDAO.addHero(hero);
    }

    @Override
    @Transactional
    public List<Hero> searchHeroes(String heroName) {
        return heroDAO.searchHeroes(heroName);
    }

    @Override
    @Transactional
    public Hero getHero(int id) {
        return heroDAO.getHero(id);
    }

    @Override
    @Transactional
    public void deleteHero(int id) {
        heroDAO.deleteHero(id);
    }
}
