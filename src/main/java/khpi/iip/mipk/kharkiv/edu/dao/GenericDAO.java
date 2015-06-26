package khpi.iip.mipk.kharkiv.edu.dao;

import khpi.iip.mipk.kharkiv.edu.dao.exceptions.CreateException;
import khpi.iip.mipk.kharkiv.edu.dao.exceptions.FinderException;
import khpi.iip.mipk.kharkiv.edu.dao.exceptions.RemoveException;
import khpi.iip.mipk.kharkiv.edu.dao.exceptions.UpdateException;

import java.util.Hashtable;
import java.util.List;

/**
 * @author: vzenkov
 */
public interface GenericDAO<T> {
    
    /**
     * Create new record in DB
     */
    public T create(T entity) throws CreateException;

    /**
     * Update existent record in DB
     */
    public T update(T entity) throws UpdateException;

    /**
     * Delete existent record from DB
     */
    public void remove(T entity) throws RemoveException;

    /**
     * Get record from DB by primary key
     */
    public T findByPrimaryKey(Long id);
    public T findByPrimaryKey(String id);

    /**
     * Execute Named Query, which return records from DB, with given parameters.
     *
     * @throws khpi.iip.mipk.kharkiv.edu.dao.exceptions.FinderException
     */
    public List<T> executeNamedQueryWithResult(String namedQuery, Object[] parameters) throws FinderException;

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
    public List<T> executeNamedQueryWithResult(String namedQuery, Object[] parameters, int first, int max) throws FinderException;

    /**
     * Execute Named Query, which return only one record from DB, with given parameters.
     *
     * @throws FinderException
     */
    public T executeNamedQueryWithOneResult(String namedQuery, Object[] parameters) throws FinderException;

    /**
     * Execute Named Query, which doesn't return result from DB, with given parameters.
     *
     * @return the number of entities updated or deleted
     * @throws Exception
     */
    public int executeNamedQueryWithNoResult(String namedQuery, Object[] parameters) throws Exception;

    /**
     * Delete records with given ids
     *
     * @param ids - ids in format: "(id1, id2, id3,..., idn)"
     * @return number or deleted rows
     * @throws RemoveException
     */
    public int removeByIds(String ids) throws RemoveException;

    /**
     * Find all records (limit by fist and max parameters)
     *
     * @param first - the start position of the first result, numbered from 0
     * @param max   - maximum number of results to retrieve
     * @return List<T>
     * @throws FinderException
     */
    public List<T> findAll(int first, int max, String orderBy, String orderDirection) throws FinderException;

    /**
     * Find all records by list of criteria
     *
     * @param criteria - list of criteria
     * @return List<T>
     * @throws FinderException
     */
    public List<T> getByCriteria(Hashtable<String, String> criteria, int first, int max, String orderBy, String orderDirection) throws FinderException;

    /**
     * Count all records
     *
     * @return count of records
     * @throws
     */
    public Long count();


    public T SQLQuery(String sql) throws FinderException;

    public List<T> SQLNativeQuery(String sql) throws FinderException;

    public List<T> SQL(String sql) throws FinderException;

}
