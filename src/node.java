class node{
    int[] objects;// an array where each object(case number represent the number of object) contains the number of knapsack belong to
    int taille;
    int number;
    public node(int[] objs , int taille_node,int num) {
        taille = taille_node;
        objects = new int[taille];
        number=num;
        for(int i=0;i<taille;i++) {
            objects[i]=objs[i];
        }
    }
    public node(int taille_node,int num) {
        taille = taille_node;
        objects = new int[taille];
        number=num;
        for(int i=0;i<taille;i++) { //(knpsack^(nb_objects+1)/(knpsack-1)
            objects[i]=0;
        }
    }
    void afficher() {
        for(int i=0;i<taille;i++) {
            System.out.print(objects[i]);
        }
    }
    void set_objects(int position , int value) {
        objects[position]=value;
    }
}
