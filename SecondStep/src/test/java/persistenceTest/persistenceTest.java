package persistenceTest;

import com.siteMonitor.Model.Ad;
import com.siteMonitor.Model.Filter;
import com.siteMonitor.Services.ParserImpl;
import com.sun.corba.se.spi.orb.ParserImplBase;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by user on 22.05.2017.
 */
public class persistenceTest {
    Logger logger = Logger.getLogger("persistence logger");

    @Test
    public void createEntityFactoryAndManager(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("siteMonitor");
        logger.info("Entity manager factory is up: " + entityManagerFactory!=null);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        logger.info("Entity manager is up: " + entityManager !=null);
        Assert.assertEquals(true, (entityManagerFactory!=null && entityManager!=null ));
    }

    @Test
    public void writeFilter(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("siteMonitor");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Filter filter = new Filter("Златоуст автомобилиq","https://www.avito.ru/zlatoust/avtomobili");
        ParserImpl parser = new ParserImpl(filter.getLink());
        filter.setAds(parser.parse());

        for (Ad ad : filter.getAds()) {
            System.out.println(ad);
        }

        entityManager.getTransaction().begin();
        //Filter result = entityManager.merge(filter);
        entityManager.persist(filter);
        entityManager.getTransaction().commit();
        entityManager.close();

        System.out.println(filter);
        //Assert.assertEquals(true, (result.getId()!=0 && result.getId()>0 ));
    }

    @Test
    public void getFilters(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("siteMonitor");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        List<Filter> result = entityManager.createQuery("SELECT e FROM Filter e", Filter.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();

        for (Filter filter : result) {
            System.out.println(filter.toString());
            for (Ad ad : filter.getAds()) {
                System.out.println(ad);
            }
        }

        Assert.assertEquals(true, result.size()>0);
    }

    @Test
    public void removeFiltetrs(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("siteMonitor");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        List<Filter> result = entityManager.createQuery("SELECT e FROM Filter e", Filter.class).getResultList();
        entityManager.getTransaction().commit();


        entityManager.getTransaction().begin();
        for (Filter filter : result) {
            entityManager.remove(filter);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void getAndChange(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("siteMonitor");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        List<Filter> result = entityManager.createQuery("SELECT e FROM Filter e", Filter.class).getResultList();
        entityManager.getTransaction().commit();

        result.get(0).getAds().remove(0);
        result.get(0).getAds().remove(1);
        result.get(0).getAds().remove(2);
        result.get(0).getAds().remove(3);

        entityManager.getTransaction().begin();
        entityManager.persist(result.get(0));
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}