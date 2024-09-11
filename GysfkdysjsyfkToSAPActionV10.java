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


public class GysfkdysjsyfkToSAPActionV10 extends BaseBean implements Action {

    private Logger logger = LoggerFactory.getLogger(GysfkdysjsyfkToSAPActionV10.class);
    private RequestManager requestManager;

    @Override
    public String execute(RequestInfo requestInfo) {
        boolean flag = true;
        String url = "/sap/bc/zcl_if_http_api/FI001?sap-client=300";
        String currentMonth="";
        currentMonth= TimeUtil.getCurrentDateString();
        logger.info("GysfkdysjsyfkToSAPAction++++++++++++++++"+currentMonth);
        String msg = "";
        requestManager = requestInfo.getRequestManager();
        int requestId = requestManager.getRequestid();//一条流程的id
        String tableName = requestManager.getBillTableName();
        int workflowid = requestManager.getWorkflowid();//一整个流程的id
        int mainId = requestManager.getBillid();
        logger.info("进入接口---GysfkdysjsyfkToSAPAction,requestId:"+requestId+","+ "表单名称：" + tableName + ",workflowid=" + workflowid);
        //主表

        //流程类型
        String ZLCLX = "GYSFK1";
        //流程主题
        String BKTXT = "";
        //公司代码
        String BUKRS = "";
        //供应商
        String LIFNR = "";
        //供应商名称
        String NAME1 = "";
        //银行科目
        String ZYHKM = "";
        //总额
        String WRBTR = "";
        //币种
        String WAERS = "";
        //承兑编号
        String ZBLNO = "";
        //对应承兑类型
        String dycdlx="";
        //付款类型
        String fklx = "";

        String bdbh = "";

        String zjpjje = "";

        String pjqysdykhbm = "";

        RecordSet rs = new RecordSet();
        String sql = "select * from "+tableName+" where requestid=? ";
        rs.executeQuery(sql,requestId);
        if (rs.next()){
            //凭证抬头文本
            BKTXT = "付"+Util.null2String(rs.getString("gysmc"))+"货款";
            //公司代码
            BUKRS = Util.null2String(rs.getString("gsdm"));
            //供应商
            LIFNR = Util.null2String(rs.getString("gysbm"));
            //供应商名称
            NAME1 = Util.null2String(rs.getString("gysmc"));
            //银行科目
            ZYHKM = Util.null2String(rs.getString("yxkm"));
            //总额
            WRBTR = Util.null2String(rs.getString("yfze"));
            //币种
            WAERS = Util.null2String(rs.getString("bz"));
            //承兑编号
            ZBLNO = Util.null2String(rs.getString("pjdh"));
            //付款类型
            fklx = Util.null2String(rs.getString("fklx"));
            //对应承兑类型
            dycdlx=Util.null2String(rs.getString("dycdlx"));

            bdbh=Util.null2String(rs.getString("lcbh"));

            zjpjje = Util.null2String(rs.getString("zjpjje"));

            pjqysdykhbm = Util.null2String(rs.getString("pjqysdykhbm"));


        }


             if ("2".equals(fklx)) {

                 Map mainData = new LinkedHashMap();

                 mainData.put("ZDJH_OA", bdbh);
                 mainData.put("ZLCLX", ZLCLX);
                 mainData.put("BKTXT", BKTXT);
                 mainData.put("BUKRS", BUKRS);
                 mainData.put("LIFNR", LIFNR);
                 mainData.put("NAME1", NAME1);
                 mainData.put("ZYHKM", ZYHKM);
                 mainData.put("WRBTR", WRBTR);
                 mainData.put("WAERS", "CNY");


                 List list = new ArrayList();

                 List item2List = new ArrayList();

                 String EBELN = "";
                 String detailSql = "select * from " + tableName + "_dt2 where mainid = ? ";
                 rs.executeQuery(detailSql, mainId);
                 while (rs.next()) {
                     Map entityMap = new LinkedHashMap();
                     //采购订单

                     String hid ="";
                     String  ZNET2="";
//
//                     //流程单据序号
                     hid=Util.null2String(rs.getString("id"));
                     //不含税金额
                     ZNET2=Util.null2String(rs.getString("bcfkje"));
                     //采购订单
                     EBELN = Util.null2String(rs.getString("ddbm"));

                     entityMap.put("EBELN", EBELN);
                     entityMap.put("ZDJH_OA", bdbh);
                     entityMap.put("ZNET2", ZNET2);
                     entityMap.put("ZITEM2_OA", hid);


                     item2List.add(entityMap);

                 }

                 logger.info("银行科目"+ZYHKM);
                 if (ZYHKM.length() > 0) {
                     Map entityMap = new LinkedHashMap();
                     entityMap.put("ZDJH_OA", bdbh);
                     entityMap.put("ZITEM_OA", "1");
                     entityMap.put("ZNET", zjpjje);

                     entityMap.put("ZLSCH", "T");
                     list.add(entityMap);
                 } else{
                     List ZBLNOList = Util.TokenizerString(ZBLNO, ",");
                     List dycdlxList = Util.TokenizerString(dycdlx, ",");
                     List pjqysList =  Util.TokenizerString(pjqysdykhbm, ",");
                     List zjpjjeList =  Util.TokenizerString(zjpjje, ",");


                        for (int i=0;i<ZBLNOList.size();i++){
                            Map entityMap = new LinkedHashMap();
                            entityMap.put("ZDJH_OA", bdbh);
                            entityMap.put("ZITEM_OA", i+"");
                            entityMap.put("ZNET", zjpjjeList.get(i));
                            entityMap.put("KUNNR", pjqysList.get(i));
                            entityMap.put("EBELN", EBELN);
                            entityMap.put("ZLSCH", dycdlxList.get(i));
                            entityMap.put("ZBLNO", ZBLNOList.get(i));
                            list.add(entityMap);
                        }

                 }



                 Map map = new HashMap();
                 map.put("HEADER",mainData);
                 map.put("ITEM", list);
                 map.put("ITEM2",item2List);

                 String params = JSON.toJSONString(map);

                 YyWsUtil yywsUtil = new YyWsUtil();
                 String result = yywsUtil.doPostSAP(params, url);

                 if (!"".equals(result)) {
                     JSONObject jsonObject = JSON.parseObject(result);
                     String ztype = jsonObject.getString("TYPE");
                     if ("S".equals(ztype)) {
                         String BELNR = jsonObject.getJSONArray("ITEM").getJSONObject(0).getString("BELNR");
                         logger.info("会计凭证的凭证编号:" + BELNR);
                         String upSql = "update " + tableName + " set hjpzh=? where requestid =? ";
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
