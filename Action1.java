package weaver.interfaces.yy;

import com.alibaba.fastjson.JSON;
import weaver.general.BaseBean;
import weaver.integration.logging.Logger;
import weaver.integration.logging.LoggerFactory;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;

public class Action1 extends BaseBean implements Action {
    private Logger logger = LoggerFactory.getLogger(FybxToSAPAction.class);
    private RequestManager requestManager;
    public String execute(RequestInfo requestInfo){
        boolean flag = true;
        String msg = "";
        Map dataMap=new LinkedHashMap();
        List dataList=new ArrayList();

        List columnList=new ArrayList();
        Map aMap=new LinkedHashMap();
        aMap.put("name","account_code");
        aMap.put("title","会计科目代码");
        aMap.put("title","1601070101");
        Map bMap=new LinkedHashMap();
        bMap.put("name","account_name");
        bMap.put("title","会计科目名称");
        bMap.put("title","11");
        columnList.add(aMap);
        columnList.add(bMap);
        Map detailsMap=new LinkedHashMap();
        detailsMap.put("type","dt1");
        detailsMap.put("column",columnList);
        List detailsList=new ArrayList();
        detailsList.add(detailsMap);
        Map metadataMap=new LinkedHashMap();
        List fieldList=new ArrayList();
        Map cMap=new LinkedHashMap();
        cMap.put("name","voucher_word");
        cMap.put("title","显示名2");
        cMap.put("content","2111");
        Map dMap=new LinkedHashMap();
        cMap.put("name","voucher_code");
        cMap.put("title","显示名3");
        cMap.put("content","32222");
        fieldList.add(cMap);
        fieldList.add(dMap);
        metadataMap.put("details",detailsList);

        metadataMap.put("type","12");
        metadataMap.put("field",fieldList);
        metadataMap.put("details",detailsList);

        List filesList=new ArrayList();
        List documentList=new ArrayList();
        Map documentMap=new LinkedHashMap();
        Map eMap=new LinkedHashMap();
        eMap.put("needDownLoad","true");
        eMap.put("ftpName","");
        List paramList=new ArrayList();
        Map gMap=new LinkedHashMap();
        gMap.put("name","path");
        gMap.put("title","附件地址");
        gMap.put("content","http://xxx");

        Map hMap=new LinkedHashMap();
        hMap.put("name","arc_document_name");
        hMap.put("title","附件名称");
        hMap.put("content","01.jpg");
        paramList.add(gMap);
        paramList.add(hMap);
        Map paramMap=new LinkedHashMap();
        paramMap.put("param",paramList);
        eMap.put("param",paramMap);

        Map fMap=new LinkedHashMap();
        fMap.put("needDownLoad","true");
        fMap.put("ftpName","");
        List paramList2=new ArrayList();
        Map iMap=new LinkedHashMap();
        iMap.put("name","path");
        iMap.put("title","附件地址");
        iMap.put("content","http://xxx");
        Map jMap=new LinkedHashMap();
        jMap.put("name","arc_document_name");
        jMap.put("title","附件名称");
        jMap.put("content","01.jpg");
        paramList2.add(iMap);
        paramList2.add(jMap);
        Map paramMap2=new LinkedHashMap();
        paramMap.put("param",paramList2);
        fMap.put("param",paramMap2);

        documentList.add(eMap);
        documentList.add(fMap);

        Map arcMap=new LinkedHashMap();
        arcMap.put("uniqueK","");
        arcMap.put("uniqueV","1111");
        arcMap.put("fondsCode","hll");
        arcMap.put("categoryCode","WSWJ");
        arcMap.put("transferCatCode","");
        arcMap.put("transferFondsCode","");
        arcMap.put("appId","");

        List elementList=new ArrayList();
        Map kMap=new LinkedHashMap();
        kMap.put("name","folder_title");
        kMap.put("title","文件题目");
        kMap.put("content","关于xx的函");
        Map lMap=new LinkedHashMap();
        lMap.put("name","year");
        lMap.put("title","年度");
        lMap.put("content","2021");
        Map mMap=new LinkedHashMap();
        mMap.put("name","file_date");
        mMap.put("title","文件日期");
        mMap.put("content","2021-08-15");
        elementList.add(kMap);
        elementList.add(lMap);
        elementList.add(mMap);

        arcMap.put("element",elementList);
        documentMap.put("document",documentList);
        filesList.add(documentMap);
        filesList.add(metadataMap);

        dataMap.put("arc",arcMap);
        dataMap.put("files",filesList);
        dataList.add(dataMap);

        if (flag) {
            return Action.SUCCESS;
        }else {
            requestManager.setMessageid("001");
            requestManager.setMessage("接口推送异常");
            requestManager.setMessagecontent("异常信息："+msg);
            return Action.FAILURE_AND_CONTINUE;
        }
    }

    public static void main(String[] args) {

        //header
        Map header = new HashMap<>();
        //获取时间戳
        String currentTimeTamp = getTimestamp();
        //系统标识
        String systemid = "yunyi";
        //密码
        String d_password = "123456";
        //封装header里的参数
        header.put("systemid", systemid);
        header.put("currentDateTime", currentTimeTamp);
        String md5Source = systemid + d_password + currentTimeTamp;
        String md5OfStr = getMD5Str(md5Source).toLowerCase();
        //Md5是：系统标识+密码+时间戳 并且md5加密的结果
        header.put("Md5", md5OfStr);
        System.out.println(JSON.toJSON(header));
    }
    public static String getMD5Str(String plainText){
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
    /**
     * 获取当前日期时间。 YYYY-MM-DD HH:MM:SS
     * @return		当前日期时间
     */
    public static String getCurDateTime() {
        Date newdate = new Date();
        long datetime = newdate.getTime();
        Timestamp timestamp = new Timestamp(datetime);
        return (timestamp.toString()).substring(0, 19);
    }
    /**
     * 获取时间戳   格式如：19990101235959
     * @return
     */
    public static String getTimestamp(){
        return getCurDateTime().replace("-", "").replace(":", "").replace(" ", "");
    }
}
