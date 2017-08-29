package com.siteMonitor.Controllers;

import com.siteMonitor.Model.Ad;
import com.siteMonitor.Model.Filter;
import com.siteMonitor.Services.IParser;
import com.siteMonitor.Services.ParserImpl;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by user on 15.05.2017.
 */
public class Controller {
    private ArrayList<Filter> filters;
    private final int sleepTime = 50_000;
    private List<IParser> parsers;

    public Controller (){
        filters = Filter.getAllFilters();
        parsers = new ArrayList<>(filters.size());
        for (Filter filter : filters) {
            System.out.println("Get filter - " + filter);
            parsers.add(new ParserImpl(filter.getLink()));
        }
        doWork();
    }

    private void doWork() {
        try{
            while (true){
                System.out.println("App wake up!!!!!");
                for (Filter filter : filters) {
                    System.out.println("\n \n \t \t***************************************");
                    System.out.println("Filter " + filter.getName() + " start work");
                    filter.setAds(parsers.get(filters.indexOf(filter)).parse());
                    System.out.println("Filter " + filter.getName() + " refreshed \n INFO: " + filter + " \nFresh ads:" );
                    for (Ad ad : filter.getFreshAds()) {
                        System.out.println(ad);
                    }
                    System.out.println("\t \t***************************************");
                }
                System.out.println("App goes to sleep....");
                sleep(sleepTime);
            }
        } catch (InterruptedException ex){
            System.out.println("Working error!!! " + ex.getMessage());
        }


    }

    public void newFilter(String name, String link) {
    }

    public void deleteFilter(Filter filter) {
    }

    public void saveFilter(Filter filter) {
    }

    public void setAsViewed(Ad ad) {
    }

    public void setAsNotViewed(Ad ad) {
    }

    public void setAsViewed(ArrayList<Ad> ad) {
    }

    public void setAsNotViewed(ArrayList<Ad> ad) {
    }

}


