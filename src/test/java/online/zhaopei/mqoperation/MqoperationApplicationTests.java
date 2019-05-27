package online.zhaopei.mqoperation;

import online.zhaopei.mqoperation.utils.CommonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MqoperationApplicationTests {

	private static final Log logger = LogFactory.getLog(MqoperationApplicationTests.class);

//	@Test
	public void contextLoads() {
	}

//	@Test
	public void removeAdd() {
		String sql = "delete from queue where and manager_id";
		logger.info(CommonUtils.removeAnd(sql));
	}

}
