package romestats.bot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TableStringBuilder<T> {
    private final List<String> columnNames;
    private final List<Function<? super T, String>> stringFunctions;

    public TableStringBuilder() {
        columnNames = new ArrayList<>();
        stringFunctions = new ArrayList<>();
    }

    public void addColumn(String columnName, Function<? super T, ?> fieldFunction) {
        columnNames.add(columnName);
        stringFunctions.add((p) -> (String.valueOf(fieldFunction.apply(p))));
    }

    private int computeMaxWidth(int column, Iterable<? extends T> elements) {
        int n = columnNames.get(column).length();
        Function<? super T, String> f = stringFunctions.get(column);
        for (T element : elements) {
            String s = f.apply(element);
            n = Math.max(n, s.length());
        }
        return n;
    }

    private String padLeft(String s, char c, int length) {
        StringBuilder sBuilder = new StringBuilder(s);
        while (sBuilder.length() < length) {
            sBuilder.insert(0, c);
        }
        s = sBuilder.toString();
        return s;
    }

    private List<Integer> computeColumnWidths(Iterable<? extends T> elements) {
        List<Integer> columnWidths = new ArrayList<>();
        for (int c = 0; c < columnNames.size(); c++) {
            int maxWidth = computeMaxWidth(c, elements);
            columnWidths.add(maxWidth);
        }
        return columnWidths;
    }

    public String createString(Iterable<? extends T> elements) {
        List<Integer> columnWidths = computeColumnWidths(elements);

        StringBuilder sb = new StringBuilder();
        for (int c = 0; c < columnNames.size(); c++) {
            if (c > 0) {
                sb.append("|");
            }
            String format = "%" + columnWidths.get(c) + "s";
            sb.append(String.format(format, columnNames.get(c)));
        }
        sb.append("\n");
        for (int c = 0; c < columnNames.size(); c++) {
            if (c > 0) {
                sb.append("+");
            }
            sb.append(padLeft("", '-', columnWidths.get(c)));
        }
        sb.append("\n");

        for (T element : elements) {
            for (int c = 0; c < columnNames.size(); c++) {
                if (c > 0) {
                    sb.append("|");
                }
                String format = "%" + columnWidths.get(c) + "s";
                Function<? super T, String> f = stringFunctions.get(c);
                String s = f.apply(element);
                sb.append(String.format(format, s));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
