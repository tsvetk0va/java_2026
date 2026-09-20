package webshop.pages;

public enum Processor {
    SLOW(0, 0f),
    MEDIUM(1, 15f),
    FAST(2, 100f);

    private final int index;
    private final float surcharge;

    Processor(int index, float surcharge) {
        this.index = index;
        this.surcharge = surcharge;
    }
    public int getIndex() {
        return index;
    }
    public float getSurcharge() {
        return surcharge;
    }
}
