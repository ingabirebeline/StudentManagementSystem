import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    static List<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static List<Integer> bonusIds = new ArrayList<>(); // students who already got the bonus

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = readInt("");

            // Options 2 to 18 need at least one student
            if (choice >= 2 && choice <= 18 && students.isEmpty()) {
                System.out.println("No students registered yet. Please register a student first (option 1).");
                continue;
            }

            switch (choice) {
                case 1: registerStudent(); break;
                case 2: displayAllStudents(); break;
                case 3: searchStudentById(); break;
                case 4: filterPassing(); break;
                case 5: filterFailing(); break;
                case 6: searchByName(); break;
                case 7: sortByName(); break;
                case 8: sortByAverage(); break;
                case 9: displayAllCourses(); break;
                case 10: displayUniqueCourses(); break;
                case 11: searchByCourse(); break;
                case 12: displayUniqueSkills(); break;
                case 13: groupByCourse(); break;
                case 14: viewStatistics(); break;
                case 15: viewBestStudent(); break;
                case 16: viewTopThree(); break;
                case 17: applyBonus(); break;
                case 18: viewFinalRanking(); break;
                case 0: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid option. Please enter a number from 0 to 18.");
            }
        } while (choice != 0);
    }

    public static void showMenu() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("STUDENT MANAGEMENT SYSTEM");
        System.out.println("==========================================");
        System.out.println("1. Register New Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Filter Passing Students");
        System.out.println("5. Filter Failing Students");
        System.out.println("6. Search Students by Name");
        System.out.println("7. Sort Students by Name");
        System.out.println("8. Sort Students by Average Marks");
        System.out.println("9. Display All Courses");
        System.out.println("10. Display Unique Courses");
        System.out.println("11. Search Students by Course");
        System.out.println("12. Display Unique Skills");
        System.out.println("13. Group Students by Course");
        System.out.println("14. View Student Performance Statistics");
        System.out.println("15. View Best Performing Student");
        System.out.println("16. View Top 3 Students");
        System.out.println("17. Apply Bonus Marks");
        System.out.println("18. View Final Student Ranking");
        System.out.println();
        System.out.println("0. Exit");
        System.out.println();
        System.out.println("==========================================");
        System.out.print("Enter your choice: ");
    }

    // ---------- INPUT HELPERS (they check the user's input) ----------

    // Reads a whole number. Asks again if the user types a word.
    public static int readInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            scanner.nextLine(); // throw away the wrong input
            System.out.print("Invalid input. Please enter a whole number: ");
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    // Reads a whole number between min and max
    public static int readIntInRange(String message, int min, int max) {
        int number = readInt(message);
        while (number < min || number > max) {
            number = readInt("Please enter a number from " + min + " to " + max + ": ");
        }
        return number;
    }

    // Reads marks between 0 and 100
    public static double readMark(String message) {
        System.out.print(message);
        while (true) {
            if (scanner.hasNextDouble()) {
                double mark = scanner.nextDouble();
                scanner.nextLine();
                if (mark >= 0 && mark <= 100) {
                    return mark;
                }
                System.out.print("Marks must be between 0 and 100. Try again: ");
            } else {
                scanner.nextLine();
                System.out.print("Invalid input. Enter marks between 0 and 100: ");
            }
        }
    }

    // Reads text that is not empty
    public static String readText(String message) {
        System.out.print(message);
        String text = scanner.nextLine().trim();
        while (text.isEmpty()) {
            System.out.print("This cannot be empty. Try again: ");
            text = scanner.nextLine().trim();
        }
        return text;
    }

    // Prints a list of students, or a message if the list is empty
    public static void printStudents(List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No students found.");
        } else {
            list.stream().forEach(s -> System.out.println(s));
        }
    }

    // ---------- TASK 1 ----------

    public static void registerStudent() {
        int id = readIntInRange("Enter student ID (a positive number): ", 1, 1000000);

        boolean exists = students.stream().anyMatch(s -> s.getId() == id);
        if (exists) {
            System.out.println("This ID already exists. Registration cancelled.");
            return;
        }

        String name = readText("Enter student name: ");
        int age = readIntInRange("Enter age (1 - 120): ", 1, 120);
        int numberOfCourses = readIntInRange("Enter number of courses (1 - 20): ", 1, 20);

        Map<String, Double> courses = new HashMap<>();
        for (int i = 1; i <= numberOfCourses; i++) {
            String courseName = readText("Course " + i + " name: ");
            while (courses.containsKey(courseName)) {
                courseName = readText("You already added this course. Enter another name: ");
            }
            double mark = readMark("Marks for " + courseName + " (0 - 100): ");
            courses.put(courseName, mark);
        }

        int numberOfSkills = readIntInRange("Enter number of skills (0 - 20): ", 0, 20);

        List<String> skills = new ArrayList<>();
        for (int i = 1; i <= numberOfSkills; i++) {
            skills.add(readText("Skill " + i + ": "));
        }

        students.add(new Student(id, name, age, courses, skills));
        System.out.println("Student registered successfully!");
    }

    public static void displayAllStudents() {
        students.stream().forEach(s -> System.out.println(s));
    }

    public static void searchStudentById() {
        int id = readInt("Enter student ID: ");

        Student found = students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);

        if (found == null) {
            System.out.println("No student found with this ID.");
        } else {
            System.out.println(found);
        }
    }

    // ---------- TASK 2 ----------

    public static void filterPassing() {
        List<Student> result = students.stream()
                .filter(s -> s.getAverage() >= 65)
                .collect(Collectors.toList());
        printStudents(result);
    }

    public static void filterFailing() {
        List<Student> result = students.stream()
                .filter(s -> s.getAverage() < 65)
                .collect(Collectors.toList());
        printStudents(result);
    }

    public static void searchByName() {
        String name = readText("Enter name to search: ");

        List<Student> result = students.stream()
                .filter(s -> s.getStudentName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        printStudents(result);
    }

    public static void sortByName() {
        students.stream()
                .sorted((a, b) -> a.getStudentName().compareToIgnoreCase(b.getStudentName()))
                .forEach(s -> System.out.println(s));
    }

    public static void sortByAverage() {
        students.stream()
                .sorted((a, b) -> Double.compare(b.getAverage(), a.getAverage()))
                .forEach(s -> System.out.println(s));
    }

    // ---------- TASK 3 ----------

    public static void displayAllCourses() {
        students.stream()
                .flatMap(s -> s.getCourses().keySet().stream())
                .forEach(c -> System.out.println(c));
    }

    public static void displayUniqueCourses() {
        students.stream()
                .flatMap(s -> s.getCourses().keySet().stream())
                .distinct()
                .sorted()
                .forEach(c -> System.out.println(c));
    }

    public static void searchByCourse() {
        String course = readText("Enter course name: ");

        List<Student> result = students.stream()
                .filter(s -> s.getCourses().keySet().stream()
                        .anyMatch(c -> c.equalsIgnoreCase(course)))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No student takes this course.");
        } else {
            result.forEach(s -> System.out.println(s));
        }
    }

    public static void displayUniqueSkills() {
        List<String> skills = students.stream()
                .flatMap(s -> s.getSkills().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        if (skills.isEmpty()) {
            System.out.println("No skills registered yet.");
        } else {
            skills.forEach(k -> System.out.println(k));
        }
    }

    public static void groupByCourse() {
        List<String> courseNames = students.stream()
                .flatMap(s -> s.getCourses().keySet().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        courseNames.forEach(course -> {
            System.out.println("Course: " + course);
            students.stream()
                    .filter(s -> s.getCourses().containsKey(course))
                    .forEach(s -> System.out.println("   - " + s.getStudentName()));
        });
    }

    // ---------- TASK 4 ----------

    public static void viewStatistics() {
        students.stream().forEach(s -> {
            DoubleSummaryStatistics stats = s.getCourses().values().stream()
                    .mapToDouble(m -> m)
                    .summaryStatistics();

            System.out.println("Student: " + s.getStudentName());
            System.out.println("   Average: " + String.format("%.2f", stats.getAverage()));
            System.out.println("   Highest: " + stats.getMax());
            System.out.println("   Lowest: " + stats.getMin());
            System.out.println("   Sum: " + stats.getSum());
            System.out.println("   Courses: " + stats.getCount());
        });

        long passed = students.stream()
                .filter(s -> s.getAverage() >= 65)
                .count();
        System.out.println("Number of students who passed: " + passed);
    }

    public static void viewBestStudent() {
        Student best = students.stream()
                .max((a, b) -> Double.compare(a.getAverage(), b.getAverage()))
                .orElse(null);

        if (best == null) {
            System.out.println("No students yet.");
        } else {
            System.out.println("Best student: " + best);
        }
    }

    public static void viewTopThree() {
        students.stream()
                .sorted((a, b) -> Double.compare(b.getAverage(), a.getAverage()))
                .limit(3)
                .forEach(s -> System.out.println(s));
    }

    // ---------- TASK 5 ----------

    public static void applyBonus() {
        // save each student's original average first
        Map<Integer, Double> originalAverages = new HashMap<>();
        students.forEach(s -> originalAverages.put(s.getId(), s.getAverage()));

        // only students above 50 who did not get the bonus before
        students.stream()
                .filter(s -> s.getAverage() > 50 && !bonusIds.contains(s.getId()))
                .forEach(s -> {
                    for (String course : s.getCourses().keySet()) {
                        double newMark = s.getCourses().get(course) + 10;
                        if (newMark > 100) {
                            newMark = 100;
                        }
                        s.getCourses().put(course, newMark);
                    }
                    bonusIds.add(s.getId());
                });

        students.stream().forEach(s -> System.out.println(s.getStudentName()
                + " | Original average: " + String.format("%.2f", originalAverages.get(s.getId()))
                + " | Final average: " + String.format("%.2f", s.getAverage())));
    }

    public static void viewFinalRanking() {
        students.stream()
                .sorted((a, b) -> Double.compare(b.getAverage(), a.getAverage()))
                .forEach(s -> System.out.println(s.getStudentName()
                        + " - " + String.format("%.2f", s.getAverage())));

        // reduce: start at 0 and add every student's average one by one
        double total = students.stream()
                .map(s -> s.getAverage())
                .reduce(0.0, (a, b) -> a + b);

        System.out.println("Total of all averages: " + String.format("%.2f", total));
    }
}