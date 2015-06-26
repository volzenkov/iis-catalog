package khpi.iip.mipk.kharkiv.edu.dao.impl;

import khpi.iip.mipk.kharkiv.edu.dao.GenericDAO;
import khpi.iip.mipk.kharkiv.edu.dao.exceptions.CreateException;
import khpi.iip.mipk.kharkiv.edu.dao.exceptions.FinderException;
import khpi.iip.mipk.kharkiv.edu.dao.exceptions.RemoveException;
import khpi.iip.mipk.kharkiv.edu.dao.exceptions.UpdateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author: vzenkov
 */
public abstract class GenericDAOImpl<T extends Serializable> implements GenericDAO<T> {

    @Autowired
    private SessionFactory sessionFactory;
    /**
     * T class, for which current manager was created
     */
    Class<T> clazz = null;

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> entityClass) {
        this.clazz = entityClass;
    }

    /**
     * Create new record in DB
     */
    public T create(T entity) throws CreateException {

        try {
            sessionFactory.getCurrentSession().persist(entity);
            sessionFactory.getCurrentSession().flush();
        } catch (Exception e) {
            throw new CreateException(e);
        }

        return entity;

    }

    /**
     * Update existent record in DB
     */
    public T update(T entity) throws UpdateException {

        try {
            entity = (T) sessionFactory.getCurrentSession().merge(entity);
            sessionFactory.getCurrentSession().flush();
        } catch (Exception e) {
            throw new UpdateException(e);
        }

        return entity;
    }

    /**
     * Delete existent record from DB
     */
    public void remove(T entity) throws RemoveException {

        try {
            sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().merge(entity));
            sessionFactory.getCurrentSession().flush();
        } catch (Exception e) {
            throw new RemoveException(e);
        }
    }

    /**
     * Get record from DB by primary key
     */
    public T findByPrimaryKey(Long id) {

        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }

    public T findByPrimaryKey(String id) {

        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }

    /**
     * Execute Named Query, which return records from DB, with given parameters.
     *
     * @throws FinderException
     */
    public List<T> executeNamedQueryWithResult(String namedQuery, Object[] parameters) throws FinderException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(namedQuery);

            System.out.println("executeNamedQueryWithResult: " + namedQuery);

            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);

                    System.out.println("parameters: " + parameters[i]);
                }
            }

            return (List<T>) query.list();
        } catch (Exception e) {
            throw new FinderException(e);
        }
    }


    /**
     * Execute Named Query, which return records from DB limits by first and max, with given parameters.
     *
     * @param namedQuery
     * @param parameters
     * @param first
     * @param max
     * @return List<T>
     * @throws FinderException
     */
    public List<T> executeNamedQueryWithResult(String namedQuery, Object[] parameters, int first, int max) throws FinderException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(namedQuery);

            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }

            query.setFirstResult(first);
            query.setMaxResults(max);

            return (List<T>) query.list();
        } catch (Exception e) {
            throw new FinderException(e);
        }
    }

    /**
     * Execute Named Query, which return only one record from DB, with given parameters.
     *
     * @throws FinderException
     */
    public T executeNamedQueryWithOneResult(String namedQuery, Object[] parameters) throws FinderException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(namedQuery);

            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }

            List<T> list = query.list();

            if (list.size() != 0)
                return list.get(0);
            else
                return null;
        } catch (Exception e) {
            throw new FinderException(e);
        }
    }

    /**
     * Execute Named Query, which doesn't return result from DB, with given parameters.
     *
     * @return the number of entities updated or deleted
     * @throws Exception
     */
    public int executeNamedQueryWithNoResult(String namedQuery, Object[] parameters) throws Exception {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(namedQuery);

            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i + 1, parameters[i]);
                }
            }

            int result = query.executeUpdate();
            sessionFactory.getCurrentSession().flush();
            return result;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Delete records with given ids
     *
     * @param ids - ids in format: "(id1, id2, id3,..., idn)"
     * @return number or deleted rows
     * @throws RemoveException
     */
//        @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int removeByIds(String ids) throws RemoveException {

        try {
            Query query = sessionFactory.getCurrentSession().createQuery("DELETE " + clazz.getSimpleName() + " WHERE id IN (" + ids + ")");

            return query.executeUpdate();
        } catch (Exception e) {
            throw new RemoveException(e);
        }
    }

    /**
     * Find all records (limit by fist and max parameters)
     *
     * @param first - the start position of the first result, numbered from 0
     * @param max   - maximum number of results to retrieve
     * @return List<T>
     * @throws FinderException
     */
//        @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<T> findAll(int first, int max, String orderBy, String orderDirection) throws FinderException {
        try {

            String sql = "SELECT table FROM " + clazz.getSimpleName() + " table";

            if (orderBy != null) {
                sql += " ORDER BY " + orderBy;
                if (orderDirection != null) {
                    sql += " " + orderDirection;
                }
            }
            System.out.println("findAll: " + sql);

            Query query = sessionFactory.getCurrentSession().createQuery(sql);

            if (first > 0)
                query.setFirstResult(first);

            if (max > 0)
                query.setMaxResults(max);

            return (List<T>) query.list();

        } catch (Exception e) {
            throw new FinderException(e);
        }
    }

    /**
     * Find all records by list of criteria
     *
     * @param criteria - list of criteria
     * @return List<T>
     * @throws FinderException
     */
//        @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<T> getByCriteria(Hashtable<String, String> criteria, int first, int max, String orderBy, String orderDirection) throws FinderException {

        Set<String> set = criteria.keySet();
        String key;
        String value;

        Iterator<String> it = set.iterator();

        String addition = "";

        while (it.hasNext()) {

            key = it.next();
            value = criteria.get(key);

            addition += key;

            if ((value.contains("?")) || (value.contains("%"))) {
                addition += " LIKE ";
            } else {
                addition += " = ";
            }

            addition += "'" + value + "'";

            if (it.hasNext()) {
                addition += " AND ";
            }
        }

        try {

            String sql = "SELECT table FROM " + clazz.getSimpleName() + " table WHERE " + addition;

            if (orderBy != null) {
                sql += " ORDER BY " + orderBy;
                if (orderDirection != null) {
                    sql += " " + orderDirection;
                }
            }
            System.out.println("getByCriteria: " + sql);

            Query query = sessionFactory.getCurrentSession().createQuery(sql);

            if (first > 0)
                query.setFirstResult(first);

            if (max > 0)
                query.setMaxResults(max);

            return query.list();
        } catch (Exception e) {
            throw new FinderException(e);
        }

    }

    /**
     * Count all records
     *
     * @return count of records
     * @throws
     */
//        @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long count() {

        String sql = "SELECT COUNT(table) FROM " + clazz.getSimpleName() + " table";

        System.out.println(sql);

        Query query = sessionFactory.getCurrentSession().createQuery(sql);

        return ((Long) query.uniqueResult()).longValue();
    }


//        @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public T SQLQuery(String sql) throws FinderException {

        try {
            Query query = sessionFactory.getCurrentSession().createQuery(sql);
            List<T> list = query.list();
            if (list.size() != 0)
                return list.get(0);
            return null;
        } catch (Exception e) {
            throw new FinderException(e);
        }

    }

//        @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<T> SQLNativeQuery(String sql) throws FinderException {

        try {
            Query query = sessionFactory.getCurrentSession().createQuery(sql);
            return (List<T>) query.list();

        } catch (Exception e) {
            throw new FinderException(e);
        }

    }

//        @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<T> SQL(String sql) throws FinderException {
        try {

//			String sql = "SELECT table FROM " + clazz.getSimpleName() + " table";

            Query query = sessionFactory.getCurrentSession().createQuery(sql);
            List<T> list = query.list();

            return list;

        } catch (Exception e) {
            throw new FinderException(e);
        }
    }


}

