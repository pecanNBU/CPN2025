package com.wnwl.CPN2025.bhh;

/**
 *
 * Simple to Introduction
 * @ProjectName:    建设工程颗粒物与噪声在线监测系统
 * @Package:        com.wnwl.CPN2025.bhh
 * @ClassName:      实体类扩展类
 * @Description:    扩展克隆方法,用于存储更新操作的日志
 * @Author:         Jenny
 * @CreateDate:     2016.7.23
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version:        V1.0
 *
 */
public class BaseBhh implements Cloneable {

    /*
    * 获取克隆对象,用于存储操作日志
    * */
    /*public Object transCopyEntity(){
        Object old = new Object();
        try {
            BeanUtils.copyProperties(old, this);
        } catch (Exception e) {
            return null;
        }
        return old;
    }*/

    /**
     *@Title: clone
     *@Description: 克隆对象,用于存储操作日志 - 浅克隆 - 仅克隆自身以外对象的id
     *@param
     *@return Object, 返回克隆的对象
     *@throws
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /*@Override 深度克隆 - 克隆自身以外对象的所有属性
    protected Object clone() throws CloneNotSupportedException {
        Administrator admin = (Administrator) super.clone();
        admin.user = (User) admin.user.clone();
        return admin;
    }*/

}
