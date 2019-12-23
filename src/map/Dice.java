package map;

public class Dice {
    public static int roll(){
        int firstDie = (int)(Math.random() * 6 + 1);
        int secondDie = (int)(Math.random() * 6 + 1);
        return firstDie+secondDie;
    }
}