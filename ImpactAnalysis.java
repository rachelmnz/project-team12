import java.util.List;
/**
*Performs the Long-Term impact analysis for the LEO Objects
*/

public class ImpactAnalysis implements Analyzable {
    private final TrackingSystem tracker;
/** 
*Creating a ImpactAnalysis with the given tracker
* 
*@param tracker the tracking system to use
*/
    public ImpactAnalysis(TrackingSystem tracker) {
        this.tracker = tracker;
    }
/** 
*Analyzes objects in the LEO older than 200 days with conjunctions
*/

    @Override
    public void analyze() {
        System.out.println("\nAnalyzing long-term impact for LEO objects (daysOld > 200 and conjunctionCount > 0)...\n");

        List<SpaceObject> objects = tracker.getAllObjects();
        int count = 0;

        for (SpaceObject obj : objects) {
            if ("LEO".equalsIgnoreCase(obj.getOrbitType()) &&
                obj.getDaysOld() > 200 &&
                obj.getConjunctionCount() > 0) {

                count++;
                System.out.printf("ID: %s | Name: %s | Country: %s | Orbit: %s | Type: %s | Days Old: %d | Conjunctions: %d\n",
                        obj.getRecordId(), obj.getSatelliteName(), obj.getCountry(),
                        obj.getOrbitType(), obj.getClass().getSimpleName(),
                        obj.getDaysOld(), obj.getConjunctionCount());
            }
        }

        System.out.printf("\nTotal high-risk LEO objects: %d\n", count);
    }
}
