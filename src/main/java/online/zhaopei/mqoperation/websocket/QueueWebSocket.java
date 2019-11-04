package online.zhaopei.mqoperation.websocket;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import online.zhaopei.mqoperation.constant.CommonConstant;
import online.zhaopei.mqoperation.task.MonitorQueueStatusTask;
import online.zhaopei.mqoperation.utils.CommonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ScheduledFuture;

@ServerEndpoint(prefix = "mqws")
@Service
public class QueueWebSocket {

    private static final Log logger = LogFactory.getLog(QueueWebSocket.class);

    private Session session;

    private ScheduledFuture scheduledFuture;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private MonitorQueueStatusTask monitorQueueStatusTask;

    private static CopyOnWriteArraySet<QueueWebSocket> webSocketSet = new CopyOnWriteArraySet<QueueWebSocket>();

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws IOException {
        this.session = session;
        webSocketSet.add(this);
        System.out.println("new connection");

        String qid = parameterMap.getParameter("qid");

        String qmid = parameterMap.getParameter("qmid");

        if (!StringUtils.isEmpty(qid) || !StringUtils.isEmpty(qmid)) {
            scheduledFuture = this.threadPoolTaskScheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        String time = CommonConstant.DATE_TIME_FORMAT.format(Calendar.getInstance().getTime());
                        if (!StringUtils.isEmpty(qmid)) {
                            //session.sendText(monitorQueueStatusTask.getQueueDepthJsonByManagerId(Long.valueOf(qmid)).toJSONString());
                            session.sendText(monitorQueueStatusTask.getQueueDepthJsonByManagerIds(qmid).toJSONString());
                        } else {
                            if (Long.valueOf(qid) > 0) {
                                session.sendText(time + "," + monitorQueueStatusTask.getQueueDepth(Long.valueOf(qid)));
                            } else {
                                session.sendText(monitorQueueStatusTask.getAllQueueDepthJson().toJSONString());
                            }
                        }
                    } catch (Exception e) {
                        CommonUtils.logError(logger, e);
                    }
                }
            }, new PeriodicTrigger(2000));
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        if (null != this.scheduledFuture) {
            this.scheduledFuture.cancel(true);
        }
        System.out.println("one connection closed");
        this.monitorQueueStatusTask.closeMQ();
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
        this.monitorQueueStatusTask.closeMQ();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println(message);
        session.sendText("Hello Netty!");
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        for (byte b : bytes) {
            System.out.println(b);
        }
        session.sendBinary(bytes);
    }

    public void sendMessage(String message) throws IOException {
        if (null != this.session) {
            this.session.sendText(message);
        }
    }

    public void sendAllMessage(String message) throws Exception {
        for (QueueWebSocket qws : webSocketSet) {
            qws.sendMessage(message);
        }
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
        }
    }

}
