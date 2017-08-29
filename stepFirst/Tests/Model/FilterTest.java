package Model;

import com.siteMonitor.DAO.DAO;
import com.siteMonitor.DAO.DAOImpl;
import com.siteMonitor.Model.Filter;
import com.siteMonitor.Services.ParserImpl;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by user on 08.05.2017.
 */
public class FilterTest {
    @Test
    public void newEmptyFilter(){
        Filter f = new Filter("satka", "https://www.avito.ru/satka/avtomobili?pmax=70000&pmin=1000");
        System.out.println("Filter :" + f);
        assertTrue(f != null);
    }

    @Test
    public void newFilter(){
        Filter f = new Filter("satka", "https://www.avito.ru/satka/avtomobili?pmax=70000&pmin=1000");
        ParserImpl parser = new ParserImpl(f.getLink());
        f.setAds(parser.parse());
        System.out.println("Filter :" + f);
        assertTrue(f.getAds().size() > 0);
    }

    @Test
    public void gettingFilersFromDatabase(){
        DAO dao = new DAOImpl("D:\\learn\\portfolio\\database\\sqlite\\database.db");
        ArrayList<Filter> filters = dao.getAllFilters();
        System.out.println("Filters in database " + filters.size());
        assertTrue(filters.size() > 0);
    }
}