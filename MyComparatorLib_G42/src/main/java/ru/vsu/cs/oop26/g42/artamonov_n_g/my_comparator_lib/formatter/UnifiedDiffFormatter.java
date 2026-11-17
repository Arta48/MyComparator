package ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.formatter;

import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.model.DiffLine;
import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.model.DiffResult;

public class UnifiedDiffFormatter implements DiffFormatter {
    @Override
    public String format(DiffResult result) {
        StringBuilder sb = new StringBuilder();
        for (DiffLine line : result.getLines()) {
            switch (line.getType()) {
                case KEEP -> sb.append("  ").append(line.getContent());
                case INSERT -> sb.append("+ ").append(line.getContent());
                case DELETE -> sb.append("- ").append(line.getContent());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}