package id.maja.x.common.workflow;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class BaseDecision {
    @Getter
    @Setter
    Object data;

    @Setter
    @Getter
    boolean valid = false;

    Workflow onErrorWorkflow;
    Workflow onSuccessWorkflow;


    public void validate() {
        throw new RuntimeException("Not implemented");
    }

    public void run() {
        if (this.valid) {
            this.runOnError();
        } else {
            this.runOnSuccess();
        }
    }

    public BaseDecision onError(Workflow input) {
        this.onErrorWorkflow = input;
        return this;
    }

    public BaseDecision onSuccess(Workflow input) {
        this.onSuccessWorkflow = input;
        return this;
    }

    public void runOnSuccess() {
        if (this.onSuccessWorkflow != null) {
            this.onSuccessWorkflow.addInput(this.data);
            this.onSuccessWorkflow.run();
        }
    }

    public void runOnError() {
        if (this.onErrorWorkflow != null) {
            this.onErrorWorkflow.addInput(this.data);
            this.onErrorWorkflow.run();
        }
    }
}
