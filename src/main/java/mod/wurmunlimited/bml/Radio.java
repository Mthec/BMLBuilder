package mod.wurmunlimited.bml;

public class Radio extends BML {
    private final BML parent;
    private final String group;
    private final String id;
    private final String text;
    private final boolean selected;

    Radio(BML parent, String group, String id, String text, boolean selected) {
        this.parent = parent;
        this.group = group;
        this.id = id;
        this.text = text;
        this.selected = selected;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        sb.append("radio{group=\"").append(group).append("\";")
                .append("id=\"").append(id).append("\";")
                .append("text=\"").append(text).append("\"");
        if (selected)
            sb.append(";selected=\"").append(selected).append("\"");
        return sb.append(end);
    }
}
