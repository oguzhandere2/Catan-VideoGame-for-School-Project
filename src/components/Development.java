package components;

import java.util.HashMap;

public class Development {
    private int[] developments; //knight, judge, victory, progress
    private HashMap<DevelopmentType, Integer> developmentMap;
    public Development() {
        developments= new int[4];
        developmentMap = new HashMap<>();
        developmentMap.put(DevelopmentType.KNIGHT,0);
        developmentMap.put(DevelopmentType.JUDGE,1);
        developmentMap.put(DevelopmentType.VICTORY,2);
        developmentMap.put(DevelopmentType.PROGRESS,3);
    }

    public Development(int brick,int ore,int grain,int wool,int lumber) {
        developments= new int[4];
        developmentMap = new HashMap<>();
        developmentMap.put(DevelopmentType.KNIGHT,0);
        developmentMap.put(DevelopmentType.JUDGE,1);
        developmentMap.put(DevelopmentType.VICTORY,2);
        developmentMap.put(DevelopmentType.PROGRESS,3);
    }

    public int getDevelopment(DevelopmentType type){
        return developments[developmentMap.get(type)];
    }

    public int getDevelopment(int developmentNo) {
        return developments[developmentNo];
    }
}
