package com.siteMonitor.Services;


import com.siteMonitor.Model.Ad;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by user on 27.07.2016.
 */


public class ParserImpl implements IParser {

    private String[] pagesLinks;
    private Document doc;

    public ParserImpl(String link) {
        try {
            doc = Jsoup.connect(link).get();
            pagesLinks = parsePagesLinks(doc);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }


    public ArrayList<Ad> parse() {
        HashSet<Ad> result = new HashSet<>(); //этот лист содержит в себе новые объявления, нужен чтобы не нарушать порядок объявлений
        for (String s : pagesLinks) {
            try {
                //System.out.println("parsing " + s);
                Document doc = Jsoup.connect(s).get();

                Elements adsHtmlElements = doc.getElementsByClass("item_table");
                        /*Elements elementsBeforeAds = doc.getElementsByClass("js-catalog_before-ads").get(0).getElementsByClass("item_table_9");
                        Elements elementsAfterAds = doc.getElementsByClass("js-catalog_after-ads").get(0).getElementsByClass("item_table_9");

                        Elements adsHtmlElements = new Elements();
                        adsHtmlElements.addAll(elementsBeforeAds);
                        adsHtmlElements.addAll(elementsAfterAds);*/
                for (Element e : adsHtmlElements) {
                    result.add(parseAd(e));
                }

            } catch (IOException ex) {
                System.out.print(ex);
            }
        }
        return new ArrayList<Ad>(result);
    }

    private Ad parseAd(Element el) {
        int id = Integer.parseInt(el.attr("id").substring(1));
        String title = el.getElementsByClass("item-description-title").text().replace("'", ""),
                priceS = el.getElementsByClass("about").text(),
                link = "http://avito.ru" + el.getElementsByClass("item-description-title-link").attr("href"),
                description = el.getElementsByClass("param").text(),
                date = el.getElementsByClass("date").text();
        boolean isViewed = false;
        int price = 0;
        if (priceS.contains("руб."))
            price = Integer.parseInt(priceS.substring(0, priceS.lastIndexOf("руб.")).replaceAll(" ", ""));
        Ad ad = new Ad(id, title, price, link, description, date, isViewed);
        return ad;
    }

    private String[] parsePagesLinks(Document d) {
        List<String> result = new ArrayList<String>();
        result.add(doc.location());
        Elements linksElements = d.getElementsByClass("pagination-pages");
        for (Element el : linksElements.select("a[href]")) {
            result.add("https://www.avito.ru" + el.attr("href"));
        }
        if (result.size() > 1) result.remove(result.size() - 1);
        String[] res = new String[result.size()];
        result.toArray(res);
        return res;
    }

    public String[] getPagesLinks() {
        return pagesLinks;
    }

}
