package ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_demo;

import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.formatter.DiffFormatter;
import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.formatter.SideBySideDiffFormatter;
import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.formatter.UnifiedDiffFormatter;
import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.model.DiffResult;
import ru.vsu.cs.oop26.g42.artamonov_n_g.my_comparator_lib.service.TextComparator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length >= 2) {
            runCli(args);
        } else {
            runInteractive();
        }
    }

    private static void runCli(String[] args) {
        List<String> filePaths = new ArrayList<>();
        boolean ignoreCase = false;
        boolean ignoreWhitespace = false;
        boolean sideBySide = false;

        for (String arg : args) {
            if (arg.equalsIgnoreCase("-i")) {
                ignoreCase = true;
            } else if (arg.equalsIgnoreCase("-w")) {
                ignoreWhitespace = true;
            } else if (arg.equalsIgnoreCase("--side")) {
                sideBySide = true;
            } else if (!arg.startsWith("-")) {
                filePaths.add(arg);
            }
        }

        if (filePaths.size() < 2) {
            System.err.println("Использование: java -jar target/text-comparator-1.0.0.jar <файл1> <файл2> [-i] [-w] [--side]");
            return;
        }

        execute(filePaths.get(0), filePaths.get(1), ignoreCase, ignoreWhitespace, sideBySide);
    }

    private static void runInteractive() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Путь к первому файлу: ");
        String path1 = scanner.nextLine().trim();

        System.out.print("Путь ко второму файлу: ");
        String path2 = scanner.nextLine().trim();

        System.out.print("Игнорировать регистр? (y/n): ");
        boolean ignoreCase = scanner.nextLine().trim().equalsIgnoreCase("y");

        System.out.print("Игнорировать начальные и конечные пробелы? (y/n): ");
        boolean trim = scanner.nextLine().trim().equalsIgnoreCase("y");

        System.out.print("Боковое сравнение (side-by-side)? (y/n): ");
        boolean side = scanner.nextLine().trim().equalsIgnoreCase("y");

        execute(path1, path2, ignoreCase, trim, side);
    }

    private static void execute(String p1, String p2, boolean ignoreCase, boolean trim, boolean sideBySide) {
        Path path1 = Path.of(p1);
        Path path2 = Path.of(p2);

        if (!Files.exists(path1)) {
            System.err.println("Файл не найден: " + p1);
            return;
        }
        if (!Files.exists(path2)) {
            System.err.println("Файл не найден: " + p2);
            return;
        }

        try {
            TextComparator comparator = new TextComparator();
            comparator.setIgnoreCase(ignoreCase);
            comparator.setIgnoreWhitespace(trim);

            DiffResult result = comparator.compare(path1, path2);
            DiffFormatter formatter = sideBySide ? new SideBySideDiffFormatter(40) : new UnifiedDiffFormatter();

            System.out.print(formatter.format(result));
            System.out.println("----------------------------------------");
            System.out.println("Статус: " + (result.hasChanges() ? "Файлы отличаются" : "Файлы идентичны"));
            System.out.println("Совпадает: " + result.getKeepCount() + " | Добавлено: " + result.getInsertCount() + " | Удалено: " + result.getDeleteCount());
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файлов: " + e.getMessage());
        }
    }
}