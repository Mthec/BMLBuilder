package mod.wurmunlimited.bml;

class HArray extends BML {
    private final BML parent;

    HArray(BML parent) {
        this.parent = parent;
    }

    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        addSeparator();
        return sb.append("harray{");
    }
}
