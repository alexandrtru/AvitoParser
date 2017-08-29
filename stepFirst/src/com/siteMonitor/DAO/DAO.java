package com.siteMonitor.DAO;

import com.siteMonitor.Exceptions.FilterCreatingException;
import com.siteMonitor.Model.Ad;
import com.siteMonitor.Model.Filter;

import java.util.ArrayList;

/**
 * Created by user on 08.05.2017.
 */
public interface DAO {
    //C
    Filter createFilter(String name, String link) throws FilterCreatingException;
    //R
    ArrayList<Filter> getAllFilters();
    ArrayList<Ad> getAdsByFilter(int filterId);

    //U
    boolean saveFilter(Filter filter);
    boolean saveAdsFromFilter(Filter filter);

    boolean setAdAsViewed(Ad ad, int filterId);
    boolean setAdsAsViewed(ArrayList<Ad> ads, int filterId);
    boolean setAdsAsViewedByFilter(Filter filter);

    boolean setAdAsNotViewed(Ad ad, int filterId);
    boolean setAdsAsNotViewed(ArrayList<Ad> ads, int filterId);
    boolean setAdsAsNotViewedByFilter(Filter filter);
    //D
    boolean deleteFilter(Filter filter);
    boolean deleteAdById(Ad ad);
    boolean deleteAds(ArrayList<Ad> ads);
    boolean deleteAdsByFilter(Filter filter);
}
