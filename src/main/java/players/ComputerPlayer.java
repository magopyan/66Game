package players;

import cards.Card;

import java.util.Random;

public class ComputerPlayer extends Player {

    private static Random random = new Random();
    private Card cardToPlay;

    public Card playRandomCard() {  // изиграва карта на случаен индекс от списъка си с карти
        int randomIndex = random.nextInt(cardsInHand.size()); // ако в ръката има 6 карти, генерираният идекс е между 0 и 5 вкл.
        cardToPlay = cardsInHand.get(randomIndex); // случайната карта се запазва в член-променливата, за да може по-късно да се get-не
        cardsInHand.remove(randomIndex);
        return cardToPlay;
    }

    public Card getCardToPlay() {
        return cardToPlay;
    }
}
