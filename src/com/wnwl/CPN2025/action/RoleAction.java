package com.wnwl.CPN2025.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wnwl.CPN2025.acegi.UrlSecuredUrlDefinition;
import com.wnwl.CPN2025.bhh.PrivActor;
import com.wnwl.CPN2025.bhh.TreeNode;
import com.wnwl.CPN2025.bhh.UserInfo;
import com.wnwl.CPN2025.hdao.PrivActorDAO;
import com.wnwl.CPN2025.hdao.TreeNodeDAO;
import com.wnwl.CPN2025.hdao.UserInfoDAO;
import com.wnwl.CPN2025.service.SystemService;
import com.wnwl.CPN2025.service.UserService;
import com.wnwl.CPN2025.util.PageBean;

public class RoleAction extends BaseAction{
	private static final long serialVersionUID = -2643439052932494049L;
	private SystemService systemService;
	private UserService userService;
    private UrlSecuredUrlDefinition urlSecuredUrlDefinition;
    private PrivActorDAO privActorDAO;
    private TreeNodeDAO treeNodeDAO;
    private UserInfoDAO userInfoDAO;
    private List<PrivActor> privActors;
    private List<TreeNode> treeNodes;
    private List<UserInfo> userInfos;
    private PrivActor privActor;
    private UserInfo userInfo;
    private TreeNode treeNode;
	private Integer nodeId;
	private Integer actorId;
	private PageBean pageBean;
	private ArrayList<Object> rows;
	private HashMap<String, Object> map;
	private String hql;
	private String sql;
	private String cond;
	private byte flag;
	private int jumpPage;
	private int pageSize;
	private long count;
	private byte nodeState;
	private String name;
	private String nodeDesc;
	private Integer pId;
	private Short sort;
    private byte type;
	private String title;
	private String url;
	private String actorName;
	private String nodeName;
	private String queryWord;
	private String userIds;
	private String nodeIds;
	private String nodeStates;
	private String actorDesc;
	private short tIndex;

	public String sortNode(){		//拖拽排序节点
		try {
            treeNodeDAO = systemService.getTreeNodeDAO();
			treeNode = treeNodeDAO.findById(nodeId);
			sort = treeNode.getSort();		//拖拽节点原本的序号
			String sql = "";
			if(treeNode.getParent().getId()==pId){		//同目录拖拽
				if(sort>tIndex){		//往前拖
					sql = "UPDATE tree_node SET sort = (CASE WHEN sort>="+tIndex+" and sort<"+sort+" THEN sort+1 ELSE sort END ) WHERE parent_id="+pId;
				}
				else{		//往后拖
					sql = "UPDATE tree_node SET sort = (CASE WHEN sort<="+tIndex+" and sort>"+sort+" THEN sort-1 ELSE sort END ) WHERE parent_id="+pId;
				}
                treeNodeDAO.updateBatch(sql);		//更新排序节点后的所在级别顺序
			}
			else{		//表示该节点移至其他父节点下，需更新拖拽前的序号
				sql = "UPDATE tree_node SET sort = (CASE WHEN sort>"+sort+" THEN sort-1 ELSE sort END ) WHERE parent_id="+treeNode.getParent().getId();
                treeNodeDAO.updateBatch(sql);		//初始化需排序节点的所在级别顺序
				sql = "UPDATE tree_node SET sort = (CASE WHEN sort>="+tIndex+" THEN sort+1 ELSE sort END ) WHERE parent_id="+pId;
                treeNodeDAO.updateBatch(sql);		//更新排序节点后的所在级别顺序
			}
			treeNode.setSort(tIndex);
			treeNode.setParent(treeNodeDAO.findById(pId));
            treeNodeDAO.save(treeNode);			//最终更新自身的序号
			flag = 0;
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String showNode(){
		//nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(), httpRequest.getRequestURI());
        nodeState = 1;
		return SUCCESS;
	}

	public String json_nodes(){
        treeNodeDAO = systemService.getTreeNodeDAO();
		cond = "id>0 order by parent.sort,sort";	//2级菜单，排序
		treeNodes = treeNodeDAO.findSelfObjects(cond);
		rows = new ArrayList<Object>();			//设备结构
		map = new HashMap<String, Object>();
		if(treeNodes!=null&&treeNodes.size()>0){
			for(TreeNode node:treeNodes){
				map = new HashMap<String, Object>();
				map.put("id", node.getId());
				map.put("name", node.getName());
				map.put("pId", node.getParent().getId());
				map.put("pName", node.getParent().getName());
				map.put("title", node.getTitle());
				map.put("sort", node.getSort());
				map.put("comment", node.getComment());
				map.put("title", node.getTitle());
				map.put("url", node.getUrl());
                map.put("type", node.getType());
                map.put("typeStr", node.getTypeStr());
				map.put("open", true);
				if(node.getId()==1||node.getId()==40){  //根节点，无法删除
					map.put("isRemove", false);
				}
				if(node.getParent().getId()!=1&&node.getParent().getId()!=40){	//限定3级菜单
					map.put("isAdd", false);
				}
				if(nodeState!=1){
					map.put("isAdd", false);
					map.put("isRemove", false);
					map.put("isRename", false);
				}
				rows.add(map);
			}
		}
		return SUCCESS;
	}

	public String addNode(){
		try {
            treeNodeDAO = systemService.getTreeNodeDAO();
			flag = 1;			//默认编辑
			if(nodeId==null){
                treeNode = new TreeNode();
				flag = 0;		//表示添加
			}
			else
                treeNode = treeNodeDAO.findById(nodeId);
			treeNode.setName(name);
			treeNode.setComment(nodeDesc);
			treeNode.setParent(treeNodeDAO.findById(pId));
			treeNode.setSort(sort);
			treeNode.setTitle(title);
			treeNode.setUrl(url);
            treeNode.setType(type);
            treeNodeDAO.save(treeNode);
			map = new HashMap<String, Object>();
			map.put("id", treeNode.getId());
			map.put("name", name);
			map.put("pId", pId);
			map.put("sort", sort);
			map.put("title", title);
			map.put("url", url);
			map.put("nodeDesc", nodeDesc);
		} catch (Exception e) {
			flag = -1;		//表示出错
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String removeNode(){
		try {
            systemService.getTreeNodeDAO().remove(nodeId);
			flag = 0;
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String checkActor(){
		flag=0;
		if(actorId==null)
			cond=" actorName='"+actorName+"'";
		else
			cond=" actorName='"+actorName+"' and id<>"+actorId;
		privActors = userService.getPrivActorDAO().findSelfObjects(cond);
		if(privActors!=null&privActors.size()>0)
			flag=1;
		return SUCCESS;
	}

	public String checkNode(){		//验证模块名
		try {
			nodeName = java.net.URLDecoder.decode(nodeName , "UTF-8");
			flag=0;
			if(nodeId==null)
				cond=" name='"+nodeName+"' and parent.id="+pId;
			else
				cond=" name='"+nodeName+"' and id<>"+nodeId+" and parent.id="+pId;
			treeNodes = systemService.getTreeNodeDAO().findSelfObjects(cond);
			if(treeNodes!=null&treeNodes.size()>0)
				flag=1;
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String showRole(){
		nodeState = userService.findNodeStatebyUrl(session.get("actorId").toString(), httpRequest.getRequestURI());
	    return SUCCESS;
	}

	public String json_role(){
		pageBean = PageBean.getInstance();
		cond = " actorName<>'admin'";
        privActorDAO = userService.getPrivActorDAO();
		count = privActorDAO.getCountbyCond(cond);
    	pageBean.setPageBean(count, jumpPage, pageSize, "");
    	privActors = privActorDAO.getList(jumpPage, pageSize, cond);
    	rows = transRows_privActors(privActors);
		return SUCCESS;
	}

	public String query_role(){
		pageBean = PageBean.getInstance();
		cond=" actorName like '%"+queryWord+"%'";
        privActorDAO = userService.getPrivActorDAO();
		count = privActorDAO.getCountbyCond(cond);
    	pageBean.setPageBean(count, jumpPage, pageSize, "");
        privActors = privActorDAO.getList(jumpPage, pageSize, cond);
    	rows = transRows_privActors(privActors);
		return SUCCESS;
	}

	private ArrayList<Object> transRows_privActors(List<PrivActor> privActors){
    	this.rows = new ArrayList<Object>();
    	List<Object> datas = new ArrayList<Object>();
    	for (PrivActor privActor:privActors){
    		datas = new ArrayList<Object>();
    		datas.add(privActor.getId());
    		datas.add(privActor.getActorName());
    		datas.add(privActor.getActorDesc());
    		if(nodeState==1){
    			datas.add("<a class='aBtn aEdit' href='javascript:' onclick='detailUsers("+privActor.getId()+",\""+privActor.getActorName()+"\");'>人员</a>"
    					+"　<a class='aBtn aEdit' href='javascript:' onclick='detailNodes("+privActor.getId()+",\""+privActor.getActorName()+"\");'>角色节点</a>"
    					+"　<a class='aBtn aEdit' href='javascript:' onclick='edit("+privActor.getId()+");'>编辑</a>"
    					+"　<a class='aBtn aDel' href='javascript:' onclick='del("+privActor.getId()+");'>删除</a>");
    		}
    		else{
    			datas.add("<a class='aBtn aEdit' href='javascript:' onclick='detailUsers("+privActor.getId()+",\""+privActor.getActorName()+"\");'>人员</a>"
    					+"　<a class='aBtn aEdit' href='javascript:' onclick='detailNodes("+privActor.getId()+",\""+privActor.getActorName()+"\");'>角色节点</a>");
    		}
    		rows.add(datas);
    	}
    	return rows;
	}

	public String showDetailRole(){
		privActor = userService.getPrivActorDAO().findById(actorId);
		map = new HashMap<String,Object>();
		map.put("id", actorId);
		map.put("actorName", privActor.getActorName());
		map.put("rName", privActor.getName());
		map.put("actorDesc", privActor.getActorDesc());
		return SUCCESS;
	}

	public String detailUser(){		//获取角色对应的已绑定用户
		hql = " from UserInfo where id in (select userInfo.id from PrivActorUser where privActor.id="+actorId+") order by depInfo.type, state";
		userInfos = userService.getUserInfoDAO().findAnyObjects(hql);
		if(userInfos!=null&&userInfos.size()>0){
			this.rows = new ArrayList<Object>();
			ArrayList<Object> rows1 = new ArrayList<Object>();
			byte depType1 , depType2 = -1;
            UserInfo userInfo;
			for(int i=0;i<userInfos.size();i++){
				userInfo = userInfos.get(i);
                depType1 = userInfo.getDepInfo().getType();
                depType2 = depType2==-1?depType1:depType2;
				if(depType1!=depType2){
					rows.add(rows1);
                    depType2 = depType1;
					rows1 = new ArrayList<Object>();
				}
				map = new HashMap<String,Object>();
				map.put("typeName", userInfo.getDepInfo().transType());
				map.put("userName", userInfo.getUserName());
				rows1.add(map);
				if(i==userInfos.size()-1)
					rows.add(rows1);
			}
		}
		return SUCCESS;
	}

	public String detailNode(){		//获取角色对应的已绑定模块
		try {
			cond = " id in (select treeNodes.id from PrivActorNodes where privActor.id="+actorId+") and id>0 order by id";
			/*List<TreeNodes> treeNodes=treeNodesService.findSelfObjects(cond);
			List<Integer> nodeIds = new ArrayList<Integer>();
			if(treeNodes!=null&&treeNodes.size()>0){
				for(TreeNodes node:treeNodes){
					nodeIds.add(node.getId());
				}
			}
			userInfo = userInfoService.findloginUser();
			cond = "from TreeNodes a where a.id<>1 ORDER BY CASE WHEN a.iconOpen=1 THEN a.sort WHEN a.iconOpen=2 THEN a.parent.sort ELSE a.parent.parent.sort END,CASE WHEN a.iconOpen=2 THEN a.sort  WHEN a.iconOpen=3 THEN a.parent.sort END,a.iconOpen,a.sort";
			treeNodes = treeNodesService.findAnyObjects(cond);
			prNodes = "";
			rows = new ArrayList<Object>();
			Integer nodeId;
			for(TreeNodes node:treeNodes){
				prNodes += node.getId()+",";
				nodeId = node.getId();
				map = new HashMap<String,Object>();
				map.put("id", nodeId);
				map.put("pId", node.getParent().getId());
				map.put("name", node.getName());
				map.put("title", "模块链接："+node.getUrl());
				if(nodeIds.contains(nodeId))
					map.put("checked", "true");
				else
					map.put("checked", "false");
				map.put("open", "true");
				map.put("fontweight", node.getParent().getId()==1?"bold":"normal");
				rows.add(map);
			}
			prNodes = prNodes.substring(0, prNodes.length()-1);
			rows1 = new ArrayList<Object>();
			privActorNodess = privActorNodesService.findPerTreeNodes(actorId);
			if(privActorNodess!=null&&privActorNodess.size()>0){
				for(PrivActorNodes privActorNodes:privActorNodess){
					map = new HashMap<String,Object>();
					map.put("nodeId",privActorNodes.getTreeNodes().getId());
					map.put("nodeState", privActorNodes.getNodeState());
					rows1.add(map);
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String ggetRoleUser(){			//获取所选角色已绑定用户以及未绑定角色的用户
		try {
            privActorDAO = userService.getPrivActorDAO();
            userInfoDAO = userService.getUserInfoDAO();
			privActor = privActorDAO.findById(actorId);
			hql = " from UserInfo where id in (select userInfo.id from PrivActorUser where privActor.id="+actorId+") order by depInfo.type, state";
			userInfos = userInfoDAO.findAnyObjects(hql);
            map = new HashMap<String, Object>();
            map.put("bindUser", transUsers(userInfos)); //本角色对应用户
			hql = " from UserInfo where id not in (select userInfo.id from PrivActorUser) order by depInfo.type, state";
			userInfos = userInfoDAO.findAnyObjects(hql);
            map.put("elseUser", transUsers(userInfos)); //未关联角色用户
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private List<Object> transUsers(List<UserInfo> userInfos){
        List<Object> rows1 = new ArrayList<Object>();
        HashMap<String, Object> map1;
        if(userInfos!=null&&userInfos.size()>0){
            for(UserInfo user:userInfos){
                map1 = new HashMap<String,Object>();
                map1.put("id", user.getId());
                map1.put("userName", user.getUserName());
                rows1.add(map1);
            }
        }
        return rows1;
    }

	public String ggetRoleNodes(){			//获取所选角色已绑定模块以及其他的模块
		/*depId=userInfoService.findDepbyLoginUser().getId();
		hql=" from TreeNodes where id in (select treeNodes.id from PrivActorNodes where privActor.id="+actorId+") and id<>1 order by parent.id,id";
		List<TreeNodes> treeNodes=treeNodesService.findAnyObjects(hql);
		this.rows = new ArrayList<Object>();
		if(treeNodes!=null&&treeNodes.size()>0){
			for(TreeNodes node:treeNodes){
				map = new HashMap<String,Object>();
				map.put("id", node.getId());
				if(node.getParent().getId()==1)
					map.put("Name", node.getName());
				else
					map.put("Name", node.getName()+"("+node.getParent().getName()+")");
				rows.add(map);
			}
		}
		hql=" from TreeNodes where id not in (select treeNodes.id from PrivActorNodes where privActor.id="+actorId+") and id<>1 order by parent.id,id";
		treeNodes=treeNodesService.findAnyObjects(hql);
		this.rows1 = new ArrayList<Object>();
		if(treeNodes!=null&&treeNodes.size()>0){
			for(TreeNodes node:treeNodes){
				map = new HashMap<String,Object>();
				map.put("id", node.getId());
				if(node.getParent().getId()==1)
					map.put("Name", node.getName());
				else
					map.put("Name", node.getName()+"("+node.getParent().getName()+")");
				rows1.add(map);
			}
		}*/
		return SUCCESS;
	}

	public String bindRoleUser(){
		try {
			sql = "delete from priv_actor_user where actor_id=" + actorId;
            privActorDAO = userService.getPrivActorDAO();
            privActorDAO.updateBatch(sql);
			if (null != userIds && userIds.trim().length() > 0) {
				sql = "insert into priv_actor_user values ";
				String[] uIds = userIds.substring(0, userIds.length() - 1).split(",");
				cond = "";
				for (String id : uIds) {
					cond += "(" + id + "," + actorId + "),";
				}
				sql += cond.substring(0, cond.length() - 1);
                privActorDAO.updateBatch(sql);
				urlSecuredUrlDefinition.reload();		//更新权限验证列表
			}
			flag = 0;
		} catch (Exception e) {
			e.printStackTrace();
			flag = 1;
		}
		return SUCCESS;
	}

	public String bindRoleNode(){
		try {
			sql = "delete from priv_actor_nodes where actorId=" + actorId;
            privActorDAO = userService.getPrivActorDAO();
            privActorDAO.updateBatch(sql);
			if (null != nodeIds && nodeIds.trim().length() > 0) {
				sql = "insert into priv_actor_nodes(actorId,nodeId,nodeState) values ";
				String[] nIds = nodeIds.substring(0, nodeIds.length() - 1).split(",");
				String[] nStates = nodeStates.substring(0, nodeStates.length() - 1).split(",");
				cond = "";
				for (int i=0;i<nIds.length;i++) {
					cond += "(" + actorId + "," + nIds[i] + ","+nStates[i]+"),";
				}
				sql += cond.substring(0, cond.length() - 1);
                privActorDAO.updateBatch(sql);
				urlSecuredUrlDefinition.reload();		//更新权限验证列表
			}
			flag = 0;
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String addRole(){
        PrivActorDAO privActorDAO = userService.getPrivActorDAO();
        if(actorId==null){
			privActor = new PrivActor();
			privActor.setName("");
			privActor.setActorDesc(actorDesc);
			privActor.setActorName(actorName);
            privActorDAO.save(privActor);
			privActor.setName("ROLE_"+privActor.getId());
            privActorDAO.save(privActor);
		}
		else{
			privActor = privActorDAO.findById(actorId);
			privActor.setActorDesc(actorDesc);
			privActor.setActorName(actorName);
            privActorDAO.save(privActor);
		}
		if(actorId!=null){
			map = new HashMap<String,Object>();
			map.put("actorId", actorId);
			map.put("actorName", actorName);
			map.put("actorDesc", actorDesc);
			map.put("more","<a class='aBtn aEdit' href='javascript:' onclick='detailUsers("+actorId+",\""+actorName+"\");'>人员浏览</a>"
					+"　<a class='aBtn aEdit' href='javascript:' onclick='detailNodes("+actorId+",\""+actorName+"\");'>角色节点浏览</a>"
					+"　<a class='aBtn aEdit' href='javascript:' onclick='edit("+actorId+");'>编辑</a>"
					+"　<a class='aBtn aDel' href='javascript:' onclick='del("+actorId+");'>删除</a>");
		}
		return SUCCESS;
	}

	public String removeRole(){
		try {
            userService.getPrivActorDAO().remove(actorId);
			flag = 0;
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public ArrayList<Object> getRows() {
		return rows;
	}

	public HashMap<String, Object> getMap() {
		return map;
	}

	public byte getFlag() {
		return flag;
	}

	public void setJumpPage(int jumpPage) {
		this.jumpPage = jumpPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}

	public void setPId(Integer pId) {
		this.pId = pId;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setActorId(Integer actorId) {
		this.actorId = actorId;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public void setQueryWord(String queryWord) {
		try {
			queryWord = java.net.URLDecoder.decode(queryWord, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.queryWord = queryWord;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public void setNodeIds(String nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setNodeStates(String nodeStates) {
		this.nodeStates = nodeStates;
	}

	public void setActorDesc(String actorDesc) {
		this.actorDesc = actorDesc;
	}

	public byte getNodeState() {
		return nodeState;
	}

	public void setNodeState(byte nodeState) {
		this.nodeState = nodeState;
	}

	public void setTIndex(byte tIndex) {
		this.tIndex = tIndex;
	}

    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UrlSecuredUrlDefinition getUrlSecuredUrlDefinition() {
        return urlSecuredUrlDefinition;
    }

    public void setUrlSecuredUrlDefinition(UrlSecuredUrlDefinition urlSecuredUrlDefinition) {
        this.urlSecuredUrlDefinition = urlSecuredUrlDefinition;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
