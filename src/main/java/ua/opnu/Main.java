package ua.opnu;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

class Student {
    private String name;
    private String group;
    private int[] marks;

    // Конструктор
    public Student(String name, String group, int[] marks) {
        this.name = name;
        this.group = group;
        this.marks = marks;
    }

    // Гетери
    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public int[] getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', group='" + group + "', marks=" + Arrays.toString(marks) + "}";
    }
}

public class Main {
    public static void main(String[] args) {
        // Завдання 1: Предикат для перевірки простого числа
        System.out.println("=== Завдання 1 ===");

        Predicate<Integer> isPrime = n -> {
            if (n <= 1) {
                return false;
            }
            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) {
                    return false;
                }
            }
            return true;
        };

        // Тестування предиката
        System.out.println("2 є простим: " + isPrime.test(2));
        System.out.println("3 є простим: " + isPrime.test(3));
        System.out.println("4 є простим: " + isPrime.test(4));
        System.out.println("17 є простим: " + isPrime.test(17));
        System.out.println("25 є простим: " + isPrime.test(25));
        System.out.println("29 є простим: " + isPrime.test(29));
        System.out.println("1 є простим: " + isPrime.test(1));
        System.out.println("0 є простим: " + isPrime.test(0));
        System.out.println("-5 є простим: " + isPrime.test(-5));

        System.out.println("\n=== Завдання 2 ===");

        Student[] students = {
                new Student("Іван Іваненко", "Група 1", new int[]{85, 90, 78, 92, 45}),  // має заборгованість (45 < 60)
                new Student("Марія Петренко", "Група 2", new int[]{75, 88, 65, 70, 80}),  // всі оцінки >= 60
                new Student("Петро Сидоренко", "Група 1", new int[]{55, 62, 58, 70, 68}), // має заборгованості (55, 58 < 60)
                new Student("Олена Коваленко", "Група 3", new int[]{90, 95, 88, 92, 87}), // всі оцінки >= 60
                new Student("Андрій Шевченко", "Група 2", new int[]{59, 65, 70, 75, 80})  // має заборгованість (59 < 60)
        };

        System.out.println("Всі студенти:");
        for (Student student : students) {
            System.out.println(student);
        }

        // Предикат для фільтрації студентів без заборгованостей
        Predicate<Student> noDebts = student -> {
            for (int mark : student.getMarks()) {
                if (mark < 60) {
                    return false; // знайдено заборгованість
                }
            }
            return true; // заборгованостей немає
        };

        // Фільтруємо студентів
        Student[] studentsWithoutDebts = filterStudents(students, noDebts);

        System.out.println("\nСтуденти без заборгованостей:");
        for (Student student : studentsWithoutDebts) {
            System.out.println(student);
        }

        // Додатково: студенти з заборгованостями
        Predicate<Student> hasDebts = student -> {
            for (int mark : student.getMarks()) {
                if (mark < 60) {
                    return true; // знайдено заборгованість
                }
            }
            return false; // заборгованостей немає
        };

        Student[] studentsWithDebts = filterStudents(students, hasDebts);
        System.out.println("\nСтуденти з заборгованостями:");
        for (Student student : studentsWithDebts) {
            System.out.println(student);
        }

        System.out.println("=== Завдання 3: Два предикати ===");

        Predicate<Student> noDebts2 = student -> {
            for (int mark : student.getMarks()) {
                if (mark < 60) return false;
            }
            return true;
        };
        Predicate<Student> fromGroup2 = student -> student.getGroup().equals("Група 2");

        List<Student> filteredStudents = new ArrayList<>();
        for (Student student : students) {
            if (noDebts2.and(fromGroup2).test(student)) filteredStudents.add(student);
        }
        System.out.println("Студенти без заборгованостей з Групи 2: " + filteredStudents + "\n");

        System.out.println("=== Завдання 4: Consumer для студентів ===");
        Consumer<Student> printStudent = s -> {
            String[] names = s.getName().split(" ");
            System.out.println(names[1] + " + " + names[0]);
        };
        System.out.println("Список студентів у форматі ПРІЗВИЩЕ + ІМ'Я:");
        Arrays.stream(students).forEach(printStudent);
        System.out.println();

        System.out.println("=== Завдання 5: Predicate + Consumer ===");
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Consumer<Integer> printSquare = n -> System.out.println("Квадрат парного числа " + n + " = " + (n * n));

        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Arrays.stream(numbers)
                .boxed()
                .filter(isEven)
                .forEach(printSquare);
        System.out.println();

        System.out.println("=== Завдання 6: Function 2^n ===");
        Function<Integer, Integer> powerOfTwo = n -> (int) Math.pow(2, n);

        int[] base = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> powers = Arrays.stream(base)
                .boxed()
                .map(powerOfTwo)
                .toList();

        System.out.println("2^n для кожного елемента: " + powers);
        System.out.println();

        System.out.println("=== Завдання 7: Function stringify() ===");
        Function<Integer, String> toWord = n -> switch (n) {
            case 0 -> "нуль";
            case 1 -> "один";
            case 2 -> "два";
            case 3 -> "три";
            case 4 -> "чотири";
            case 5 -> "п'ять";
            case 6 -> "шість";
            case 7 -> "сім";
            case 8 -> "вісім";
            case 9 -> "дев'ять";
            default -> "невідомо";
        };

        int[] digits = {0,1,2,3,4,5,6,7,8,9};
        List<String> words = Arrays.stream(digits)
                .boxed()
                .map(toWord)
                .toList();

        System.out.println("Перетворення чисел у слова:");
        System.out.println(words);
        System.out.println();
    }

    // Метод фільтрації масиву студентів
    public static Student[] filterStudents(Student[] students, Predicate<Student> filter) {
        Student[] result = new Student[students.length];
        int counter = 0;

        for (Student student : students) {
            if (filter.test(student)) {
                result[counter] = student;
                counter++;
            }
        }

        return Arrays.copyOfRange(result, 0, counter);


    }



}
