import javax.swing.*;
import java.util.Random;

public class Dialogs extends JDialog {
    Random rand = new Random();
    public Dialogs(){
        setLocationRelativeTo(null);
    }
    public void newGame(){
        int choice = JOptionPane.showOptionDialog(this,"Create new game?","New Game?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Confirm", "Cancel"}, null);
        if(choice == JOptionPane.YES_OPTION){
            //new MainFrame(1);
        }
    }
    public void aboutMSG(){
        JOptionPane.showMessageDialog(this,"HOW TO PLAY OMOK: \n1. Select Game mode(Human, Computer)\n" +
                " 2. Initiate game by clicking any Play button\n" +
                "3. Connect 5 stones in a row (horizontal, vertical, diagonal)");
    }
    public void exitSys(){
        System.exit(0);
    }
    public void winMessage(Player player){
        JOptionPane.showMessageDialog(this,"Congratuations " + player.name() + " won!");
    }
    public void fullBoard(){
        int choice = JOptionPane.showOptionDialog(this,"The Board is full:((\nCreate new game to break the tie!!","TIE!!!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Confirm", "Cancel"}, null);
        if(choice == JOptionPane.YES_OPTION){
          //  new MainFrame(1);
        }
    }
    public void turnMSG(Player player){
        JOptionPane.showMessageDialog(this, player.name() + " is your turn!");
    }

    public void play(NetworkAdapter network){
        int choice = JOptionPane.showOptionDialog(this, "Play game?", "New Game", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Yes", "NO!"}, null);
        if(choice == JOptionPane.YES_OPTION){
            network.writePlayAck(true, rand.nextBoolean());
        }
        else {
            network.writePlayAck(false, rand.nextBoolean());
        }
    }
    public void loseMessage(Human p2){
        JOptionPane.showMessageDialog(this,p2.name() + " won!\nWell played! :(");
    }

}
