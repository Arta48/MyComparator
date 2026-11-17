package ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.algorithm;

import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.model.DiffResult;

import java.util.List;
import java.util.function.BiPredicate;

public interface DiffAlgorithm {
    DiffResult compare(List<String> original, List<String> revised, BiPredicate<String, String> equality);
}