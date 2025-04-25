import java.util.*;

public class TrackingSystem {
    private List<SpaceObject> allObjects;

    public TrackingSystem(List<SpaceObject> objects) {
        this.allObjects = objects;
    }

    public List<SpaceObject> getAllObjects() {
        return allObjects;
    }

    public TrackingSystem() {
        allObjects = new ArrayList<>();
    }

    public void addSpaceObject(SpaceObject obj) {
        allObjects.add(obj);
    }

    public List<SpaceObject> getObjectsByType(String type) {
        List<SpaceObject> result = new ArrayList<>();
        for (SpaceObject obj : allObjects) {
            if (obj.getObjectType() != null && obj.getObjectType().equalsIgnoreCase(type.trim())) {
                result.add(obj);
            }
        }
        return result;
    }
    

    public void writeUpdatedReport(String a){
        return;
    }

    public void trackObjectsInSpace(){
        //
        return;
    }

    public void assessOrbitStatus(){
        //
        return;
    }





}
