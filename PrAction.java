package weaver.interfaces.yy;

import com.alibaba.fastjson.JSON;
import com.engine.common.util.ServiceUtil;
import com.engine.workflow.service.HtmlToPdfService;
import com.engine.workflow.service.impl.HtmlToPdfServiceImpl;
import weaver.general.BaseBean;
import weaver.hrm.User;
import weaver.integration.logging.Logger;
import weaver.integration.logging.LoggerFactory;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;
import weaver.workflow.workflow.WorkflowConfigComInfo;

import java.util.HashMap;

import java.util.Map;

public class PrAction implements Action {

    private Logger logger = LoggerFactory.getLogger(PrAction.class);
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

        getFilePath(user,requestid);
        return Action.FAILURE_AND_CONTINUE;
    }

    //获取打印模板的pdf
    public void getFilePath(User user, int requestId) {
        try {
            BaseBean baseBean = new BaseBean();
            String modeId =baseBean.getPropValue("","");
            Map<String, Object> params = new HashMap<>();
            WorkflowConfigComInfo workflowConfigComInfo = new WorkflowConfigComInfo();
            String useWk = workflowConfigComInfo.getValue("htmltopdf_usewk");
            params.put("useWk", useWk);    //是否使用wkhtmltopdf插件转pdf 1：是  0：否  不传则默认使用Itext插件
            params.put("requestid", requestId);    //必传
            params.put("modeid", modeId);    //模板id(传模板id则根据模板生成.不传则默认使用显示模板)
            //params.put("path","/usr/weaver/pdffile");  //存储路径(不传则windows默认D:/testpdf;linux默认/usr/testpdf)
            params.put("onlyHtml", "0");    //0:转pdf  1:只转成html  2:转html和pdf  (不传则默认=0)
            params.put("keepsign", "0");   //1:保留底部签字意见 0：不保留 (不传则默认=1)
            params.put("pageSize", "100"); //底部签字意见最大显示数量  (默认=100)
            params.put("isTest", "1");    //外部调用必传isTest=1
            params.put("limitauth", "0"); //不校验权限
            HtmlToPdfService htmlToPdfService = (HtmlToPdfService) ServiceUtil.getService(HtmlToPdfServiceImpl.class, user);
            Map<String, Object> pathMap = htmlToPdfService.getFormDatas(params);
            logger.info("表单生成pdf：" + JSON.toJSONString(pathMap));
            String filePath = (String) pathMap.get("path");
            String filename = (String) pathMap.get("filename");
            String filePath1 = filePath + "/" + filename;
            logger.info(filePath1);

        } catch (Exception e) {
            logger.info(e);
        }
    }

}
