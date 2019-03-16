package online.zhaopei.mqoperation.controller;

import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;
import online.zhaopei.mqoperation.websocket.QueueWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Properties;

@Controller
public class MonitorsController {

    @Autowired
    private QueueWebSocket queueWebSocket;

    @RequestMapping("/")
    public ModelAndView index(String message) throws Exception {
//        Properties properties = new Properties();
//        properties.put("hostname", "172.16.33.55");
//        properties.put("port", 1800);
//        properties.put("channel", "JAVA.CHANNEL");
//        properties.put("CCSID", 1381);
//
//        MQQueueManager manager = new MQQueueManager("DFWTINTRA", properties);
//        System.out.println(manager.getResolvedObjectString());
//        System.out.println(manager.getDescription());
//        System.out.println(manager.getName());
//        System.out.println(manager.getAlternateUserId());
//        System.out.println(manager.getCharacterSet());
//        System.out.println(manager.getCloseOptions());
//        System.out.println(manager.getCommandLevel());
//        System.out.println(manager.getOpenOptions());
//
//        int openOptions = MQConstants.MQOO_INQUIRE;
//        MQQueue queue = manager.accessQueue("WICH.RECE.ENT", openOptions);
//
//        System.out.println("该队列当前的深度为:" + queue.getCurrentDepth());
//        System.out.println("该队列打开输入计数:" + queue.getOpenInputCount());
//        System.out.println("该队列打开输出计数为:" + queue.getOpenOutputCount());
//        System.out.println("该队列最大消息大小为:" + queue.getMaximumMessageLength());
//        queue.close();
//        manager.disconnect();
//        this.queueWebSocket.sendMessage(message);
        return new ModelAndView("index");
    }

    @PostMapping("/sendMessage")
    @ResponseBody
    public String sendMessage(String message) throws Exception {
        this.queueWebSocket.sendAllMessage(message);
        return "SUCCESS";
    }
}
