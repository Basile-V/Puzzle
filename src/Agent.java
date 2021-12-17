import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
        while (!this.fini()){
            try {
                bouger();
                Thread.sleep(100);
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }
//
//    private void communiquer() {
//
//    }
//
//    private void agir(){
//
//    }

    private void bouger(){
        Map.Entry<Deplacement, int[]> res = this.cheminLibre(this.pos);
        int[] nextPos;
        if(res == null){
            int[] vectObj = new int[] {Math.abs(this.pos[0] - this.obj[0]), Math.abs(this.pos[1] - this.obj[1])};
            Deplacement[] deplacements = this.deplacementsPos();
            if(vectObj[0] >= vectObj[1] && deplacements[0] != null) {
                nextPos = this.futurPos(deplacements[0]);
                if (this.grid.set(nextPos[0], nextPos[1], this, true)) {
                    System.out.println("L'agent " + this.nom + " s'est déplacé");
                } else {
                    nextPos = this.futurPos(deplacements[1]);
                    if (this.grid.set(nextPos[0], nextPos[1], this, true)) {
                        System.out.println("L'agent " + this.nom + " s'est déplacé");
                    }
                }
            } else if(deplacements[1] != null) {
                nextPos = this.futurPos(deplacements[1]);
                if (this.grid.set(nextPos[0], nextPos[1], this, true)) {
                    System.out.println("L'agent " + this.nom + " s'est déplacé");
                } else if(deplacements[0] != null){
                    nextPos = this.futurPos(deplacements[0]);
                    if (this.grid.set(nextPos[0], nextPos[1], this, true)) {
                        System.out.println("L'agent " + this.nom + " s'est déplacé");
                    }
                }
            }
        } else{
            if(this.grid.set(res.getValue()[0], res.getValue()[1], this, true)) {
                switch (res.getKey()){
                    case Droite :
                        System.out.println("L'agent " + this.nom + " s'est déplacé vers la droite\n\n");
                        break;
                    case Gauche:
                        System.out.println("L'agent " + this.nom + " s'est déplacé vers la gauche\n\n");
                        break;
                    case Haut:
                        System.out.println("L'agent " + this.nom + " s'est déplacé vers le haut\n\n");
                        break;
                    case Bas:
                        System.out.println("L'agent " + this.nom + " s'est déplacé vers le bas\n\n");
                        break;
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

    private synchronized int[] futurPos(Deplacement deplacement){
        return applyOn2Arrays(Integer::sum, this.pos, DeplacementMap.map.get(deplacement));
    }

    private synchronized int[] futurPos(Deplacement deplacement, int[] position){
        return applyOn2Arrays(Integer::sum, position, DeplacementMap.map.get(deplacement));
    }

    private Deplacement[] deplacementsPos() {
        Deplacement[] deplacements = new Deplacement[2];
        if(this.pos[1] > this.obj[1]){
            deplacements[1] = Deplacement.Gauche;
        } else if(this.pos[1] < this.obj[1]) {
            deplacements[1] = Deplacement.Droite;
        }
        if(this.pos[0] > this.obj[0]){
            deplacements[0] = Deplacement.Haut;
        } else if(this.pos[0] < this.obj[0]) {
            deplacements[0] = Deplacement.Bas;
        }
        return deplacements;
    }

    public Map.Entry<Deplacement, int[]> cheminLibre(int[] position){
        int[] sum;
        if(this.obj[0] > position[0]) {
            sum = this.futurPos(Deplacement.Bas, position);
            if(sum == this.obj && this.grid.get(sum[0], sum[1]) == null){
                return new AbstractMap.SimpleEntry<>(Deplacement.Bas, sum);
            }
            else if(this.grid.get(sum[0], sum[1]) == null && this.cheminLibre(sum) != null){
                return new AbstractMap.SimpleEntry<>(Deplacement.Bas, sum);
            } else if(this.obj[1] > position[1]){
                sum = this.futurPos(Deplacement.Droite, position);
                if(sum == this.obj && this.grid.get(sum[0], sum[1]) == null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Droite, sum);
                }
                else if(this.grid.get(sum[0], sum[1]) == null && this.cheminLibre(sum) != null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Droite, sum);
                }
            } else if(this.obj[1] < position[1]){
                sum = this.futurPos(Deplacement.Gauche, position);
                if(sum == this.obj && this.grid.get(sum[0], sum[1]) == null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Gauche, sum);
                }
                else if(this.grid.get(sum[0], sum[1]) == null && this.cheminLibre(sum) != null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Gauche, sum);
                }
            }
        }else if(this.obj[0] < position[0]){
            sum = this.futurPos(Deplacement.Haut, position);
            if(sum == this.obj && this.grid.get(sum[0], sum[1]) == null){
                return new AbstractMap.SimpleEntry<>(Deplacement.Haut, sum);
            }
            else if(this.grid.get(sum[0], sum[1]) == null && this.cheminLibre(sum) != null){
                return new AbstractMap.SimpleEntry<>(Deplacement.Haut, sum);
            } else if(this.obj[1] > position[1]){
                sum = this.futurPos(Deplacement.Droite, position);
                if(sum == this.obj && this.grid.get(sum[0], sum[1]) == null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Droite, sum);
                }
                else if(this.grid.get(sum[0], sum[1]) == null && this.cheminLibre(sum) != null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Droite, sum);
                }
            } else if(this.obj[1] < position[1]){
                sum = this.futurPos(Deplacement.Gauche, position);
                if(sum == this.obj && this.grid.get(sum[0], sum[1]) == null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Gauche, sum);
                }
                else if(this.grid.get(sum[0], sum[1]) == null && this.cheminLibre(sum) != null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Gauche, sum);
                }
            }
        } else{
            if(this.obj[1] > position[1]){
                sum = this.futurPos(Deplacement.Droite, position);
                if(sum == this.obj && this.grid.get(sum[0], sum[1]) == null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Droite, sum);
                }
                else if(this.grid.get(sum[0], sum[1]) == null && this.cheminLibre(sum) != null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Droite, sum);
                }
            } else if(this.obj[1] < position[1]){
                sum = this.futurPos(Deplacement.Gauche, position);
                if(sum == this.obj && this.grid.get(sum[0], sum[1]) == null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Gauche, sum);
                }
                else if(this.grid.get(sum[0], sum[1]) == null && this.cheminLibre(sum) != null){
                    return new AbstractMap.SimpleEntry<>(Deplacement.Gauche, sum);
                }
            }
        }
        return null;
    }

    public void setPos(int x, int y){
        this.pos[0] = x;
        this.pos[1] = y;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "nom=" + nom +
                ", pos=" + Arrays.toString(pos) +
                ", obj=" + Arrays.toString(obj) +
                ", grid=" + grid +
                '}';
    }
}
