package weaver.interfaces.yy;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

public class GysfkToZjMXActionV2 implements Action {


    private Logger logger = LoggerFactory.getLogger(GysfkToZjMXActionV2.class);
    private RequestManager requestManager;

    @Override
    public String execute(RequestInfo requestInfo) {
        boolean flag = true;
        String currentMonth="";
        currentMonth= TimeUtil.getCurrentDateString();
        logger.info("GysfkToZjMXAction++++++++++++++++"+currentMonth);
        String msg1 = "";
        requestManager = requestInfo.getRequestManager();
        int requestId = requestManager.getRequestid();
        String tableName = requestManager.getBillTableName();
        int workflowid = requestManager.getWorkflowid();
        int mainId = requestManager.getBillid();//明细表id
        int userId = requestManager.getUserId();

        YyWsUtil yyWsUtil = new YyWsUtil();

        RecordSet rs = new RecordSet();

        String url = "";
        logger.info("进入接口---GysfkToZjMXAction,requestId:"+requestId+","+ "表单名称：" + tableName + ",workflowid=" + workflowid);

        //主表
        String serial_no_erp = "";//OA序列号，唯一值

        String oa_bill_no = "";//OA单号，唯一值
        String affairId = requestId+"";//OA流程号，用于回传
        String forson_id = "";//OA行项目号，用于回传
        String corp_code = "";//付款单位代码
        String bank_acc = "";//付款人银行帐号
        String acc_name = "";//付款人名称
        String bank_name = "";//付款人开户行名称
        String opp_bank_acc = "";//收款人账号
        String opp_acc_name = "";//收款人户名
        String audit_user ="1" ;//财务审批人
        String bdry = yyWsUtil.getWokeCode(userId+"",rs);
        if (!"".equals(bdry)){
            audit_user=bdry;
        }
        String payee_bank_code = "";//收款人联行号
        String voucher_type = "T";//付款方式
        String cur_code = "";//汇款币别
        String isforindividual = "0";//对公对私标志
        String amt = "";//付款金额
        String abs = "";//摘要/备注
        String purpose = "";//用途
        String wish_date = TimeUtil.getCurrentDateString().replace("-","");//期望支付日期

        String SAP_usName= "1";//SAP凭证过账人
        if (!"".equals(bdry)){
            SAP_usName=bdry;
        }
        String SAP_ZDJLY=requestId+"";//SAP唯一标识
        String SAP_ZDJH=requestId+"";//SAP付款申请单据号
        String SAP_ZITEM="";//SAP付款申请单据序号
        String SAP_LIFNR="";//SAP供应商编号
        String SAP_BUKRS="";//SAP公司代码
        String SAP_WAERS="";//SAP货币码

        String pay_oa_type_monthe="";//oa付款类型全称
        String pay_oa_type="";//oa付款类型前缀
        String Pay_from_TYPE="OA";//业务单据来源

        String fklx="";//付款类型
        String fkfs="";//付款方式

        String remit_type="";//汇款类型
        String payee_acc="";//收款人账号
        String payee_acc_name="";//收款人户名
        String payee_swift_code="";//收款开户行SWIFT CODE
        String payee_bank="";//收款开户行名称
        String payee_bank_add="";//收款开户行地址
        String payee_country_code="";//收款人国别
        String pay_money="";//汇款金额

        String cost_sign="1";//国内外费用承担标志
        String pay_type="";//付款类型
        String is_bonded="";//是否为保税货物项下付款
        String payee_city_name="";//收款人城市

        Double cdfkje = 0.00;
        Double dhfkje = 0.00;

        Double ybfkje = 0.00;
        Double bbfkje = 0.00;

        String EBELN = "";//采购订单号
        String VERTN = "";//合同号


        String sql = "select * from " + tableName + " where requestid=? ";
        if (rs.executeQuery(sql,requestId)&&rs.next()){
            oa_bill_no= Util.null2String(rs.getString("lcbh"));
            corp_code=Util.null2String(rs.getString("gsdm"));
            opp_bank_acc=Util.null2String(rs.getString("gysskzh"));
            opp_acc_name=Util.null2String(rs.getString("gysmc"));
            payee_bank_code=Util.null2String(rs.getString("lxhswiftcode"));
            cur_code=Util.null2String(rs.getString("bz"));
            //amt=Util.null2String(rs.getString("yfze"));
            purpose=Util.null2String(rs.getString("ytsm"));
            SAP_LIFNR=Util.null2String(rs.getString("gysxx"));
            SAP_BUKRS=Util.null2String(rs.getString("gsdm"));
            SAP_WAERS=Util.null2String(rs.getString("bz"));
            pay_oa_type_monthe = Util.null2String(rs.getString("lcbh"));
            fklx=Util.null2String(rs.getString("fklx"));
            pay_oa_type= pay_oa_type_monthe.substring(0,6);


            payee_acc=Util.null2String(rs.getString("skrzh"));
            payee_acc_name=Util.null2String(rs.getString("skrhm"));
            payee_swift_code=Util.null2String(rs.getString("skkhx"));
            payee_bank=Util.null2String(rs.getString("skkhxmc"));
            payee_bank_add=Util.null2String(rs.getString("skkhxdz"));
            payee_country_code=Util.null2String(rs.getString("skrgb"));
            //pay_money=Util.null2String(rs.getString("yfze"));
            //cost_sign=Util.null2String(rs.getString("gnwfycdbz"));


            payee_city_name=Util.null2String(rs.getString("skrcs"));
            abs=Util.null2String(rs.getString("ytsm"));

            cdfkje = Util.getDoubleValue(rs.getString("cdfkje"),0.00);
            dhfkje = Util.getDoubleValue(rs.getString("dhfkje"),0.00);

            ybfkje = Util.getDoubleValue(rs.getString("yfzeyb"),0.00);
            bbfkje = Util.getDoubleValue(rs.getString("yfzebb"),0.00);

        }


        //订有色金属-预付款
        List dtyfkListBb = new ArrayList();//明细表2
        //订有色金属-尾款
        List dtwkListBb = new ArrayList();//明细表5
        //退保证金押金
        List tbzjListBb = new ArrayList();//明细表3
        //无账期付款
        List wzqfkListBb = new ArrayList();//明细表1
        //有账期预付
        List yzqtqfkListBb = new ArrayList();
        //内部资金调拨
        List nbzzListBbdh = new ArrayList();//明细表4

        List nbzzListBbcd = new ArrayList();//明细表4

        //订有色金属-预付款
        List dtyfkListwb = new ArrayList();//明细表2
        //订有色金属-尾款
        List dtwkListwb  = new ArrayList();//明细表5
        //退保证金押金
        List tbzjListwb  = new ArrayList();//明细表3
        //无账期付款
        List wzqfkListwb  = new ArrayList();//明细表1
        //有账期预付
        List yzqtqfkListwb  = new ArrayList();
        //内部资金调拨
        List nbzzListwbdh  = new ArrayList();//明细表4

        List nbzzListwbcd  = new ArrayList();//明细表4



        //退保证金押金
        if ("4".equals(fklx)){
            String mainsql="select * from " + tableName + "_dt3 where mainid=? ";

            purpose = "退保证金";

            if (rs.executeQuery(mainsql,mainId)){
                while (rs.next()){
                    forson_id = Util.null2String(rs.getString("id"));
                    cur_code=Util.null2String(rs.getString("bz"));
                    amt=Util.null2String(rs.getString("bcfkje"));
                    abs=Util.null2String(rs.getString("bz1"));
                    opp_bank_acc=Util.null2String(rs.getString("gysskzh"));
                    opp_acc_name=Util.null2String(rs.getString("gysmc"));
                    payee_bank_code=Util.null2String(rs.getString("lxhswiftcode"));
                    SAP_ZITEM=Util.null2String(rs.getString("id"));
                    SAP_LIFNR=Util.null2String(rs.getString("gysbm"));
                    SAP_WAERS=Util.null2String(rs.getString("bz"));

                    payee_acc=Util.null2String(rs.getString("gysskzh"));
                    payee_acc_name=Util.null2String(rs.getString("gysmc"));
                    payee_swift_code=Util.null2String(rs.getString("lxhswiftcode"));
                    payee_bank=Util.null2String(rs.getString("skkhxmc"));
                    payee_bank_add=Util.null2String(rs.getString("skkhxdz"));
                    payee_country_code=Util.null2String(rs.getString("gysgb"));
                    pay_money=Util.null2String(rs.getString("bcfkje"));
                    fkfs=Util.null2String(rs.getString("fkfs"));
                    remit_type=Util.null2String(rs.getString("hklx"));
                    pay_type=Util.null2String(rs.getString("fklx1"));
                    is_bonded=Util.null2String(rs.getString("sfwbshwxxfk"));
                    String sffkcg = Util.null2String(rs.getString("sffkcg"));

                    if (!"0".equals(sffkcg)){
                        continue;
                    }

                    if ("0".equals(fkfs)){
                        voucher_type="T";
                    }
                    if ("1".equals(fkfs)){
                        voucher_type="U";
                    }

                    if ("CNY".equals(cur_code)){
                        logger.info("退保证金-人民币");
                        Map map = new HashMap();
                        map.put("serial_no_erp",IdUtil.fastUUID().replace("-",""));
                        map.put("oa_bill_no",oa_bill_no);
                        map.put("affairId",affairId);
                        map.put("forson_id",forson_id);
                        map.put("corp_code",corp_code);
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
                        tbzjListBb.add(map);
                    }else{
                        logger.info("退保证金-外币");
                        Map map = new HashMap();

                        map.put("serial_no_erp",IdUtil.fastUUID().replace("-",""));
                        map.put("oa_bill_no",oa_bill_no);
                        map.put("affairId",affairId);
                        map.put("forson_id",forson_id);
                        map.put("corp_code",corp_code);
                        map.put("remit_type",remit_type);
                        map.put("wish_date",wish_date);
                        map.put("cur_code",cur_code);
                        map.put("purpose",purpose);
                        map.put("payee_acc",payee_acc);
                        map.put("payee_acc_name",payee_acc_name);
                        map.put("payee_swift_code",payee_swift_code);
                        map.put("payee_bank",payee_bank);
                        map.put("payee_bank_add",payee_bank_add);
                        map.put("payee_country_code",payee_country_code);
                        map.put("pay_money",pay_money);
                        map.put("cost_sign",cost_sign);
                        map.put("pay_type",pay_type);
                        map.put("is_bonded",is_bonded);
                        map.put("payee_city_name",payee_city_name);
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
                        map.put("voucher_type",voucher_type);
                        map.put("isforindividual",isforindividual);
                        tbzjListwb.add(map);
                    }
                }
            }

        }


        //内部资金调拨
        if ("5".equals(fklx)){

            purpose = "内部调拨";

            String mainsql="select * from " + tableName + "_dt4 where mainid=? ";

            if (rs.executeQuery(mainsql,mainId)){
                while (rs.next()){
                    forson_id = Util.null2String(rs.getString("id"));
                    cur_code=Util.null2String(rs.getString("bz"));
                    //amt=Util.null2String(rs.getString("fkze"));
                    opp_bank_acc=Util.null2String(rs.getString("gysskzh"));
                    opp_acc_name=Util.null2String(rs.getString("nbgysmc"));
                    payee_bank_code=Util.null2String(rs.getString("lxhswiftcode"));
                    SAP_ZITEM=Util.null2String(rs.getString("id"));
                    SAP_LIFNR=Util.null2String(rs.getString("nbgysbm1"));
                    SAP_WAERS=Util.null2String(rs.getString("bz"));

                    payee_acc=Util.null2String(rs.getString("gysskzh"));
                    payee_acc_name=Util.null2String(rs.getString("nbgysmc"));
                    payee_swift_code=Util.null2String(rs.getString("lxhswiftcode"));
                    payee_bank=Util.null2String(rs.getString("skkhxmc"));
                    payee_bank_add=Util.null2String(rs.getString("skkhxdz"));
                    payee_country_code=Util.null2String(rs.getString("gysgb"));
                    //pay_money=Util.null2String(rs.getString("bcfkje"));
                    fkfs=Util.null2String(rs.getString("fkfs"));
                    remit_type=Util.null2String(rs.getString("hklx"));
                    pay_type=Util.null2String(rs.getString("fklx1"));
                    is_bonded=Util.null2String(rs.getString("sfwbshwxxfk"));
                    cdfkje = Util.getDoubleValue(rs.getString("cdfkje"),0.00);
                    dhfkje = Util.getDoubleValue(rs.getString("dhfkje"),0.00);
                    String dhsfcg =  Util.null2String(rs.getString("dhsfcg"));
                    String cdsfcg =  Util.null2String(rs.getString("cdsfcg"));
                    if (cdfkje>0 && !"0".equals(cdsfcg)) {

                        voucher_type="U";

                        if ("CNY".equals(cur_code)) {
                            logger.info("内部资金调拨-人民币");
                            Map map = new HashMap();
                            map.put("serial_no_erp", IdUtil.fastUUID().replace("-",""));
                            map.put("oa_bill_no", oa_bill_no);
                            map.put("affairId", affairId);
                            map.put("forson_id", forson_id);
                            map.put("corp_code", corp_code);
                            map.put("opp_bank_acc", opp_bank_acc);
                            map.put("opp_acc_name", opp_acc_name);
                            map.put("audit_user", audit_user);
                            map.put("payee_bank_code", payee_bank_code);
                            map.put("voucher_type", voucher_type);
                            map.put("cur_code", cur_code);
                            map.put("isforindividual", isforindividual);
                            map.put("amt", cdfkje);
                            map.put("purpose", purpose);
                            map.put("wish_date", wish_date);
                            map.put("SAP_usName", SAP_usName);
                            map.put("SAP_ZDJLY", SAP_ZDJLY);
                            map.put("SAP_ZDJH", SAP_ZDJH);
                            map.put("SAP_ZITEM", SAP_ZITEM);
                            map.put("SAP_LIFNR", SAP_LIFNR);
                            map.put("SAP_BUKRS", SAP_BUKRS);
                            map.put("SAP_WAERS", SAP_WAERS);
                            map.put("pay_oa_type_monthe", pay_oa_type_monthe);
                            map.put("pay_oa_type", pay_oa_type);
                            map.put("Pay_from_TYPE", Pay_from_TYPE);
                            nbzzListBbcd.add(map);
                        } else {
                            logger.info("内部资金调拨-外币");
                            Map map = new HashMap();
                            map.put("serial_no_erp", IdUtil.fastUUID().replace("-",""));
                            map.put("oa_bill_no", oa_bill_no);
                            map.put("affairId", affairId);
                            map.put("forson_id", forson_id);
                            map.put("corp_code", corp_code);
                            map.put("remit_type", remit_type);
                            map.put("wish_date", wish_date);
                            map.put("purpose", purpose);
                            map.put("cur_code", cur_code);
                            map.put("payee_acc", payee_acc);
                            map.put("payee_acc_name", payee_acc_name);
                            map.put("payee_swift_code", payee_swift_code);
                            map.put("payee_bank", payee_bank);
                            map.put("payee_bank_add", payee_bank_add);
                            map.put("payee_country_code", payee_country_code);
                            map.put("pay_money", cdfkje);
                            map.put("cost_sign", cost_sign);
                            map.put("pay_type", pay_type);
                            map.put("is_bonded", is_bonded);
                            map.put("payee_city_name", payee_city_name);
                            map.put("SAP_usName", SAP_usName);
                            map.put("SAP_ZDJLY", SAP_ZDJLY);
                            map.put("SAP_ZDJH", SAP_ZDJH);
                            map.put("SAP_ZITEM", SAP_ZITEM);
                            map.put("SAP_LIFNR", SAP_LIFNR);
                            map.put("SAP_BUKRS", SAP_BUKRS);
                            map.put("SAP_WAERS", SAP_WAERS);
                            map.put("pay_oa_type_monthe", pay_oa_type_monthe);
                            map.put("pay_oa_type", pay_oa_type);
                            map.put("Pay_from_TYPE", Pay_from_TYPE);
                            nbzzListwbcd.add(map);
                        }
                    }

                    if (dhfkje>0 && !"0".equals(dhsfcg)) {
                        voucher_type="T";
                        if ("CNY".equals(cur_code)) {
                            logger.info("内部资金调拨-人民币");
                            Map map = new HashMap();
                            map.put("serial_no_erp", IdUtil.fastUUID().replace("-",""));
                            map.put("oa_bill_no", oa_bill_no);
                            map.put("affairId", affairId);
                            map.put("forson_id", forson_id);
                            map.put("corp_code", corp_code);
                            map.put("opp_bank_acc", opp_bank_acc);
                            map.put("opp_acc_name", opp_acc_name);
                            map.put("audit_user", audit_user);
                            map.put("payee_bank_code", payee_bank_code);
                            map.put("voucher_type", voucher_type);
                            map.put("cur_code", cur_code);
                            map.put("isforindividual", isforindividual);
                            map.put("amt", dhfkje);
                            map.put("purpose", purpose);
                            map.put("wish_date", wish_date);
                            map.put("SAP_usName", SAP_usName);
                            map.put("SAP_ZDJLY", SAP_ZDJLY);
                            map.put("SAP_ZDJH", SAP_ZDJH);
                            map.put("SAP_ZITEM", SAP_ZITEM);
                            map.put("SAP_LIFNR", SAP_LIFNR);
                            map.put("SAP_BUKRS", SAP_BUKRS);
                            map.put("SAP_WAERS", SAP_WAERS);
                            map.put("pay_oa_type_monthe", pay_oa_type_monthe);
                            map.put("pay_oa_type", pay_oa_type);
                            map.put("Pay_from_TYPE", Pay_from_TYPE);
                            nbzzListBbdh.add(map);
                        } else {
                            logger.info("内部资金调拨-外币");
                            Map map = new HashMap();
                            map.put("serial_no_erp", IdUtil.fastUUID().replace("-",""));
                            map.put("oa_bill_no", oa_bill_no);
                            map.put("affairId", affairId);
                            map.put("forson_id", forson_id);
                            map.put("corp_code", corp_code);
                            map.put("remit_type", remit_type);
                            map.put("wish_date", wish_date);
                            map.put("purpose", purpose);
                            map.put("cur_code", cur_code);
                            map.put("payee_acc", payee_acc);
                            map.put("payee_acc_name", payee_acc_name);
                            map.put("payee_swift_code", payee_swift_code);
                            map.put("payee_bank", payee_bank);
                            map.put("payee_bank_add", payee_bank_add);
                            map.put("payee_country_code", payee_country_code);
                            map.put("pay_money", dhfkje);
                            map.put("cost_sign", cost_sign);
                            map.put("pay_type", pay_type);
                            map.put("is_bonded", is_bonded);
                            map.put("payee_city_name", payee_city_name);
                            map.put("SAP_usName", SAP_usName);
                            map.put("SAP_ZDJLY", SAP_ZDJLY);
                            map.put("SAP_ZDJH", SAP_ZDJH);
                            map.put("SAP_ZITEM", SAP_ZITEM);
                            map.put("SAP_LIFNR", SAP_LIFNR);
                            map.put("SAP_BUKRS", SAP_BUKRS);
                            map.put("SAP_WAERS", SAP_WAERS);
                            map.put("pay_oa_type_monthe", pay_oa_type_monthe);
                            map.put("pay_oa_type", pay_oa_type);
                            map.put("Pay_from_TYPE", Pay_from_TYPE);
                            nbzzListwbdh.add(map);
                        }
                    }


                }
            }

        }

        if (tbzjListBb.size()>0) {
            url = "/erp/rest/YYGetBillFromOACNYRestful";
            String params = JSON.toJSONString(tbzjListBb);
            logger.info("params:"+params);
            String result = yyWsUtil.doPostZJ(params, url);
            if (!"".equals(result)) {
                JSONArray jsonArray = JSON.parseArray(result);
                if (jsonArray.size() > 0) {
                    for (int i=0;i<jsonArray.size();i++) {
                        String status = jsonArray.getJSONObject(i).getString("status");
                        String hid = jsonArray.getJSONObject(0).getString("forson_id");
                        String msg = "";
                        int tszt = 0;
                        if (!"S".equals(status)) {
                            flag = false;
                            tszt = 1;
                            msg1 = jsonArray.getJSONObject(0).getString("message");
                        }
                        String updsql = "update "+tableName+"_dt3 set sffkcg =? ,fksbrz =? where id =? ";
                        if (!rs.executeUpdate(updsql,tszt,msg1,hid)){
                            logger.info(updsql);
                        }
                    }
                } else {
                    flag = false;
                    msg1 = "请求返回为空";
                }
            } else {
                flag = false;
                msg1 = "请求未响应";
            }
        }


        if (tbzjListwb.size()>0) {
            url = "/erp/rest/YYGetBillFromOARestful";
            String params = JSON.toJSONString(tbzjListwb);
            logger.info("params:"+params);
            String result = yyWsUtil.doPostZJ(params, url);
            if (!"".equals(result)) {
                JSONArray jsonArray = JSON.parseArray(result);
                if (jsonArray.size() > 0) {
                    for (int i=0;i<jsonArray.size();i++) {
                        String status = jsonArray.getJSONObject(i).getString("status");
                        String hid = jsonArray.getJSONObject(0).getString("forson_id");
                        String msg = "";
                        int tszt = 0;
                        if (!"S".equals(status)) {
                            flag = false;
                            tszt = 1;
                            msg1 = jsonArray.getJSONObject(0).getString("message");
                        }
                        String updsql = "update "+tableName+"_dt3 set sffkcg =? ,fksbrz =? where id =? ";
                        if (!rs.executeUpdate(updsql,tszt,msg1,hid)){
                            logger.info(updsql);
                        }
                    }
                } else {
                    flag = false;
                    msg1 = "请求返回为空";
                }
            } else {
                flag = false;
                msg1 = "请求未响应";
            }
        }

        if (nbzzListBbdh.size()>0) {
            url = "/erp/rest/YYGetBillFromOACNYRestful";
            String params = JSON.toJSONString(nbzzListBbdh);
            logger.info("params:"+params);
            String result = yyWsUtil.doPostZJ(params, url);

            if (!"".equals(result)) {
                JSONArray jsonArray = JSON.parseArray(result);
                if (jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        String status = jsonArray.getJSONObject(i).getString("status");
                        String hid = jsonArray.getJSONObject(i).getString("forson_id");
                        String msg = "";
                        int tszt = 0;
                        if (!"S".equals(status)) {
                            flag = false;
                            tszt = 1;
                            msg = jsonArray.getJSONObject(i).getString("message");
                        }
                        String updsql = "update " + tableName + "_dt4 set dhsfcg =? ,dhsbrz =? where id =? ";
                        if (!rs.executeUpdate(updsql, tszt, msg, hid)) {
                            logger.info(updsql);
                        }
                    }
                } else {
                    flag = false;
                    msg1 = "请求返回为空";
                }
            } else {
                flag = false;
                msg1 = "请求未响应";
            }
        }
        if (nbzzListBbcd.size()>0) {
            url = "/erp/rest/YYGetBillFromOACNYRestful";
            String params = JSON.toJSONString(nbzzListBbcd);
            logger.info("params:"+params);
            String result = yyWsUtil.doPostZJ(params, url);

            if (!"".equals(result)) {
                JSONArray jsonArray = JSON.parseArray(result);
                if (jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        String status = jsonArray.getJSONObject(i).getString("status");
                        String hid = jsonArray.getJSONObject(i).getString("forson_id");
                        String msg = "";
                        int tszt = 0;
                        if (!"S".equals(status)) {
                            flag = false;
                            tszt = 1;
                            msg = jsonArray.getJSONObject(i).getString("message");
                        }
                        String updsql = "update " + tableName + "_dt4 set dhsfcg =? ,dhsbrz =? where id =? ";
                        if (!rs.executeUpdate(updsql, tszt, msg, hid)) {
                            logger.info(updsql);
                        }
                    }
                } else {
                    flag = false;
                    msg1 = "请求返回为空";
                }
            } else {
                flag = false;
                msg1 = "请求未响应";
            }
        }
        if (nbzzListwbdh.size()>0) {
            url = "/erp/rest/YYGetBillFromOARestful";
            String params = JSON.toJSONString(nbzzListwbdh);
            logger.info("params:"+params);
            String result = yyWsUtil.doPostZJ(params, url);

            if (!"".equals(result)) {
                JSONArray jsonArray = JSON.parseArray(result);
                if (jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        String status = jsonArray.getJSONObject(i).getString("status");
                        String hid = jsonArray.getJSONObject(i).getString("forson_id");
                        String msg = "";
                        int tszt = 0;
                        if (!"S".equals(status)) {
                            flag = false;
                            tszt = 1;
                            msg = jsonArray.getJSONObject(i).getString("message");
                        }
                        String updsql = "update " + tableName + "_dt4 set cdsfcg =? ,cdsbrz =? where id =? ";
                        if (!rs.executeUpdate(updsql, tszt, msg, hid)) {
                            logger.info(updsql);
                        }
                    }
                } else {
                    flag = false;
                    msg1 = "请求返回为空";
                }
            } else {
                flag = false;
                msg1 = "请求未响应";
            }
        }
        if (nbzzListwbcd.size()>0) {
            url = "/erp/rest/YYGetBillFromOARestful";
            String params = JSON.toJSONString(nbzzListwbcd);
            logger.info("params:"+params);
            String result = yyWsUtil.doPostZJ(params, url);

            if (!"".equals(result)) {
                JSONArray jsonArray = JSON.parseArray(result);
                if (jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        String status = jsonArray.getJSONObject(i).getString("status");
                        String hid = jsonArray.getJSONObject(i).getString("forson_id");
                        String msg = "";
                        int tszt = 0;
                        if (!"S".equals(status)) {
                            flag = false;
                            tszt = 1;
                            msg = jsonArray.getJSONObject(i).getString("message");
                        }
                        String updsql = "update " + tableName + "_dt4 set cdsfcg =? ,cdsbrz =? where id =? ";
                        if (!rs.executeUpdate(updsql, tszt, msg, hid)) {
                            logger.info(updsql);
                        }
                    }
                } else {
                    flag = false;
                    msg1 = "请求返回为空";
                }
            } else {
                flag = false;
                msg1 = "请求未响应";
            }
        }

        if (flag) {
            return Action.SUCCESS;
        }else {
            requestManager.setMessageid("001");
            requestManager.setMessage("接口推送异常");
            requestManager.setMessagecontent("异常信息："+msg1);
            return Action.FAILURE_AND_CONTINUE;
        }
    }

}
