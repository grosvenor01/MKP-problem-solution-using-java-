import java.util.ArrayList;
class node{
    int[] objects;// an array where each object(case number represent the number of object) contains the number of knapsack belong to
    int taille;
    int number;
    int heuristic;
    int cost=0;
    int value=0;
    boolean state = true;
    public String tostirng(){
        String str="";
        for(int i=0;i<taille ; i++){
            str+=objects[i]+"  ";
        }
        return str;
    }
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
    void set_objects(int position , int value , node precedent_node , int weight , ArrayList<obj> obj_arr) {
        objects[position]=value;
        this.heuristic = precedent_node.heuristic-weight;
        if(weight==0){
            this.cost= precedent_node.cost + 1000; //if the knapsack is not inside the cost is 1000
        }
        else{
          this.cost= precedent_node.cost + weight;  
        }
        for(int i=0;i<=position;i++){
            if(objects[i]!=-1){
                this.value += obj_arr.get(objects[i]-1).value;
            }
            
        }
        
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
        ArrayList<Integer> knap_arr = new ArrayList<>();
        for(int j=0 ; j<knapsack_arr.size();j++){
            knap_arr.add(knapsack_arr.get(j).max_weight);
        }
        for(int i=0 ; i<taille ; i++){ 
            if(this.objects[i]==-1){
                counter ++;
            }
            else{
                knap_arr.set(this.objects[i]-1, knap_arr.get(objects[i]-1)-obj_arr.get(i).weight);
            }
        }
        for(int i=0 ; i<knap_arr.size() ; i++){ // if there is an object in a bad knap (capacity < all objects)
            if(knap_arr.get(i)<0){
                return -1;
            }
        }
        return counter;
    }
}
