package map;

import java.util.Collections;
import java.util.ArrayList;

public class Cards {
    private ArrayList<DevelopmentCard> cardList;
    private final int CAPACITY = 25;
    public Cards() {
        cardList = new ArrayList<DevelopmentCard>(CAPACITY);

        for (int i = 0; i <= 11; i++) {
            cardList.add(new DevelopmentCard(DevelopmentType.KNIGHT));
        }
        for (int i = 0; i <= 4; i++){
            cardList.add(new DevelopmentCard(DevelopmentType.VICTORY));
        }
        cardList.add(new DevelopmentCard(DevelopmentType.PLENTY));
        cardList.add(new DevelopmentCard(DevelopmentType.PLENTY));

        cardList.add(new DevelopmentCard(DevelopmentType.ROADBUILDING));
        cardList.add(new DevelopmentCard(DevelopmentType.ROADBUILDING));

        cardList.add(new DevelopmentCard(DevelopmentType.MONOPOLY));
        cardList.add(new DevelopmentCard(DevelopmentType.MONOPOLY));

        cardList.add(new DevelopmentCard(DevelopmentType.JUDGE));
        cardList.add(new DevelopmentCard(DevelopmentType.JUDGE));

        Collections.shuffle(cardList);
    }

    public DevelopmentCard drawCard() {
        if (cardList.size() > 0) {
            DevelopmentCard card = cardList.get(0);
            cardList.remove(0);
            shuffle();
            return card;
        }
        else
            return null;
    }

    public boolean isEmpty() {
        return cardList.isEmpty();
    }

    public void shuffle(){
        Collections.shuffle(cardList);
    }
}
