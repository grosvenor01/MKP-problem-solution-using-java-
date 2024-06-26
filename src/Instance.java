//Salam...
import java.util.Scanner;
import java.util.Stack;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

public class Instance {

	/* Attributes */

	ArrayList<knapsack> knapsack_arr;
	ArrayList<obj> obj_arr;
	int objects_num;
	int knapsack_num;
	int total_nodes;
	ArrayList<node> nodes_arr;
	int[][] matrix_pb;
	int knapsack_sum=0;
	/* PARTIE I */
	int[] knapsackCapacities;
	public Instance(int knap_num , int obj_num) {

		int max_weightk = 200;// use it to generate a max weight for all knapsacks
		int min_weightk = 20;// use it to generate a min weight for all knapsacks
		int max_weighto = 150;// use it to generate a max weight for all objects
		int min_weighto = 20;// use it to generate a min weight for the objects
		int max_valo = 100;// use it to generate a max value for the objects
		int min_valo = 1;// use it to generate a min value for all objects
		knapsack new_knap;
		obj new_obj;
		Scanner scanner = new Scanner(System.in);
		Random random = new Random();

		/*
		 * Tsema bon hna wedjed le min max poids t3 krateb ou objects, ou les valeurs t3
		 * objects
		 * DEclara variables new_knap ha tkon sac à dos intermédiaire
		 * kif kif DEclara variables new_obj ha tkon obj intermédiaire
		 * wedjed scanner tA3o bch yebda ydekhel yrecevi user inputs
		 * wedjed tani deux arraylists knapsack ou obj yhet fihom kaml les objets t3na
		 * ou les knapsacks
		 */
		knapsack_arr = new ArrayList<knapsack>(); // array of knapsakcs
		obj_arr = new ArrayList<obj>(); // array of objects
		knapsack_num = knap_num;
		knapsackCapacities=new int[knapsack_num];
		for (int i = 0; i < knapsack_num; i++) {
			int max = random.nextInt(max_weightk - 10 + 1) + min_weightk;
			knapsackCapacities[i]=max;
			new_knap = new knapsack(max, i + 1);
			knapsack_arr.add(new_knap);
			knapsack_sum +=max;
		}

		/*
		 * 9alek hna abdou rah demand l user nombre de knapsacks,
		 * ste3mel intermediaire t3na new_knap bch yecryi sac jdid ou ymontih f
		 * arraylist t3 les sacs
		 * darha plusieurs fois 3la hsab nombre de knapsacks
		 */
		objects_num = obj_num;
		for (int i = 0; i < objects_num; i++) {
			int max = random.nextInt(max_weighto - 10 + 1) + min_weighto;
			int max_val = random.nextInt(max_valo - 10 + 1) + min_valo;
			new_obj = new obj(max, max_val, i + 1);
			obj_arr.add(new_obj);
		}
		scanner.close();
		System.out.println("Instance created successfully!");
		/*
		 * 9alek hna khona abdou dar la meme chose kima m3a les sacs brk m3a les objects
		 * ha lmerra
		 * brk les objets 3ndhom both poid et valeurs
		 */
	}

	public void afficher_Instance() {
		System.out.println("=====knapsacks=====");
		for (int i = 0; i < knapsack_num; i++) {
			System.out.print("The ");
			System.out.print(knapsack_arr.get(i).number);
			System.out.print(" knapsack have the max weight of ");
			System.out.println(knapsack_arr.get(i).max_weight);
		}
		/* Affichage t3 les knapsck by accessing to thier properties using getters */
		System.out.println("=====objects=====");
		for (int i = 0; i < objects_num; i++) {
			System.out.print("The ");
			System.out.print(obj_arr.get(i).number);
			System.out.print(" object have the weight of ");
			System.out.print(obj_arr.get(i).weight);
			System.out.print(" and a value of ");
			System.out.println(obj_arr.get(i).value);
		}

		/* Affichage t3 les objects by accessing to thier properties using getters */
	}

	/* PARTIE II */

	// generating a matrix of the problem
	public void Generate_model() {
		total_nodes = (int) ((Math.pow(knapsack_num + 1, objects_num + 1) - 1) / (knapsack_num));
		System.out.println("Nombre total de noeuds " + total_nodes);

		// generating all nodes
		nodes_arr = new ArrayList<node>();
		node my_node = new node(objects_num, 0);
		my_node.set_heuritic(knapsack_sum);
		nodes_arr.add(my_node);
		int c = 0, position = 0;
		int d = 1;
		int counter = knapsack_num + 1, counter2 = 0; // counter = 3 , counter2=0
		while (true) {
			for (int i = 0; i < knapsack_num + 1; i++) {
				my_node = new node(nodes_arr.get(c).objects, objects_num, d);
				if (i == knapsack_num) {
					my_node.set_objects(position, -1, nodes_arr.get(c),0);
				} else {
					my_node.set_objects(position, i + 1, nodes_arr.get(c),obj_arr.get(position).weight);
				}
				nodes_arr.add(my_node);
				d++;
				counter2++; // counter2=3
			}
			if (counter == counter2) {
				position++;
				counter = counter * (knapsack_num + 1); // counter =9
				counter2 = 0; // counter2=0
			}
			c++; // c=1
			if (d >= total_nodes) { // d=4
				break;
			}
		}

		/*
		 * Had la partie de code Ha9 Allah mani fahem kifah rahi temchi bsah mohim tmed
		 * deux choses
		 * lewla kaml les nodes t3 la matrice dakhel arraylist wesemha nodes_arr
		 * avantage, kol node rah associated m3a nombre entier positif
		 * So haka nmanipuliw matrice d'adjacence ghir b les indexes t3ha, ou ki nhab
		 * naccidi l node
		 * nesta3mel index ta3o bch nel9ah f arraylist node_arr.
		 * 3la nabi slat ness (salla allah 3aleyh wa sallem)
		 */

		// generate adjacency matrix
		matrix_pb = new int[total_nodes][total_nodes];
		for (int i = 0; i < total_nodes; i++) {
			for (int j = 0; j < total_nodes; j++) {
				matrix_pb[i][j] = 0;
			}
		}
		int base = 1;
		for (int i = 0; i < total_nodes; i++) {
			for (int j = i + base; j < i + +base + knapsack_num + 1 && j < total_nodes; j++) {
				matrix_pb[i][j] = 1;
			}
			base = base + knapsack_num;
		}
		System.out.println("model ganerated successfully!");
	}

	public void Afficher_model() {
		// Affichage noeuds
		/*System.out.println("======nodes======");
		System.out.println();
		for (int i = 0; i < total_nodes; i++) {
			nodes_arr.get(i).afficher();
			System.out.print("heuristic value --> "+nodes_arr.get(i).heuristic+"    cost  --> "+nodes_arr.get(i).cost);
			System.out.println();
		}*/

		// Affichage matrice
		/*System.out.println("======matrix======");
		for (int i = 0; i < total_nodes; i++) {
			for (int j = 0; j < total_nodes; j++) {
				System.out.print(matrix_pb[i][j]);
			}
			System.out.println();
		}*/

		/*
		 * In sum na3erfou mn lewel pattern T3 graphe t3na kifah ydji et on l'a traduit
		 * en algorithme bch
		 * y3emerhon fl matrice qlq soit nombre d'objets et de knapsack (cas général)
		 */
	}

	public void BFS_solve() {
		// BFS BASED ON THE ADJACENCY MATRIX
		int[] sac = new int[knapsack_arr.size()];		
		LinkedList<Integer> file = new LinkedList<Integer>();
		file.offer(0);

		// 2H 3ladjal hadi!
		int cpt = 0;
		int leaf = 0,check=0;
		ArrayList<node> sol_arr = new ArrayList<node>();
		ArrayList<Integer> sol_val = new ArrayList<Integer>();
		int i, bestval = 0, totalval = 0, best_config = -1;
		node config;
		while (!file.isEmpty()) {
			cpt = 0;
			totalval=0;
			for (int s = 0; s < knapsack_arr.size(); s++) {
				sac[s] = knapsack_arr.get(s).max_weight;
			}
			i = file.poll();

			// parcours ligne matrice et ajoute le noeuds fils à la file, on les compte dans cpt
			for (int j = 0; j < total_nodes; j++) {
				if (matrix_pb[i][j] == 1) {
					cpt++;
					file.offer(j);
				}

			}

			//ICI on verifie cpt, donc le nombre de noeuds fils ajoutés à la file, si 0 donc notre noeud est une feuille
			if (cpt == 0) {
				// ce noued i est une feuille, je le récupere de node_arr à indice i
				leaf ++;
				config = nodes_arr.get(i);
				for (int k = 0; k < config.taille; k++) {
					/* in case the object is taken */
					if (config.objects[k] != -1) {
						/* nehou le poids t3 l'objet li dinah ml poid li yehemlou sac à dos */
						sac[config.objects[k] - 1] = sac[config.objects[k] - 1] - obj_arr.get(k).weight;
						/* ida sac yehmel, nzidou valeur t3 l'objet l valeur total */
						if (sac[config.objects[k] - 1] >= 0) {
							totalval = totalval + obj_arr.get(k).value;
							/* ida mayahmelch nehi kaml had l config ou redje3 la valeur t3ha à 0 */
						} else {
							totalval = -1;
							check++;
							break;
						}
					}

				}

				// Doka na3arfou randement t3 had la combin, ida khir min li 9belha
				// nsauvgardiwha
				// hia ou valeur t3ha

				if (totalval != -1) {
					sol_arr.add(nodes_arr.get(i));
					sol_val.add(totalval);
					if (bestval < totalval ) {
						bestval = totalval;
						best_config = i;
					}
				}
			}/* end of leaf treatement */
	} /* end of while */

		 

		//Affichage solutions possibles
		System.out.println( "\n\n=======Solutions possibles : \n" );		
		System.out.println("nombre de solution possibles : " + sol_arr.size() );
		System.out.println("nombre de combinaisons impossibles : " + check );
		System.out.println("nombre de feuilles : " + leaf );
		if (sol_arr.size() == 0){
			System.out.println("Aucune solution possible");
		}else{
			/* System.out.println("Un essai rapide:");
			sol_arr.get(0).afficher();			
			sol_arr.get(1).afficher();
			sol_arr.get(2).afficher(); */
			for(int k=0 ; k< sol_arr.size() ; k++ ){
				System.out.print(k + " : ");
				sol_arr.get(k).afficher();
				System.out.println(" / " + sol_val.get(k));
			}
			
		}
		System.out.println( "\n\n=======Solution optimale :\n" );
		if (best_config == -1) {
			System.out.println("Aucune configuration correcte !");
		} else {
			System.out.println("La configuration la plus optimal est la suivante :");
			nodes_arr.get(best_config).afficher();
			System.out.println(" avec une valeur de " + bestval + " Et best_config:" + best_config + "\n");

		}
	}

	// DFS personnalisé kima BFS

	public void DFS_solve() {
		// BFS BASED ON THE ADJACENCY MATRIX
		int[] sac = new int[knapsack_arr.size()];		
		LinkedList<Integer> pile = new LinkedList<Integer>();
		pile.offer(0);

		// 2H 3ladjal hadi!
		int cpt = 0;
		int leaf = 0,check=0;
		ArrayList<node> sol_arr = new ArrayList<node>();
		ArrayList<Integer> sol_val = new ArrayList<Integer>();
		int i, bestval = 0, totalval = 0, best_config = -1;
		node config;
		while (!pile.isEmpty()) {
			cpt = 0;
			totalval=0;
			for (int s = 0; s < knapsack_arr.size(); s++) {
				sac[s] = knapsack_arr.get(s).max_weight;
			}
			i = pile.removeLast();

			// parcours ligne matrice et ajoute le noeuds fils à la file, on les compte dans cpt
			for (int j = 0; j < total_nodes; j++) {
				if (matrix_pb[i][j] == 1) {
					cpt++;
					pile.offer(j);
				}

			}

			//ICI on verifie cpt, donc le nombre de noeuds fils ajoutés à la file, si 0 donc notre noeud est une feuille
			if (cpt == 0) {
				// ce noued i est une feuille, je le récupere de node_arr à indice i
				leaf ++;
				config = nodes_arr.get(i);
				for (int k = 0; k < config.taille; k++) {
					/* in case the object is taken */
					if (config.objects[k] != -1) {
						/* nehou le poids t3 l'objet li dinah ml poid li yehemlou sac à dos */
						sac[config.objects[k] - 1] = sac[config.objects[k] - 1] - obj_arr.get(k).weight;
						/* ida sac yehmel, nzidou valeur t3 l'objet l valeur total */
						if (sac[config.objects[k] - 1] >= 0) {
							totalval = totalval + obj_arr.get(k).value;
							/* ida mayahmelch nehi kaml had l config ou redje3 la valeur t3ha à 0 */
						} else {
							totalval = -1;
							check++;
							break;
						}
					}

				}

				// Doka na3arfou randement t3 had la combin, ida khir min li 9belha
				// nsauvgardiwha
				// hia ou valeur t3ha

				if (totalval != -1) {
					sol_arr.add(nodes_arr.get(i));
					sol_val.add(totalval);
					if (bestval < totalval ) {
						bestval = totalval;
						best_config = i;
					}
				}
			}/* end of leaf treatement */
	} /* end of while */

		 

		//Affichage solutions possibles
		System.out.println( "\n\n=======Solutions possibles : \n" );		
		System.out.println("nombre de solution possibles : " + sol_arr.size() );
		System.out.println("nombre de combinaisons impossibles : " + check );
		System.out.println("nombre de feuilles : " + leaf );
		if (sol_arr.size() == 0){
			System.out.println("Aucune solution possible");
		}else{
			/* System.out.println("Un essai rapide:");
			sol_arr.get(0).afficher();			
			sol_arr.get(1).afficher();
			sol_arr.get(2).afficher(); */
			for(int k=0 ; k< sol_arr.size() ; k++ ){
				System.out.print(k + " : ");
				sol_arr.get(k).afficher();
				System.out.println(" / " + sol_val.get(k));
			}
			
		}
		System.out.println( "\n\n=======Solution optimale :\n" );
		if (best_config == -1) {
			System.out.println("Aucune configuration correcte !");
		} else {
			System.out.println("La configuration la plus optimal est la suivante :");
			nodes_arr.get(best_config).afficher();
			System.out.println(" avec une valeur de " + bestval + " Et best_config:" + best_config + "\n");

		}
	}


    public void best_solution(int index, ArrayList<node> nodes_arr, solution current_solution, ArrayList<obj> obj_arr, ArrayList<knapsack> knapsack_arr, ArrayList<solution> sols) {
	    // remove the case of -1 appearing in the objects array
	    node sol = nodes_arr.get(index);
	    int sumv = 0;
	    int sumw = 0;
	    ArrayList<knapsack> testing_arr = new ArrayList<>();

	    // Create a deep copy of knapsack_arr
	    for (knapsack knap : knapsack_arr) {
	        testing_arr.add(new knapsack(knap.max_weight, knap.number));
	    }

	    for (int i = 0; i < sol.taille; i++) {
	        if (sol.objects[i] == -1) {
	            // remove this case where an object is not in knapsacks
	            continue;
	        } else {
	            knapsack knap = testing_arr.get(sol.objects[i] - 1);
	            knap.max_weight = knap.max_weight - obj_arr.get(i).weight;
	            testing_arr.set(sol.objects[i] - 1, knap);
	            sumv = sumv + obj_arr.get(i).value;
	            sumw = sumw + obj_arr.get(i).weight;
	        }
	    }

	    boolean status = true;
	    for (int i = 0; i < testing_arr.size(); i++) {
	        if (testing_arr.get(i).max_weight < 0) {
	            status = false;
	        }
	    }

		// afficher tous les solution DFS
	    /*if (status) {
	        nodes_arr.get(index).afficher();
	        System.out.print("  ");
	    }*/
	    if (status && current_solution.sum_value < sumv) {
	        sols.clear();
	        sols.add(new solution(sol, sumv));
	    }
	}

    public void dfsUtil(int start, int[][] adjacencyMatrix, boolean[] visited, Stack<Integer> stack,ArrayList<node> nodes_arr,ArrayList<obj> obj_arr,solution current_sol,ArrayList<knapsack> knapsack_arr,ArrayList<solution> sols) {
        stack.push(start);
        visited[start] = true;

        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();
            boolean isLeaf = true; // Flag to check if the current vertex is a leaf node

            for (int i = 0; i < adjacencyMatrix.length; i++) {
                if (adjacencyMatrix[currentVertex][i] == 1 && !visited[i]) {
                    stack.push(i);
                    visited[i] = true;
                    isLeaf = false; // If there is an unvisited neighbor, current vertex is not a leaf
                }
            }
            
            // If the current vertex is a leaf node, print its number
            if (isLeaf) {
            	best_solution(currentVertex,nodes_arr,current_sol,obj_arr,knapsack_arr,sols);
            }
        }
    }
    
    public void dfs(solution current_sol,ArrayList<solution> sols) {
        int vertices = matrix_pb.length;
        boolean[] visited = new boolean[vertices];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                dfsUtil(i, matrix_pb, visited, stack,nodes_arr,obj_arr,current_sol,knapsack_arr,sols);
            }
        }
    }
	public node AstarAlgorithme(){
		ArrayList<node> overt=new ArrayList<node>();
		ArrayList<node> ferme= new ArrayList<node>();
		ArrayList<node> Solutions = new ArrayList<node>();
		for(int j=0;j<total_nodes;j++){ // add the child nodes to the overt list
			if(matrix_pb[0][j]!=0){
				overt.add(nodes_arr.get(j));
			}
		}
		// structuring the overt list smmaller to bigger
		int counter =0;
		while(!overt.isEmpty()){
			Collections.sort(overt, Comparator.comparingInt(node -> node.heuristic + node.cost)); //sorting the list ascndenly 
            node first = overt.get(0);
			for(int i=0;i<total_nodes;i++){
				if(matrix_pb[first.number][i]!=0){
					overt.add(nodes_arr.get(i));
					counter++;
				}
			}
			if(counter ==0){ //if we have no child = the node is a child
				if(Solutions.size()==0){
					if(first.is_solution(knapsack_arr, obj_arr)!=-1)
					Solutions.add(first);
				}
				else {
					if(first.cost+first.heuristic>Solutions.get(0).cost+Solutions.get(0).heuristic ){
						if( first.is_solution(knapsack_arr , obj_arr) !=-1 && first.is_solution(knapsack_arr , obj_arr) < Solutions.get(0).is_solution(knapsack_arr , obj_arr)){ //less number of objects are outside the knapsacks
							Solutions.clear();
						    Solutions.add(first);
							
						}
						
					}
					else if( first.is_solution(knapsack_arr , obj_arr) !=-1 && first.cost+first.heuristic==Solutions.get(0).cost+Solutions.get(0).heuristic && first.is_solution(knapsack_arr , obj_arr)==1){
						if(first.is_solution(knapsack_arr , obj_arr) < Solutions.get(0).is_solution(knapsack_arr , obj_arr)){
						    Solutions.add(first);
						}
					}
					else if(first.is_solution(knapsack_arr, obj_arr)!=-1){ // case ou ce fils est optimal = est plus optimal que tous les autre donc on sort
						overt.clear();
					}
				}
			}
			counter =0;
			ferme.add(first);
			overt.remove(first);
		}
		return Solutions.get(0);
		// najoutiw les cas ta3 fils ta3ha f fermé w n3wdou nrtbou hata nwsslou l cas ma3ndouch des fils ncoumpariwah m3a ga3 lokhrin ida kan sghir hadak houwa solution 
		// show th Solution 
	}
}/* end of app class */
