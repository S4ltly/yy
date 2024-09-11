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


public class FybxToSAPAction extends BaseBean implements Action {

    //日志
    private Logger logger = LoggerFactory.getLogger(FybxToSAPAction.class);
    //流程信息
    private RequestManager requestManager;

    @Override
    public String execute(RequestInfo requestInfo) {
        boolean flag = true;
        String url = "/sap/bc/zcl_if_http_api/FI001?sap-client=110";
        //取当前时间
        String currentMonth="";
        currentMonth= TimeUtil.getCurrentDateString();
        logger.info("FybxToSAPAction++++++++++++++++"+currentMonth);
        String msg = "";
        //取当前流程信息
        requestManager = requestInfo.getRequestManager();
        //流程requestId
        int requestId = requestManager.getRequestid();
        //流程表名
        String tableName = requestManager.getBillTableName();
        //流程workflowid
        int workflowid = requestManager.getWorkflowid();
        //流程表单id字段
        int mainId = requestManager.getBillid();
        logger.info("进入接口---FybxToSAPAction,requestId:"+requestId+","+ "表单名称：" + tableName + ",workflowid=" + workflowid);
        //主表
        //流程编号
        String ZPRONUM = "";
        //流程类型
        String ZLCLX = "";
        //凭证抬头文本
        String BKTXT = "";
        //凭证中的过账日期
        String BUDAT = "";
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

        //执行数据库语句
        RecordSet rs = new RecordSet();
        String sql = "select * from "+tableName+" where requestid=? ";
        rs.executeQuery(sql,requestId);
        if (rs.next()){
            ZPRONUM = Util.null2String(rs.getString("djbh"));
            //流程类型
            ZLCLX = Util.null2String(rs.getString("djbh"));
            //凭证抬头文本
            BKTXT = Util.null2String(rs.getString("djbh"));
            //凭证中的过账日期
            BUDAT = Util.null2String(rs.getString("djbh"));
            //公司代码
            BUKRS = Util.null2String(rs.getString("djbh"));
            //往来单位
            ZWLDW = Util.null2String(rs.getString("djbh"));
            //供应商帐户组
            KTOKK = Util.null2String(rs.getString("djbh"));
            //名称
            NAME1 = Util.null2String(rs.getString("djbh"));
            //银行账户
            ZYHZH = Util.null2String(rs.getString("djbh"));
            //以本币计的金额
            DMBTR = Util.null2String(rs.getString("djbh"));
            //成本中心
            KOSTL = Util.null2String(rs.getString("djbh"));
            //订单编号
            AUFNR = Util.null2String(rs.getString("djbh"));
            //预留字段1
            ZYL01 = Util.null2String(rs.getString("djbh"));
            //预留字段2
            ZYL02 = Util.null2String(rs.getString("djbh"));
            //预留字段3
            ZYL03 = Util.null2String(rs.getString("djbh"));
            //预留字段4
            ZYL04 = Util.null2String(rs.getString("djbh"));
            //预留字段5
            ZYL05 = Util.null2String(rs.getString("djbh"));
            //预留字段6
            ZYL06 = Util.null2String(rs.getString("djbh"));
            //预留字段7
            ZYL07 = Util.null2String(rs.getString("djbh"));
            //预留字段8
            ZYL08 = Util.null2String(rs.getString("djbh"));
            //预留字段9
            ZYL09 = Util.null2String(rs.getString("djbh"));
            //预留字段10
            ZYL10 = Util.null2String(rs.getString("djbh"));
        }


            logger.info("传应付单");
        //拼接入参
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

            // 取明细数据
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

                ZPRONUM1 = Util.null2String(rs.getString("yskmbm"));
                BUZEI = Util.null2String(rs.getString("yskmbm"));
                HKONT = Util.null2String(rs.getString("jjsl"));
                ZFPLX = Util.null2String(rs.getString("hsdj"));
                ZNET = Util.null2String(rs.getString("gzbmbm"));
                ZTAX = Util.null2String(rs.getString("yskmbm"));
                ZYL11 = Util.null2String(rs.getString("yskmbm"));
                ZYL12 = Util.null2String(rs.getString("jjsl"));
                ZYL13 = Util.null2String(rs.getString("hsdj"));
                ZYL14 = Util.null2String(rs.getString("gzbmbm"));
                ZYL15 = Util.null2String(rs.getString("yskmbm"));
                ZYL16 = Util.null2String(rs.getString("yskmbm"));
                ZYL17 = Util.null2String(rs.getString("jjsl"));
                ZYL18 = Util.null2String(rs.getString("hsdj"));
                ZYL19 = Util.null2String(rs.getString("gzbmbm"));
                ZYL20 = Util.null2String(rs.getString("gzbmbm"));
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

            //转json字符串
            String params = JSON.toJSONString(mainData);
            logger.info("Param:"+ params);

            //sap请求
            YyWsUtil yyWsUtil = new YyWsUtil();
            //接口返回信息
            String result = yyWsUtil.doPostSAP(params,url);
            logger.info("result:"+result);

            if (!"".equals(result)){

                JSONObject jsonObject = JSON.parseObject(result);

                String ztype = jsonObject.getString("ztype");
                if ("S".equals(ztype)){

                    String BELNR= jsonObject.getString("BELNR");

                    logger.info("会计凭证的凭证编号:"+BELNR);

                    String upSql = "update "+tableName+" set bzbh=? where requestid =? ";
                    //执行update语句
                    rs.executeUpdate(upSql,BELNR,requestId);
                }else {
                    flag = false;
                    msg = jsonObject.getString("ZMSG");
                }
            }

        if (flag) {
            //返回成功
            return Action.SUCCESS;
        }else {
            //输出返回错误信息
            requestManager.setMessageid("001");
            requestManager.setMessage("接口推送异常");
            requestManager.setMessagecontent("异常信息："+msg);
            //返回错误
            return Action.FAILURE_AND_CONTINUE;
        }
    }
}
