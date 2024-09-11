package weaver.interfaces.yy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.integration.logging.Logger;
import weaver.integration.logging.LoggerFactory;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GysfkDdActionV10 implements Action {

    private Logger logger = LoggerFactory.getLogger(GysfkDdActionV10.class);
    private RequestManager requestManager;

    @Override
    public String execute(RequestInfo requestInfo) {

        boolean flag = true;
        String msg = "";
        String currentMonth="";
        currentMonth= TimeUtil.getCurrentDateString();
        logger.info("GysfkDdAction++++++++++++++++"+currentMonth);
        String msg1 = "";
        requestManager = requestInfo.getRequestManager();
        int requestId = requestManager.getRequestid();
        String tableName = requestManager.getBillTableName();
        int workflowid = requestManager.getWorkflowid();
        int mainId = requestManager.getBillid();//明细表id
        int userId = requestManager.getUserId();

        YyWsUtil yyWsUtil = new YyWsUtil();

        RecordSet rs = new RecordSet();  // RecordSetDataSource rsd = new RecordSetDataSource("SAP_jkb");

        String url = "/sap/bc/zcl_if_http_api/MM006?sap-client=300";
        logger.info("进入接口---GysfkDdAction,requestId:"+requestId+","+ "表单名称：" + tableName + ",workflowid=" + workflowid);
        try {
            //采购订单类型
            //凭证日期
            //公司代码
            //采购组织
            //采购组
            //供应商编码
            String BSART = "ZP09";
            String AEDAT = "";
            String BUKRS = "";
            String EKORG = "9000";
            String EKGRP = "";
            String LIFNR = "";

            String EBELP = "0";
            String KNTTP = "";
            String PSTYP = "";
            String MATNR = "";
            String TXZ01 = "";
            String MENGE = "";
            String MATKL = "";
            String MEINS = "";
            String NETPR = "";
            String PEINH = "";
            String BPRME = "";
            String WERKS = "";
            String EINDT = "";
            String RETPO = "";
            String LOEKZ = "";
            String KOSTL = "";
            String MWSKZ = "";
            String BEDNR = "";
            String AFNAM = "";
            String ZHWB = "";
            String fklxws = "";

            //判断是否推送成功
            String zjsftscg ="";

            String mainsql = " select * from " + tableName + " where requestid=? ";
            if (rs.executeQuery(mainsql, requestId) && rs.next()) {
                AFNAM = Util.null2String(rs.getString("sqr"));
                //申请人
                fklxws = Util.null2String(rs.getString("fklx"));
                AEDAT = Util.null2String(rs.getString("sqrq"));
                BUKRS = Util.null2String(rs.getString("gsdm"));
                LIFNR = Util.null2String(rs.getString("gysbm"));
                zjsftscg = Util.null2String(rs.getString("zjsftscg"));

            }

            if("0".equals(zjsftscg)) {
                logger.info("资金是否推送" + zjsftscg);

                logger.info("付款类型" + fklxws);

                if ("2".equals(fklxws)) {

                    String detailsql = "select * from " + tableName + "_dt2 where mainid=? ";

                    //参数
                    List mainList = new ArrayList();
                    if (rs.executeQuery(detailsql, mainId)) {
                        while (rs.next()) {
                            EKGRP = Util.null2String(rs.getString("cgz"));
                            // EBELP= Util.null2String(rs.getString(""));
                            //行项目
                            KNTTP = Util.null2String(rs.getString("K"));
                            //科目分配类别
                            //PSTYP	 = Util.null2String(rs.getString(""));
                            //项目类别
                            MATNR = Util.null2String(rs.getString("wlbm"));
                            //物料号
                            TXZ01 = Util.null2String(rs.getString("wlms"));
                            //短文本
                            MENGE = Util.null2String(rs.getString("slkg"));
                            //数量
                            MATKL = Util.null2String(rs.getString("wlz"));
                            //物料组
                            MEINS = Util.null2String(rs.getString("dddw"));
                            //订单单位
                            NETPR = Util.null2String(rs.getString("wsdj"));
                            //净价
                            PEINH = Util.null2String(rs.getString("jgdw"));
                            //价格单位
                            BPRME = Util.null2String(rs.getString("ddjgdw"));
                            //订单价格单位
                            WERKS = Util.null2String(rs.getString("gc"));
                            //收货工厂
                            EINDT = Util.null2String(rs.getString("yjjq"));
                            //交货日期
                            //RETPO	 = Util.null2String(rs.getString(""));
                            //退货标识
                            // LOEKZ	 = Util.null2String(rs.getString(""));
                            //删除标识
                            KOSTL = Util.null2String(rs.getString("cbzx"));
                            //成本中心
                            MWSKZ = Util.null2String(rs.getString("sm1"));
                            //税码
                            // BEDNR	 = Util.null2String(rs.getString(""));
                            //跟踪编号
                            ZHWB = Util.null2String(rs.getString("bz"));
                            //行项目文本（备注）

                            Map map = new HashMap();
                            map.put("BSART", BSART);
                            map.put("AEDAT", AEDAT);
                            map.put("BUKRS", BUKRS);
                            map.put("EKORG", EKORG);
                            map.put("EKGRP", EKGRP);
                            map.put("LIFNR", LIFNR);
                            map.put("EBELP", EBELP);
                            map.put("PSTYP", "0");
                            // map.put("KNTTP",KNTTP);
                            map.put("MATNR", MATNR);
                            //map.put("TXZ01",TXZ01);
                            map.put("MENGE", MENGE);
                            //map.put("MATKL",MATKL);
                            map.put("MEINS", MEINS);
                            map.put("NETPR", NETPR);
                            map.put("PEINH", PEINH);
                            map.put("BPRME", BPRME);
                            map.put("WERKS", WERKS);
                            map.put("EINDT", EINDT);

                            map.put("EMLIF", LIFNR);
                            map.put("EINDT", "X");

                            map.put("KOSTL", KOSTL);
                            map.put("MWSKZ", MWSKZ);
                            map.put("ZHWB", ZHWB);
                            mainList.add(map);
                        }
                    }

                    String params = JSON.toJSONString(mainList);
                    logger.info("params:" + params);
                    //sap请求
                    //接口返回信息
                    String result = yyWsUtil.doPostSAP(params, url);
                    logger.info("result:" + result);
                    if (!"".equals(result)) {

                        JSONObject jsonObject = JSON.parseObject(result);

                        String ztype = jsonObject.getString("type");
                        if ("S".equals(ztype)) {
                            String EBELN = jsonObject.getJSONArray("item").getJSONObject(0).getString("ebeln");

                            String updsql = "update " + tableName + "_dt2 set ddbm= ? where mainid=? ";
                            if (!rs.executeUpdate(updsql, EBELN, mainId)) {
                                logger.info("sql----" + updsql);
                            }
                        } else {
                            flag = false;
                            msg = jsonObject.getString("msg");
                        }
                    }
                }

            }
            }catch(Exception e){
                logger.info(e);
            }


        if (flag){
            return Action.SUCCESS;
        }else {
            //输出返回错误信息
            requestManager.setMessageid("001");
            requestManager.setMessage("接口推送异常");
            requestManager.setMessagecontent("异常信息："+msg);
            return Action.FAILURE_AND_CONTINUE;
        }
    }
}
