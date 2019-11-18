package components;

public class Dice {
    public static int roll(){
        int firstDice = (int)(Math.random()*6)+1;
        int secondDice = (int)(Math.random()*6)+1;
        return firstDice + secondDice;
    }
}
