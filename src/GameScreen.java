import javax.swing.JFrame;

public class GameScreen extends JFrame {
    public GameScreen(String title){
        super(title);
    }
    public static void main(String[] args) {
        GameScreen screen = new GameScreen("Space Game");
        Game game = new Game();
        screen.setResizable(false);
        screen.setFocusable(false);
        screen.setSize(800,600);

        game.requestFocus();
        game.addKeyListener(game);
        game.setFocusable(true);
        game.setFocusTraversalKeysEnabled(false);

        screen.add(game);

        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setVisible(true);

    }
}
