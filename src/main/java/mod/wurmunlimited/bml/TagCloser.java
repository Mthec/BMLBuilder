package mod.wurmunlimited.bml;

class TagCloser extends BML {
    private final BML parent;

    TagCloser(BML parent) {
        this.parent = new CharRemover(parent, ';');
    }

    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        removeSeparator();
        return sb.append(end);
    }
}
