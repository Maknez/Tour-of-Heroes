package com.meshpy.rest;

import com.meshpy.entity.Hero;
import com.meshpy.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(allowedHeaders = "Access-Control-Allow-Headers")
@RestController
@RequestMapping("/api")
public class HeroRestController {

    @Autowired
    private HeroService heroService;

    @RequestMapping(method = RequestMethod.GET, path = "/heroes")
    // @GetMapping("/heroes")
    public List<Hero> getHeroes() {
        return heroService.getHeroes();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/heroes/details/{heroId}")
    public Hero getHero(@PathVariable int heroId) {
        Hero hero = heroService.getHero(heroId);

        if (hero == null) {
            System.out.println("Hero not found");
            return null;
        }

        return hero;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/heroes")
    public Hero addHero(@RequestBody Hero hero) {
        // pass and id in JSON, set id to 0
        // this is force a save of new item instead of update
        hero.setId(0);

        heroService.addHero(hero);
        return hero;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/heroes")
    public Hero updateHero(@RequestBody Hero hero) {
        heroService.addHero(hero);
        return hero;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/heroes/{heroId}")
    public String deleteHero(@PathVariable int heroId) {
        Hero hero = heroService.getHero(heroId);

        if (hero == null) {
            System.out.println("Hero not found - cannot delete");
            return null;
        }

        heroService.deleteHero(heroId);
        return "Deleted hero id - " + heroId;
    }
}
