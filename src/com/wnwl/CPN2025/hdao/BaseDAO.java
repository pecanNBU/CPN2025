package com.wnwl.CPN2025.hdao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.wnwl.CPN2025.util.PageBean;
/**
 * 
 * @ClassName: BaseDao
 * @Description: interface of all required methods for other dao interfaces
 * @author: 198910310@qq.com
 * @date 2011-7-9上午8:07:32
 *
 * @param <T>: generalized entity class
 * @param <ID>: data type of primary key
 */
public interface BaseDAO<T, ID extends Serializable> {
	/**
	 * @param HQL the HQL to set
	 */
	void setHql(String hql);  
	/**  
	 * @return HQL
	 */  
	String getHql();  	       
	/**
	 *@Title: save
	 *@Description: save entity
	 *@param entity: a object of T
	 *@return ID:primary key of entity
	 *@throws
	 */
	ID save(T entity); 
	/**
	 *@Title: save
	 *@Description: save entity with
	 *@param hsql:sql statement for save
	 *@return hsql: a insert sql statement
	 *@throws
	 */
	void save(final String sql);
	/**
	 *@Title: saveBatch
	 *@Description: batch save entity with insert sql
	 *@param arg0: first arg in sql
	 *@param strs: second args in sql which in a  String array
	 *@param sql: insert sql for batch save
	 *@throws
	 */
	void saveBatch(final int arg0, final String[] strs, final String sql) ;
	/**
	 *@Title: saveBatch
	 *@Description: batch save entity with insert sql
	 *@param arg0:first arg in sql
	 *@param strs: third args in sql which in a  String array
	 *@param sql: insert sql for batch save
	 *@return arg1:second arg in sql
	 *@throws
	 */
	void saveBatch(final int arg0, final int arg1, final String sql, final String strs[]);
	/**
	 *@Title: saveBatch
	 *@Description: batch save entity with insert sql
	 *@param arg0:first arg in sql
	 *@param ids: primary keys' in a list
	 *@param sql: insert sql for batch save
	 *@throws
	 */
	void saveBatch(final int arg0, final List ids, final String sql);
	void saveBatch(final int arg0, final Timestamp stdt, final List ids, final String sql);
	void saveBatch(final String sql, final float strs[], final List<Integer> list, final int start, final int len);
	void saveBatch(final int arg11, final String query, final String sql, final boolean strs[], final int start, int len);
	void saveBatch(final int arg1, final String query, final String sql, final short strs[], final int start, final int len);
	/**
	 *@Title: saveOrUpdate
	 *@Description: save entity if entity not exists or update entity
	 *@param entity: a object of T
	 *@return void
	 *@throws
	 */
	void saveOrUpdate(T entity); 
	/**
	 *@Title: updateBatch
	 *@Description: update with a sql
	 *@param entity
	 *@return sql: update information for upate
	 *@throws
	 */
	void updateBatch(final String sql);
	
	void updateBatch(final List<String> sqls);
	
	void entityBatchs(final List<Object> entitys);
	
	void remove(T entity);
	/**
	 *@Title: removeAll
	 *@Description: bulk delete entities which belong to a collection
	 *@param entities: a entity set
	 *@return void
	 *@throws
	 */
	void removeAll(Collection<T> entities);
	/**
	 *@Title: remove
	 *@Description: delete entity with id 
	 *@param  id: primary key
	 *@return void
	 *@throws
	 */
	void remove(int id);
	/**
	 *@Title: removes
	 *@Description: bulk delete entities with id is in ts
	 *@param  ts: primary keys' array
	 *@return void
	 *@throws
	 */
	void removes(String[] ts);
	/**
	 *@Title: removesWLHArr
	 *@Description: bulk delete entities with id is in ts
	 *@param  ts: primary keys' array
	 *@return int
	 *@throws
	 */
	int removesWLHArr(String ts, Class cla);
	/**
	 *@Title: removes
	 *@Description: delete a or some record(s) with sql
	 *@param sql:delete sql
	 *@return void
	 *@throws
	 */
	void removes(String sql);	
	/**
	 *@Title: removesWLH
	 *@Description: delete a or some record(s) with sql
	 *@param sql:delete sql
	 *@return void
	 *@throws
	 */
	int removeWLH(String sql);
	/**
	 *@Title: removeswlh
	 *@Description: delete a or some record(s) with sql
	 *@param sql:delete sql
	 *@return int
	 *@throws
	 */
	int removeswlh(String sql);
	//update通过sql语句
	int updateBySql(String sql);
	
	/**
	 *@Title: TDSDao
	 *@Description: find a entity by key
	 *@param id
	 *@return T
	 *@throws
	 */
	T findById(Integer id);  
	/**
	 *@Title: TDSDao
	 *@Description: find a entity by HQL
	 *@param hql
	 *@return T
	 *@throws
	 */
	T findFirstObject(String hql);
	T findFirstObject(String hql, String sql);
	/**
	 *@Title: findSelfObjects
	 *@Description: find list self entities by HQL which use self dao
	 *@param cond: where condition for hql
	 *@return List<T>
	 *@throws
	 */
	List<T> findSelfObjects(String cond);
	/**
	 *@Title: findAnyObjects
	 *@Description: find list entities by HQL which use any dao
	 *@param hql
	 *@return List<T>
	 *@throws
	 */
	List<T> findAnyObjects(String hql);
	/**
	 *@Title: findUnknowCombine
	 *@Description: find any properties combine
	 *@param hql
	 *@return List
	 *@throws
	 */
	@SuppressWarnings("rawtypes")
	List findUnknowCombine(String hql);
	List findSql(String sql);
	/**
	 *@Title: finSqlByPage
	 *@Description: find records's num with hql
	 *@param hql
	 *@return List
	 *@throws
	 */
	List findSqlByPage(Integer pageSize, Integer jump, String sql);
	/**
	 *@Title: getTotalCount
	 *@Description: get total count with parameters for calculating page number 
	 *@param p
	 *@param str
	 *@param ob2
	 *@return Long
	 *@throws Exception
	 */
	Long getTotalCount(PageBean p, String str[], Object ob2[])  
	          throws Exception;  
	/**
	 *@Title: getTotalCount
	 *@Description: get total count with no parameters for calculating page number 
	 *@param page
	 *@return Long
	 *@throws Exception
	 */
	Long getTotalCount();
	Long getCountbyCond(String cond);
	Long getCountbyHql(String hql);
	Long getCountbySql(String sql);
	/**
	 *@Title: findAll
	 *@Description: find all self objects
	 *@return List<T>
	 *@throws
	 */
	List<T> findAll();      
	/**
	 *@Title: getList
	 *@Description: find list pagebeans with no parameters which have entities info
	 *@param page
	 *@return List
	 *@throws
	 */
	List<T> getList(final int page, final int rows);
	/**
	 *@Title: getList
	 *@Description: find list T with no parameters which have entities info
	 *@param page
	 *@return List
	 *@throws
	 */
	List<T> getList(final int page, final int rows, final String cond);
	List<T> getListByHql(final int i, final int j, final String hql);
	/**
	 *@Title: getList
	 *@Description: find list pagebeans with no parameters which have entities info
	 *@param page
	 *@return List
	 *@throws
	 */
	@SuppressWarnings("rawtypes")
	List<T> getList(PageBean page);
	/**
	 *@Title: getList
	 *@Description: find list pagebeans with no parameters which have entities info
	 *@param page
	 *@return List
	 *@throws
	 */
	List<T> getList(final PageBean p, final String cond);
	/**
	 *@Title: getList
	 *@Description: find list pagebeans with no parameters which have entities info
	 *@param page
	 *@return List
	 *@throws
	 */
	List<T> getAnyList(final PageBean p, final String hsql);
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
	List<T> getList(PageBean page, String str[], Object ob2[]) throws Exception;
	List<T> getBySqlQuery(final String sql);
	/**
	 *@Title: TDSDao
	 *@Description: save entity with
	 *@param entity
	 *@return ID
	 *@throws
	 */
	List<T> getByQuery(final String sql);
	/**
	 *@Title: getEntityId
	 *@Description: pages show style
	 *@param page
	 *@return int
	 *@throws
	 */
	//int getEntityByCriteria(Class cla,String sql);
	Integer getEntityId(String hql);
	Long getEntityIdL(String hql);
	/**
	 * 插入相应的属性值 sql语句
	 */
	long insertEntityBySql(String sql);
	List getListBySql(int page, int rows, String sql);
	byte findNodeStatebyUrl(Integer actorId, String url);
	T findLastObject();
	Integer findLastId();
	List<T> findAllbyDep(Integer depId);
	List<T> castQueryBySql(final String sql, final T t);/**

     /*@Title: addEntity
     *@Description: 添加实体类
     *@param entity: 实体类
     *@return void
     *@throws
     */
    void addEntity(T entity);

    /*@Title: updateEntity
     *@Description: 更新实体类
     *@param entityOld: 实体类-改前(用作记录操作日志)
     * @param entityNew: 实体类-改后
     *@return void
     *@throws
     */
    void updateEntity(Object entityOld, T entityNew);
    /*@Title: removeEntity
     *@Description: 删除实体类
     *@param entity: 实体类
     *@return void
     *@throws
     */
    void removeEntity(T entity);

    /*@Title: removeEntity
     *@Description: 删除实体类
     *@param id
     *@return void
     *@throws
     */
    void removeEntity(int id);

    /**
     *@Title: saveLog
     *@Description: 避免被日志捕捉,故单独写一个方法
     *@param entity: LogEntity
     *@return void
     *@throws
     */
    void saveLog(T entity);

    /**
     *@Title:       getPageJsonByCond
     *@Description: 针对页面获取数据的方法(json_*)
     *@param        cond: 条件
     *@param        jumpPage: 第几页
     *@param        pageSize: 页码
     *@return       HashMap
     *@throws
     */
    HashMap<String, Object> getPageJsonByCond(String cond, int jumpPage, int pageSize);
}