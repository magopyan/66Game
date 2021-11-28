package enums;

public enum CardRank { // девятка/вале/царица/поп/десятка/асак, като всеки приема като параметър своята сила/брой точки

    nine(0),
    J(2),
    Q(3),
    K(4),
    ten(10),
    A(11);

    private final int strength;

    CardRank(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }
}
