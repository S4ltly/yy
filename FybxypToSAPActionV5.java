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

import java.util.*;


public class FybxypToSAPActionV5 extends BaseBean implements Action {

    private Logger logger = LoggerFactory.getLogger(FybxypToSAPActionV5.class);
    private RequestManager requestManager;

    @Override
    public String execute(RequestInfo requestInfo) {
        boolean flag = true;
        String url = "/sap/bc/zcl_if_http_api/FI001?sap-client=300";
        String currentMonth="";
        currentMonth= TimeUtil.getCurrentDateString();
        logger.info("FybxypToSAPAction++++++++++++++++"+currentMonth);
        String msg = "";
        requestManager = requestInfo.getRequestManager();
        int requestId = requestManager.getRequestid();//一条流程的id
        String tableName = requestManager.getBillTableName();
        int workflowid = requestManager.getWorkflowid();//一整个流程的id
        int mainId = requestManager.getBillid();
        logger.info("进入接口---FybxypToSAPAction,requestId:"+requestId+","+ "表单名称：" + tableName + ",workflowid=" + workflowid);
        //主表
        //流程单据号
        String ZDJH_OA = "";
        //流程类型
        String ZLCLX = "FYBXYP";
        //流程主题
        String BKTXT = "";
        //公司代码
        String BUKRS = "";
        //供应商
        String LIFNR = "";
        //供应商名称
        String NAME1 = "";
        //供应商地址
        String STRAS = "";
        //供应商国别
        String LAND1 = "";
        //供应商联行号/SWIFT
        String BANKL = "";
        //供应商账号
        String BANKN = "";
        //银行科目
        String ZYHKM = "";
        //往来科目
        String ZWLKM = "";
        //总额
        String WRBTR = "";
        //币种
        String WAERS = "";
        //成本中心
        String KOSTL = "";
        //产品研发项目
        String ZZFI03 = "";
        //资金收款
        String AUFNR = "";
        //经办人工号
        String ZZFI02 = "";
        //税额-交通
        String ZTAXJT = "";
        //税额-其他
        String ZTAXQT = "";
        //客户
        String KUNNR = "";


        String userid="";
        String userName="";

        //是否有发票
        String sfyfp="";

        //是否对公付款
        String dgfk = "";



//        RecordSetDataSource rsd = new RecordSetDataSource("SAP_jkb");
        RecordSet rs = new RecordSet();
        RecordSet rs1 = new RecordSet();
        YyWsUtil yywsUtil = new YyWsUtil();
        String sql = "select * from " + tableName + " where requestid=? ";
        rs.executeQuery(sql,requestId);
        if (rs.next()){
            //流程单据号
            ZDJH_OA = Util.null2String(rs.getString("bdbh"));
            //报销人员id
            userid=Util.null2String(rs.getString("bxry"));
            userName  = yywsUtil.getHrmName(userid, rs1);
            //凭证抬头文本

            BUKRS = Util.null2String(rs.getString("fkdwdm"));
            //供应商
            LIFNR = Util.null2String(rs.getString("gysbm"));
            //供应商名称
            NAME1 = Util.null2String(rs.getString("gysmc"));
            //供应商地址
            STRAS = Util.null2String(rs.getString("gysdz"));
            //银行科目
            ZYHKM = Util.null2String(rs.getString("yxkm"));
            //总额
            WRBTR = Util.null2String(rs.getString("hltzhzje"));
            //币种
            WAERS = Util.null2String(rs.getString("bz"));
            //成本中心
            KOSTL = Util.null2String(rs.getString("cdbm"));
            //产品研发项目
            ZZFI03 = Util.null2String(rs.getString("cpxm"));
            //内部订单
            AUFNR = Util.null2String(rs.getString("hsyfxm"));
            //经办人工号
            ZZFI02 = yywsUtil.getWokeCode(userid,rs1);
            //税额-交通
            ZTAXJT = Util.null2String(rs.getString("sejt"));
            //税额-其他
            ZTAXQT = Util.null2String(rs.getString("seqt"));
            //客户
            KUNNR = Util.null2String(rs.getString("kh"));
            //是否有发票
            sfyfp=Util.null2String(rs.getString("sfyfp"));

            LAND1 = Util.null2String(rs.getString("gysgb"));
            BANKL = Util.null2String(rs.getString("gyslhh"));
            BANKN = Util.null2String(rs.getString("gyszh"));

            dgfk = Util.null2String(rs.getString("dgfk"));
        }





//        String sql1 = " select * from SAP_MERCHANTS_INFO where externalCode=?";
//        if (rsd.execute(sql1, LIFNR) && rsd.next()) {
//            LAND1 = Util.null2String(rs.getString("country"));
//            BANKL = Util.null2String(rs.getString("bankCode"));
//            BANKN = Util.null2String(rs.getString("bankAcc"));
//        }
            logger.info(sfyfp);
            if ("0".equals(sfyfp)) {

                Map mainData = new LinkedHashMap();


                if ("0".equals(dgfk)){
                    logger.info("对公");
                    BKTXT ="付"+NAME1+"款";
                    ZWLKM = "2241010200";
                }

                if ("1".equals(dgfk)){
                    logger.info("对私");
                    BKTXT = userName+"报销费用";
                }

                mainData.put("ZDJH_OA", ZDJH_OA);
                mainData.put("ZLCLX", ZLCLX);
                mainData.put("BKTXT", BKTXT);
                mainData.put("BUKRS", BUKRS);
                if (!"".equals(LIFNR)) {
                    mainData.put("LIFNR", LIFNR);
                    mainData.put("NAME1", NAME1);
                    mainData.put("STRAS", STRAS);
                    mainData.put("LAND1", LAND1);
                    mainData.put("BANKL", BANKL);
                    mainData.put("BANKN", BANKN);
                }
                mainData.put("ZYHKM", ZYHKM);
                mainData.put("ZWLKM", ZWLKM);
                mainData.put("WRBTR", WRBTR);
                mainData.put("WAERS", WAERS);
                mainData.put("KOSTL", KOSTL);
                mainData.put("ZZFI02", ZZFI02);
                mainData.put("AUFNR", AUFNR);

                mainData.put("ZTAXJT", ZTAXJT);
                mainData.put("ZTAXQT", ZTAXQT);


                // 明细
                List list = new ArrayList();
                String detailSql = "select * from " + tableName + "_dt1 where mainid = ? ";
                rs.executeQuery(detailSql, mainId);
                while (rs.next()) {
                    //费用科目
                    String HKONT = "";
                    //不含税金额
                    String ZNET = "";

                    //费用科目
                    HKONT = Util.null2String(rs.getString("fykm"));
                    //不含税金额
                    ZNET = Util.null2String(rs.getString("fyje"));
                    String id = Util.null2String(rs.getString("id"));


                    Map entityMap = new LinkedHashMap();

                    entityMap.put("ZDJH_OA", ZDJH_OA);
                    entityMap.put("ZITEM_OA", id);
                    entityMap.put("HKONT", HKONT);
                    entityMap.put("ZNET", ZNET);

                    if ("0".equals(dgfk)){
                        logger.info("对公");
                        entityMap.put("KUNNR", KUNNR);
                    }

                    if ("1".equals(dgfk)){
                        logger.info("对私");
                        entityMap.put("ZYGKH", ZZFI02);
                    }

                    list.add(entityMap);
                }

                Map map = new HashMap();
                map.put("HEADER", mainData);
                map.put("ITEM", list);
                String params = JSON.toJSONString(map);

                String result = yywsUtil.doPostSAP(params, url);

                if (!"".equals(result)) {
                    JSONObject jsonObject = JSON.parseObject(result);
                    String ztype = jsonObject.getString("TYPE");
                    if ("S".equals(ztype)) {
                        String BELNR = jsonObject.getJSONArray("ITEM").getJSONObject(0).getString("BELNR");
                        logger.info("会计凭证的凭证编号:" + BELNR);
                        String upSql = "update " + tableName + " set hjpz=? where requestid =? ";
                        rs.executeUpdate(upSql, BELNR, requestId);
                    } else {
                        flag = false;
                        msg = jsonObject.getString("MSG");
                    }
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
}
