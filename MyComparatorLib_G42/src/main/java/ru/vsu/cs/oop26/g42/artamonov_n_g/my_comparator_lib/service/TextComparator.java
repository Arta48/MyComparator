package ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.service;

import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.algorithm.DiffAlgorithm;
import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.algorithm.LcsDiffAlgorithm;
import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.model.DiffResult;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.BiPredicate;

public class TextComparator {
    private final DiffAlgorithm algorithm;
    private boolean ignoreCase;
    private boolean ignoreWhitespace;
    private Charset charset = StandardCharsets.UTF_8;

    public TextComparator() {
        this(new LcsDiffAlgorithm());
    }

    public TextComparator(DiffAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public void setIgnoreWhitespace(boolean ignoreWhitespace) {
        this.ignoreWhitespace = ignoreWhitespace;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public DiffResult compare(Path firstPath, Path secondPath) throws IOException {
        List<String> firstLines = Files.readAllLines(firstPath, charset);
        List<String> secondLines = Files.readAllLines(secondPath, charset);
        return compare(firstLines, secondLines);
    }

    public DiffResult compare(List<String> original, List<String> revised) {
        BiPredicate<String, String> equality = (s1, s2) -> {
            String v1 = ignoreWhitespace ? s1.trim() : s1;
            String v2 = ignoreWhitespace ? s2.trim() : s2;
            return ignoreCase ? v1.equalsIgnoreCase(v2) : v1.equals(v2);
        };
        return algorithm.compare(original, revised, equality);
    }
}