package sample;

public class Message {
    private Agent emetteur;
    private Agent obstacle;
    private int[] obj;


    public Message(Agent emetteur, Agent obstacle, int[] obj){
        this.emetteur = emetteur;
        this.obstacle = obstacle;
        this.obj = obj;
    }

    public void notifier(){
        System.out.println(emetteur.getName() + " " + obstacle.getName());
        this.obstacle.makePlace();
    }
}
