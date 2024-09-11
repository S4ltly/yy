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


public class FybxwpfkToSAPAction extends BaseBean implements Action {

    private Logger logger = LoggerFactory.getLogger(FybxwpfkToSAPAction.class);
    private RequestManager requestManager;

    @Override
    public String execute(RequestInfo requestInfo) {
        boolean flag = true;
        String url = "/sap/bc/zcl_if_http_api/FI001?sap-client=110";
        String currentMonth="";
        currentMonth= TimeUtil.getCurrentDateString();
        logger.info("FybxwpfkToSAPAction++++++++++++++++"+currentMonth);
        String msg = "";
        requestManager = requestInfo.getRequestManager();
        int requestId = requestManager.getRequestid();
        String tableName = requestManager.getBillTableName();
        int workflowid = requestManager.getWorkflowid();
        int mainId = requestManager.getBillid();
        logger.info("进入接口---FybxwpfkToSAPAction,requestId:"+requestId+","+ "表单名称：" + tableName + ",workflowid=" + workflowid);
        //主表
        //流程编号
        String ZPRONUM = "";
        //流程类型
        String ZLCLX = "FYWP01";
        //凭证抬头文本
        String BKTXT = "";
        //凭证中的过账日期
        String BUDAT = TimeUtil.getCurrentDateString();
        //公司代码
        String BUKRS = "";
        //往来单位
        String ZWLDW = "";
        //供应商帐户组
        String KTOKK = "";
        //名称
        String NAME1 = "";
        //银行账户
        String ZYHZH = "";
        //以本币计的金额
        String DMBTR = "";
        //成本中心
        String KOSTL = "";
        //订单编号
        String AUFNR = "";
        //预留字段1
        String ZYL01 = "";
        //预留字段2
        String ZYL02 = "";
        //预留字段3
        String ZYL03 = "";
        //预留字段4
        String ZYL04 = "";
        //预留字段5
        String ZYL05 = "";
        //预留字段6
        String ZYL06 = "";
        //预留字段7
        String ZYL07 = "";
        //预留字段8
        String ZYL08 = "";
        //预留字段9
        String ZYL09 = "";
        //预留字段10
        String ZYL10 = "";

        RecordSet rs = new RecordSet();
        String sql = "select * from "+tableName+" where requestid=? ";
        rs.executeQuery(sql,requestId);
        if (rs.next()){
            //流程编号
            ZPRONUM = Util.null2String(rs.getString("bdbh"));
            //流程类型
//            ZLCLX = Util.null2String(rs.getString("djbh"));
            //凭证抬头文本
            BKTXT = Util.null2String(rs.getString("gysmc"));
            //凭证中的过账日期
//            BUDAT = Util.null2String(rs.getString("djbh"));
            //公司代码
            BUKRS = Util.null2String(rs.getString("cdzt"));
            //往来单位
//            ZWLDW = Util.null2String(rs.getString("gys"));
            //供应商帐户组
//            KTOKK = Util.null2String(rs.getString("gyszh"));
            //名称
            NAME1 = Util.null2String(rs.getString("gysmc"));
            //银行账户
//            ZYHZH = Util.null2String(rs.getString("gysyx"));
            //以本币计的金额
//            DMBTR = Util.null2String(rs.getString("djbh"));
            //成本中心
//            KOSTL = Util.null2String(rs.getString("fysjgzbm"));

            //订单编号
//            AUFNR = Util.null2String(rs.getString("nbdd"));
//            //预留字段1
//            ZYL01 = Util.null2String(rs.getString("djbh"));
//            //预留字段2
//            ZYL02 = Util.null2String(rs.getString("djbh"));
//            //预留字段3
//            ZYL03 = Util.null2String(rs.getString("djbh"));
//            //预留字段4
//            ZYL04 = Util.null2String(rs.getString("djbh"));
//            //预留字段5
//            ZYL05 = Util.null2String(rs.getString("djbh"));
//            //预留字段6
//            ZYL06 = Util.null2String(rs.getString("djbh"));
//            //预留字段7
//            ZYL07 = Util.null2String(rs.getString("djbh"));
//            //预留字段8
//            ZYL08 = Util.null2String(rs.getString("djbh"));
//            //预留字段9
//            ZYL09 = Util.null2String(rs.getString("djbh"));
//            //预留字段10
//            ZYL10 = Util.null2String(rs.getString("djbh"));
        }


            logger.info("传应付单");
            Map mainData = new LinkedHashMap();
            mainData.put("ZPRONUM",ZPRONUM);
            mainData.put("ZLCLX",ZLCLX);
            mainData.put("BKTXT",BKTXT);
            mainData.put("BUDAT",BUDAT);
            mainData.put("BUKRS",BUKRS);
            mainData.put("ZWLDW",ZWLDW);
            mainData.put("KTOKK",KTOKK);
            mainData.put("NAME1",NAME1);
            mainData.put("ZYHZH",ZYHZH);
            mainData.put("DMBTR",DMBTR);
            mainData.put("KOSTL",KOSTL);
            mainData.put("AUFNR",AUFNR);
            mainData.put("ZYL01",ZYL01);
            mainData.put("ZYL02",ZYL02);
            mainData.put("ZYL03",ZYL03);
            mainData.put("ZYL04",ZYL04);
            mainData.put("ZYL05",ZYL05);
            mainData.put("ZYL06",ZYL06);
            mainData.put("ZYL07",ZYL07);
            mainData.put("ZYL08",ZYL08);
            mainData.put("ZYL09",ZYL09);
            mainData.put("ZYL10",ZYL10);

            // 明细
            List list = new ArrayList();
            String detailSql = "select * from "+tableName+"_dt1 where mainid = ? ";
            rs.executeQuery(detailSql,mainId);
            while (rs.next()) {
                //流程编号
                String ZPRONUM1 = "";
                //会计凭证中的行项目编号
                String BUZEI = "";
                //总账科目
                String HKONT = "";
                //发票类型
                String ZFPLX = "";
                //不含税金额
                String ZNET = "";
                //税额
                String ZTAX = "";
                //预留字段11
                String ZYL11 = "";
                //预留字段12
                String ZYL12 = "";
                //预留字段13
                String ZYL13 = "";
                //预留字段14
                String ZYL14 = "";
                //预留字段15
                String ZYL15 = "";
                //预留字段16
                String ZYL16 = "";
                //预留字段17
                String ZYL17 = "";
                //预留字段18
                String ZYL18 = "";
                //预留字段19
                String ZYL19 = "";
                //预留字段20
                String ZYL20 = "";

//                ZPRONUM1 = Util.null2String(rs.getString("yskmbm"));
//                BUZEI = Util.null2String(rs.getString("yskmbm"));
//                HKONT = Util.null2String(rs.getString("fykm"));
//                ZFPLX = Util.null2String(rs.getString("hsdj"));
//                ZNET = Util.null2String(rs.getString("gzbmbm"));
                ZTAX = Util.null2String(rs.getString("se"));
//                ZYL11 = Util.null2String(rs.getString("yskmbm"));
//                ZYL12 = Util.null2String(rs.getString("jjsl"));
//                ZYL13 = Util.null2String(rs.getString("hsdj"));
//                ZYL14 = Util.null2String(rs.getString("gzbmbm"));
//                ZYL15 = Util.null2String(rs.getString("yskmbm"));
//                ZYL16 = Util.null2String(rs.getString("yskmbm"));
//                ZYL17 = Util.null2String(rs.getString("jjsl"));
//                ZYL18 = Util.null2String(rs.getString("hsdj"));
//                ZYL19 = Util.null2String(rs.getString("gzbmbm"));
//                ZYL20 = Util.null2String(rs.getString("gzbmbm"));
                Map entityMap = new LinkedHashMap();
                entityMap.put("ZPRONUM",ZPRONUM1);
                entityMap.put("BUZEI",BUZEI);
                entityMap.put("HKONT",HKONT);
                entityMap.put("ZFPLX",ZFPLX);
                entityMap.put("ZNET",ZNET);
                entityMap.put("ZTAX",ZTAX);
                entityMap.put("ZYL11",ZYL11);
                entityMap.put("ZYL12",ZYL12);
                entityMap.put("ZYL13",ZYL13);
                entityMap.put("ZYL14",ZYL14);
                entityMap.put("ZYL15",ZYL15);
                entityMap.put("ZYL16",ZYL16);
                entityMap.put("ZYL17",ZYL17);
                entityMap.put("ZYL18",ZYL18);
                entityMap.put("ZYL19",ZYL19);
                entityMap.put("ZYL20",ZYL20);
                list.add(entityMap);
            }
            mainData.put("ITEM",list);
            String params = JSON.toJSONString(mainData);
            logger.info("Param:"+ params);
            YyWsUtil yyWsUtil = new YyWsUtil();
            String result = yyWsUtil.doPostSAP(params,url);
            logger.info("result:"+result);

            if (!"".equals(result)){
                JSONObject jsonObject = JSON.parseObject(result);
                String ztype = jsonObject.getString("ztype");
                if ("S".equals(ztype)){
                    String BELNR= jsonObject.getString("BELNR");
                    logger.info("会计凭证的凭证编号:"+BELNR);
                    String upSql = "update "+tableName+" set bzbh=? where requestid =? ";
                    rs.executeUpdate(upSql,BELNR,requestId);
                }else {
                    flag = false;
                    msg = jsonObject.getString("ZMSG");
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
