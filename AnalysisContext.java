/**
 * AnalysisContext.java
 * Context class for applying Strategy Pattern with Analyzable interface.
 */
public class AnalysisContext {
    
    private Analyzable strategy;

    public void setStrategy(Analyzable strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        if (strategy != null) {
            strategy.analyze();
        } else {
            System.out.println("[ERROR] No strategy set.");
        }
    }
}
