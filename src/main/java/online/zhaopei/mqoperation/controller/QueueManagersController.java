package online.zhaopei.mqoperation.controller;

import online.zhaopei.mqoperation.domain.QueueManager;
import online.zhaopei.mqoperation.service.QueueManagerService;
import online.zhaopei.mqoperation.utils.CommonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/queueManagers")
public class QueueManagersController {

    private static final Log logger = LogFactory.getLog(QueueManagersController.class);

    @Autowired
    private QueueManagerService queueManagerService;

    @RequestMapping
    public ModelAndView index(QueueManager queueManager) {
        ModelAndView modelAndView = new ModelAndView("queuemanager/index");
        List<QueueManager> queueManagerList = this.queueManagerService.select(queueManager);
        modelAndView.addObject("queueManagerList", queueManagerList);
        modelAndView.addObject("queueManager", queueManager);
        return modelAndView;
    }

    @RequestMapping("/toAdd")
    public ModelAndView toAdd() {
        ModelAndView modelAndView = new ModelAndView("queuemanager/form");
        modelAndView.addObject("queueManager", new QueueManager());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView add(QueueManager queueManager, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/queueManagers");
        try {
            this.queueManagerService.insert(queueManager);
            redirectAttributes.addFlashAttribute("status", "添加队列管理器成功!");
        } catch (Exception e){
            CommonUtils.logError(logger, e);
            redirectAttributes.addFlashAttribute("status", "添加队列管理器失败!");
            redirectAttributes.addFlashAttribute("info", e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping("/delete")
    public ModelAndView delete(long id, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/queueManagers");
        try {
            this.queueManagerService.deleteById(id);
            redirectAttributes.addFlashAttribute("status", "删除成功!");
        } catch (Exception e) {
            CommonUtils.logError(logger, e);
            redirectAttributes.addFlashAttribute("status", "删除失败!");
            redirectAttributes.addFlashAttribute("info", e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping("/toUpdate")
    public ModelAndView toUpdate(long id) {
        ModelAndView modelAndView = new ModelAndView("queuemanager/form");
        modelAndView.addObject("queueManager", this.queueManagerService.selectById(id));
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView update(QueueManager queueManager) {
        ModelAndView modelAndView = new ModelAndView("redirect:/queueManagers");
        this.queueManagerService.update(queueManager);
        return modelAndView;
    }
}
