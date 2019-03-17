package online.zhaopei.mqoperation.runner;

import online.zhaopei.mqoperation.utils.CommonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class ReadConfigRunner implements CommandLineRunner {

    private static final Log logger = LogFactory.getLog(ReadConfigRunner.class);

    @Override
    public void run(String... args) throws Exception {
//        String sql = "delete from queue where and manager_id";
//        logger.info(CommonUtils.removeAnd(sql));
//        BufferedReader br = null;
//        String line = null;
//        StringBuilder result = new StringBuilder("");
//        JSONArray jsonArray = new JSONArray();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream("mqlist.json"), "UTF-8"));
//            while (!StringUtils.isEmpty(line = br.readLine())) {
//                result.append(line);
//            }
//            jsonArray = JSON.parseArray(result.toString());
//            logger.info("jsonarray size = " + jsonArray.size());
//            logger.error("gson=" + gson.toJson(jsonArray));
//        } catch (Exception e) {
//            CommonUtils.logError(logger, e);
//        } finally {
//            if (null != br) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    CommonUtils.logError(logger, e);
//                }
//            }
//        }
    }
}
