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

import java.util.HashMap;
import java.util.Map;


public class BytterAction extends BaseBean implements Action {

    private Logger logger = LoggerFactory.getLogger(BytterAction.class);
    private RequestManager requestManager;

    @Override
    public String execute(RequestInfo requestInfo) {
        boolean flag = true;
        String currentMonth="";
        currentMonth= TimeUtil.getCurrentDateString();
        logger.info("BytterAction++++++++++++++++"+currentMonth);
        String msg = "";
        requestManager = requestInfo.getRequestManager();
        int requestId = requestManager.getRequestid();
        String tableName = requestManager.getBillTableName();
        int workflowid = requestManager.getWorkflowid();
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


        RecordSet rs = new RecordSet();
        String sql="select * from " + tableName + " where requestId=?";
        rs.executeQuery(sql,requestId);
        if (rs.next()){
            serial_no_erp=Util.null2String(rs.getString("djlx"));
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
        }
        Map map = new HashMap();

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
