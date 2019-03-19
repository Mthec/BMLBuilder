package mod.wurmunlimited.bml;

class Table extends BML {
    private final BML parent;
    private final String[] columnTitles;
    private int rowCount = 0;

    Table(BML bml, String[] columnTitles) {
        parent = bml;
        this.columnTitles = columnTitles;
    }

    void addRow() {
        ++rowCount;
    }

    @Override
    StringBuilder buildBML() {
        StringBuilder sb = parent.buildBML();
        // Row count starts at 1 if Wurm Questions are correct.
        sb.append("table{rows=\"").append(rowCount + 1).append("\";cols=\"").append(columnTitles.length).append("\";");
        for (String title : columnTitles) {
            sb.append("label{text=\"").append(title).append("\"};");
        }
        addSeparator();
        return sb.deleteCharAt(sb.length() - 1);
    }
}
