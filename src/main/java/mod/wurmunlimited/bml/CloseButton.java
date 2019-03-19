package mod.wurmunlimited.bml;

public class CloseButton extends BML {
    private final BML parent;
    private final String id;

    CloseButton(BML parent, String id) {
        this.parent = parent;
        this.id = id;
    }

    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        sb.append("closebutton{id=\"").append(id).append("\"");
        return sb.append(end);
    }
}
