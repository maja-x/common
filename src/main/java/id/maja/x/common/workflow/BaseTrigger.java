package id.maja.x.common.workflow;

import lombok.Data;

@Data
public class BaseTrigger {
    private TriggerType type = null;
    private String name = "";

    public void run(Object obj) {

    }
}
