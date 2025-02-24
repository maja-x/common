package id.maja.x.common.workflow;

import lombok.Data;

@Data
public class WorkflowTask<T> {
    private String name;
    private WorkflowType type;
    private int order;
    private T task;
}
