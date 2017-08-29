import com.siteMonitor.DAO.DAO;
import com.siteMonitor.DAO.DAOImpl;
import com.siteMonitor.Model.Filter;
import com.siteMonitor.Services.ParserImpl;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by user on 12.05.2017.
 */
public class FillingDataBase {
    private Filter filters[] = new Filter[5];
    private DAO dao = new DAOImpl("D:\\learn\\portfolio\\database\\sqlite\\database.db");

    @Test
    public void fillMany() {
        filters[0] = Filter.createFilter("chelyabinsk", "https://www.avito.ru/chelyabinsk/avtomobili?pmax=70000&pmin=1000");
        filters[1] = Filter.createFilter("zlatoust", "https://www.avito.ru/zlatoust/avtomobili?pmax=70000&pmin=1000");
        filters[2] = Filter.createFilter("kopeysk", "https://www.avito.ru/kopeysk/avtomobili?pmax=70000&pmin=1000");
        filters[3] = Filter.createFilter("korkino", "https://www.avito.ru/korkino/avtomobili?pmax=70000&pmin=1000");
        filters[4] = Filter.createFilter("miass", "https://www.avito.ru/miass/avtomobili?pmax=70000&pmin=1000");

        ArrayList<ParserImpl> parsers = new ArrayList<>(5);
        for (Filter filter : filters) {
            ParserImpl p = new ParserImpl(filter.getLink());
            filter.setAds(p.parse());
            parsers.add(p);
            System.out.println(filter);
        }
    }

    @Test
    public void fillOnce() {
        Filter filter = Filter.createFilter("chel_rabota", "https://www.avito.ru/chelyabinsk/vakansii?pmax=35000&pmin=20000");
        ParserImpl p = new ParserImpl(filter.getLink());
        filter.setAds(p.parse());
        dao.saveAdsFromFilter(filter);
        System.out.println(filter);
    }


}
