import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

public  class Agent extends Thread {

    public char nom;
    private int[] pos;
    private final int[] obj;
    private final Grid grid;


    public Agent(char nom, int[] pos, int[] obj, Grid grid){
        this.nom = nom;
        this.pos = pos;
        this.obj = obj;
        this.grid = grid;
    }

    @Override
    public void run() {
        System.out.println("Agent "+ this.nom + " start running");
        while (!this.fini()){
            try {
                bouger();
                Thread.sleep(100);
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    private void communiquer() {

    }

    private void agir(){

    }

    private void bouger(){
        if(this.obj[0] > this.pos[0]) {
            int[] sum = this.futurPos(Deplacement.Bas);
            if(this.grid.set(sum[0], sum[1], this, true)){
                this.pos = sum;
                System.out.println("L'agent " + this.nom + " s'est déplacé vers le bas\n\n");
            } else if(this.obj[1] > this.pos[1]){
                sum = this.futurPos(Deplacement.Droite);
                if(this.grid.set(sum[0], sum[1], this, true)){
                    this.pos = sum;
                    System.out.println("L'agent " + this.nom + " s'est déplacé vers la droite\n\n");
                }
            } else if(this.obj[1] < this.pos[1]){
                sum = this.futurPos(Deplacement.Gauche);
                if(this.grid.set(sum[0], sum[1], this, true)){
                    this.pos = sum;
                    System.out.println("L'agent " + this.nom + " s'est déplacé vers la gauche\n\n");
                }
            }
        }else if(this.obj[0] < this.pos[0]){
            int[] sum = this.futurPos(Deplacement.Haut);
            if(this.grid.set(sum[0], sum[1], this, true)){
                this.pos = sum;
                System.out.println("L'agent " + this.nom + " s'est déplacé vers le haut\n\n");
            } else if(this.obj[1] > this.pos[1]){
                sum = this.futurPos(Deplacement.Droite);
                if(this.grid.set(sum[0], sum[1], this, true)){
                    this.pos = sum;
                    System.out.println("L'agent " + this.nom + " s'est déplacé vers la droite\n\n");
                }
            } else if(this.obj[1] < this.pos[1]){
                sum = this.futurPos(Deplacement.Gauche);
                if(this.grid.set(sum[0], sum[1], this, true)){
                    this.pos = sum;
                    System.out.println("L'agent " + this.nom + " s'est déplacé vers la gauche\n\n");
                }
            }
        } else{
            if(this.obj[1] > this.pos[1]){
                int[] sum = this.futurPos(Deplacement.Droite);
                if(this.grid.set(sum[0], sum[1], this, true)){
                    this.pos = sum;
                    System.out.println("L'agent " + this.nom + " s'est déplacé vers la droite");
                }
            } else if(this.obj[1] < this.pos[1]){
                int[] sum = this.futurPos(Deplacement.Gauche);
                if(this.grid.set(sum[0], sum[1], this, true)){
                    this.pos = sum;
                    System.out.println("L'agent " + this.nom + " s'est déplacé vers la gauche");
                }
            }
        }
    }

    public int[] getPos(){
        return pos;
    }

    private int[] applyOn2Arrays(IntBinaryOperator operator, int[] a, int[] b) {
        return IntStream.range(0, a.length)
                .map(index -> operator.applyAsInt(a[index], b[index]))
                .toArray();
    }

    public boolean fini(){
        return this.pos == this.obj;
    }

    public int[] futurPos(Deplacement deplacement){
        return applyOn2Arrays(Integer::sum, this.pos, DeplacementMap.map.get(deplacement));
    }
}
