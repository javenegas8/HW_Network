import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class OmokServer {
    private static final String USAGE = "Usage: java OmokServer";
    private static final int PORT = 9000;

    JTextArea msgDisplay;
    NetworkAdapter network;
    Dialogs dialogs = new Dialogs();
    Board board = new Board();
    Human p2 = new Human("p2");
    MainFrameServer frame;

    public OmokServer(){
        var display = new JFrame();
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var panel = new JPanel();
        display.add(panel);
        display.setPreferredSize(new Dimension(300,400));
        msgDisplay = new JTextArea(20,20);
        DefaultCaret caret = (DefaultCaret) msgDisplay.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane msgScrollPane = new JScrollPane(msgDisplay);
        panel.add(msgScrollPane);

        display.pack();
        display.setVisible(true);
    }
    public void start(){
        System.out.println("OmokServer started on port 8000");
        try{
            ServerSocket s = new ServerSocket(PORT);
            Socket socket = s.accept();
            System.out.println("Client connected");
            network = new NetworkAdapter(socket);
            network.setMessageListener(new NetworkAdapter.MessageListener() {
                public void messageReceived(NetworkAdapter.MessageType type, int x, int y) {
                    switch (type) {
                        case PLAY:
                            msgDisplay.append("play:" + "\n");
                            dialogs.play(network);
                            break;
                        case PLAY_ACK:
                            msgDisplay.append("play_ack: " + x + ", " + y + "\n");
                            break;
                        case MOVE:
                            msgDisplay.append("move_ack: " + x + ", " + y + "\n");
                            board.placeStone(x,y,p2);
                            frame.drawOpponent(x,y,p2);
                            break;
                        case MOVE_ACK:
//                            board.placeStone(x,y,p2);
//                            System.out.println(frame);
//                            frame.drawOpponent(x,y,p2);
                        case QUIT:
                            msgDisplay.append("Game ended.");
                            break;
                    }
                }
            });
            // receive messages asynchronously
            network.receiveMessagesAsync();
            frame = new MainFrameServer(network, board);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        if(args.length > 0){
            System.out.println(USAGE);
            System.exit(-1);
        }
        new OmokServer().start();
    }
}
