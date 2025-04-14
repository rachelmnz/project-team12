public class RunSimulation {
    public static void main(String[] args) {
        TrackingSystem tracker = new TrackingSystem();
        FileHandler fileHandler = new FileHandler(tracker);
        fileHandler.loadData("debris_data.csv");

        System.out.println("Loaded objects:");
        for (SpaceObject obj : tracker.getAllObjects()) {
            obj.displayInfo();
        }
    }
}
