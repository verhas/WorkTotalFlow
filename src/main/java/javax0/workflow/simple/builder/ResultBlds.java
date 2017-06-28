package javax0.workflow.simple.builder;

public class ResultBlds<R> extends BldMap<R,ResultBld<R>> {
    @Override
    ResultBld<R> factory() {
        return new ResultBld();
    }
}
