package mod.wurmunlimited.bml;

import com.google.common.base.Joiner;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public abstract class BML {
    private static int endCounter = 0;
    static String end = "}";

    void addSeparator() {
        ++endCounter;
        end = "};";
    }

    void removeSeparator() {
        --endCounter;
        if (endCounter == 0)
            end = "}";
    }

    public Button button(String id, String label) {
        return new Button(this, id, label);
    }

    public Button button(String label) {
        return button("submit", label);
    }

    public BML closeButton(String id) {
        return new CloseButton(this, id);
    }

    public BML checkbox(String id) {
        return checkbox(id, "", false);
    }

    public BML checkbox(String id, boolean checked) {
        return checkbox(id, "", checked);
    }

    public BML checkbox(String id, String label) {
        return checkbox(id, label, false);
    }

    public BML checkbox(String id, String label, boolean checked) {
        return new Checkbox(this, id, label, checked);
    }

    public BML radio(String group, String id, String text) {
        return radio(group, id, text, false);
    }

    public BML radio(String group, String id, String text, boolean selected) {
        return new Radio(this, group, id, text, selected);
    }

    public Entry entry(String id, int maxChars) {
        return entry(id, "", maxChars);
    }

    public Entry entry(String id, String value, int maxChars) {
        return new Entry(this, id, maxChars, value);
    }

    public Text text(String text) {
        return new Text(this, text);
    }

    public BML label(String text) {
        return new Label(this, text);
    }

    public Header header(String text) {
        return new Header(this, text);
    }

    public BML dropdown(String id, String options) {
        return dropdown(id, options, 0);
    }

    public BML dropdown(String id, String options, int selected) {
        return new Dropdown(this, id, options, selected);
    }

    public BML dropdown(String id, List<String> options, int selected) {
        return dropdown(id, Joiner.on(",").join(options), selected);
    }

    public BML dropdown(String id, List<String> options) {
        return dropdown(id, Joiner.on(",").join(options), 0);
    }

    public BML newLine() {
        return new Text(this, "");
    }

    public BML spacer() {
        return new Label(this, "spacedlxg", " ");
    }

    public BML raw(String rawString) {
        return new Raw(this, rawString);
    }

    // Compound tags

    public BML harray(Function<BML, BML> contents) {
        HArray h = new HArray(this);
        return new TagCloser(contents.apply(h));
    }

    public <T> BML table(String[] columnTitles, Collection<T> rowData, BMLFor<T> rowBuilder) {
        Table table = new Table(this, columnTitles);
        BML bml = table;
        for (T t : rowData) {
            BML nextBml = rowBuilder.apply(t, bml);
            if (nextBml != bml) {
                table.addRow();
                nextBml = new CharRemover(nextBml, ';');
            }
            bml = nextBml;
        }
        return new TagCloser(bml);
    }

    // Conditionals

    public BML If(boolean condition, Function<BML, BML> If) {
        return If(condition, If, null);
    }

    public BML If(boolean condition, Function<BML, BML> If, Function<BML, BML> Else) {
        if (condition)
            return If.apply(this);
        else if (Else != null)
            return Else.apply(this);
        else
            return this;
    }

    public <T> BML forEach(Iterable<T> iterable, BMLFor<T> action) {
        BML bml = this;
        for (T t : iterable) {
            bml = action.apply(t, bml);
        }
        return bml;
    }

    abstract StringBuilder buildBML();

    public String build() {
        return buildBML().append("}};null;null;}").toString();
    }
}
