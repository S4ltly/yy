package weaver.interfaces.yy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;

public class YunyiTestAction {


    public static void main(String[] args) {
        JSONArray jsonArray = JSON.parseArray("[{\n" +
                "  \"oaqqid\":\"11111\",\n" +
                "  \"oamxid\":\"22\",\n" +
                "  \"je\":\"1\",\n" +
                "  \"fs\":\"0\",\n" +
                "}]");
        YunyiTestAction yunyiTestAction = new YunyiTestAction();
        System.out.println(yunyiTestAction.doAction(jsonArray));
    }

    public String doAction(JSONArray jsonArray){
        //当前日期
        String currentDate = getCurrentDate();
        //当前时间
        String currentTime = getCurrentTime();
        //获取时间戳
        String currentTimeTamp = getTimestamp();



        Map params = new HashMap<>();
        Map mainparams = new HashMap<>();
        List paramsList = new ArrayList();


        //header
        Map header = new HashMap<>();

        //系统标识
        String systemid = "yunyi";
        //密码
        String d_password = "123456";
        //封装header里的参数
        header.put("systemid", systemid);
        header.put("currentDateTime", currentTimeTamp);
        String md5Source = systemid + d_password + currentTimeTamp;

        System.out.println(currentTimeTamp);

        String md5OfStr = getMD5Str(md5Source).toLowerCase();
        //Md5是：系统标识+密码+时间戳 并且md5加密的结果
        header.put("Md5", md5OfStr);
        mainparams.put("header", header);


        //封装operationinfo参数
        JSONObject operationinfo = new JSONObject();
        operationinfo.put("operator", "1");

        for (int i = 0; i < jsonArray.size(); i++) {
            Map paramDatajson = new HashMap<>();
            JSONObject maintable = new JSONObject();
            JSONObject dataObj = jsonArray.getJSONObject(i);
//            maintable.put("ygoriginid", dataObj.getString("emp_origin_id"));
//            maintable.put("yxzh", dataObj.getString("account"));
//            maintable.put("khdz", dataObj.getString("address"));
//            maintable.put("khyx", dataObj.getString("account_name"));
//            maintable.put("cjsj", dataObj.getString("create_date"));
//            maintable.put("xgsj", dataObj.getString("modify_date"));
//            maintable.put("khxxh", dataObj.getString("c_khhhh"));
            //封装mainTable参数
            paramDatajson.put("mainTable", dataObj);
            paramDatajson.put("operationinfo", operationinfo);
            paramsList.add(paramDatajson);
        }


        // logger.info("===请求参数datajson===" + jsonArray.toJSONString());
        mainparams.put("data", paramsList);
        params.put("datajson", mainparams);
        CloseableHttpResponse response;// 响应类,
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //restful接口url
        HttpPost httpPost = new HttpPost("http://172.16.0.20:8081/api/cube/restful/interface/saveOrUpdateModeData/SAPFKXX");



        //装填参数
        List nvps = new ArrayList();

        if(params!=null){

            //lambda表达式遍历
            params.forEach((key, value) -> {
                nvps.add(new BasicNameValuePair((String) key, JSONObject.toJSONString(value)));
            });

        }

        try{

            httpPost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            response = httpClient.execute(httpPost);

            if (response != null && response.getEntity() != null) {
                //返回信息
                String resulString = EntityUtils.toString(response.getEntity());
                // logger.info("oa返回参数："+resulString);
                return resulString;

            }else{
                // logger.info("获取数据失败，请查看日志");
            }

        }catch (Exception e){
            // logger.info("请求失败====errormsg:"+e.getMessage());
        }
        return null;

    }

    public String getMD5Str(String plainText){
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);
        // 如果生成数字未满32位，需要前面补0
        // 不能把变量放到循环条件，值改变之后会导致条件变化。如果生成30位 只能生成31位md5
        int tempIndex = 32 - md5code.length();
        for (int i = 0; i < tempIndex; i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public  String getCurrentTime() {
        Date newdate = new Date();
        long datetime = newdate.getTime();
        Timestamp timestamp = new Timestamp(datetime);
        String currenttime = (timestamp.toString()).substring(11, 13) + ":" + (timestamp.toString()).substring(14, 16) + ":"
                + (timestamp.toString()).substring(17, 19);
        return currenttime;
    }

    public  String getCurrentDate() {
        Date newdate = new Date();
        long datetime = newdate.getTime();
        Timestamp timestamp = new Timestamp(datetime);
        String currentdate = (timestamp.toString()).substring(0, 4) + "-" + (timestamp.toString()).substring(5, 7) + "-"
                + (timestamp.toString()).substring(8, 10);
        return currentdate;
    }
    /**
     * 获取当前日期时间。 YYYY-MM-DD HH:MM:SS
     * @return		当前日期时间
     */
    public  String getCurDateTime() {
        Date newdate = new Date();
        long datetime = newdate.getTime();
        Timestamp timestamp = new Timestamp(datetime);
        return (timestamp.toString()).substring(0, 19);
    }
    /**
     * 获取时间戳   格式如：19990101235959
     * @return
     */
    public  String getTimestamp(){
        return getCurDateTime().replace("-", "").replace(":", "").replace(" ", "");
    }

    public  int getIntValue(String v, int def) {
        try {
            return Integer.parseInt(v);
        } catch (Exception ex) {
            return def;
        }
    }


    public  String null2String(Object s) {
        return s == null ? "" : s.toString();

    }
}
