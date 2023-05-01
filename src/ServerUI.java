import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerUI extends JFrame {
    String hostName;
    final String ip = "1.";
    int port;
    Socket socket;
    private Dialogs dialogs = new Dialogs();
    BufferedReader in;
    PrintWriter out;
    NetworkAdapter network;
    Board board = new Board();
    Human p2 = new Human("p2");
    MainFrameServer frame;


    public ServerUI(){
        super("Omok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(350, 450));
        this.setLayout(new GridLayout(3,1));

        add(createPlayerBlock());

        var thirdPanel = new JPanel();
        var field = new JTextArea();
        field.setPreferredSize(new Dimension(300,100));
        thirdPanel.add(field);
        var closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(70,30));
        thirdPanel.add(closeButton);

        add(createOpponentBlock(field));
        add(thirdPanel);

        this.pack();
        this.setVisible(true);

        closeButton.addActionListener(e->{
            System.exit(-1);
        });
    }
    private JPanel createPlayerBlock(){
        var panel = new JPanel();
        panel.setLayout(new GridLayout(4,0));
        panel.add(new JLabel("Player"));

        var hostLine = new JPanel();
        hostLine.add(new JLabel("Host name:"));
        var name = new JTextField("JavierVenegas", 20);
        name.setEditable(false);
        hostLine.add(name);
        panel.add(hostLine);

        var ipLine = new JPanel();
        ipLine.add(new JLabel("IP number:"));
        var ip = new JTextField("192.168.1.65",20);
        ip.setEditable(false);
        ipLine.add(ip);
        panel.add(ipLine);

        var portLine = new JPanel();
        portLine.add(new JLabel("Port number:"));
        var port = new JTextField("8000", 20);
        port.setEditable(false);
        portLine.add(port);
        panel.add(portLine);

        return panel;
    }
    private JPanel createOpponentBlock(JTextArea field){
        var panel = new JPanel();
        panel.setLayout(new GridLayout(4,0));
        panel.add(new JLabel("Opponent"));

        var hostLine = new JPanel();
        hostLine.add(new JLabel("Host name/IP:"));
        var host = new JTextField(20);
        host.setText("localhost");
        hostLine.add(host);
        panel.add(hostLine);

        var ipLine = new JPanel();
        ipLine.add(new JLabel("Port number:"));
        var ip = new JTextField(20);
        ip.setText("8000");
        ipLine.add(ip);
        panel.add(ipLine);

        var connectButton = new JButton("Connect");
        var disconnectButton = new JButton("Disconnect");
        var buttons = new JPanel();
        buttons.add(connectButton);
        buttons.add(disconnectButton);
        panel.add(buttons);

        connectButton.addActionListener(e->{
            try {
                socket = new Socket(host.getText(), Integer.parseInt(ip.getText()));

                network = new NetworkAdapter(socket);
                network.setMessageListener(new NetworkAdapter.MessageListener() {
                    public void messageReceived(NetworkAdapter.MessageType type, int x, int y) {
                        switch (type) {
                            case PLAY:
                                field.append("play:" + "\n");
                                dialogs.play(network);
                                break;
                            case PLAY_ACK:
                                field.append("play_ack: " + x + ", " + y + "\n");
                                break;
                            case MOVE:
                                field.append("move_ack: " + x + ", " + y + "\n");
                                frame.drawOpponent(x,y,p2);
                                break;
                            case MOVE_ACK:

                            case QUIT:
                                field.append("Game ended.");
                        }
                    }
                });
                // receive messages asynchronously
                network.receiveMessagesAsync();

                frame = new MainFrameServer(network,board);

                //     <<send and receive data by using in and out>>

                connectButton.setEnabled(false);
            } catch (UnknownHostException ex){
                warn("Unknown host: " + host.getText());
            }catch (IOException ex) {
                warn("Unable to connect to server");
            } catch (NumberFormatException ex){
                warn("Invalid port number: " + ip.getText());
            }
        });


        return panel;
    }
    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "JavaChat",
                JOptionPane.PLAIN_MESSAGE);
    }
    public void writePlay(){
        network.writePlay();
    }
    public void writeMove(int x, int y){
        network.writeMove(x,y);
    }


    public static void main(String[] args) {
        ServerUI dialog = new ServerUI();
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

}
