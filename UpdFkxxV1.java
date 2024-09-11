package weaver.interfaces.yy;


import weaver.conn.RecordSet;
import weaver.formmode.customjavacode.AbstractModeExpandJavaCodeNew;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.integration.logging.Logger;
import weaver.integration.logging.LoggerFactory;
import weaver.soa.workflow.request.RequestInfo;


import java.util.HashMap;
import java.util.Map;

public class UpdFkxxV1 extends AbstractModeExpandJavaCodeNew {

    private Logger logger = LoggerFactory.getLogger(UpdFkxxV1.class);

    @Override
    public Map<String, String> doModeExpand(Map<String, Object> param) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            User user = (User)param.get("user");
            int billid = -1;//数据id
            int modeid = -1;//模块id
            RequestInfo requestInfo = (RequestInfo)param.get("RequestInfo");
            if(requestInfo!=null){
                billid = Util.getIntValue(requestInfo.getRequestid());
                modeid = Util.getIntValue(requestInfo.getWorkflowid());
                if(billid>0&&modeid>0){
                    //------请在下面编写业务逻辑代码------
                    BaseBean baseBean = new BaseBean();

                    logger.info("进入UpdFkxxV1");

                    RecordSet rs = new RecordSet();
                    RecordSet updRs = new RecordSet();
                    RecordSet sltRs = new RecordSet();
                    String sql = "select * from uf_saphc where id ="+billid ;
                    logger.info("sql:"+sql);
                    rs.execute(sql);
                    if (rs.next()){

                        String oaqqid = "";
                        String oamxid = "";
                        //String sapbh = "";
                        double je = 0 ;
                        String fs = "";
                        String zjwybsf = "";
                        String fksqdjh = "";
                        String fksqdjxh = "";
                        oaqqid =  Util.null2String(rs.getString("zjwybsf"));
                        oamxid =  Util.null2String(rs.getString("oamxid"));
                        zjwybsf =  Util.null2String(rs.getString("oaqqid"));
                        fksqdjh =  Util.null2String(rs.getString("fksqdjh"));
                        fksqdjxh =  Util.null2String(rs.getString("fksqdjxh"));
                        je =  Util.getDoubleValue(rs.getString("je"));
                        fs =  Util.null2String(rs.getString("fs"));
                        logger.info("oa请求id"+oaqqid);
                        String sltsql = "SELECT a.tablename\n" +
                                "FROM workflow_bill a\n" +
                                "JOIN workflow_base b ON a.id = b.formid\n" +
                                "JOIN workflow_requestbase c ON b.id = c.workflowid\n" +
                                "WHERE c.requestid = "+oaqqid ;
                        sltRs.execute(sltsql);
                        String bh ="";
                        if (fksqdjh.length()>=6){
                             bh = fksqdjh.substring(0,6);
                        }
                        if (sltRs.next()){
                            String tablename="";
                            tablename =  Util.null2String(sltRs.getString("tablename"));
                            logger.info("表名"+tablename);
                            if (!"".equals(tablename)) {

                                String updsql ="";
                                //承兑
                                if ("0".equals(fs)) {

                                    if ("GYSYF0".equals(bh)) {
                                        updsql = "update " + tablename + "_dt1 set sjfkcdhp = isnull(sjfkcdhp,0)+" + je + " where id=" + oamxid;
                                    }
                                    if ("GYSFK4".equals(bh)) {
                                        updsql = "update " + tablename + " set yfcdje = isnull(yfcdje,0)+" + je + " where id=" + oamxid;
                                    }
                                    if ("GYSFK6".equals(bh)) {
                                        updsql = "update " + tablename + "_dt4 set cdyfkje = isnull(cdyfkje,0)+" + je + " where id=" + oamxid;
                                    }
                                    if ("GYSFK5".equals(bh)) {
                                        updsql = "update " + tablename + "_dt3 set cdyfkje = isnull(cdyfkje,0)+" + je + " where id=" + oamxid;
                                    }

                                }
                                //电汇
                                if ("1".equals(fs)){

                                    if ("GYSYF0".equals(bh)) {
                                        updsql = "update " + tablename + "_dt1 set sjfkdh = isnull(sjfkdh,0)+" + je + " where id=" + oamxid;
                                    }
                                    if ("GYSFK4".equals(bh)) {
                                        updsql = "update " + tablename + " set yfdhje = isnull(yfdhje,0)+" + je + " where id=" + oamxid;
                                    }
                                    if ("GYSFK6".equals(bh)) {
                                        updsql = "update " + tablename + "_dt4 set dhyfkje = isnull(dhyfkje,0)+" + je + " where id=" + oamxid;
                                    }
                                    if ("GYSFK5".equals(bh)) {
                                        updsql = "update " + tablename + "_dt3 set dhyfkje = isnull(dhyfkje,0)+" + je + " where id=" + oamxid;
                                    }
                                }
                                logger.info("更新语句：" + updsql);
                                if (!updRs.executeUpdate(updsql)) {
                                    logger.info("失败：" + updsql);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            result.put("errmsg","自定义出错信息");
            result.put("flag", "false");
        }
        return result;
    }
}
