package ch.epfl.cs108;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public final class Main {
    private static IFSComponent ifsComponent;

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JFrame window = new JFrame("IFS");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ifsComponent = new IFSComponent();
            window.getContentPane().add(ifsComponent);

            window.pack();
            window.setVisible(true);
        });

        IFS sierpinskiTriangle = new IFS.Builder()
                .setMinX(-0.1)
                .setMinY(-0.1)
                .setWidth(1.2)
                .addFunction(p -> p.scaled(0.5))
                .addFunction(p -> p.scaled(0.5).translated(0.5, 0))
                .addFunction(p -> p.scaled(0.5).translated(0.25, 0.5))
                .build();

        IFS sierpinskiCarpet = new IFS.Builder()
                .setMinX(-0.1)
                .setMinY(-0.1)
                .setWidth(1.2)
                .addFunction(p -> p.scaled(1d/3d))
                .addFunction(p -> p.scaled(1d/3d).translated(0, 1d/3d))
                .addFunction(p -> p.scaled(1d/3d).translated(0, 2d/3d))
                .addFunction(p -> p.scaled(1d/3d).translated(1d/3d, 0))
                .addFunction(p -> p.scaled(1d/3d).translated(1d/3d, 2d/3d))
                .addFunction(p -> p.scaled(1d/3d).translated(2d/3d, 0))
                .addFunction(p -> p.scaled(1d/3d).translated(2d/3d, 1d/3d))
                .addFunction(p -> p.scaled(1d/3d).translated(2d/3d, 2d/3d))
                .build();

        ifsComponent.setIFS(sierpinskiTriangle);
    }
}
