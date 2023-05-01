public class Client {
    private Board board = new Board();

    public Client(){
    }
    public void start(){
        var frame = new MainFrameClient();
    }
    public static void main(String[] args){

        new Client().start();
    }

}
