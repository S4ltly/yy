package weaver.interfaces.yy;



import cn.hutool.http.HttpRequest;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.integration.logging.Logger;
import weaver.integration.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class YyWsUtil {
    private Logger logger = LoggerFactory.getLogger(YyWsUtil.class);

    /**
     * SAP请求
     * @param params
     * @param url
     * @return
     */
    public String  doPostSAP(String params,String url){
        String result="";
        logger.info(">>>>parajson="+params);
        try {
            result = HttpRequest.post("https://172.16.43.33:44300"+url)
                    .header("Content-Type","application/json")
                    .header("sender","OA")
                    .header("route","X")
                    .header("Authorization","Basic QUJBUDAyX0pLOkFzZGYxMjM0NTY=")
                    .body(params)
                    .execute().body();
            logger.info("sap接口返回信息"+result);
        }catch (Exception e){
            logger.info(e);
        }
        return result;
    }


    /**
     * ZJ请求
     * @param params
     * @param url
     * @return
     */
    public String  doPostZJ(String params,String url){
        String result="";
        logger.info(">>>>parajson="+params);
        try {
            result = HttpRequest.post("http://172.16.0.136:8091"+url)
                    .header("Content-Type","application/json")
                    .body(params)
                    .execute().body();
            logger.info("ZJ接口返回信息"+result);
        }catch (Exception e){
        }
        return result;
    }

    public String getWokeCode(String uid, RecordSet recordSet){
        String workcode="";
        String sql = "select workcode from hrmresource where id =? ";
        if (recordSet.executeQuery(sql,uid)&&recordSet.next()){
            workcode = Util.null2String(recordSet.getString("workcode"));
        }
        return workcode;
    }

    public String getHrmName(String uid, RecordSet recordSet){
        String userName="";
        String sql = "select lastname from hrmresource where id =? ";
        if (recordSet.executeQuery(sql,uid)&&recordSet.next()){
            userName = Util.null2String(recordSet.getString("lastname"));
        }
        return userName;
    }

    public static void main(String[] args) {
        YyWsUtil yyWsUtil = new YyWsUtil();
        Map map = new HashMap();
        map.put("BUKRS","1000");
        map.put("LIFNR","10056");
        System.out.println(yyWsUtil.doPostSAP("[{\n" +
                "    \"bukrs\": \"\"}]","/sap/bc/zcl_if_http_api/CO001?sap-client=300"));
    }
}
