package mod.wurmunlimited.bml;

public class Dropdown extends BML {
    private final BML parent;
    private final String id;
    private final String options;
    private final int selected;

    Dropdown(BML parent, String id, String options, int selected) {
        this.parent = parent;
        this.id = id;
        this.options = options;
        this.selected = selected;
    }

    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        sb.append("dropdown{id=\"").append(id).append("\";")
                .append("options=\"").append(options).append("\";")
                .append("default=\"").append(selected).append("\"");
        return sb.append(end);
    }
}
