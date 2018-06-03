package ch.epfl.cs108;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class LSystem {
    
    private String string;
    private Map<Character, String> rules;
    private String lineChars;
    private int turningAngle;
    
    public LSystem(String string, Map<Character, String> rules, String lineChars, int turningAngle) {
        // TODO: à compléter (exercice 1)
        this.string = string;
        this.rules = Collections.unmodifiableMap(rules);
        this.lineChars = lineChars;
        this.turningAngle = turningAngle;
        
    }

    public String string() {
        // TODO: à compléter (exercice 1)
        return string;
    }

    public Map<Character, String> rules() {
        // TODO: à compléter (exercice 1)
        return rules;
    }

    public Set<Character> lineChars() {
        // TODO: à compléter (exercice 1)
        Set<Character> charSet = new TreeSet<>();
        for (int i = 0; i < lineChars.length(); ++i)
        {
            charSet.add(lineChars.charAt(i));
        }
        return charSet;
    }

    public int turningAngle() {
        // TODO: à compléter (exercice 1)
        return turningAngle;
    }

    public LSystem evolve() {
        String newString = "";
        for (int i = 0; i < string.length(); ++i)
        {
            
            newString += (rules.getOrDefault(string.charAt(i), String.valueOf(string.charAt(i))));
            
            
        }
        return new LSystem(newString, rules, lineChars, turningAngle);
    }

    public LSystem evolve(int steps) {
        LSystem thisLSystem = this;
        for (int i = 0; i < steps; i ++)
        {
            thisLSystem = thisLSystem.evolve();
        }
        return thisLSystem;
    }
}
