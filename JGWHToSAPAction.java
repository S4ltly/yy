package weaver.interfaces.yy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.integration.logging.Logger;
import weaver.integration.logging.LoggerFactory;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JGWHToSAPAction extends BaseBean implements Action {
    private Logger logger = LoggerFactory.getLogger(JGWHToSAPAction.class);
    private RequestManager requestManager;

    public String execute(RequestInfo requestInfo){
        boolean flag = true;
        String url = "/sap/bc/zcl_if_http_api/MM004?sap-client=300";
        String currentMonth="";
        currentMonth= TimeUtil.getCurrentDateString();
        logger.info("JGWHToSAPAction++++++++++++++++"+currentMonth);
        String msg = "";
        requestManager = requestInfo.getRequestManager();
        int requestId = requestManager.getRequestid();
        String tableName = requestManager.getBillTableName();
        int workflowid = requestManager.getWorkflowid();
        int mainId = requestManager.getBillid();
        logger.info("进入接口---JGWHToSAPAction,requestId:"+requestId+","+ "表单名称：" + tableName + ",workflowid=" + workflowid);

        //主表
        String ZYWLX="";
        //业务类型
        String LIFNR="";
        //供应商编码
        String MATNR="";
        //物料编号
        String EKORG="";
        //采购组织
        String WERKS="";
        //工厂
        String ESOKZ="";
        //信息类别
        String APLFZ="";
        //计划交货时间
        String EKGRP="";
        //采购组
        String MINBM="";
        //最小采购数量
        String MWSKZ="";
        //税码
        String NETPR="";
        //净价
        String WAERS="";
        //货币码
        String PEINH="";
        //价格单位
        String BPRME="";
        //订单价格单位
        String DATAB="";
        //单价有效起始日期
        String DATBI="";
        //单价有效截止日期
        String KBETR="";
        //关税税率
        String ZMJBM="";
        //模具编码
        String ZMJSL="";
        //模具数量
        String ZMJJG="";
        //模具价格
        String ZHXCL="";
        //费用后续处理方式
        String ZMBSL="";
        //目标数量
        String ZKSRQ="";
        //统计开始日期
        String ZJSRQ="";
        //统计结束日期
        String ZOAZH="";
        //OA账号
        String TDLINE="";
        //备注
        String sqr = "";
        //申请人

        RecordSet rs = new RecordSet();
        String sql = "select * from "+tableName+" where requestid=? ";
        rs.executeQuery(sql,requestId);
        if (rs.next()){
            ZYWLX = Util.null2String(rs.getString("jglx"));
            //业务类型
            LIFNR=Util.null2String(rs.getString("gysdm"));
            //供应商编码
            MATNR=Util.null2String(rs.getString("cldmyc"));
            //物料编号
            EKORG=Util.null2String(rs.getString("cgzzdm"));
            //采购组织
            WERKS=Util.null2String(rs.getString("gcdm"));
            //工厂
            ESOKZ=Util.null2String(rs.getString("xxjllb"));
            //信息类别
            APLFZ=Util.null2String(rs.getString("jhjhsjtl"));
            //计划交货时间
            EKGRP=Util.null2String(rs.getString("cgz"));
            //采购组
            MINBM=Util.null2String(rs.getString("zxcgsl"));
            //最小采购数量
            MWSKZ=Util.null2String(rs.getString("sm"));
            //税码
            NETPR=Util.null2String(rs.getString("wsjg"));
            //净价
            WAERS=Util.null2String(rs.getString("hb"));
            //货币码
            PEINH=Util.null2String(rs.getString("jgdw"));
            //价格单位
            BPRME=Util.null2String(rs.getString("jgdw"));
            //订单价格单位
            DATAB=Util.null2String(rs.getString("yxqqsrq"));
            //单价有效起始日期
            DATBI=Util.null2String(rs.getString("yxqjzrq"));
            //单价有效截止日期
            KBETR=Util.null2String(rs.getString("gs"));
            //关税税率
            ZMJBM=Util.null2String(rs.getString("mjbm"));
            //模具编码
            ZMJSL=Util.null2String(rs.getString("mjsl"));
            //模具数量
            ZMJJG=Util.null2String(rs.getString("mjwsjg"));
            //模具价格
            ZHXCL=Util.null2String(rs.getString("fyhxclfs"));
            //费用后续处理方式
            ZMBSL=Util.null2String(rs.getString("mbsl"));
            //目标数量
            ZKSRQ=Util.null2String(rs.getString("sltjkssj"));
            //统计开始日期
            ZJSRQ=Util.null2String(rs.getString("sltjjssj"));
            //统计结束日期
            sqr=Util.null2String(rs.getString("sqr"));
            //OA账号
            TDLINE=Util.null2String(rs.getString("bzsml"));
            //备注
        }
        String arr[] = {"A","B","C","D"};
        logger.info("传价格维护单");
        int ywlx = Integer.valueOf(ZYWLX);
        Map map = new LinkedHashMap();
        map.put("ZYWLX",arr[ywlx]);
        map.put("LIFNR",LIFNR);
        map.put("MATNR",MATNR);
        map.put("EKORG",EKORG);
        map.put("WERKS",WERKS);
        map.put("ESOKZ",ESOKZ);
        map.put("APLFZ",APLFZ);
        map.put("EKGRP",EKGRP);
        map.put("MINBM",MINBM);
        map.put("MWSKZ",MWSKZ);
        map.put("NETPR",NETPR);
        map.put("WAERS",WAERS);
        map.put("PEINH",PEINH);
        map.put("BPRME",BPRME);
        map.put("DATAB",DATAB);
        map.put("DATBI",DATBI);
        map.put("KBETR",KBETR);
        map.put("ZMJBM",ZMJBM);
        map.put("ZMJSL",ZMJSL);
        map.put("ZMJJG",ZMJJG);
        map.put("ZHXCL",ZHXCL);
        map.put("ZMBSL",ZMBSL);
        map.put("ZKSRQ",ZKSRQ);
        map.put("ZJSRQ",ZJSRQ);
        if (!"".equals(sqr)){
            ZOAZH = getWorkCode(sqr,rs);
        }
        map.put("ZOAZH",ZOAZH);
        map.put("TDLINE",TDLINE);
        List list = new ArrayList();
        list.add(map);

        String params = JSON.toJSONString(list);
        logger.info("Param:"+ params);
        YyWsUtil yyWsUtil = new YyWsUtil();
        String result = yyWsUtil.doPostSAP(params,url);
        logger.info("result:"+result);
        if (!"".equals(result)){
            JSONObject jsonObject = JSON.parseObject(result);
                String ztype = jsonObject.getString("type");
                if ("S".equals(ztype)){
                    String infnr= jsonObject.getJSONArray("item").getJSONObject(0).getString("infnr");
                    logger.info("采购信息记录号 :"+infnr);
                    String upSql = "update "+tableName+" set cgjlbh=? where requestid =? ";
                    rs.executeUpdate(upSql,infnr,requestId);
                }else {
                    flag = false;
                    msg = jsonObject.getString("msg");
                }
        }

        if (flag) {
            return Action.SUCCESS;
        }else {
            requestManager.setMessageid("001");
            requestManager.setMessage("接口推送异常");
            requestManager.setMessagecontent("异常信息："+msg);
            return Action.FAILURE_AND_CONTINUE;
        }
    }

    /**
     * 根据userid获取人员工号
     * @param userId
     * @param recordSet
     * @return
     */
    public String getWorkCode(String userId,RecordSet recordSet){
        String workCode = "";
        String sql = "select workcode from hrmresource where id =? ";
        recordSet.executeQuery(sql,userId);
        if (recordSet.next()){
            workCode = Util.null2String(recordSet.getString("workcode"));
        }
        return workCode;
    }
}
