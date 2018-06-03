package ch.epfl.cs108;

import static javax.swing.SwingUtilities.invokeLater;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.geom.Path2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

public final class Main {
    private static LSystem koch() 
    {
        Map<Character, String> rules = new HashMap<>();
        rules.put('F', "F-F++F-F");
        return new LSystem("F++F++F++F", rules, "F", 60);
    }
    private static LSystem ierpinski()
    {
        Map<Character, String> rules = new HashMap<>();
        rules.put('A', "+B-A-B+");
        rules.put('B', "-A+B+A-");
        return new LSystem("A", rules, "AB", 60);
    }

    private static LSystem hilbert()
    {
        Map<Character, String> rules = new HashMap<>();
        rules.put('A', "-BF+AFA+FB-");
        rules.put('B', "+AF-BFB-FA+");
        return new LSystem("A", rules, "F", 90);
    }
    private static LSystem plant()
    {
        Map<Character, String> rules = new HashMap<>();
        rules.put('X', "F-[[X]+X]+F[+FX]-X");
        rules.put('F', "FF");
        
        return new LSystem("---X", rules, "F", 30);
    }
    
    private static LSystem tree()
    {
        Map<Character, String> rules = new HashMap<>();
        rules.put('F', "FF+[+F-F-F]-[-F+F+F]");
        
        return new LSystem("----F", rules, "F", 20);
    }
    public static void main(String[] args) {
        // Le L-système à dessiner
        LSystem lSystem = plant().evolve(5);
        

        invokeLater(() -> {
            Path2D lSystemPath = LSystemPainter.paint(lSystem);

            JFrame mainWindow = new JFrame("L-Système");
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            mainWindow.getContentPane().setLayout(new BorderLayout());
            PathComponent pathComponent = new PathComponent();
            pathComponent.setPreferredSize(new Dimension(400, 400));
            pathComponent.setPath(lSystemPath);
            mainWindow.getContentPane().add(pathComponent, BorderLayout.CENTER);

            mainWindow.pack();
            mainWindow.setVisible(true);
        });
    }
}
