package id.maja.xcore.workflow;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class BaseProcess<T, R> {
    private final List<BaseTrigger> onInitTriggers = new ArrayList<>();
    private final List<BaseTrigger> onSuccessTriggers = new ArrayList<>();
    private final List<BaseTrigger> onErrorTriggers = new ArrayList<>();

    @Getter
    private String name;

    public void addTrigger(BaseTrigger trigger) {
        log.debug("Adding Trigger");
        log.debug("Trigger Type = {}", trigger.getType());
        log.debug("Trigger Name = {}", trigger.getName());
        if (trigger.getType() == TriggerType.ON_INIT) {
            onInitTriggers.add(trigger);
        } else if (trigger.getType() == TriggerType.ON_SUCCESS) {
            onSuccessTriggers.add(trigger);
        } else if (trigger.getType() == TriggerType.ON_ERROR) {
            onErrorTriggers.add(trigger);
        } else {
            log.error("Unknown Trigger Type");
        }
    }

    public R run(T obj) {
        this.runTrigger(obj, TriggerType.ON_INIT);
        try {
            Object result = this.exec(obj);
            this.runTrigger(obj, TriggerType.ON_SUCCESS);
            return (R) result;
        } catch (Exception e) {
            this.runTrigger(obj, TriggerType.ON_ERROR);
            return null;
        }
    }

    public R exec(T obj){
        return null;
    }

    @Async("taskExecutor")
    public void runTrigger(Object obj, TriggerType type) {
        List<BaseTrigger> triggers = new ArrayList<>();
        if (type == TriggerType.ON_INIT) {
            triggers = onInitTriggers;
        } else if (type == TriggerType.ON_SUCCESS) {
            triggers = onSuccessTriggers;
        } else if (type == TriggerType.ON_ERROR) {
            triggers = onErrorTriggers;
        } else {
            log.error("Unknown Trigger Type");
        }
        if (!triggers.isEmpty()) {
            for (BaseTrigger trigger : triggers) {
                log.debug("Running trigger {}", trigger.getName());
                trigger.run(obj);
            }
        }
    }

}
