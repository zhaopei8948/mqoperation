package online.zhaopei.mqoperation.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueueManager {

    private Long id;

    private String name;

    private String describe;

    private String ip;

    private Integer port;

    private Integer ccsid;

    private String channel;
}
