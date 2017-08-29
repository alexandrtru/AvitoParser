package com.siteMonitor.Model;

import com.siteMonitor.DAO.DAO;
import com.siteMonitor.DAO.DAOImpl;

import javax.persistence.*;
import java.util.*;

/**
 * Created by user on 03.01.2017.
 */

@Entity
@NamedQuery(name = "Filter.getAll", query = "SELECT f FROM Filter f")
@Table(name = "filters")
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "filter")
    private List<Ad> ads = new ArrayList<Ad>();

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    @Transient
    private int freshAdsCount = 0;
    @Transient
    private int lostAdsCount = 0;
    @Transient
    private static DAO dao = new DAOImpl();

    public Filter() {
    }

    public Filter(String name, String link) { //конструктор для нового фильтра
        this.name = name;
        this.link = link;
        this.ads = new ArrayList<>();
    }

    public Filter(int id, String name, String link, List<Ad> ads) { //Конструктор для получения фильтра из базы
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

    private void handleList(List<Ad> adsFromSite) {
        freshAdsCount = 0;
        lostAdsCount = 0;

        ArrayList<Ad> remainingAds = new ArrayList<>(ads);
        remainingAds.retainAll(adsFromSite);

        adsFromSite.removeAll(remainingAds);//fresh ads

        //FOR DEBUG
        freshAds.clear();
        freshAds = (ArrayList<Ad>)adsFromSite;

        ads.removeAll(remainingAds);//lost ads

       // daoFilter.deleteAds(ads);

        freshAdsCount = adsFromSite.size();
        lostAdsCount = ads.size();

        ads.clear();
        ads.addAll(adsFromSite);
        ads.addAll(remainingAds);
    } //обработчик списка объявлений

    public void setAds(List<Ad> ads) {
        if (this.ads.isEmpty()) {
            this.ads = ads;
            this.freshAdsCount = ads.size();
            this.lostAdsCount = 0;
        } else {
            handleList(ads);
        }
        save();
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

    public List<Ad> getAds() {
        return ads;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Transient
    private ArrayList<Ad> freshAds = new ArrayList<>();
    public ArrayList<Ad> getFreshAds(){
        return  freshAds;
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

    public void save(){
        dao.saveFilter(this);
    }

    public void deleteFromDataBase(){
        dao.deleteFilter(this);
    }

    public static List<Filter> getAllFilters(){
        return dao.getAllFilters();
    }
}
