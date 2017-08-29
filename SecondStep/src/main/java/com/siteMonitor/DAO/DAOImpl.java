package com.siteMonitor.DAO;

import com.siteMonitor.Exceptions.FilterCreatingException;
import com.siteMonitor.Model.Filter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 24.05.2017.
 */
public class DAOImpl implements DAO {
    private  static final EntityManagerFactory managerFactory;
    private EntityManager entityManager;

    static {
        managerFactory = Persistence.createEntityManagerFactory("siteMonitor");
    }

    public DAOImpl() {
        this.entityManager = managerFactory.createEntityManager();
    }

    @Override
    public List<Filter> getAllFilters() {
        entityManager.getTransaction().begin();
        List<Filter> resultList = entityManager.createNamedQuery("Filter.getAll", Filter.class).getResultList();
        entityManager.getTransaction().commit();
        //entityManager.close();
        return resultList;
    }

    @Override
    public void saveFilter(Filter filter) {
        entityManager.getTransaction().begin();
        entityManager.persist(filter);
        entityManager.getTransaction().commit();
        //entityManager.close();
    }

    @Override
    public void deleteFilter(Filter filter) {
        entityManager.getTransaction().begin();
        entityManager.remove(filter);
        entityManager.getTransaction().commit();
        //entityManager.close();
    }
}
