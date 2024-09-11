package weaver.interfaces.yy;

import com.alibaba.fastjson.JSON;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.integration.logging.Logger;
import weaver.integration.logging.LoggerFactory;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

import java.util.HashMap;
import java.util.Map;

public class ZjglAction extends BaseBean implements Action {

    private Logger logger = LoggerFactory.getLogger(ZjglAction.class);
    private RequestManager requestManager;

    @Override
    public String execute(RequestInfo requestInfo){
        boolean flag = true;
        String currentMonth="";
        currentMonth= TimeUtil.getCurrentDateString();
        logger.info("BytterAction++++++++++++++++"+currentMonth);
        String msg = "";
        requestManager = requestInfo.getRequestManager();
        int requestId = requestManager.getRequestid();
        String tableName = requestManager.getBillTableName();
        int workflowid = requestManager.getWorkflowid();//流程id
        int mainId = requestManager.getBillid();//明细表id
        logger.info("进入接口---BytterAction,requestId:"+requestId+","+ "表单名称：" + tableName + ",workflowid=" + workflowid);
        //主表
        String serial_no_erp = "";//OA序列号，唯一值
        String oa_bill_no = "";//OA单号，唯一值
        String affairId = "";//OA流程号，用于回传
        String forson_id = "";//OA行项目号，用于回传
        String corp_code = "";//付款单位代码
        String bank_acc = "";//付款人银行帐号
        String acc_name = "";//付款人名称
        String bank_name = "";//付款人开户行名称
        String opp_bank_acc = "";//收款人账号
        String opp_acc_name = "";//收款人户名
        String audit_user = "";//财务审批人
        String payee_bank_code = "";//收款人联行号
        String voucher_type = "";//付款方式
        String cur_code = "";//汇款币别
        String isforindividual = "";//对公对私标志
        String amt = "";//付款金额
        String abs = "";//摘要/备注
        String purpose = "";//用途
        String wish_date = "";//期望支付日期

        String remit_type="";//汇款类型
        String payer_acc="";//汇款人账号
        String payer_acc_name="";//汇款人户名
        String payee_acc="";//收款人账号
        String payee_acc_name="";//收款人户名
        String payee_add="";//收款人地址
        String payee_swift_code="";//收款开户行SWIFT CODE
        String payee_bank="";//收款开户行名称
        String payee_bank_add="";//收款开户行地址
        String payee_country_code="";//收款人国别
        String payee_permanent_country="";//收款人常驻国家
        String pay_money="";//汇款金额
        String add_word="";//汇款附言
        String rmk="";//备注
        String cost_sign="";//国内外费用承担标志
        String pay_type="";//付款类型
        String is_bonded="";//是否为保税货物项下付款
        String bus_no="";//外汇局批件/备案表号/业务编号
        String contract_no="";//合同号
        String invoice_no="";//发票号
        String ip_addr="";//IP地址
        String reverse1="";//备用字段1
        String reverse2="";//备用字段2
        String reverse3="";//备用字段3


        String Ssp_usName="";//SAP凭证过账人
        String SAP_ZDJLY="";//SAP唯一标识
        String SAP_ZDJH="";//SAP付款申请单据号
        String SAP_ZITEM="";//SAP付款申请单据序号
        String SAP_LIFNR="";//SAP供应商编号
        String SAP_BUKRS="";//SAP公司代码
        String SAP_WAERS="";//SAP货币码

        RecordSet rs = new RecordSet();
        String sql="select * from " + tableName + " where requestId=?";
        rs.executeQuery(sql,requestId);
        if (rs.next()){
            serial_no_erp= Util.null2String(rs.getString("djlx"));
            oa_bill_no=Util.null2String(rs.getString("djlx"));
            affairId=Util.null2String(rs.getString("djlx"));
            forson_id=Util.null2String(rs.getString("djlx"));
            corp_code=Util.null2String(rs.getString("djlx"));
            bank_acc=Util.null2String(rs.getString("djlx"));
            acc_name=Util.null2String(rs.getString("djlx"));
            bank_name=Util.null2String(rs.getString("djlx"));
            opp_bank_acc=Util.null2String(rs.getString("djlx"));
            opp_acc_name=Util.null2String(rs.getString("djlx"));
            audit_user=Util.null2String(rs.getString("djlx"));
            payee_bank_code=Util.null2String(rs.getString("djlx"));
            voucher_type=Util.null2String(rs.getString("djlx"));
            cur_code=Util.null2String(rs.getString("djlx"));
            isforindividual=Util.null2String(rs.getString("djlx"));
            amt=Util.null2String(rs.getString("djlx"));
            abs=Util.null2String(rs.getString("djlx"));
            purpose=Util.null2String(rs.getString("djlx"));
            wish_date=Util.null2String(rs.getString("djlx"));

            remit_type=Util.null2String(rs.getString("djlx"));
            payer_acc=Util.null2String(rs.getString("djlx"));
            payer_acc_name=Util.null2String(rs.getString("djlx"));
            payee_acc=Util.null2String(rs.getString("djlx"));
            payee_acc_name=Util.null2String(rs.getString("djlx"));
            payee_add=Util.null2String(rs.getString("djlx"));
            payee_swift_code=Util.null2String(rs.getString("djlx"));
            payee_bank=Util.null2String(rs.getString("djlx"));
            payee_bank_add=Util.null2String(rs.getString("djlx"));
            payee_country_code=Util.null2String(rs.getString("djlx"));
            payee_permanent_country=Util.null2String(rs.getString("djlx"));
            pay_money=Util.null2String(rs.getString("djlx"));
            add_word=Util.null2String(rs.getString("djlx"));
            rmk=Util.null2String(rs.getString("djlx"));
            cost_sign=Util.null2String(rs.getString("djlx"));
            pay_type=Util.null2String(rs.getString("djlx"));
            is_bonded=Util.null2String(rs.getString("djlx"));
            bus_no=Util.null2String(rs.getString("djlx"));
            contract_no=Util.null2String(rs.getString("djlx"));
            invoice_no=Util.null2String(rs.getString("djlx"));
            ip_addr=Util.null2String(rs.getString("djlx"));
            reverse1=Util.null2String(rs.getString("djlx"));
            reverse2=Util.null2String(rs.getString("djlx"));
            reverse3=Util.null2String(rs.getString("djlx"));
            Ssp_usName=Util.null2String(rs.getString("djlx"));
            SAP_ZDJLY=Util.null2String(rs.getString("djlx"));
            SAP_ZDJH=Util.null2String(rs.getString("djlx"));
            SAP_ZITEM=Util.null2String(rs.getString("djlx"));
            SAP_LIFNR=Util.null2String(rs.getString("djlx"));
            SAP_BUKRS=Util.null2String(rs.getString("djlx"));
            SAP_WAERS=Util.null2String(rs.getString("djlx"));
        }
        Map map = new HashMap();
        if (corp_code=="CNY"){
            map.put("serial_no_erp",serial_no_erp);
            map.put("oa_bill_no",oa_bill_no);
            map.put("affairId",affairId);
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
            map.put("Ssp_usName",Ssp_usName);
            map.put("SAP_ZDJLY",SAP_ZDJLY);
            map.put("SAP_ZDJH",SAP_ZDJH);
            map.put("SAP_ZITEM",SAP_ZITEM);
            map.put("SAP_LIFNR",SAP_LIFNR);
            map.put("SAP_BUKRS",SAP_BUKRS);
            map.put("SAP_WAERS",SAP_WAERS);
        }else{
            map.put("serial_no_erp",serial_no_erp);
            map.put("oa_bill_no",oa_bill_no);
            map.put("affairId",affairId);
            map.put("forson_id",forson_id);
            map.put("corp_code",corp_code);
            map.put("remit_type",remit_type);
            map.put("wish_date",wish_date);
            map.put("cur_code",cur_code);
            map.put("payer_acc",payer_acc);
            map.put("payer_acc_name",payer_acc_name);
            map.put("payee_acc",payee_acc);
            map.put("payee_acc_name",payee_acc_name);
            map.put("payee_add",payee_add);
            map.put("payee_swift_code",payee_swift_code);
            map.put("payee_bank",payee_bank);
            map.put("payee_bank_add",payee_bank_add);
            map.put("payee_country_code",payee_country_code);
            map.put("payee_permanent_country",payee_permanent_country);
            map.put("pay_money",pay_money);
            map.put("add_word",add_word);
            map.put("rmk",rmk);
            map.put("cost_sign",cost_sign);
            map.put("pay_type",pay_type);
            map.put("is_bonded",is_bonded);
            map.put("bus_no",bus_no);
            map.put("contract_no",contract_no);
            map.put("invoice_no",invoice_no);
            map.put("audit_user",audit_user);
            map.put("ip_addr",ip_addr);
            map.put("reverse1",reverse1);
            map.put("reverse2",reverse2);
            map.put("reverse3",reverse3);
            map.put("Ssp_usName",Ssp_usName);
            map.put("SAP_ZDJLY",SAP_ZDJLY);
            map.put("SAP_ZDJH",SAP_ZDJH);
            map.put("SAP_ZITEM",SAP_ZITEM);
            map.put("SAP_LIFNR",SAP_LIFNR);
            map.put("SAP_BUKRS",SAP_BUKRS);
            map.put("SAP_WAERS",SAP_WAERS);
        }
        String params = JSON.toJSONString(map);

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
