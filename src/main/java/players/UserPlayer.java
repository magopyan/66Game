package players;

import cards.Card;

import java.util.List;

public class UserPlayer extends Player {

    public void playChosenCard(Card card) {
        cardsInHand.remove(card);
    } // изиграва подадената като параметър карта

    public List<Card> getCardsInHand() {
        return cardsInHand;
    } // връща картите от "ръката"
}
