package enums;

public enum CardSuit { // цвят

    Club(CardColor.black),
    Diamond(CardColor.red),
    Heart(CardColor.red),
    Spade(CardColor.black);

    private final CardColor cardColor;

    CardSuit(CardColor cardColor) { // приема чисто козметичния CardColor
        this.cardColor = cardColor;
    }

    public CardColor getCardColor() {
        return cardColor;
    }
}
