package ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.formatter;

import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.model.DiffLine;
import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.model.DiffResult;

public class SideBySideDiffFormatter implements DiffFormatter {
    private final int columnWidth;

    public SideBySideDiffFormatter() {
        this(40);
    }

    public SideBySideDiffFormatter(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    @Override
    public String format(DiffResult result) {
        StringBuilder sb = new StringBuilder();
        String empty = " ".repeat(columnWidth);

        for (DiffLine line : result.getLines()) {
            String content = truncate(line.getContent(), columnWidth);
            switch (line.getType()) {
                case KEEP -> sb.append(pad(content)).append(" | ").append(content);
                case DELETE -> sb.append(pad(content)).append(" < ");
                case INSERT -> sb.append(empty).append(" > ").append(content);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    private String pad(String text) {
        if (text.length() >= columnWidth) {
            return text;
        }
        return text + " ".repeat(columnWidth - text.length());
    }

    private String truncate(String text, int max) {
        if (text.length() > max) {
            return text.substring(0, Math.max(0, max - 3)) + "...";
        }
        return text;
    }
}