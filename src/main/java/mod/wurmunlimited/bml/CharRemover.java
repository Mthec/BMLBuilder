package mod.wurmunlimited.bml;

class CharRemover extends BML {
    private final BML parent;
    private final char character;

    CharRemover(BML bml, char character) {
        parent = bml;
        this.character = character;
    }

    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        char c = sb.charAt(sb.length() - 1);
        if (c == character)
            sb.deleteCharAt(sb.length() - 1);
        return sb;
    }
}
