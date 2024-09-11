package weaver.interfaces.yy;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
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

public class FybxToZjActionV6 extends BaseBean implements Action {

    private Logger logger = LoggerFactory.getLogger(FybxToZjActionV6.class);
    private RequestManager requestManager;

    @Override
    public String execute(RequestInfo requestInfo) {
        boolean flag = true;
        String currentMonth="";
        currentMonth= TimeUtil.getCurrentDateString();
        logger.info("FybxToZjActionV4++++++++++++++++"+currentMonth);
        String msg = "";
        requestManager = requestInfo.getRequestManager();
        int requestId = requestManager.getRequestid();
        String tableName = requestManager.getBillTableName();
        int workflowid = requestManager.getWorkflowid();
        int mainId = requestManager.getBillid();//明细表id
        int userId = requestManager.getUserId();

        YyWsUtil yyWsUtil = new YyWsUtil();

        RecordSet rs = new RecordSet();

        String url = "";
        logger.info("进入接口---BytterAction,requestId:"+requestId+","+ "表单名称：" + tableName + ",workflowid=" + workflowid);
        //主表
        String serial_no_erp = IdUtil.fastUUID().replace("-","");//OA序列号，唯一值
        String oa_bill_no = "";//OA单号，唯一值
        String affairId = "";//OA流程号，用于回传
        String forson_id = "1";//OA行项目号，用于回传
        String corp_code = "";//付款单位代码
        String bank_acc = "";//付款人银行帐号
        String acc_name = "";//付款人名称
        String bank_name = "";//付款人开户行名称
        String opp_bank_acc = "";//收款人账号
        String opp_acc_name = "";//收款人户名
        String audit_user = yyWsUtil.getWokeCode(userId+"",rs);//财务审批人
        String payee_bank_code = "";//收款人联行号
        String voucher_type = "";//付款方式
        String cur_code = "";//汇款币别
        String isforindividual = "";//对公对私标志
        double amt = 0.00;//付款金额
        String abs = "";//摘要/备注
        String purpose = "";//用途
        String wish_date = TimeUtil.getCurrentDateString().replace("-","");//期望支付日期
        String pay_oa_type_monthe="";//oa付款类型全称
        String pay_oa_type="";//oa付款类型前缀
        String Pay_from_TYPE="OA";//业务单据来源
        String EBELN="";//采购订单号
        String VERTN="";//合同号


        String remit_type="";//汇款类型
        String payer_acc="";//汇款人账号
        String payer_acc_name="";//汇款人户名
        String payee_acc="";//收款人账号
        String payee_acc_name="";//收款人户名
        String payer_add_en="";//汇款人地址
        String payer_bic="";//汇款人SWIFT_CODE
        String payee_add="";//收款人地址
        String payee_swift_code="";//收款开户行SWIFT CODE
        String payee_bank="";//收款开户行名称
        String payee_bank_add="";//收款开户行地址
        String payee_country_code="";//收款人国别
        String payee_city_name="";//收款人城市
        String pay_money="";//汇款金额
        String rmk_cn="";//汇款附言
        String rmk="";//备注
        String cost_sign="";//国内外费用承担标志
        String pay_type="";//付款类型
        String is_bonded="";//是否为保税货物项下付款
        String bus_no="";//外汇局批件/备案表号/业务编号
        String reverse1="";//备用字段1
        String reverse2="";//备用字段2
        String reverse3="";//备用字段3


        String SAP_usName= yyWsUtil.getWokeCode(userId+"",rs);//SAP凭证过账人
        String SAP_ZDJLY=requestId+"";//SAP唯一标识
        String SAP_ZDJH=requestId+"";//SAP付款申请单据号
        String SAP_ZITEM="1";//SAP付款申请单据序号
        String SAP_LIFNR="";//SAP供应商编号
        String SAP_BUKRS="";//SAP公司代码
        String SAP_WAERS="";//SAP货币码

        //供应商
        String gys = "";
        String fkfs = "";


        String sql="select * from " + tableName + " where requestId=?";

        if (rs.executeQuery(sql,requestId)&&rs.next()){
            //serial_no_erp= Util.null2String(rs.getString("oaxlh"));
            oa_bill_no=Util.null2String(rs.getString("bdbh"));
            //affairId=Util.null2String(rs.getString("oalch"));
            //forson_id=Util.null2String(rs.getString("oaxxmh"));
            corp_code=Util.null2String(rs.getString("fkdwdm"));
//            bank_acc=Util.null2String(rs.getString("fkryxzh"));
//            acc_name=Util.null2String(rs.getString("fkrmc"));
//            bank_name=Util.null2String(rs.getString("fkrkhxmc"));
            opp_bank_acc=Util.null2String(rs.getString("gyszh"));
            opp_acc_name=Util.null2String(rs.getString("gysyx"));
            payee_bank_code=Util.null2String(rs.getString("gyslhh"));
//            audit_user=Util.null2String(rs.getString("cwspr"));

//            voucher_type=Util.null2String(rs.getString("fkfs"));
            cur_code=Util.null2String(rs.getString("bz"));
            isforindividual=Util.null2String(rs.getString("dgfk"));
            amt=Util.getDoubleValue(rs.getString("bxzje"),0.00);
            abs=Util.null2String(rs.getString("dgbxsm"));
            purpose=Util.null2String(rs.getString("dgbxsm"));
            //wish_date=Util.null2String(rs.getString("qwzfrq"));
            pay_oa_type_monthe=Util.null2String(rs.getString("bdbh"));
//            pay_oa_type=Util.null2String(rs.getString("oafklxqz"));
//            Pay_from_TYPE=Util.null2String(rs.getString("ywdjly"));

            EBELN=Util.null2String(rs.getString("xmh"));
            VERTN=Util.null2String(rs.getString("hth"));

            rmk_cn=Util.null2String(rs.getString("dgbxsm"));
            remit_type=Util.null2String(rs.getString("hklx"));
//            payer_acc=Util.null2String(rs.getString("hkrzh"));
//            payer_acc_name=Util.null2String(rs.getString("hkrhm"));
            payee_acc=Util.null2String(rs.getString("skrzh"));
            payee_acc_name=Util.null2String(rs.getString("skrhm"));
//            payer_add_en=Util.null2String(rs.getString("hkrdz"));
//            payer_bic=Util.null2String(rs.getString("hkrswiftcode"));
            payee_add=Util.null2String(rs.getString("skrdz"));
            payee_swift_code=Util.null2String(rs.getString("skkhx"));
            payee_bank=Util.null2String(rs.getString("skkhxmc"));
            payee_bank_add=Util.null2String(rs.getString("skkhxdz"));
            payee_country_code=Util.null2String(rs.getString("skrgb"));
            payee_city_name = Util.null2String(rs.getString("gyscs"));

            pay_money=Util.null2String(rs.getString("bxzje"));

            //rmk=Util.null2String(rs.getString("bz"));
            cost_sign=Util.null2String(rs.getString("gnwfycdbz"));
            pay_type=Util.null2String(rs.getString("fklx"));
            is_bonded=Util.null2String(rs.getString("sfwbshwxxfk"));
            bus_no=Util.null2String(rs.getString("whjpjbabhywbh"));
//            reverse1=Util.null2String(rs.getString("djlx"));
//            reverse2=Util.null2String(rs.getString("djlx"));
//            reverse3=Util.null2String(rs.getString("djlx"));
//            SAP_usName=Util.null2String(rs.getString("sappzgzr"));
//            SAP_ZDJLY=Util.null2String(rs.getString("sapwybs"));
//            SAP_ZDJH=Util.null2String(rs.getString("sapfksqdjh"));
//            SAP_ZITEM=Util.null2String(rs.getString("sapfksqdjxh"));
            SAP_LIFNR=Util.null2String(rs.getString("gysbm"));
            SAP_BUKRS=Util.null2String(rs.getString("fkdwdm"));
            SAP_WAERS=Util.null2String(rs.getString("bz"));

            gys = Util.null2String(rs.getString("gysbm"));
            fkfs = Util.null2String(rs.getString("fkfs1"));
        }
        if ("0".equals(fkfs)){
            voucher_type="T";
        }
        if ("1".equals(fkfs)){
            voucher_type="U";
        }

//        if ("0".equals(isforindividual)){
//            isforindividual="对私";
//        }
//        if ("1".equals(isforindividual)){
//            isforindividual="对公";
//        }

        pay_oa_type= pay_oa_type_monthe.substring(0,6);

//            RecordSetDataSource rsd = new RecordSetDataSource("SAP_jkb");
//            String sql1 = " select * from SAP_VENDOR_INFO where LIFNR=?";
//            if (rsd.execute(sql1, gys) && rsd.next()) {
//
//            }
            Map gysmap = new HashMap();
            gysmap.put("LIFNR",gys);
            gysmap.put("EKORG",corp_code);
            String gysparam = JSON.toJSONString(gysmap);
            String getZhurl = "/sap/bc/zcl_if_http_api/MM002?sap-client=300";
            String gysresult = yyWsUtil.doPostSAP(gysparam,getZhurl);
            if (!"".equals(gysresult)){

                JSONObject jsonObject = JSON.parseObject(gysresult);

                String ztype = jsonObject.getString("type");
                if ("S".equals(ztype)){
                    if ("CNY".equals(cur_code)){
                        opp_bank_acc=Util.null2String(jsonObject.getString("gyszh"));
                        opp_acc_name=Util.null2String(jsonObject.getString("gysyx"));
                        payee_bank_code=Util.null2String(jsonObject.getString("gyslhh"));
                    }else {
                        payee_acc = Util.null2String(jsonObject.getString("skrzh"));
                        payee_acc_name = Util.null2String(jsonObject.getString("skrhm"));
                        payee_add = Util.null2String(jsonObject.getString("skrdz"));
                        payee_swift_code = Util.null2String(jsonObject.getString("skkhx"));
                        payee_bank = Util.null2String(jsonObject.getString("skkhxmc"));
                        payee_bank_add = Util.null2String(jsonObject.getString("skkhxdz"));
                        payee_country_code = Util.null2String(jsonObject.getString("skrgb"));
                        payee_city_name = Util.null2String(jsonObject.getString("gyscs"));
                    }


                }else {
                    flag = false;
                    msg = jsonObject.getString("msg");
                }
            }




        Map map = new HashMap();
        if ("CNY".equals(cur_code)){
            url = "/erp/rest/YYGetBillFromOACNYRestful";
            map.put("serial_no_erp",serial_no_erp);
            map.put("oa_bill_no",oa_bill_no);
            map.put("affairId",requestId);
            map.put("forson_id",forson_id);
            map.put("corp_code",corp_code);
            map.put("bank_acc",bank_acc);
            map.put("acc_name",acc_name);
            map.put("bank_name",bank_name);
            map.put("opp_bank_acc",opp_bank_acc);
            map.put("opp_acc_name",opp_acc_name);
            map.put("audit_user",audit_user);
            map.put("payee_bank_code",payee_bank_code);
            map.put("voucher_type",voucher_type);
            map.put("cur_code",cur_code);
            map.put("isforindividual",isforindividual);
            map.put("amt",amt);
            map.put("abs",abs);
            map.put("purpose",purpose);
            map.put("wish_date",wish_date);
            map.put("SAP_usName",SAP_usName);
            map.put("SAP_ZDJLY",SAP_ZDJLY);
            map.put("SAP_ZDJH",SAP_ZDJH);
            map.put("SAP_ZITEM",SAP_ZITEM);
            map.put("SAP_LIFNR",SAP_LIFNR);
            map.put("SAP_BUKRS",SAP_BUKRS);
            map.put("SAP_WAERS",SAP_WAERS);
            map.put("pay_oa_type_monthe",pay_oa_type_monthe);
            map.put("pay_oa_type",pay_oa_type);
            map.put("Pay_from_TYPE",Pay_from_TYPE);
            map.put("EBELN",EBELN);
            map.put("VERTN",VERTN);

        }else{
            url = "/erp/rest/YYGetBillFromOARestful";
            map.put("serial_no_erp",serial_no_erp);
            map.put("oa_bill_no",oa_bill_no);
            map.put("affairId",requestId);
            map.put("forson_id",forson_id);
            map.put("corp_code",corp_code);
            map.put("remit_type",remit_type);
            map.put("wish_date",wish_date);
            map.put("cur_code",cur_code);
            map.put("payer_acc",payer_acc);
            map.put("payer_acc_name",payer_acc_name);
            map.put("payer_add_en",payer_add_en);
            map.put("payer_bic",payer_bic);
            map.put("payee_acc",payee_acc);
            map.put("payee_acc_name",payee_acc_name);
            map.put("payee_add",payee_add);
            map.put("payee_swift_code",payee_swift_code);
            map.put("payee_city_name",payee_city_name);
            map.put("payee_bank",payee_bank);
            map.put("payee_bank_add",payee_bank_add);
            map.put("payee_country_code",payee_country_code);
            map.put("pay_money",pay_money);
            map.put("rmk_cn",rmk_cn);
            map.put("rmk",rmk);
            map.put("cost_sign",cost_sign);
            map.put("pay_type",pay_type);
            map.put("is_bonded",is_bonded);
            map.put("bus_no",bus_no);
            map.put("audit_user",audit_user);
            map.put("reverse1",reverse1);
            map.put("reverse2",reverse2);
            map.put("reverse3",reverse3);
            map.put("SAP_usName",SAP_usName);
            map.put("SAP_ZDJLY",SAP_ZDJLY);
            map.put("SAP_ZDJH",SAP_ZDJH);
            map.put("SAP_ZITEM",SAP_ZITEM);
            map.put("SAP_LIFNR",SAP_LIFNR);
            map.put("SAP_BUKRS",SAP_BUKRS);
            map.put("SAP_WAERS",SAP_WAERS);
            map.put("pay_oa_type_monthe",pay_oa_type_monthe);
            map.put("pay_oa_type",pay_oa_type);
            map.put("Pay_from_TYPE",Pay_from_TYPE);
        }
        List mainList = new ArrayList();
        mainList.add(map);
        String params = JSON.toJSONString(mainList);

        if (!"".equals(opp_bank_acc)||!"".equals(payee_acc)){
            String result = yyWsUtil.doPostZJ(params, url);
            if (!"".equals(result)) {
                JSONArray jsonArray = JSON.parseArray(result);
                if (jsonArray.size() > 0) {
                    String status = jsonArray.getJSONObject(0).getString("status");
                    if (!"S".equals(status)) {
                        flag = false;
                        msg = jsonArray.getJSONObject(0).getString("message");
                    }
                } else {
                    flag = false;
                    msg = "请求返回为空";
                }
            } else {
                flag = false;
                msg = "请求未响应";
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
