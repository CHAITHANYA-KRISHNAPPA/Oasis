import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Main Application Class
public class OnlineReservationSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm();
        });
    }
}

// User Authentication Class
class UserDatabase {
    private static Map<String, String> users = new HashMap<>();
    
    static {
        // Pre-loaded users for demonstration
        users.put("admin", "admin123");
        users.put("user1", "pass123");
        users.put("arman", "arman123");
    }
    
    public static boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
    
    public static void addUser(String username, String password) {
        users.put(username, password);
    }
}

// Login Form Class
class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public LoginForm() {
        setTitle("Online Reservation System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with gradient-like background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(41, 128, 185), 
                                                          0, getHeight(), new Color(109, 213, 250));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("ONLINE RESERVATION SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(userLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        usernameField = new JTextField(15);
        mainPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(passLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        mainPanel.add(passwordField, gbc);
        
        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("LOGIN");
        loginButton.setPreferredSize(new Dimension(150, 35));
        loginButton.setBackground(new Color(46, 204, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> handleLogin());
        mainPanel.add(loginButton, gbc);
        
        // Enter key listener
        passwordField.addActionListener(e -> handleLogin());
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (UserDatabase.authenticate(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new MainMenu(username);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
}

// Main Menu Class
class MainMenu extends JFrame {
    private String currentUser;
    
    public MainMenu(String username) {
        this.currentUser = username;
        setTitle("Online Reservation System - Main Menu");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(44, 62, 80));
        
        // Header
        JLabel headerLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);
        
        // Menu buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 20, 20));
        buttonPanel.setBackground(new Color(44, 62, 80));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        
        JButton reserveButton = createMenuButton("Make Reservation");
        reserveButton.addActionListener(e -> {
            new ReservationForm(currentUser);
        });
        
        JButton cancelButton = createMenuButton("Cancel Reservation");
        cancelButton.addActionListener(e -> {
            new CancellationForm(currentUser);
        });
        
        JButton logoutButton = createMenuButton("Logout");
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.addActionListener(e -> {
            this.dispose();
            new LoginForm();
        });
        
        buttonPanel.add(reserveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(logoutButton);
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        add(panel);
        setVisible(true);
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        return button;
    }
}

// Reservation Data Class
class Reservation {
    private String pnr;
    private String username;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String dateOfJourney;
    private String from;
    private String destination;
    
    public Reservation(String username, String trainNumber, String trainName, 
                      String classType, String dateOfJourney, String from, String destination) {
        this.pnr = generatePNR();
        this.username = username;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.destination = destination;
    }
    
    private String generatePNR() {
        return "PNR" + System.currentTimeMillis() % 1000000000;
    }
    
    public String getPnr() { return pnr; }
    public String getUsername() { return username; }
    public String getTrainNumber() { return trainNumber; }
    public String getTrainName() { return trainName; }
    public String getClassType() { return classType; }
    public String getDateOfJourney() { return dateOfJourney; }
    public String getFrom() { return from; }
    public String getDestination() { return destination; }
    
    @Override
    public String toString() {
        return String.format("PNR: %s\nTrain: %s (%s)\nClass: %s\nDate: %s\nFrom: %s\nTo: %s",
                           pnr, trainName, trainNumber, classType, dateOfJourney, from, destination);
    }
}

// Reservation Database
class ReservationDatabase {
    private static Map<String, Reservation> reservations = new HashMap<>();
    
    public static void addReservation(Reservation reservation) {
        reservations.put(reservation.getPnr(), reservation);
    }
    
    public static Reservation getReservation(String pnr) {
        return reservations.get(pnr);
    }
    
    public static boolean cancelReservation(String pnr) {
        return reservations.remove(pnr) != null;
    }
    
    public static ArrayList<Reservation> getUserReservations(String username) {
        ArrayList<Reservation> userReservations = new ArrayList<>();
        for (Reservation r : reservations.values()) {
            if (r.getUsername().equals(username)) {
                userReservations.add(r);
            }
        }
        return userReservations;
    }
}

// Reservation Form Class
class ReservationForm extends JFrame {
    private String currentUser;
    private JTextField trainNumberField, trainNameField, dateField, fromField, destinationField;
    private JComboBox<String> classComboBox;
    
    public ReservationForm(String username) {
        this.currentUser = username;
        setTitle("Make Reservation");
        setSize(500, 550);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        JLabel headerLabel = new JLabel("TRAIN RESERVATION FORM");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Train Number
        addFormField(formPanel, gbc, 0, "Train Number:", trainNumberField = new JTextField(20));
        
        // Train Name
        addFormField(formPanel, gbc, 1, "Train Name:", trainNameField = new JTextField(20));
        
        // Class Type
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Class Type:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        classComboBox = new JComboBox<>(new String[]{"Sleeper", "AC 3-Tier", "AC 2-Tier", "AC 1st Class", "General"});
        formPanel.add(classComboBox, gbc);
        
        // Date of Journey
        addFormField(formPanel, gbc, 3, "Date (DD-MM-YYYY):", dateField = new JTextField(20));
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        
        // From
        addFormField(formPanel, gbc, 4, "From (Place):", fromField = new JTextField(20));
        
        // Destination
        addFormField(formPanel, gbc, 5, "Destination:", destinationField = new JTextField(20));
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton insertButton = new JButton("INSERT");
        insertButton.setBackground(new Color(46, 204, 113));
        insertButton.setForeground(Color.WHITE);
        insertButton.setFont(new Font("Arial", Font.BOLD, 14));
        insertButton.setPreferredSize(new Dimension(120, 35));
        insertButton.setFocusPainted(false);
        insertButton.addActionListener(e -> handleReservation());
        
        JButton backButton = new JButton("BACK");
        backButton.setBackground(new Color(149, 165, 166));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(120, 35));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> this.dispose());
        
        buttonPanel.add(insertButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }
    
    private void handleReservation() {
        // Validation
        if (trainNumberField.getText().isEmpty() || trainNameField.getText().isEmpty() ||
            dateField.getText().isEmpty() || fromField.getText().isEmpty() || 
            destinationField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create reservation
        Reservation reservation = new Reservation(
            currentUser,
            trainNumberField.getText(),
            trainNameField.getText(),
            (String) classComboBox.getSelectedItem(),
            dateField.getText(),
            fromField.getText(),
            destinationField.getText()
        );
        
        ReservationDatabase.addReservation(reservation);
        
        JOptionPane.showMessageDialog(this, 
            "Reservation Successful!\n\n" + reservation.toString(), 
            "Success", JOptionPane.INFORMATION_MESSAGE);
        
        this.dispose();
    }
}

// Cancellation Form Class
class CancellationForm extends JFrame {
    private String currentUser;
    private JTextField pnrField;
    private JTextArea displayArea;
    
    public CancellationForm(String username) {
        this.currentUser = username;
        setTitle("Cancel Reservation");
        setSize(500, 450);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(231, 76, 60));
        JLabel headerLabel = new JLabel("CANCELLATION FORM");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        inputPanel.add(new JLabel("Enter PNR Number:"));
        pnrField = new JTextField(15);
        inputPanel.add(pnrField);
        
        JButton searchButton = new JButton("SEARCH");
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> searchReservation());
        inputPanel.add(searchButton);
        
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        
        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Reservation Details"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton cancelButton = new JButton("CONFIRM CANCELLATION");
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 13));
        cancelButton.setPreferredSize(new Dimension(200, 35));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> confirmCancellation());
        
        JButton backButton = new JButton("BACK");
        backButton.setBackground(new Color(149, 165, 166));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> this.dispose());
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void searchReservation() {
        String pnr = pnrField.getText().trim();
        
        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter PNR number!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Reservation reservation = ReservationDatabase.getReservation(pnr);
        
        if (reservation == null) {
            displayArea.setText("No reservation found with PNR: " + pnr);
            JOptionPane.showMessageDialog(this, "Reservation not found!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            displayArea.setText(reservation.toString());
        }
    }
    
    private void confirmCancellation() {
        String pnr = pnrField.getText().trim();
        
        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter PNR number!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Reservation reservation = ReservationDatabase.getReservation(pnr);
        
        if (reservation == null) {
            JOptionPane.showMessageDialog(this, "Reservation not found!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel this reservation?", 
            "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (ReservationDatabase.cancelReservation(pnr)) {
                JOptionPane.showMessageDialog(this, 
                    "Reservation cancelled successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                displayArea.setText("");
                pnrField.setText("");
            }
        }
    }
}