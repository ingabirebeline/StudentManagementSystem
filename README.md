# Student Management System (Java Stream API)

A simple menu-driven console application written in Java. It manages student records and practises **Java Collections**, **Object-Oriented Programming (OOP)** and the **Stream API**.

## Project files

| File | Purpose |
|---|---|
| `Student.java` | The `Student` class: id, name, age, courses (`Map<String, Double>`), skills (`List<String>`), constructor, getters, setters, `getAverage()` and `toString()` |
| `Main.java` | The menu, input checking, and one method for each menu option |

## Features

The program shows a menu and keeps running until the user chooses `0`.

**Task 1: Registration and management**
1. Register a new student (duplicate IDs are not allowed)
2. Display all students
3. Search a student by ID

**Task 2: Search, filter and sort**
4. Filter passing students (average 65 or above)
5. Filter failing students (average below 65)
6. Search students by name
7. Sort students by name
8. Sort students by average marks (highest first)

**Task 3: Courses and skills**
9. Display all courses
10. Display unique courses
11. Search students by course
12. Display unique skills
13. Group students by course

**Task 4: Performance and statistics**
14. View statistics (average, highest, lowest, sum, number of courses, number of students who passed)
15. View the best performing student
16. View the top 3 students

**Task 5: Bonus marks and final results**
17. Apply bonus marks (average above 50 gets +10 in each course, maximum 100, only once per student)
18. View the final student ranking

## Stream API methods used

`stream()`, `forEach()`, `filter()`, `findFirst()`, `anyMatch()`, `map()`, `sorted()`, `collect()`, `average()`, `flatMap()`, `distinct()`, `mapToDouble()`, `summaryStatistics()`, `max()`, `count()`, `limit()`, `reduce()`

## Input checking

- Menu choices, IDs, ages and numbers must be whole numbers. If the user types a word, the program asks again.
- Marks must be between 0 and 100.
- Names and course names cannot be empty.
- A student ID cannot be registered twice.
- If no students are registered, the program shows a helpful message.

## How to run

You need the Java JDK installed.

1. Download or clone this repository.
2. Open a terminal in the project folder.
3. Compile the files:

   ```
   javac Student.java Main.java
   ```

4. Run the program:

   ```
   java Main
   ```

5. Type the number of an option and press Enter. Start with option `1` to register a student.

## Author

Beline Ingabire