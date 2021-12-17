import java.util.ArrayList;
import java.util.List;

public class Grid {
    private Agent[] agents;
    private List<List<Agent>> grid;
    private int nbMoves;

    public Grid(int n, int m) {
        this.nbMoves = 0;
        this.grid = new ArrayList<>();
        for(int i = 0; i<n; i++){
            List<Agent> row = new ArrayList<>();
            for(int j = 0; j<m; j++){
                row.add(null);
            }
            this.grid.add(row);
        }
    }

    public void start(){
        if(reussite()) terminerLeProgramme();
        else{
            for (Agent a : this.agents){
                a.start();
            }
            try {
                while(reussite()) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }}
    }

    private boolean reussite(){
        boolean reussite = true;
        int i = 0;
        while (reussite &&  i < this.agents.length){
            if(!this.agents[i++].fini()){
                reussite = false;
            }
        }
        return reussite;
    }

    public void setAgents(Agent[] agents){
        this.agents = agents;
        for (int i = 0; i < agents.length; i++){
            this.set(agents[i].getPos()[0], agents[i].getPos()[1], agents[i], false);
            this.nbMoves --;
        }
    }

    public Agent get(int x, int y){
        return this.grid.get(y).get(x);
    }

    public synchronized boolean set(int x, int y, Agent a, boolean verbose){
        if(this.get(x, y) == null){
            this.grid.get(a.getPos()[1]).set(a.getPos()[0], null);
            this.grid.get(y).set(x, a);
            a.setPos(x, y);
            this.nbMoves++;
            if (verbose) printGrid();
            return true;
        } else return false;
    }

    public void printGrid(){
        System.out.println("Grille au bout de : " + this.nbMoves + " mouvements");
        String line = "";
        String line2;
        for (int i = 0; i < this.grid.size(); i++){
            line += "--";
        }
        for(int y = 0; y < this.grid.get(0).size(); y++){
            System.out.println(line + "-");
            line2 = "";
            for (int x = 0; x < this.grid.size(); x++){
                if(this.grid.get(x).get(y) == null){
                    line2 += "| ";
                }
                else{
                    line2 += "|" + this.grid.get(x).get(y).nom;
                }
            }
            System.out.println(line2 + "|");
        }
        System.out.println(line + "-");
    }

    public void terminerLeProgramme(){
        System.out.println("Réussite : " + this.reussite() +" | Nombre de déplacements : "+this.nbMoves);
    }
}
