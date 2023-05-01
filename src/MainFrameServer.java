import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameServer extends JFrame implements ActionListener{
    JMenuBar menu;
    JMenu GameLabel;
    JMenuItem play, about, exit;
    Dialogs dialogs = new Dialogs();
    NetworkAdapter network;
    Board board;
    BoardPanel boardPanel;

    public MainFrameServer(NetworkAdapter network, Board board){
        super("Omok");
        this.network = network;
        this.board = board;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(350,525));
        this.setLayout(new BorderLayout());

        createMenuBar();

        var toolBar = createJToolBar();
        this.add(toolBar,BorderLayout.NORTH);

        var panelForBoard = createCenterLayout();
        this.add(panelForBoard,BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
    }
    private void createMenuBar(){
        this.menu = new JMenuBar();
        this.GameLabel = new JMenu("Game");
        this.menu.add(this.GameLabel);
        this.play = new JMenuItem("play");
        this.about = new JMenuItem("about");
        this.exit = new JMenuItem("exit");
        this.GameLabel.add(play);
        this.GameLabel.add(about);
        this.GameLabel.add(exit);
        this.setJMenuBar(menu);

        play.addActionListener(e->{
            //serverUI.writePlay();
        });
        about.addActionListener(e->{
            dialogs.aboutMSG();
        });
        exit.addActionListener(e->{
            dialogs.exitSys();
        });
    }
    private JToolBar createJToolBar(){
        JToolBar toolBar = new JToolBar(/*"Omok"*/);
//        JButton playInToolBar = new JButton(new ImageIcon(getClass().getResource("playImage.png")));
        JButton playInToolBar = new JButton("Play");
        playInToolBar.setToolTipText("Create a new Game");
        toolBar.add(playInToolBar);

//        JButton aboutInToolBar = new JButton(new ImageIcon(getClass().getResource("questionMark.png")));
        JButton aboutInToolBar = new JButton("About");
        toolBar.add(aboutInToolBar);

        playInToolBar.addActionListener(e->{
            //  serverUI.writePlay();
        });
        aboutInToolBar.addActionListener(e->{
            dialogs.aboutMSG();
        });

        return toolBar;
    }
    private JPanel createCenterLayout(){
        var panel = new JPanel();
        panel.setLayout(new BorderLayout());
        var centerTopWithButtons = new JPanel();
        panel.add(centerTopWithButtons, BorderLayout.NORTH);
        var playButton = new JButton("Play");
        centerTopWithButtons.add(playButton);
    //    var pairButton = new JButton("Pair");
        boardPanel = new BoardPanel(network, board);
        panel.add(boardPanel, BorderLayout.CENTER);

        playButton.addActionListener(e->{
            network.writePlay();
            playButton.setEnabled(false);
        });
        return panel;
    }
    public void drawOpponent(int x, int y, Human p2){
        Graphics g = getGraphics();
        g.setColor(Color.BLACK);
        boardPanel.drawOpponent(x,y,g);
    }
    public void setNetwork(NetworkAdapter network){
        this.network = network;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
