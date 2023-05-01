import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameClient extends JFrame implements ActionListener{
    JMenuBar menu;
    JMenu GameLabel;
    JMenuItem play, about, exit;
    Dialogs dialogs = new Dialogs();
//    NetworkAdapter network;
    Board board = new Board();
    ServerUI serverUI;

    public MainFrameClient(){
        super("Omok");
//        this.network = network;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(350,525));
        this.setLayout(new BorderLayout());

        createMenuBar();

        var toolBar = createJToolBar();
        this.add(toolBar,BorderLayout.NORTH);

        var boardPanel = createCenterLayout();
        this.add(boardPanel,BorderLayout.CENTER);

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
              serverUI.writePlay();
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
        playButton.setEnabled(false);
        var pairButton = new JButton("Pair");
        centerTopWithButtons.add(pairButton);
        var boardPanel = new BoardPanel(null, board);
        panel.add(boardPanel, BorderLayout.CENTER);

        playButton.addActionListener(e->{
            serverUI.writePlay();
            playButton.setEnabled(false);
        });
        pairButton.addActionListener(e->{
            serverUI = new ServerUI();
            playButton.setEnabled(true);
            this.setVisible(false);
        });
        return panel;
    }
//    public void setNetwork(NetworkAdapter network){
//        this.network = network;
//    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
