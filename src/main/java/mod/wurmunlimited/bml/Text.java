package mod.wurmunlimited.bml;

public class Text extends BML {
    private final BML parent;
    private final String text;
    private Alignment alignment = Alignment.None;
    protected FontStyle style = FontStyle.None;
    private short r = 0;
    private short g = 0;
    private short b = 0;

    enum FontStyle {
        None,
        Italic,
        Bold,
        BoldItalic
    }

    enum Alignment {
        None,
        Center
    }

    Text(BML parent, String text) {
        this.parent = parent;
        this.text = text;
    }

    public Text italic() {
        style = FontStyle.Italic;
        return this;
    }

    public Text bold() {
        style = FontStyle.Bold;
        return this;
    }

    public Text bolditalic() {
        style = FontStyle.BoldItalic;
        return this;
    }

    public Text color(short r, short b, short g) {
        this.r = r;
        this.b = b;
        this.g = g;
        return this;
    }

    public Text safe() {
        return color((short)0, (short)255, (short)0);
    }

    public Text warning() {
        return color((short)255, (short)211, (short)0);
    }

    public Text error() {
        return color((short)255, (short)0, (short)0);
    }

    public Text center() {
        alignment = Alignment.Center;
        return this;
    }

    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        if (alignment == Alignment.Center)
            sb.append("center{");

        sb.append("text{");
        switch (style) {
            case Italic:
                sb.append("type=\"italic\";");
                break;
            case Bold:
                sb.append("type=\"bold\";");
                break;
            case BoldItalic:
                sb.append("type=\"bolditalic\";");
                break;
        }
        if (r + g + b != 0)
            sb.append(String.format("color=\"%d,%d,%d\";", r, g, b));

        sb.append("text=\"").append(text).append("\"");

        if (alignment == Alignment.Center)
            sb.append("}");
        return sb.append(end);
    }
}
