package javax0.workflow.samples;

import javax0.workflow.Result;
import javax0.workflow.Step;
import javax0.workflow.Workflow;
import javax0.workflow.exceptions.ValidatorFailed;
import javax0.workflow.simple.WorkflowBuilder;
import javax0.workflow.utils.Executor;
import javax0.workflow.utils.WorkflowWrapper;
import org.junit.Test;

import java.util.function.Supplier;

import static javax0.workflow.utils.Entry.entry;

public class VacationRequest {

    @Test
    public void sampleVacationRequestWorkflow() throws ValidatorFailed {
        Workflow<String, String, String, Object, Object> workflow = createWorkflow();

        WorkflowWrapper<String, String, String, Object, Object> wf = new WorkflowWrapper<>(workflow);
        wf.setLogger(System.out::println);

        while (wf.notOnlyIn("approved")) {
            if (wf.isIn("start")) {
                if (wf.canExecute("submit")) {// condition says it can be submitted
                    wf.execute();
                }
            }
            if (wf.isIn("RM approval pending")) {
                if (wf.canExecute("approve")) {// condition says it can be submitted
                    wf.execute();
                }
            }
            if (wf.isIn("PM approval pending")) {
                if (wf.canExecute("approve")) {// condition says it can be submitted
                    wf.execute();
                }
            }
        }
    }

    @Test
    public void sampleVacationRequestWorkflowExecutorUsed() throws ValidatorFailed {
        Workflow<String, String, String, Object, Object> workflow = createWorkflow();

        WorkflowWrapper<String, String, String, Object, Object> wf = new WorkflowWrapper<>(workflow);
        wf.setLogger(System.out::println);

        Executor<String, String, String, Object, Object> executor = new Executor<>(wf,
                m -> {
                    if (m.containsKey("start")) return entry("start", "submit");
                    if (m.containsKey("RM approval pending")) return entry("RM approval pending", "approve");
                    if (m.containsKey("PM approval pending")) return entry("PM approval pending", "approve");
                    return null;
                },
                (q) -> null,
                t -> {
                }
        );
        while (wf.notOnlyIn("approved")) {
            executor.step();
        }
    }

    private Workflow<String, String, String, Object, Object> createWorkflow() {
        WorkflowBuilder<String, String, String, Object, Object> wb = new WorkflowBuilder<>("OK");
        /* TODO create a builder that converts a string of the following form to builder calls
        start -> submit -> RM approval pending, PM approval pending
              -> withdraw -> withdrawn
        PM approval pending -> approve ? OK -> final auto approve pending
                                       ?  reject -> rejected
        RM approval pending -> approve ? OK -> final auto approve pending
                                       ?  reject -> rejected
                            -> reconsider -> PM approval pending
         */
        wb.from("start").action("submit").to("RM approval pending", "PM approval pending")
                .action("withdraw").to("withdrawn");
        wb.from("PM approval pending").action("approve").when("OK").then("final auto approve pending")
                .when("reject").then("rejected");
        wb.from("RM approval pending").action("approve").when("OK").then("final auto approve pending")
                .when("reject").then("rejected");
        wb.from("final auto approve pending").to("approved");
        wb.from("RM approval pending").action("reconsider").to("PM approval pending");
        wb.action("approve");
        wb.action("reconsider");
        wb.action("withdraw");
        wb.action("submit")
                .parameter("a", "b")
                .preFunction((action) -> null)
                .condition((action) -> true)
                .postFunction((action, t, user) -> {
                    Step<String, String, String, Object, Object> step = action.getStep();
                    Workflow<String, String, String, Object, Object> workflow = step.getWorkflow();
                    Supplier<Result<String, String, String, Object, Object>> supplier = workflow.result(action, "OK");
                    return supplier.get();
                })
                .validator((action, t, user) -> true)
        ;

        return wb.start("start");
    }

}
