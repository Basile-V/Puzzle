import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public List<List<Agent>> grid;

    public static void main(String[] args) {
        int n = 5;
        int m = 5;
        Grid grid = new Grid(n, m);
        Agent soleil = new Agent('S', new int[]{0, 3}, new int[]{0, 1}, grid);
        Agent sablier = new Agent('x', new int[]{1, 2}, new int[]{1, 2}, grid);
//        Agent croix = new Agent('c', new int[]{0, 2}, new int[]{0, 3}, grid);
        Agent etoile = new Agent('e', new int[]{3, 3}, new int[]{2, 1}, grid);
        Agent[] agents = {soleil, sablier, etoile};
        grid.setAgents(agents);
        grid.printGrid();
        grid.start();
    }
}
