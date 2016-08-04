package com.wnwl.CPN2025.log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;


/**
 * 功能描述：根据实体类得到对应的表名、主键名、字段名工具类 </p>
 * 注：po类名须与对应映射文件名一致，即Student.java与Student.hbm.xml
 *
 * @Date：Nov 10, 2008
 * @Time：3:13:07 PM
 *
 */
public class HibernateConfigurationHelper {
    public static Configuration hibernateConf;
    @SuppressWarnings("unused")
    private static Configuration getHibernateConf() {
        if (hibernateConf == null) {
            return new Configuration();
        }
        return hibernateConf;
    }
    @SuppressWarnings("unchecked")
    public static PersistentClass getPersistentClass(Class clazz) {
        synchronized (HibernateConfigurationHelper.class) {
            PersistentClass pc = getHibernateConf().getClassMapping(
                    clazz.getName());
            if (pc == null) {
                hibernateConf = getHibernateConf().addClass(clazz);
                pc = getHibernateConf().getClassMapping(clazz.getName());

            }
            return pc;
        }
    }
    /**
     * 功能描述：获取实体对应的表名
     *
     * @param clazz
     *            实体类
     * @return 表名
     */
    @SuppressWarnings("unchecked")
    public static String getTableName(Class clazz) {
        return getPersistentClass(clazz).getTable().getName();
    }
    /**
     * 功能描述：获取实体对应表的主键字段名称
     *
     * @param clazz
     *            实体类
     * @return 主键字段名称
     */
    @SuppressWarnings("unchecked")
    public static String getPkColumnName(Class clazz) {

        return getPersistentClass(clazz).getTable().getPrimaryKey()
                .getColumn(0).getName();

    }
    /**
     * 根据实体类获取所有列的备注信息
     * @param clazz
     * @return
     */
    public static Map<String, String> getComments(Class clazz){
        System.out.println("test");
        PersistentClass persistentClass = getPersistentClass(clazz);
        Iterator<Property> it=persistentClass.getPropertyIterator();
        Map<String, String> comments=new HashMap<String, String>();
        while(it.hasNext()){
            Property propert= it.next();
            Iterator<Column> columns=propert.getColumnIterator();
            while(columns.hasNext()){
                Column column= columns.next();
                String comment=column.getComment();
                if (StringUtils.isNotEmpty(comment)) {
                    comments.put(propert.getName(), comment);
                }
            }
        }
        return comments;
    }
    /**
     * 根据实体类和属性获取列的备注
     * @param clazz
     * @param propertyName
     * @return
     */
    public static String getComment(Class clazz,String propertyName){
        PersistentClass persistentClass = getPersistentClass(clazz);
        Property property = persistentClass.getProperty(propertyName);
        Iterator i=property.getColumnIterator();
        while(i.hasNext()){
            System.out.println(((Column)i.next()).getComment());
        }
        System.out.println(property.getMetaAttributes());
        Iterator it = property.getColumnIterator();
        if (it.hasNext()) {
            Column column = (Column) it.next();
            return column.getComment();
        }
        return null;
    }
    /**
     * 功能描述：通过实体类和属性，获取实体类属性对应的表字段名称
     *
     * @param clazz
     *            实体类
     * @param propertyName
     *            属性名称
     * @return 字段名称
     */
    @SuppressWarnings("unchecked")
    public static String getColumnName(Class clazz, String propertyName) {
        PersistentClass persistentClass = getPersistentClass(clazz);
        Property property = persistentClass.getProperty(propertyName);

        Iterator i=property.getColumnIterator();
        while(i.hasNext()){
            System.out.println(((Column)i.next()).getComment());
        }
        System.out.println(property.getMetaAttributes());
        Iterator it = property.getColumnIterator();
        if (it.hasNext()) {
            Column column = (Column) it.next();
            return column.getName();
        }
        return null;
    }
}