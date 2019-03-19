package mod.wurmunlimited.bml;

public class BMLBuilder extends BML {
    private final int id;
    private final boolean vertical;
    private final boolean horizontal;
    private final String question;

    public BMLBuilder(int id)  {
        this(id, true, false, "");
    }

    public BMLBuilder(int id, boolean vertical, boolean horizontal, String question)  {
        this.id = id;
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.question = question;
    }

    @Override
    StringBuilder buildBML() {
        return new StringBuilder("border{center{text{type=\"bold\";text=\"" + question + "\"}};null;scroll{vertical=\"").append(vertical)
                       .append("\";horizontal=\"").append(horizontal).append("\";varray{rescale=\"true\";passthrough{id=\"id\";text=\"")
                .append(id).append("\"}");
    }
}
