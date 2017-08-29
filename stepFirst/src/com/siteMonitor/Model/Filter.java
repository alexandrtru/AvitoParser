package com.siteMonitor.Model;

import com.siteMonitor.DAO.DAO;
import com.siteMonitor.DAO.DAOImpl;
import com.siteMonitor.Exceptions.FilterCreatingException;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by user on 03.01.2017.
 */
public class Filter {
    private int id;
    private ArrayList<Ad> ads;
    private String name;
    private String link;
    private int freshAdsCount = 0;
    private int lostAdsCount = 0;
    private static DAO dao = new DAOImpl("D:\\learn\\portfolio\\database\\sqlite\\database.db");

    public static Filter createFilter(String name, String link) {
        try {
            return dao.createFilter(name, link);
        } catch (FilterCreatingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Filter> getAllFilters(){
        return dao.getAllFilters();
    }

    public Filter(int id, String name, String link, ArrayList<Ad> ads) { //Конструктор для получения фильтра из базы
        this.id = id;
        this.name = name;
        this.link = link;
        this.ads = ads;

        countFresh();
    }

    private void countFresh() {
        for (Ad ad : this.ads) {
            if (!ad.isViewed()) freshAdsCount++;
        }
    }

    public Filter(String name, String link) { //конструктор для нового фильтра
        this.name = name;
        this.link = link;
        this.ads = new ArrayList<Ad>();
    }

    private void handleList(ArrayList<Ad> adsFromSite) {
        freshAdsCount = 0;
        lostAdsCount = 0;

        ArrayList<Ad> remainingAds = new ArrayList<>(ads);
        remainingAds.retainAll(adsFromSite);

        adsFromSite.removeAll(remainingAds);//fresh ads

        //FOR DEBUG
        freshAds.clear();
        freshAds = adsFromSite;

        ads.removeAll(remainingAds);//lost ads

        dao.deleteAds(ads);

        freshAdsCount = adsFromSite.size();
        lostAdsCount = ads.size();

        ads.clear();
        ads.addAll(adsFromSite);
        ads.addAll(remainingAds);
    } //обработчик списка объявлений

    public void setAds(ArrayList<Ad> ads) {
        if (this.ads.isEmpty()) {
            this.ads = ads;
            this.freshAdsCount = ads.size();
            this.lostAdsCount = 0;
        } else {
            handleList(ads);
        }
        dao.saveAdsFromFilter(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public ArrayList<Ad> getAds() {
        return ads;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", adsCount=" + ads.size() +
                ", freshAdsCount=" + freshAdsCount +
                ", lostAdsCount=" + lostAdsCount +
                '}';
    }

    private ArrayList<Ad> freshAds = new ArrayList<>();
    public ArrayList<Ad> getFreshAds(){
        return  freshAds;
    }
}
