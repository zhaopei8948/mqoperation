package online.zhaopei.mqoperation.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Queue {

    private Long id;

    private String name;

    private String describe;

    private Long managerId;

    private QueueManager queueManager;
}
