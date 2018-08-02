package com.meshpy.dao;

import com.meshpy.entity.Hero;

import java.util.List;

public interface HeroDAO {

    public List<Hero> getHeroes();
    public void addHero(Hero hero);
    public List<Hero> searchHeroes(String heroName);
    public Hero getHero(int id);
    public void deleteHero(int id);
}
