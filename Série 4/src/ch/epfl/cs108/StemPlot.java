package ch.epfl.cs108;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class StemPlot {
    public static void print(Collection<Integer> data) {
        
        Map<Integer, List<Integer>> mapStemLeaf = new HashMap<>();
        
        for (Integer oneIntegerOfData : data)
        {
            int stem = oneIntegerOfData / 10;
            int leaf = oneIntegerOfData % 10;
            if (!mapStemLeaf.containsKey(stem))
            {
                List<Integer> leaves = new LinkedList<>();
                leaves.add(leaf);
                mapStemLeaf.put(stem, leaves);
            }
            else
            {
                mapStemLeaf.get(stem).add(leaf);
            }        
        }
        
        
        for (Integer stem : mapStemLeaf.keySet())
        {
            System.out.print(String.format("%2d", stem));
            System.out.print("|");
            Collections.sort(mapStemLeaf.get(stem));
            for (Integer integer : mapStemLeaf.get(stem))
            {
                System.out.print(integer);
            }
            System.out.println("");
        }
    
    }
    
}
