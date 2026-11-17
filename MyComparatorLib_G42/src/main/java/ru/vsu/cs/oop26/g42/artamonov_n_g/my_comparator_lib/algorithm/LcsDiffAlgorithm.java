package ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.algorithm;

import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.model.DiffLine;
import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.model.DiffResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

public class LcsDiffAlgorithm implements DiffAlgorithm {
    @Override
    public DiffResult compare(List<String> original, List<String> revised, BiPredicate<String, String> equality) {
        int m = original.size();
        int n = revised.size();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (equality.test(original.get(i), revised.get(j))) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }

        List<DiffLine> lines = new ArrayList<>();
        int i = m;
        int j = n;

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && equality.test(original.get(i - 1), revised.get(j - 1))) {
                lines.add(new DiffLine(DiffLine.Type.KEEP, original.get(i - 1), i, j));
                i--;
                j--;
            } else if (j > 0 && (i == 0 || dp[i][j - 1] >= dp[i - 1][j])) {
                lines.add(new DiffLine(DiffLine.Type.INSERT, revised.get(j - 1), -1, j));
                j--;
            } else if (i > 0 && (j == 0 || dp[i][j - 1] < dp[i - 1][j])) {
                lines.add(new DiffLine(DiffLine.Type.DELETE, original.get(i - 1), i, -1));
                i--;
            }
        }

        Collections.reverse(lines);
        return new DiffResult(lines);
    }
}