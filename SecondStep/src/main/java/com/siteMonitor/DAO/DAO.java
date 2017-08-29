package com.siteMonitor.DAO;

import com.siteMonitor.Exceptions.FilterCreatingException;
import com.siteMonitor.Model.Ad;
import com.siteMonitor.Model.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 08.05.2017.
 */
public interface DAO {
    //R
    List<Filter> getAllFilters();

    //CU
    void saveFilter(Filter filter);

    //D
    void deleteFilter(Filter filter);
}
