import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // Create a new instance of the problem
        Instance cas_0 = new Instance();

    
        //Affichage données de l'instance en cours
        System.out.println("\n\n======Affichage instance du probleme======\n\n");
        cas_0.afficher_Instance();
    
        //modélisation de l'instance généré:
        cas_0.Generate_model();
        
        //Affichage modéle de l'instance
         System.out.println("\n\n======Affichage modéle======\n\n");
        cas_0.Afficher_model(); 
        
        
        //Solution à la instance courante
        System.out.println("\n\n======Solution BFS======\n\n");
        cas_0.BFS_solve();


        System.out.println("\n\n======Solution DFS======\n\n");

        solution sol = new solution(cas_0.nodes_arr.get(0), 0);
        ArrayList<solution> solutions = new ArrayList<solution>();
        cas_0.dfs(sol,solutions);
        if(solutions.size()>0) {
            solutions.get(0).afficher();
        }else {
             System.out.println("No Solution Found");
        }

        //implementation d'algorithme A* 
        //1- tronsformer la matrice 
        System.out.println("\n");
        cas_0.matrix_tronsformation();
        //2- ajouter le cout d'heuristic a chaque noued 
    }
}