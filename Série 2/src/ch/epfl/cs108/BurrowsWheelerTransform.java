package ch.epfl.cs108;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

import javax.sound.midi.VoiceStatus;

public final class BurrowsWheelerTransform {
    private BurrowsWheelerTransform() {}
    public static void main(String[]args){
        System.out.println(backward(new Pair<Integer, String>(2, "vjaa")));
    }
    public static Pair<Integer, String> forward(String str) {
        if (str.length() == 0)
        {
            throw new IllegalArgumentException();
        }
        List<String> mainList = new ArrayList<>();
        Queue<Character> oneRotation= new ArrayDeque<>();
        
        for (int i = 0; i < str.length(); ++i)
        {
            oneRotation.add(str.charAt(i));     
        }
        
        for(int j = 0; j < str.length(); ++j)
        {
            String oneString = "";
            for (Character character : oneRotation)
            {
                oneString += character;
            }
            
            mainList.add(oneString);
            oneRotation.add(oneRotation.remove());
        }
        
        Collections.sort(mainList);
        
        String vertical = "";
        int index = mainList.indexOf(str);
        for ( String s : mainList )
        {
            vertical += s.charAt(str.length()-1);
        }
        
        return new Pair<Integer, String>(index, vertical);
        
    }

    public static String backward(Pair<Integer, String> bwt) {
        String givenString = bwt.second();
        List<String> listOfStrings = new ArrayList<>(Collections.nCopies(givenString.length(), ""));
        
        
        for (int j = 0; j < givenString.length(); ++j)
        {
            for (int i = 0; i < givenString.length(); ++i)
            {             
                listOfStrings.set(i,givenString.charAt(i) +  listOfStrings.get(i));
            }
            
            
            Collections.sort(listOfStrings);
        }
       
        
        return listOfStrings.get(bwt.first());
    }
}
