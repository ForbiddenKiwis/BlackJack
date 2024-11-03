public class Card {
    private String value;
    private String type;

    public Card(String value, String type) {
        this.value=value;
        this.type=type;
    }

    public String toString() {
        return value + "-" + type;
    }

    public int getValue(){
        if ("AJQK".contains(value)) { // JQKA
            if (value == "A") {
                return 11;
            } else {
                return 10;
            }
        }
        return Integer.parseInt(value); // 2-10
    }

    public boolean isAce(){
        return value.equals("A");
    }

    public String getImagePath(){
        return "./cards/" + toString() + ".png";
    }
}
