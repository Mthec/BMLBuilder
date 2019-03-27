package mod.wurmunlimited.bml;

public class Button extends BML implements Hover {
    private final BML parent;
    private final String id;
    private final String label;
    private String message = "";
    private String question = "";
    private String hover = "";

    Button(BML parent, String id, String label) {
        this.parent = parent;
        this.id = id;
        this.label = label;
    }

    public BML confirm(String message, String question) {
        this.message = message;
        this.question = question;
        return this;
    }

    public Button hover(String text) {
        hover = text;
        return this;
    }

    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        sb.append("button{text=\"").append(label)
                .append("\";id=\"").append(id).append("\"");
        if (!hover.isEmpty()) {
            sb.append(";hover=\"").append(hover).append("\"");
        }
        if (!message.isEmpty()) {
            sb.append(";confirm=\"").append(message)
                    .append("\";question=\"").append(question).append("\"");
        }
        return sb.append(end);
    }
}
