import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TicTacToe extends JPanel implements ActionListener {

    boolean playerX;
    boolean gameDone = false;
    int winner = -1;
    int player1wins = 0, player2wins = 0;
    int[][] board = new int[3][3];

    int lineWidth = 5;
    int lineLength = 270;
    int x = 15, y = 100;
    int offset = 95;
    int a = 0;
    int b = 5;
    int selX = 0;
    int selY = 0;


    JButton jButton;

    public TicTacToe() {
        Dimension size = new Dimension(420, 300);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

jButton = new JButton("Play Again?");
jButton.addActionListener(this);
jButton.setVisible(false);

jButton.setBackground(new Color(255, 20, 147)); // Deep pink
jButton.setForeground(Color.WHITE); // White text
jButton.setFont(new Font("Helvetica", Font.BOLD, 16));
jButton.setFocusPainted(false);
jButton.setBorderPainted(false);
jButton.setOpaque(true);
jButton.setBorder(javax.swing.BorderFactory.createLineBorder(Color.WHITE, 2, true));
jButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));


jButton.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        jButton.setBackground(new Color(255, 105, 180)); // Lighter pink on hover
    }

    public void mouseExited(java.awt.event.MouseEvent evt) {
        jButton.setBackground(new Color(255, 20, 147)); // Original deep pink
    }
});


add(jButton);
addMouseListener(new XOListener());

    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        TicTacToe tPanel = new TicTacToe();
        frame.add(tPanel);

       frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                try {
                    File file = new File("score.txt");
                    if (file.exists()) {
                        Scanner sc = new Scanner(file);
                        if (sc.hasNextLine()) tPanel.setPlayer1wins(Integer.parseInt(sc.nextLine()));
                        if (sc.hasNextLine()) tPanel.setPlayer2wins(Integer.parseInt(sc.nextLine()));
                        sc.close();
                    }
                } catch (IOException | NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }

            public void windowClosing(WindowEvent e) {
                try (PrintWriter pw = new PrintWriter("score.txt")) {
                    pw.println(tPanel.player1wins);
                    pw.println(tPanel.player2wins);
                } catch (FileNotFoundException fe) {
                    
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resetGame();
        repaint();
    }

    public JButton getJButton() {
        return jButton;
    }

    public void setPlayer1wins(int a) {
        player1wins = a;
    }

    public void setPlayer2wins(int a) {
        player2wins = a;
    }

    public void resetGame() {
        playerX = true;
        winner = -1;
        gameDone = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
        getJButton().setVisible(false);
        repaint();
    }

    @Override
    public void paintComponent(Graphics page) {
        super.paintComponent(page);
        drawBoard(page);
        drawUI(page);
        drawGame(page);
    }

    public void drawBoard(Graphics page) {
    setBackground(new Color(0x101820)); // Deep charcoal background

    page.setColor(new Color(0xFF007F)); //  hot pink

    // Vertical and horizontal grid lines
    page.fillRoundRect(x, y, lineLength, lineWidth, 5, 30);
    page.fillRoundRect(x, y + offset, lineLength, lineWidth, 5, 30);
    page.fillRoundRect(y, x, lineWidth, lineLength, 30, 5);
    page.fillRoundRect(y + offset, x, lineWidth, lineLength, 30, 5);



    //UI
} public void drawUI(Graphics page) {
    Color panelBG = new Color(0x196F6D);     // UI side panel background
    Color textColor = new Color(0xF5F5F5);   // Soft white
    Color circleColor = new Color(0x7FDBDA); // Pale turquoise

    page.setColor(panelBG);
    page.fillRect(300, 0, 120, 300);

    Font font = new Font("Helvetica", Font.PLAIN, 20);
    page.setFont(font);

    page.setColor(textColor);
    page.drawString("Win Count", 310, 30);
    page.drawString(": " + player1wins, 362, 70);
    page.drawString(": " + player2wins, 362, 105);

    // Triangle symbol 
Graphics2D g2 = (Graphics2D) page;
g2.setColor(new Color(0xFF007F)); // Hot pink 
g2.setStroke(new BasicStroke(2)); 
int[] xPoints = { 329 + 13, 329 + 27, 329 };
int[] yPoints = { 47, 47 + 27, 47 + 27 };
g2.drawPolygon(xPoints, yPoints, 3);

// Circle symbol
g2.setColor(circleColor);
g2.drawOval(328, 80, 30, 30);

// Text info
page.setColor(textColor);
Font font1 = new Font("Serif", Font.ITALIC, 18);
page.setFont(font1);

if (gameDone) {
   if (winner == 1) {
    page.drawString("The winner is", 310, 150);

    g2.setColor(new Color(0xFF007F)); // Hot pink triangle
    int[] tx = { 350 + 10, 350 + 20, 350 };
    int[] ty = { 160, 160 + 20, 160 + 20 };
    g2.drawPolygon(tx, ty, 3);

} else if (winner == 2) {
    page.drawString("The winner is", 310, 150);

    g2.setColor(circleColor); 
    g2.drawOval(350, 160, 20, 20); 
} else if (winner == 3) {
    page.drawString("It's a tie", 330, 178);
}

} else {
    Font font2 = new Font("serif", Font.ITALIC, 20);
    page.setFont(font2);
    page.drawString("It's", 350, 160);
    page.drawString(playerX ? "\u25B3's turn" : "O's turn", 325, 180);
}


Image squid = Toolkit.getDefaultToolkit().getImage("squid.png");
page.drawImage(squid, 325, 200, 70, 70, this);

// Footer
Font c = new Font("Serif", Font.ITALIC, 15);
page.setFont(c);
page.setColor(new Color(255, 255, 255)); // White color
page.drawString("Win or Eliminate!", 305, 280);



}


 public void drawGame(Graphics page) {
    Graphics2D g2 = (Graphics2D) page;
    g2.setStroke(new BasicStroke(3)); // Thicker outlines

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            int drawX = 30 + offset * i;
            int drawY = 30 + offset * j;

            if (board[i][j] == 1) {
                // Player 1: Triangle
                g2.setColor(new Color(0xFF007F)); // Hot pink
                int[] xPoints = { drawX + 20, drawX + 40, drawX };
                int[] yPoints = { drawY, drawY + 40, drawY + 40 };
                g2.drawPolygon(xPoints, yPoints, 3);
            } else if (board[i][j] == 2) {
                // Player 2: Circle
                g2.setColor(new Color(0x7FDBDA)); // Turquoise
                g2.drawOval(drawX, drawY, 40, 40);
            }
        }
    }
}

    public class XOListener implements MouseListener {
        @Override
        public void mousePressed(MouseEvent event) {
            selX = -1;
            selY = -1;
            if (!gameDone) {
                a = event.getX();
                b = event.getY();

                if (a > 12 && a < 99) selX = 0;
                else if (a > 103 && a < 195) selX = 1;
                else if (a > 200 && a < 287) selX = 2;

                if (b > 12 && b < 99) selY = 0;
                else if (b > 103 && b < 195) selY = 1;
                else if (b > 200 && b < 287) selY = 2;

                if (selX != -1 && selY != -1 && board[selX][selY] == 0) {
                    board[selX][selY] = playerX ? 1 : 2;
                    playerX = !playerX;
                    System.out.println("Clicked => x:" + a + ", y:" + b + " board:(" + selX + ", " + selY + ")");
                    checkWinner();
                } else {
                    System.out.println("Invalid click");
                }
                repaint();
            }
        }

        @Override public void mouseClicked(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}

       public void checkWinner() {
    if (gameDone) {
        System.out.println("Game Done");
        return;
    }

    int temp = -1;

    // Horizontal checks
    if ((board[0][0] == board[0][1]) && (board[0][1] == board[0][2]) && board[0][0] != 0) {
        temp = board[0][0];
    } else if ((board[1][0] == board[1][1]) && (board[1][1] == board[1][2]) && board[1][0] != 0) {
        temp = board[1][0];
    } else if ((board[2][0] == board[2][1]) && (board[2][1] == board[2][2]) && board[2][0] != 0) {
        temp = board[2][0];
    }

    // Vertical checks
    else if ((board[0][0] == board[1][0]) && (board[1][0] == board[2][0]) && board[0][0] != 0) {
        temp = board[0][0];
    } else if ((board[0][1] == board[1][1]) && (board[1][1] == board[2][1]) && board[0][1] != 0) {
        temp = board[0][1];
    } else if ((board[0][2] == board[1][2]) && (board[1][2] == board[2][2]) && board[0][2] != 0) {
        temp = board[0][2];
    }

    // Diagonal checks
    else if ((board[0][0] == board[1][1]) && (board[1][1] == board[2][2]) && board[0][0] != 0) {
        temp = board[0][0];
    } else if ((board[0][2] == board[1][1]) && (board[1][1] == board[2][0]) && board[0][2] != 0) {
        temp = board[0][2];
    }

    // Handle result
    if (temp != -1) {
        winner = temp;
        gameDone = true;

        if (winner == 1) {
            player1wins++;
        } else if (winner == 2) {
            player2wins++;
        }

        jButton.setVisible(true);
        repaint();
        return;
    }

    // Tie check
    boolean boardFull = true;
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (board[i][j] == 0) {
                boardFull = false;
                break;
            }
        }
    }

    if (boardFull) {
        winner = 3; // tie
        gameDone = true;
        jButton.setVisible(true);
        repaint();
    }
}

        }
    }

