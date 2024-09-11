package weaver.interfaces.yy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.integration.logging.Logger;
import weaver.integration.logging.LoggerFactory;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FkmxToSAPAction implements Action{

    private Logger logger = LoggerFactory.getLogger(FkmxToSAPAction.class);
    private RequestManager requestManager;

    @Override
    public String execute(RequestInfo requestInfo) {

        requestManager = requestInfo.getRequestManager();
        requestManager.getLastoperator();
        String requestname = requestManager.getRequestname();
        //2.拿到当前流程主表表名
        String table = requestManager.getBillTableName();
        int workflowid = requestManager.getWorkflowid();
        int requestid = requestManager.getRequestid();
        User user = requestManager.getUser();
        int userId = requestManager.getUserId();
        int mainId = requestManager.getBillid();
        boolean flag = true;
        String msg = "";
        //1.调用第三方Api获取token
        logger.info("---M进入Action=-TsDaAction-   当前流程requetid=" + requestid + ",表单名称：" + table + ",workflowid=" + workflowid);

        RecordSet recordSet = new RecordSet();
        String sql ="select * from "+table+"_dt1 where mainid = ? ";
        List dataList = new ArrayList();
        if (recordSet.executeQuery(sql,mainId)){
            while (recordSet.next()){
                String ZDJH = "";
                String ZITEM = "";
                ZDJH = Util.null2String(recordSet.getString("fksqdh"));
                ZITEM = Util.null2String(recordSet.getString("fksqdhhxh"));
                Map map = new HashMap();
                map.put("ZDJH",ZDJH);
                map.put("ZITEM",ZITEM);
                dataList.add(map);
            }
        }
        if (dataList.size()>0){
            Map mainMap = new HashMap();
            mainMap.put("DATA",dataList);
            String params = JSON.toJSONString(mainMap);
            logger.info("params:"+params);
            String url = "/sap/bc/zcl_if_http_api/FI001?sap-client=300";
            //sap请求
            YyWsUtil yyWsUtil = new YyWsUtil();
            //接口返回信息
            String result = yyWsUtil.doPostSAP(params,url);
            logger.info("result:"+result);
            if (!"".equals(result)){

                JSONObject jsonObject = JSON.parseObject(result);

                String ztype = jsonObject.getString("TYPE");
                if (!"S".equals(ztype)){
                    flag = false;
                    msg = jsonObject.getString("MSG");
                }
            }
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
