package online.zhaopei.mqoperation.domain;

import lombok.Data;

import java.util.List;

@Data
public class QueueGroup {

    private Long id;

    private String name;

    private String describe;

    private List<Long> queueIdList;
}
