package mod.wurmunlimited.bml;

class Label extends BML {
    private final BML parent;
    private final String text;
    private final String id;

    Label(BML bml, String id, String text) {
        parent = bml;
        this.id = id;
        this.text = text;
    }

    Label(BML bml, String text) {
        parent = bml;
        this.id = null;
        this.text = text;
    }

    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        sb.append("label{text=\"").append(text).append("\"");
        if (id != null)
            sb.append(";id=\"").append(id).append("\"");
        return sb.append(end);
    }
}
