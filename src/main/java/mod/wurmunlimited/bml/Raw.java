package mod.wurmunlimited.bml;

class Raw extends BML {
    private final BML parent;
    private final String rawString;

    Raw(BML bml, String rawString) {
        parent = bml;
        this.rawString = rawString;
    }

    @Override
    StringBuilder buildBML() {
        return parent.buildBML().append(rawString);
    }
}
