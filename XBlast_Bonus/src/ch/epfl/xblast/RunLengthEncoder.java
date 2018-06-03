package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.List;



public final class RunLengthEncoder
{
    
    
    private RunLengthEncoder(){}
    
    public static List<Byte> encode(List<Byte> list)
    {
        
        List<Byte> provList = new ArrayList<>();
        List<Byte> finalList = new ArrayList<>();
        
        for (Byte b : list)
        {
            if (b < 0){throw new IllegalArgumentException();}
            if (!provList.contains(b))
            {
                if (!list.isEmpty())
                {
                    int size = provList.size();
                    if(size <= 2)
                    {
                       finalList.addAll(provList); 
                    }
                    else
                    {
                        finalList.add((byte)(-(size-2)));
                        finalList.add(provList.get(0));
                    }
                    
                    provList.clear();
                }
                
                
                
            }
            provList.add(b);
            if (provList.size()>=130)
            {
                finalList.add((byte)(-(provList.size()-2)));
                finalList.add(provList.get(0));
                provList.clear();
                
            }
            
        }
        
        int size = provList.size();
        if(size <= 2)
        {
           finalList.addAll(provList); 
        }
        else
        {
            finalList.add((byte)(-(size-2)));
            finalList.add(provList.get(0));
        }
        
        return finalList;
    }
    
    public static List<Byte> decode(List<Byte> list)
    {
        
        if (list.get(list.size()-1)<0){throw new IllegalArgumentException();}
        
        List<Byte> finalList = new ArrayList<>();

        
        for(int i = 0; i < list.size(); ++i)
        {
            Byte actualByte = list.get(i);
            if(actualByte < 0)
            {
                for(int j = 0; j < -(actualByte-2); ++j)
                {
                    finalList.add(list.get(i+1));
                }
                ++i;
            }
            else
            {
                finalList.add(actualByte);
            }
        }
        
        return finalList;
    }
    
}
