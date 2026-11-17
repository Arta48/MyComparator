package ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.model;

public class DiffLine {
    public enum Type {
        KEEP,
        INSERT,
        DELETE
    }

    private final Type type;
    private final String content;
    private final int oldLineNum;
    private final int newLineNum;

    public DiffLine(Type type, String content, int oldLineNum, int newLineNum) {
        this.type = type;
        this.content = content;
        this.oldLineNum = oldLineNum;
        this.newLineNum = newLineNum;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getOldLineNum() {
        return oldLineNum;
    }

    public int getNewLineNum() {
        return newLineNum;
    }
}