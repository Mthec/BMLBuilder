package mod.wurmunlimited.bml;

public class Header extends Text {
    Header(BML parent, String text) {
        super(parent, text);
        style = FontStyle.Bold;
    }

    @Override
    public Text italic() {
        return this;
    }
}
