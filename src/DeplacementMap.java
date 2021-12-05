import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DeplacementMap{
    public static final Map<Deplacement, int[]> map;
    static  {
        Map<Deplacement, int[]> amap = new HashMap<>();
        amap.put(Deplacement.Haut, new int[]{-1, 0});
        amap.put(Deplacement.Bas, new int[]{1, 0});
        amap.put(Deplacement.Gauche, new int[]{0, -1});
        amap.put(Deplacement.Droite, new int[]{0, 1});
        map = Collections.unmodifiableMap(amap);
    }
}
