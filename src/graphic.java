import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.TitledBorder;

public class graphic extends JFrame {
    int num_knap = 0;
    int num_obj = 0;
    final private Font mainfont = new Font("Serif", Font.BOLD, 18);
    JTextField tfknap, tfobj;
    JScrollPane imagePanel1, imagePanel2, imagePanel3;
    JPanel AlgosPanel; // Declare AlgosPanel as a class member

    public void initialize() {
        /* Labels */
        JLabel knapsack_number = new JLabel("Enter the number of knapsack\n");
        knapsack_number.setFont(mainfont);
        JLabel objects_number = new JLabel("Enter the number of objects\n");
        objects_number.setFont(mainfont);

        /* TextFields */
        tfknap = new JTextField();
        tfknap.setPreferredSize(new Dimension(0, 50));
        tfobj = new JTextField();
        tfobj.setPreferredSize(new Dimension(0, 50));

        /* Submitting Button */
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(50, 150, 100, 30);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    long startTime = System.nanoTime();
                    long endTime = System.nanoTime();

                    Instance cas_0 = new Instance(num_knap, num_obj);
                    // Affichage données de l'instance en cours
                    System.out.println("\n\n======Affichage instance du probleme======\n\n");
                    cas_0.afficher_Instance();

                    // modélisation de l'instance généré:
                    cas_0.Generate_model();

                    // Affichage modéle de l'instance
                    System.out.println("\n\n======Affichage modéle======\n\n");
                    cas_0.Afficher_model();

                    // Solution à la instance courante
                    System.out.println("\n\n======Solution BFS======\n\n");
                    startTime = System.nanoTime();
                    node bfs_sol;
                    bfs_sol=cas_0.BFS_solve();
                    endTime = System.nanoTime();
                    double BFSTime = (double) (endTime - startTime) /1000000;

                    System.out.println("\n\n======Solution DFS======\n\n");
                    startTime = System.nanoTime();
                    node dfs_sol;
                    dfs_sol=cas_0.DFS_solve();
                    endTime = System.nanoTime();
                    double DFSTime = (double) (endTime - startTime) /1000000;

                    // implementation d'algorithme A*
                    startTime = System.nanoTime();
                    node Astar_sols = cas_0.AstarAlgorithme();
                    endTime = System.nanoTime();
                    double AstarTime = (double) (endTime - startTime) /1000000;
                    // Update image panels
                    updateImagePanels(dfs_sol, bfs_sol , Astar_sols,  DFSTime,BFSTime,AstarTime);

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid number entered. Please enter a valid integer.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /* FORM panel */
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 4, 5, 5));
        formPanel.add(knapsack_number);
        formPanel.add(tfknap);
        formPanel.add(objects_number);
        formPanel.add(tfobj);
        formPanel.add(submitButton);

        /* Display panel */
        imagePanel1 = createImagePanel("DFS", null,0);
        imagePanel2 = createImagePanel("BFS", null,0);
        imagePanel3 = createImagePanel("A*", null,0);

        AlgosPanel = new JPanel(); // Initialize AlgosPanel
        AlgosPanel.setLayout(new GridLayout(1, 3));
        AlgosPanel.add(imagePanel1);
        AlgosPanel.add(imagePanel2);
        AlgosPanel.add(imagePanel3);
        /* Main panel */
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        mainpanel.setBackground(new Color(128, 128, 255));
        mainpanel.add(formPanel, BorderLayout.NORTH);
        mainpanel.add(AlgosPanel);
        setTitle("Multiple knapsack problem solver");
        setSize(500, 200);
        setMinimumSize(new Dimension(300, 200));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(mainpanel);
        setVisible(true);
    }

    private boolean validateInput() {
        try {
            num_knap = Integer.parseInt(tfknap.getText());
            num_obj = Integer.parseInt(tfobj.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private void updateImagePanels(node dfs_sol, node bfs_sol, node astar_sol, double DFSTime, double BFSTime, double AstarTime) {

        // Remove existing image panels
        imagePanel1.removeAll();
        imagePanel2.removeAll();
        imagePanel3.removeAll();
    
        // Create new image panels
        imagePanel1 = createImagePanel("DFS", dfs_sol, DFSTime);
        imagePanel2 = createImagePanel("BFS", bfs_sol, BFSTime);
        imagePanel3 = createImagePanel("A*", astar_sol, AstarTime);
    
        // Add the new image panels to the AlgosPanel
        AlgosPanel.removeAll();
        AlgosPanel.setLayout(new GridLayout(1, 3));
        AlgosPanel.add(imagePanel1);
        AlgosPanel.add(imagePanel2);
        AlgosPanel.add(imagePanel3);
    
        // Repaint the frame to update the changes
        revalidate();
        repaint();
    }
    
    private JScrollPane createImagePanel(String name, node sol, double executionTime) {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(num_knap, 2));
    
        // Create TitledBorder with the specified name
        TitledBorder titledBorder = BorderFactory.createTitledBorder(name);
        titledBorder.setTitleFont(mainfont);
        imagePanel.setBorder(titledBorder);
    
        for (int i = 0; i < num_knap; i++) {
            String str = "";
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(
                    "C:\\Users\\abdo7\\OneDrive\\Bureau\\Meta Project\\MKP-problem-solution-using-java-\\src\\image.png")
                    .getImage()
                    .getScaledInstance(100, 100, Image.SCALE_DEFAULT));
            JLabel imageLabel = new JLabel(imageIcon);
            JLabel textLabel = new JLabel("Knapsack number: " + (i + 1));
            JLabel content;
            if (sol != null) {
                for (int j = 0; j < sol.objects.length; j++) {
                    if (sol.objects[j] == i + 1) {
                        str += (j + 1) + "  ";
                    }
                }
                content = new JLabel(str);
            } else {
                content = new JLabel("");
            }
            imagePanel.add(imageLabel);
            imagePanel.add(textLabel);
            imagePanel.add(content);
        }
    
        // Execution time label
        JLabel timeLabel = new JLabel("Execution Time: " + executionTime + " milliseconds");
    
        // Create a panel to hold the imagePanel and timeLabel
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(imagePanel, BorderLayout.CENTER);
        panel.add(timeLabel, BorderLayout.SOUTH);
    
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
        return scrollPane;
    }
}