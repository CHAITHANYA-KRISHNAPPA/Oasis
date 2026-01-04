import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private int randomNumber;
    private int attempts;
    private int maxAttempts = 10;
    private int score = 100;
    private int round = 1;
    private final int MIN_RANGE = 1;
    private final int MAX_RANGE = 100;
    
    private JLabel titleLabel;
    private JLabel infoLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JLabel roundLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JButton newGameButton;
    private JTextArea feedbackArea;
    
    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Initialize game
        generateRandomNumber();
        
        // Create UI components
        createUIComponents();
        
        setVisible(true);
    }
    
    private void createUIComponents() {
        // Top panel with title and info
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(45, 45, 45));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        titleLabel = new JLabel("NUMBER GUESSING GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoLabel = new JLabel("Guess a number between " + MIN_RANGE + " and " + MAX_RANGE);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        topPanel.add(titleLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(infoLabel);
        
        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        statsPanel.setBackground(new Color(45, 45, 45));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        roundLabel = new JLabel("Round: " + round);
        roundLabel.setForeground(Color.WHITE);
        roundLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        attemptsLabel = new JLabel("Attempts: " + attempts + "/" + maxAttempts);
        attemptsLabel.setForeground(Color.WHITE);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        statsPanel.add(roundLabel);
        statsPanel.add(attemptsLabel);
        statsPanel.add(scoreLabel);
        
        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(new Color(60, 60, 60));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel promptLabel = new JLabel("Enter your guess: ");
        promptLabel.setForeground(Color.WHITE);
        
        guessField = new JTextField(10);
        guessField.setFont(new Font("Arial", Font.PLAIN, 16));
        
        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        guessButton.setBackground(new Color(70, 130, 180));
        guessButton.setForeground(Color.WHITE);
        guessButton.addActionListener(new GuessButtonListener());
        
        newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 14));
        newGameButton.setBackground(new Color(34, 139, 34));
        newGameButton.setForeground(Color.WHITE);
        newGameButton.addActionListener(new NewGameButtonListener());
        
        inputPanel.add(promptLabel);
        inputPanel.add(guessField);
        inputPanel.add(guessButton);
        inputPanel.add(newGameButton);
        
        // Feedback area
        feedbackArea = new JTextArea(15, 40);
        feedbackArea.setEditable(false);
        feedbackArea.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackArea.setBackground(new Color(30, 30, 30));
        feedbackArea.setForeground(Color.WHITE);
        feedbackArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        feedbackArea.setText("Welcome to the Number Guessing Game!\nStart guessing...\n\n");
        
        JScrollPane scrollPane = new JScrollPane(feedbackArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        // Add all panels to frame
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(statsPanel, BorderLayout.CENTER);
        northPanel.add(inputPanel, BorderLayout.SOUTH);
        
        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add Enter key listener
        guessField.addActionListener(e -> guessButton.doClick());
    }
    
    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
        attempts = 0;
    }
    
    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int userGuess = Integer.parseInt(guessField.getText());
                attempts++;
                
                if (userGuess < MIN_RANGE || userGuess > MAX_RANGE) {
                    feedbackArea.append("Please enter a number between " + MIN_RANGE + " and " + MAX_RANGE + "!\n\n");
                    guessField.setText("");
                    return;
                }
                
                updateAttemptsLabel();
                
                if (userGuess == randomNumber) {
                    int pointsEarned = calculatePoints();
                    score += pointsEarned;
                    feedbackArea.append("🎉 CONGRATULATIONS! You guessed it!\n");
                    feedbackArea.append("The number was: " + randomNumber + "\n");
                    feedbackArea.append("Attempts used: " + attempts + "\n");
                    feedbackArea.append("Points earned: +" + pointsEarned + "\n");
                    feedbackArea.append("Total Score: " + score + "\n\n");
                    feedbackArea.append("Click 'New Game' to play another round!\n\n");
                    
                    guessButton.setEnabled(false);
                    guessField.setEnabled(false);
                } else if (userGuess < randomNumber) {
                    feedbackArea.append("Attempt " + attempts + ": " + userGuess + " is too LOW! Try higher.\n");
                    score = Math.max(0, score - 5);
                } else {
                    feedbackArea.append("Attempt " + attempts + ": " + userGuess + " is too HIGH! Try lower.\n");
                    score = Math.max(0, score - 5);
                }
                
                updateScoreLabel();
                
                if (attempts >= maxAttempts && userGuess != randomNumber) {
                    feedbackArea.append("\n❌ Game Over! You've used all " + maxAttempts + " attempts.\n");
                    feedbackArea.append("The correct number was: " + randomNumber + "\n");
                    feedbackArea.append("Final Score: " + score + "\n\n");
                    feedbackArea.append("Click 'New Game' to try again!\n\n");
                    
                    guessButton.setEnabled(false);
                    guessField.setEnabled(false);
                }
                
                guessField.setText("");
                
            } catch (NumberFormatException ex) {
                feedbackArea.append("Invalid input! Please enter a valid number.\n\n");
                guessField.setText("");
            }
        }
    }
    
    private class NewGameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            round++;
            generateRandomNumber();
            updateRoundLabel();
            updateAttemptsLabel();
            guessButton.setEnabled(true);
            guessField.setEnabled(true);
            guessField.setText("");
            feedbackArea.append("========================================\n");
            feedbackArea.append("NEW GAME STARTED - Round " + round + "\n");
            feedbackArea.append("========================================\n\n");
        }
    }
    
    private int calculatePoints() {
        if (attempts <= 3) {
            return 50;
        } else if (attempts <= 6) {
            return 30;
        } else {
            return 10;
        }
    }
    
    private void updateAttemptsLabel() {
        attemptsLabel.setText("Attempts: " + attempts + "/" + maxAttempts);
    }
    
    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }
    
    private void updateRoundLabel() {
        roundLabel.setText("Round: " + round);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NumberGuessingGame());
    }
}