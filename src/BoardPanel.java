import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class BoardPanel extends JPanel implements  MouseListener{

    Board board;
    Human p1 = new Human("P1");
    Human p2 = new Human("P2");
    Dialogs dialogs = new Dialogs();
    NetworkAdapter network;
//    Dimension d = this.getSize();
    boolean acceptClick = true;
    boolean win = false;
    public BoardPanel(NetworkAdapter network, Board board){
        this.addMouseListener(this);
        this.network = network;
        this.board = board;
    }
    protected void paintComponent(Graphics g) {
        Dimension d = getSize();
        System.out.println(d.width + " " + d.height);
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.BLACK);
        int x = 0, y = 0;
        double widthIncrement = d.width / 15;
        double heightIncrement = d.height / 15;
        int currX = 0;
        for (x = 0; x <= 14; x++) {
            g.drawLine(currX, y, currX, d.height);
            currX += widthIncrement;
        }
        x = 0;
        int currY = 0;
        for (y = 0; y <= 14; y++) {
            g.drawLine(x, currY, d.width, currY);
            currY += heightIncrement;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(acceptClick) {
            Graphics g = getGraphics();
            int mouseX = e.getX();
            int mouseY = e.getY();
            Dimension d = getSize();

            int x = (int) Math.floor(mouseX / (d.width / 15));
            int y = (int) Math.floor(mouseY / (d.height / 15));
            int temp = x;
            x = y;
            y = temp;

            if (board.isOccupied(x, y)) {
                return;
            } else {
                board.placeStone(x, y, p1);
                network.writeMove(x, y);
                g.setColor(Color.WHITE);
                draw(x, y, g, d);
                if (board.isWonBy(p1)) { /*add chat dialog that says that somebody has won*/
                    repaintWinRow(g, d, board);
                    dialogs.winMessage(p1);
                }
            }
        }
    }
    public void draw(int x, int y, Graphics g, Dimension d){
        if (x >= 0 && x <= 14 && y >= 0 && y <= 14) {
            g.fillOval(  5+ y * (d.width / 15),  10 + x * (d.height / 15), 15, 15);
        }
        acceptClick = false;

    }
    public void drawOpponent(int x, int y, Graphics g){
        Dimension d = getSize();
        if (x >= 0 && x <= 14 && y >= 0 && y <= 14) {
            g.fillOval(  10 + y * (d.width / 15),  130 + x * (d.height / 15), 15, 15);
        }
        if(!win)
            p2Won(x,y,p2,g);
        acceptClick = true;
    }
    private void p2Won(int x, int y, Human p2, Graphics g){
        board.placeStone(x,y,p2);
        if (board.isWonBy(p2)){ /*add chat dialog that says that somebody has won*/
            win = true;
            repaintWinRowOpponent(g,board);
            dialogs.loseMessage(p2);
        }
    }
    private void repaintWinRow(Graphics g, Dimension d, Board board){
        g.setColor(Color.RED);
        Board.Place curr;
        for(int i = 0; i < 5; i++){
            curr = board.winningRow.get(i);
            draw(curr.x, curr.y, g, d);
        }
    }
    private void repaintWinRowOpponent(Graphics g, Board board){
        g.setColor(Color.RED);
        Board.Place curr;
        for(int i = 0; i < 5; i++){
            System.out.println(i);
            curr = board.winningRow.get(i);
            drawOpponent(curr.x, curr.y, g);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}