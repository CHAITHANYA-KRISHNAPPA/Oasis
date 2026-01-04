import java.util.*;

class OnlineExam {

    static String username = "student";
    static String password = "1234";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        if (!login()) {
            System.out.println("Login failed!");
            return;
        }

        int choice;
        do {
            System.out.println("\n--- ONLINE EXAM MENU ---");
            System.out.println("1. Update Profile & Password");
            System.out.println("2. Start Exam");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    updateProfile();
                    break;
                case 2:
                    startExam();
                    break;
                case 3:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 3);
    }

    // LOGIN
    static boolean login() {
        System.out.print("Enter Username: ");
        String u = sc.next();
        System.out.print("Enter Password: ");
        String p = sc.next();

        return u.equals(username) && p.equals(password);
    }

    // UPDATE PROFILE
    static void updateProfile() {
        System.out.print("Enter new username: ");
        username = sc.next();
        System.out.print("Enter new password: ");
        password = sc.next();
        System.out.println("Profile updated successfully!");
    }

    // START EXAM
    static void startExam() {
        System.out.println("\nExam started (Time: 10 seconds)");
        int score = 0;

        long startTime = System.currentTimeMillis();
        long endTime = startTime + 10000; // 10 seconds

        String[] questions = {
            "1. Java is ?\n1) OOP\n2) POP\n3) Machine\n4) None",
            "2. int size is?\n1) 2\n2) 4\n3) 8\n4) 16"
        };

        int[] answers = {1, 2};

        for (int i = 0; i < questions.length; i++) {
            if (System.currentTimeMillis() > endTime) {
                System.out.println("\nTime up! Auto submitting...");
                break;
            }

            System.out.println(questions[i]);
            System.out.print("Enter answer: ");
            int ans = sc.nextInt();

            if (ans == answers[i]) {
                score++;
            }
        }

        System.out.println("Exam submitted!");
        System.out.println("Score: " + score + "/" + questions.length);
    }

    // LOGOUT
    static void logout() {
        System.out.println("Session closed. Logged out successfully.");
    }
}
