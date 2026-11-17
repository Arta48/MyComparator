package ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.model;

import java.util.Collections;
import java.util.List;

public class DiffResult {
    private final List<DiffLine> lines;
    private final int keepCount;
    private final int insertCount;
    private final int deleteCount;

    public DiffResult(List<DiffLine> lines) {
        this.lines = Collections.unmodifiableList(lines);
        int keeps = 0;
        int inserts = 0;
        int deletes = 0;
        for (DiffLine line : lines) {
            switch (line.getType()) {
                case KEEP -> keeps++;
                case INSERT -> inserts++;
                case DELETE -> deletes++;
            }
        }
        this.keepCount = keeps;
        this.insertCount = inserts;
        this.deleteCount = deletes;
    }

    public List<DiffLine> getLines() {
        return lines;
    }

    public boolean hasChanges() {
        return insertCount > 0 || deleteCount > 0;
    }

    public int getKeepCount() {
        return keepCount;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public int getDeleteCount() {
        return deleteCount;
    }
}