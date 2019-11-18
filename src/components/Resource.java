package components;

import java.util.HashMap;

public class Resource {
    private int[] resources; //brick, ore, grain, wool, lumber
    private HashMap<ResourceType, Integer> resourceMap;
    public Resource() {
        resources = new int[5];
        resourceMap = new HashMap<>();
        resourceMap.put(ResourceType.BRICK,0);
        resourceMap.put(ResourceType.ORE,1);
        resourceMap.put(ResourceType.GRAIN,2);
        resourceMap.put(ResourceType.WOOL,3);
        resourceMap.put(ResourceType.LUMBER,4);
    }

    public Resource(int brick,int ore,int grain,int wool,int lumber) {
        resources = new int[]{brick,ore,grain,wool,lumber};
        resourceMap = new HashMap<>();
        resourceMap.put(ResourceType.BRICK,0);
        resourceMap.put(ResourceType.ORE,1);
        resourceMap.put(ResourceType.GRAIN,2);
        resourceMap.put(ResourceType.WOOL,3);
        resourceMap.put(ResourceType.LUMBER,4);
    }

    public int getResource(ResourceType type){
        return resources[resourceMap.get(type)];
    }

    public int getResource(int resourceNo) {
        return resources[resourceNo];
    }

    public void updateResource(int resourceNo, int newValue){
        resources[resourceNo] = newValue;
    }

    public void updateResource(ResourceType type, int newValue) {
        resources[resourceMap.get(type)] = newValue;
    }

    public static boolean checkResource(Resource own, Resource aim) {
        for(int i = 0; i < 5; i++) {
            if(own.getResource(i) < aim.getResource(i))
                return false;
        }
        return true;
    }

}
