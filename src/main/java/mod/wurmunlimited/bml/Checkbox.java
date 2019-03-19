package mod.wurmunlimited.bml;

class Checkbox extends BML {
    private final BML parent;
    private final String label;
    private final String id;
    private final boolean checked;

    Checkbox(BML parent, String label, String id, boolean checked) {
        this.parent = parent;
        this.label = label;
        this.id = id;
        this.checked = checked;
    }

    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        if (!label.isEmpty())
            sb.append("harray{label{text=\"").append(label).append("\"}");
        sb.append("checkbox{")
                .append("id=\"").append(id).append("\";")
                .append("selected=\"").append(checked).append("\";")
                .append("text=\" \"");
        if (!label.isEmpty())
            sb.append("}");
        return sb.append(end);
    }
}
