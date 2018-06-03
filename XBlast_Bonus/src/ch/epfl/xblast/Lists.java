package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class Lists
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class Lists
{
    private Lists()
    {}

    /**
     * Create a mirrored List of the given List
     * 
     * @param l
     *          a List of objects <T> that we want to mirror
     * 
     * @return
     *          the mirrored List
     * 
     * @throws IllegalArgumentExeption
     *          if the List is empty
     */

    public static <T> List<T> mirrored(List<T> l)
    {
        if (l.size() == 0)
        {
            throw new IllegalArgumentException();
        }
        
        ArrayList<T> newList = new ArrayList<>();
        
        newList.addAll(l);
        
        for (int i = l.size() - 2; i >= 0; --i)
        {
            newList.add(l.get(i));
        }
        return newList;
    }

    
    /**
     * Create a Lost containig all permutations of a given List
     * 
     * @param l
     *          a List of objects <T> that we permut
     * 
     * @return
     *          the list with permuaed lists
     */
    
    public static <T> List<List<T>> permutations(List<T> l)
    {
        if (l.size() == 0)
        {
            ArrayList<List<T>> list = new ArrayList<List<T>>();

            list.add(Collections.emptyList());
            
            return list;
        }
        T firstElement = l.get(0);
        
        ArrayList<List<T>> returnArray = new ArrayList<List<T>>();

        List<T> subListWithoutFirst = l.subList(1, l.size());

        List<List<T>> permutatedSubLists = permutations(subListWithoutFirst);

        for (List<T> provList : permutatedSubLists)
        {       
            for (int i = 0; i <= provList.size(); ++i)
            {
                ArrayList<T> provArray = new ArrayList<>(provList);
                provArray.add(i, firstElement);
                returnArray.add(provArray);
            }
        }    
        return Collections.unmodifiableList(returnArray);
    }
}
