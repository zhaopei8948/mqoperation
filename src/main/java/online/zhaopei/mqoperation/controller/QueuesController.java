package online.zhaopei.mqoperation.controller;

import com.ibm.mq.MQQueue;
import com.ibm.mq.constants.MQConstants;
import online.zhaopei.mqoperation.constant.QueueConstant;
import online.zhaopei.mqoperation.domain.MQQueueInfo;
import online.zhaopei.mqoperation.domain.QueueManager;
import online.zhaopei.mqoperation.service.QueueManagerService;
import online.zhaopei.mqoperation.task.MonitorQueueStatusTask;
import online.zhaopei.mqoperation.utils.CommonUtils;
import online.zhaopei.mqoperation.domain.Queue;
import online.zhaopei.mqoperation.service.QueueService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/queues")
public class QueuesController {

    private static final Log logger = LogFactory.getLog(QueuesController.class);

    @Autowired
    private QueueService queueService;

    @Autowired
    private QueueManagerService queueManagerService;

    @Autowired
    private MonitorQueueStatusTask monitorQueueStatusTask;

    @RequestMapping
    public ModelAndView index(Queue queue) {
        ModelAndView modelAndView = new ModelAndView("queue/index");
        List<Queue> queueList = this.queueService.select(queue);
        modelAndView.addObject("queueList", queueList);
        modelAndView.addObject("queue", queue);
        return modelAndView;
    }

    @RequestMapping("/toAdd")
    public ModelAndView toAdd() {
        ModelAndView modelAndView = new ModelAndView("queue/form");
        modelAndView.addObject("queue", new Queue());
        modelAndView.addObject("queueManagerList", this.queueManagerService.select(new QueueManager()));
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView add(Queue queue, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/queues");
        try {
            this.queueService.insert(queue);
            redirectAttributes.addFlashAttribute("status", "添加队列成功!");
        } catch (Exception e) {
            CommonUtils.logError(logger, e);
            modelAndView.addObject("status", "添加队列失败!");
            modelAndView.addObject("queue", queue);
            modelAndView.addObject("info", e.getMessage());
            modelAndView.setViewName("queue/form");
        }
        return modelAndView;
    }

    @RequestMapping("/toUpdate")
    public ModelAndView toUpdate(long id) {
        ModelAndView modelAndView = new ModelAndView("queue/form");
        Queue queue = this.queueService.selectById(id);
        modelAndView.addObject("queue", queue);
        modelAndView.addObject("queueManagerList", this.queueManagerService.select(new QueueManager()));
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView update(Queue queue, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/queues");

        try {
            this.queueService.update(queue);
            redirectAttributes.addFlashAttribute("status", "更新队列成功!");
        } catch (Exception e) {
            CommonUtils.logError(logger, e);
            modelAndView.addObject("status", "更新队列失败!");
            modelAndView.addObject("queue", queue);
            modelAndView.addObject("info", e.getMessage());
            modelAndView.setViewName("queue/form");
        }

        return modelAndView;
    }

    @RequestMapping("/delete")
    public ModelAndView deleteById(long id, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/queues");
        try {
            this.queueService.deleteById(id);
            redirectAttributes.addFlashAttribute("status", "删除队列成功!");
        } catch (Exception e) {
            CommonUtils.logError(logger, e);
            redirectAttributes.addFlashAttribute("status", "删除队列失败!");
        }
        return modelAndView;
    }

    @RequestMapping("/show")
    public ModelAndView show(long id) {
        ModelAndView modelAndView = new ModelAndView("queue/show");
        modelAndView.addObject("queue", this.queueService.getQueueAndManager(id));
        return modelAndView;
    }

    @RequestMapping("/checkInfo")
    public ModelAndView checkInfo(long id) {
        ModelAndView modelAndView = new ModelAndView("queue/check_info");
        modelAndView.addObject("queue", this.queueService.getQueueAndManager(id));
        MQQueue mqQueue = this.monitorQueueStatusTask.getQueueTaskById(id);
        MQQueueInfo mqQueueInfo = new MQQueueInfo();
        try {
            mqQueueInfo.setCurrentDepth(mqQueue.getCurrentDepth());
            mqQueueInfo.setInhibitGet(mqQueue.getInhibitGet() == MQConstants.MQQA_GET_INHIBITED);
            mqQueueInfo.setInhibitPut(mqQueue.getInhibitPut() == MQConstants.MQQA_PUT_INHIBITED);
            mqQueueInfo.setMaximumDepth(mqQueue.getMaximumDepth());
            mqQueueInfo.setMaximumMessageLength(mqQueue.getMaximumMessageLength());
            mqQueueInfo.setOpenInputCount(mqQueue.getOpenInputCount());
            mqQueueInfo.setOpenOutputCount(mqQueue.getOpenOutputCount());
            mqQueueInfo.setQueueType(mqQueue.getQueueType());
        } catch (Exception e) {
            CommonUtils.logError(logger, e);
        }
        modelAndView.addObject("mqQueueInfo", mqQueueInfo);
        modelAndView.addObject("MQQueueType", QueueConstant.MQQUEUE_TYPE_MAP);
        return modelAndView;
    }
}
