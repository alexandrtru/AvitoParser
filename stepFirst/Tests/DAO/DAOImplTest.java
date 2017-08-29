package DAO;

import com.siteMonitor.DAO.*;
import com.siteMonitor.Model.Filter;
import com.siteMonitor.Services.ParserImpl;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by user on 09.05.2017.
 */
public class DAOImplTest {

    private DAO dao = new DAOImpl("D:\\learn\\portfolio\\database\\sqlite\\database.db");

    @Test
    public void getAllFilters() throws Exception {
        ArrayList<Filter> filters = dao.getAllFilters();

        for (Filter filter : filters) {
            System.out.println(filter);

            /*System.out.println("*********************************");
            for (Ad ad : filter.getAds()) {
                System.out.println(ad);
            }*/
        }
    }

    @Test
    public void writeEmptyFilter() {
        Filter f = new Filter("kopeysk", "https://www.avito.ru/kopeysk/avtomobili?pmax=100000&pmin=1000");
        assertTrue(dao.saveFilter(f) && f.getId() != 0);
    }

    @Test
    public void writeFiltersWithAds() throws Exception {
        Filter f = new Filter("kopeysk", "https://www.avito.ru/kopeysk/avtomobili?pmax=100000&pmin=1000");
        ParserImpl parser = new ParserImpl(f.getLink());
        f.setAds(parser.parse());
        assertTrue(dao.saveFilter(f) && f.getId() != 0);
    }

    @Test
    public void deleteFilter(){
        ArrayList<Filter> filters = dao.getAllFilters();

        for (Filter filter : filters) {
            System.out.println(dao.deleteFilter(filter));
        }
    }

    @Test
    public void deleteAd(){
        ArrayList<Filter> filters = dao.getAllFilters();
        assertEquals(true, dao.deleteAdById(filters.get(0).getAds().get(0)));
    }

    @Test
    public void deleteListAds(){
        ArrayList<Filter> filters = dao.getAllFilters();
        System.out.println(filters.get(1).getAds().size());
        assertEquals(true, dao.deleteAds(filters.get(1).getAds()));
    }

    @Test
    public void setAdViewed(){
        ArrayList<Filter> filters = dao.getAllFilters();
        assertEquals(true, dao.setAdAsViewed(filters.get(0).getAds().get(0), filters.get(0).getId()));
    }

    @Test
    public void setAdsViewed(){
        ArrayList<Filter> filters = dao.getAllFilters();
        assertEquals(true, dao.setAdsAsViewed(filters.get(0).getAds(), filters.get(0).getId()));
    }

    @Test
    public void setAdNotViewed(){
        ArrayList<Filter> filters = dao.getAllFilters();
        assertEquals(true, dao.setAdAsNotViewed(filters.get(0).getAds().get(0), filters.get(0).getId()));
    }

    @Test
    public void setAdsNotViewed(){
        ArrayList<Filter> filters = dao.getAllFilters();
        assertEquals(true, dao.setAdsAsNotViewed(filters.get(0).getAds(), filters.get(0).getId()));
    }

    @Test
    public void setAdsViewedByFilter(){
        ArrayList<Filter> filters = dao.getAllFilters();
        assertEquals(true, dao.setAdsAsViewedByFilter(filters.get(0)));
    }

    @Test
    public void setAdsNotViewedByFilter(){
        ArrayList<Filter> filters = dao.getAllFilters();
        assertEquals(true, dao.setAdsAsNotViewedByFilter(filters.get(0)));
    }

}
