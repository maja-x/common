package id.maja.x.common.workflow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class Workflow {
    private List<WorkflowTask<?>> tasks = new ArrayList<>();
    private Object data;

    public Workflow addInput(Object input) {
        this.data = input;
        return this;
    }

    public Workflow addProcess(BaseProcess process) {
        log.debug(String.valueOf(process));
        WorkflowTask<BaseProcess> task = new WorkflowTask<>();
        task.setName(process.getName());
        task.setType(WorkflowType.PROCESS);
        task.setOrder(tasks.size());
        task.setTask(process);
        this.tasks.add(task);
        return this;
    }

    public void run() {
        boolean isRunning = true;
        int taskIndex = 0;
        while (isRunning) {
            WorkflowTask<?> task = this.tasks.get(taskIndex);
            if (task.getType() == WorkflowType.PROCESS) {
                try {
                    BaseProcess process = (BaseProcess) task.getTask();
                    //log.debug("Running Process {}", process.getClass().getName());
                    Object input = this.data;
                    this.data = process.run(input);
                    if (this.data == null) {
                        log.error("---------------------------------------");
                        log.error("Workflow Failed");
                        log.error("Process = {}", process.getName());
                        log.error("Input = {}", input);
                        log.error("Output = {}", this.data);
                        log.error("---------------------------------------");
                        throw new Exception("Empty response");
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    isRunning = false;
                }
            }

            taskIndex++;
            if (taskIndex >= this.tasks.size()) {
                isRunning = false;
            }
        }
    }
}
