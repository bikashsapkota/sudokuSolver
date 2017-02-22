import java.awt.*;        // Uses AWT's Layout Managers
import java.awt.event.*;  // Uses AWT's Event Handlers
import javax.swing.*;     // Uses Swing's Container/Components

/**
 * The Sudoku game.
 * To solve the number puzzle, each row, each column, and each of the
 * nine 3×3 sub-grids shall contain all of the digits from 1 to 9
 */
class Sudoku extends JFrame {
    // Name-constants for the game properties
    public static final int GRID_SIZE = 9;    // Size of the board
    public static final int SUBGRID_SIZE = 3; // Size of the sub-grid

    // Name-constants for UI control (sizes, colors and fonts)
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int CANVAS_WIDTH  = CELL_SIZE * GRID_SIZE;
    public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE;
    // Board width/height in pixels
    public static final Color OPEN_CELL_BGCOLOR = Color.YELLOW;
    public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);  // RGB
    public static final Color OPEN_CELL_TEXT_NO = Color.RED;
    public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
    public static final Color CLOSED_CELL_TEXT = Color.BLACK;
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

    // The game board composes of 9x9 JTextFields,
    // each containing String "1" to "9", or empty String
    private JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];


    private boolean[][] masks =
            {{true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true},
                    {true, true, true, true, true, true, true, true, true}};

    /**
     * Constructor to setup the game and the UI Components
     */
    public Sudoku() {
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));  // 9x9 GridLayout

        // Allocate a common listener as the ActionEvent listener for all the
        //  JTextFields
        // ... [TODO 3] (Later) ....

        // Construct 9x9 JTextFields and add to the content-pane
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                tfCells[row][col] = new JTextField(); // Allocate element of array
                tfCells[row][col].setSize(5,5);
                cp.add(tfCells[row][col]);            // ContentPane adds JTextField
                tfCells[row][col].setText("");     // set to empty string
                tfCells[row][col].setEditable(true);
                tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
                tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
                tfCells[row][col].setFont(FONT_NUMBERS);
            }
        }

        // Set the size of the content-pane and pack all the components
        //  under this container.
        cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
        setTitle("Sudoku");
        setVisible(true);
    }

    /** The entry main() entry method */
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();
        // [TODO 1] (Now)
        // Check Swing program template on how to run the constructor



    }

    // Define the Listener Inner Class
    // ... [TODO 2] (Later) ...
}