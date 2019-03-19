package mod.wurmunlimited.bml;

public class Entry extends BML {
    private final BML parent;
    private final String id;
    private final int maxChars;
    private final String value;
    private String onEnter = "";

    Entry(BML parent, String id, int maxChars, String value) {
        this.parent = parent;
        this.id = id;
        this.maxChars = maxChars;
        this.value = value;
    }

    public Entry onEnter(String command) {
        onEnter = command;
        return this;
    }

    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        sb.append("input{text=\"");
        if (!value.isEmpty())
            sb.append(value);
        sb.append("\";id=\"").append(id).append("\";");
        sb.append("maxchars=\"").append(maxChars).append("\"");
        if (!onEnter.isEmpty())
            sb.append(";onenter=\"").append(onEnter).append("\"");
        return sb.append(end);
    }
}
