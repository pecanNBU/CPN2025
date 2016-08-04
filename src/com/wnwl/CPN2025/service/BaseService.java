package com.wnwl.CPN2025.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.wnwl.CPN2025.util.PageBean;

/**
 *
 * Simple to Introduction
 * @ProjectName:    建设工程颗粒物与噪声在线监测系统
 * @Package:        com.wnwl.CPN2025.service
 * @ClassName:      数据库操作接口
 * @Description:    增删改查
 * @Author:         Jenny
 * @CreateDate:     2016.7.18
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version:        V1.0
 *
 */
public interface BaseService <T, ID extends Serializable>{	
	
	/**
	 *@Title: save
	 *@Description: save entity
	 *@param entity: a object of T
	 *@return ID:primary key of entity
	 *@throws
	 */
	public ID save(T entity); 
	/**
	 *@Title: save
	 *@Description: save entity with
	 *@param hsql:sql statement for save
	 *@return hsql: a insert sql statement
	 *@throws
	 */
	public void save(final String sql);
	/**
	 *@Title: saveBatch
	 *@Description: batch save entity with insert sql
	 *@param arg0: first arg in sql
	 *@param strs: second args in sql which in a  String array
	 *@param sql: insert sql for batch save
	 *@throws
	 */
	public void saveBatch(final int arg0, final String[] strs, final String sql) ;
	/**
	 *@Title: saveBatch
	 *@Description: batch save entity with insert sql
	 *@param arg0:first arg in sql
	 *@param strs: third args in sql which in a  String array
	 *@param sql: insert sql for batch save
	 *@return arg1:second arg in sql
	 *@throws
	 */
	public void saveBatch(final int arg0, final int arg1, final String sql, final String strs[]);
	/**
	 *@Title: saveBatch
	 *@Description: batch save entity with insert sql
	 *@param arg0:first arg in sql
	 *@param ids: primary keys' in a list
	 *@param sql: insert sql for batch save
	 *@throws
	 */
	public void saveBatch(final int arg0, final List ids, final String sql);
	
	/**
	 *@Title: saveOrUpdate
	 *@Description: save entity if entity not exists or update entity
	 *@param entity: a object of T
	 *@return void
	 *@throws
	 */
	public void saveOrUpdate(T entity); 
	/**
	 *@Title: updateBatch
	 *@Description: update with a sql
	 *@param entity
	 *@return sql: update information for upate
	 *@throws
	 */
	public void updateBatch(final String sql);
	
	public void updateBatch(List<String> hsql);
	
	public void entityBatchs(List<Object> entitys);
	
	public void remove(T entity);
	/**
	 *@Title: removeAll
	 *@Description: bulk delete entities which belong to a collection
	 *@param entities: a entity set
	 *@return void
	 *@throws
	 */
	public void removeAll(Collection<T> entities);
	/**
	 *@Title: remove
	 *@Description: delete entity with id 
	 *@param  id: primary key
	 *@return void
	 *@throws
	 */
	public void remove(int id);
	/**
	 *@Title: removes
	 *@Description: bulk delete entities with id is in ts
	 *@param  ts: primary keys' array
	 *@return void
	 *@throws
	 */
	public void removes(String[] ts);
	/**
	 *@Title: removes
	 *@Description: delete a or some record(s) with sql
	 *@param sql:delete sql
	 *@return void
	 *@throws
	 */
	public void removes(String sql);	
	
	/**
	 *@Title: TDSDao
	 *@Description: find a entity by key
	 *@param id
	 *@return T
	 *@throws
	 */
	public T findById(Integer addr);  
	/**
	 *@Title: TDSDao
	 *@Description: find a entity by HQL
	 *@param hql
	 *@return T
	 *@throws
	 */
	public T findFirstObject(String hql, String sql);
	public T findFirstObject(String hql); 
	/**
	 *@Title: findSelfObjects
	 *@Description: find list self entities by HQL which use self dao
	 *@param cond: where condition for hql
	 *@return List<T>
	 *@throws
	 */
	public List<T> findSelfObjects(String cond);
	/**
	 *@Title: findAnyObjects
	 *@Description: find list entities by HQL which use any dao
	 *@param hql
	 *@return List<T>
	 *@throws
	 */
	public List<T> findAnyObjects(String hql);
	/**
	 *@Title: findUnknowCombine
	 *@Description: find any properties combine
	 *@param hql
	 *@return List
	 *@throws
	 */
	@SuppressWarnings("rawtypes")
	public List findUnknowCombine(String hql);
	public List findSql(String sql);
	/**
	 *@Title: getTotalCount
	 *@Description: get total count with parameters for calculating page number 
	 *@param p
	 *@param str
	 *@param ob2
	 *@return Long
	 *@throws Exception
	 */
	public Long getTotalCount(PageBean p, String str[], Object ob2[])  
	          throws Exception;  
	/**
	 *@Title: getTotalCount
	 *@Description: get total count with no parameters for calculating page number 
	 *@param page
	 *@return Long
	 *@throws Exception
	 */
	public Long getTotalCount();
	public Long getCountbyCond(String cond);
	public Long getCountbyHql(String hql);
	public Long getCountbySql(String sql);
	/**
	 *@Title: findAll
	 *@Description: find all self objects
	 *@return List<T>
	 *@throws
	 */
	public List<T> findAll();      
	/**
	 *@Title: getList
	 *@Description: find list pagebeans with no parameters which have entities info
	 *@param page
	 *@return List
	 *@throws
	 */
	public List<T> getList(final int page, final int rows);
	/**
	 *@Title: getList
	 *@Description: find list T with no parameters which have entities info
	 *@param page
	 *@return List
	 *@throws
	 */
	public List<T> getList(final int page, final int rows, final String cond);
	public List<T> getListByHql(final int page, final int rows, final String hql);
	public List getListBySql(final int page, final int rows, final String sql);
	/**
	 *@Title: getList
	 *@Description: find list pagebeans with no parameters which have entities info
	 *@param page
	 *@return List
	 *@throws
	 */
	@SuppressWarnings("rawtypes")
	public List<T> getList(PageBean page);
	/**
	 *@Title: getList
	 *@Description: find list pagebeans with no parameters which have entities info
	 *@param page
	 *@return List
	 *@throws
	 */
	public List<T> getList(final PageBean p, final String cond);
	/**
	 *@Title: getList
	 *@Description: find list pagebeans with no parameters which have entities info
	 *@param page
	 *@return List
	 *@throws
	 */
	public List<T> getAnyList(final PageBean p, final String hsql);
	/**
	 *@Title: getList
	 *@Description: find list pagebeans with  parameters which have entities info
	 *@param page
	 *@param str
	 *@param ob2
	 *@return List
	 *@throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<T> getList(PageBean page, String str[], Object ob2[]) throws Exception;
	/**
	 *@Title: TDSDao
	 *@Description: save entity with
	 *@param entity
	 *@return ID
	 *@throws
	 */
	public List<T> getByQuery(final String sql);
	/**
	 *@Title: getEntityId
	 *@Description: pages show style
	 *@param page
	 *@return Integer
	 *@throws
	 */
	public Integer getEntityId(String hql);
	public Long getEntityIdL(String hql);
	
	public long insertEntityBySql(String sql);
	public int removeWLH(String sql);
	/**
	 *@Title: TDSService
	 *@Description: get basic info of pages 
	 *@param jumpPage
	 *@param pageSize
	 *@param  purl
	 *@return Page
	 * @throws Exception
	 */
	public List findSqlByPage(Integer pageSize, Integer jumpSize, String sql);
	public int updateBySql(String sql);
	public byte findNodeStatebyUrl(String actorId, String url);
	public T findLastObject(); 		//获取最后条数据
	public Integer findLastId(); 	//获取最后条数据的id
	public List<T> findAllbyDep(Integer depId);	//获取该单位的所有数据
	public List<T> castQueryBySql(final String sql, final T t);
}
