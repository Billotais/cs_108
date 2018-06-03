package ch.epfl.cs108;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import javax.swing.event.ListSelectionEvent;

public class LZWConcreteEncoder implements LZWEncoder
{
    private final SortedSet<Character> alphabet;
    private final int capacitiy;
    public LZWConcreteEncoder(SortedSet<Character> alphabet, int capacity)
    {
        this.alphabet = alphabet;
        this.capacitiy = capacity;
    }
    
    @Override
    public List<Integer> encode(String s)
    {
        SortedSet<String> trueAlphabet = new TreeSet<>();
        for (Character character : alphabet)
        {
            trueAlphabet.add(Character.toString(character));
        }
        
        
        List<Integer> finalList = new ArrayList<>();
        int position = 0;
        while (position < s.length())
        {
            String usedPrefix = s.substring(position,position + 2);
            for (int i = position + 2; i < s.length(); ++i)
            {
                String subString = s.substring(position, i);
                if (alphabet.contains(subString))
                {
                    if(alphabet.size() < capacitiy)
                    {
                        usedPrefix = s.substring(position, i+1);
                        
                    }
                    else
                    {
                        usedPrefix = subString;
                    }
                   
                }
            }
            trueAlphabet.add(usedPrefix);
            
            
            
                
        }
    }

}
