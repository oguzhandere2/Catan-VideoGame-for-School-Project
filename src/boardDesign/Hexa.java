package boardDesign;

public class Hexa {
    private HexaType type;
    private int no;
    private int[] adjacents;
    public Hexa(HexaType type, int no) {
        this.type = type;
        this.no = no;
        adjacents = new int[19];

        if(no == 0){
            adjacents[1]++;
            adjacents[3]++;
            adjacents[4]++;
        }
        if(no == 1){
            adjacents[0]++;
            adjacents[2]++;
            adjacents[4]++;
            adjacents[5]++;
        }
        if(no == 2){
            adjacents[1]++;
            adjacents[5]++;
            adjacents[6]++;
        }
        if(no == 3){
            adjacents[0]++;
            adjacents[4]++;
            adjacents[8]++;
            adjacents[7]++;
        }
        if(no == 4){
            adjacents[0]++;
            adjacents[1]++;
            adjacents[5]++;
            adjacents[9]++;
            adjacents[8]++;
            adjacents[3]++;
        }
        if(no == 5){
            adjacents[1]++;
            adjacents[2]++;
            adjacents[6]++;
            adjacents[9]++;
            adjacents[4]++;
        }
        if(no == 6){
            adjacents[2]++;
            adjacents[5]++;
            adjacents[10]++;
            adjacents[11]++;
        }
        if(no == 7){
            adjacents[3]++;
            adjacents[8]++;
            adjacents[12]++;
        }
        if(no == 8){
            adjacents[3]++;
            adjacents[4]++;
            adjacents[9]++;
            adjacents[13]++;
            adjacents[12]++;
            adjacents[7]++;
        }
        if(no == 9){
            adjacents[4]++;
            adjacents[5]++;
            adjacents[10]++;
            adjacents[14]++;
            adjacents[13]++;
            adjacents[8]++;
        }
        if(no == 10){
            adjacents[5]++;
            adjacents[6]++;
            adjacents[11]++;
            adjacents[15]++;
            adjacents[14]++;
            adjacents[9]++;
        }
        if(no == 11){
            adjacents[6]++;
            adjacents[10]++;
            adjacents[15]++;
        }
        if(no == 12){
            adjacents[7]++;
            adjacents[8]++;
            adjacents[13]++;
            adjacents[16]++;
        }
        if(no == 13){
            adjacents[8]++;
            adjacents[9]++;
            adjacents[14]++;
            adjacents[17]++;
            adjacents[16]++;
            adjacents[12]++;
        }
        if(no == 14){
            adjacents[9]++;
            adjacents[10]++;
            adjacents[13]++;
            adjacents[15]++;
            adjacents[17]++;
            adjacents[18]++;
        }
        if(no == 15){
            adjacents[10]++;
            adjacents[11]++;
            adjacents[14]++;
            adjacents[18]++;
        }
        if(no == 16){
            adjacents[12]++;
            adjacents[13]++;
            adjacents[17]++;
        }
        if(no == 17){
            adjacents[13]++;
            adjacents[14]++;
            adjacents[16]++;
            adjacents[18]++;
        }
        if(no == 18){
            adjacents[14]++;
            adjacents[15]++;
            adjacents[17]++;
        }

    }

    public HexaType getType() {
        return type;
    }

    public int getNo() {
        return no;
    }

}