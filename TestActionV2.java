package weaver.interfaces.yy;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.integration.logging.Logger;
import weaver.integration.logging.LoggerFactory;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

public class TestActionV2 implements Action {
    private Logger logger = LoggerFactory.getLogger(TestActionV2.class);
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
        //1.调用第三方Api获取token
        logger.info("---M进入Action=-TsDaAction-   当前流程requetid=" + requestid + ",表单名称：" + table + ",workflowid=" + workflowid);

        RecordSet recordSet = new RecordSet();
        RecordSet rs = new RecordSet();
        recordSet.execute("select sfqy,gxsj from uf_hrmtbdaxx ");
        if (recordSet.next()) {
            String kg = "";
            String gxsj = "";
            kg = Util.null2String(recordSet.getString("sfqy"));
            gxsj = Util.null2String(recordSet.getString("gxsj"));
            logger.info("kg"+kg+",sj"+gxsj);
            logger.info(checkId("12",rs));
        }
        return Action.FAILURE_AND_CONTINUE;
    }


        public boolean checkId(String id, RecordSet recordSet){
            logger.info("select * from uf_checkid where ryid='"+id+"'" );
            recordSet.execute("select * from uf_checkid where ryid='"+id+"'" );
            if (recordSet.next()){
                return false;
            }
            return true;
        }


    }
