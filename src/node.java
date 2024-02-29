import java.util.ArrayList;
class node{
    int[] objects;// an array where each object(case number represent the number of object) contains the number of knapsack belong to
    int taille;
    int number;
    int heuristic;
    public node(int[] objs , int taille_node,int num,int knapsack_sum , ArrayList<obj> obj_arr) {
        taille = taille_node;
        objects = new int[taille];
        number=num;
        for(int i=0;i<taille;i++) {
            objects[i]=objs[i];
            if(objs[i]!=0){
                knapsack_sum -= obj_arr.get(i).weight;
            }
        }
        heuristic = knapsack_sum;
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
    void set_objects(int position , int value) {
        objects[position]=value;
    }
    int index_last(){
        for(int i=0;i<taille ; i++){
            if(objects[i]==0){
                return i-1; // return the last modified case in the array
            }
        }
        return taille-1; //cas full
    }
}
