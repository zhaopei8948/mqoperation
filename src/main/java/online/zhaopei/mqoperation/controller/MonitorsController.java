package online.zhaopei.mqoperation.controller;

import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;
import online.zhaopei.mqoperation.domain.Queue;
import online.zhaopei.mqoperation.service.QueueService;
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

    @Autowired
    private QueueService queueService;

    @RequestMapping("/")
    public ModelAndView index(String message) throws Exception {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("queueList", this.queueService.select(new Queue()));
        return modelAndView;
    }
}
