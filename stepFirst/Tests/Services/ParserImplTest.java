package Services;

import com.siteMonitor.Model.Ad;
import com.siteMonitor.Services.ParserImpl;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by user on 08.05.2017.
 */
public class ParserImplTest {

    ParserImpl parser = new ParserImpl("https://www.avito.ru/chelyabinskaya_oblast/avtomobili?pmax=70000&pmin=1000");


    @Test
    public void parse() throws Exception {
        for (String s : parser.getPagesLinks()) {
            System.out.println(s);
        }
        ArrayList<Ad> result = parser.parse();
        for (Ad ad : result) {
            System.out.println(ad);
        }
    }
}