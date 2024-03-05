import java.util.ArrayList;
class node{
    int[] objects;// an array where each object(case number represent the number of object) contains the number of knapsack belong to
    int taille;
    int number;
    int heuristic;
    int cost=0;
    boolean state = true;
    public node(int[] objs , int taille_node,int num) {
        taille = taille_node;
        objects = new int[taille];
        number=num;
        for (int i = 0; i <= taille; i++) {
            if (i < objs.length) { // Check array bounds
                objects[i] = objs[i];
            }
        }
    }
    public node(int taille_node,int num) {
        taille = taille_node;
        objects = new int[taille];
        number=num;
        for(int i=0;i<taille;i++) { //(knpsack^(nb_objects+1)/(knpsack-1)
            objects[i]=0;
        }
        heuristic=0;
    }
    void afficher() {
        for(int i=0;i<taille;i++) {
            System.out.print(objects[i]);
        }
    }
    void set_objects(int position , int value , node precedent_node , int weight) {
        objects[position]=value;
        this.heuristic = precedent_node.heuristic-weight;
        this.cost= precedent_node.cost + weight;
    }
    int index_last(){
        for(int i=0;i<taille ; i++){
            if(objects[i]==0){
                return i-1; // return the last modified case in the array
            }
        }
        return taille-1; //cas full
    }
    int getTotalCost() {
        return heuristic;
    }
    void set_heuritic(int value){
        this.heuristic = value;
    }
    int is_solution(ArrayList<knapsack> knapsack_arr , ArrayList<obj> obj_arr ){
        int counter =0;
        ArrayList<knapsack> knap_arr = new ArrayList<>(knapsack_arr);
        for(int i=0 ; i<taille ; i++){
            if(this.objects[i]==-1){
                counter ++;
            }
            else{
                knapsack knap = knap_arr.get(objects[i]-1);
                knap.max_weight -= obj_arr.get(i).weight;
                knap_arr.set(objects[i]-1 , knap);
                if(knap_arr.get(objects[i]-1).max_weight<0){
                    return -1;
                }
            }
        }
        return counter;
    }
}
