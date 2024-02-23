class solution{
    node solution_node;
    int sum_value;
    public solution(node sol_node,int sumv) {
        solution_node = sol_node;
        sum_value = sumv;
    }
    public void afficher() {
        System.out.println("The Solution is : ");
        solution_node.afficher();
        
    }
}