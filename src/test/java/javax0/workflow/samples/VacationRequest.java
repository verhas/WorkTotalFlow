package javax0.workflow.samples;

import javax0.workflow.Result;
import javax0.workflow.Step;
import javax0.workflow.Workflow;
import javax0.workflow.simple.builder.WorkflowBuilder;
import org.junit.Test;

import java.util.function.Supplier;

public class VacationRequest {

    @Test
    public void sampleVacationRequestWorkflow() {
        WorkflowBuilder<String, String, String, Object> wb = new WorkflowBuilder<>("OK");
        wb.from("start").action("submit").to("RM approval pending", "PM approval pending")
                .action("withdraw").to("withdrawn");
        wb.from("PM approval pending").action("approve").when("OK").then("final auto approve pending")
                .when("reject").then("rejected");
        wb.from("RM approval pending").action("approve").when("OK").then("final auto approve pending")
                .when("reject").then("rejected");
        wb.from("RM approval pending").action("reconsider").when("OK").then("PM approval pending");
        wb.action("submit")
                .parameter("a", "b")
                .preFunction((action) -> null)
                .condition((action) -> true)
                .postFunction((action, t, user) -> {
                    Step<String, String, String, Object> step = action.getStep();
                    Workflow<String, String, String, Object> workflow = step.getWorkflow();
                    Supplier<Result<String, String, String, Object>> supplier = workflow.result(action, "OK");
                    Result<String, String, String, Object> result = supplier.get();
                    return result;
                })
                .validator((action, t, user) -> true)
        ;

        Workflow<String, String, String, Object> workflow = wb.start("start");
    }

}
