public class BinarySearchTree {

    private TreeNode root = null;
    
    public TreeNode insertKey(TreeNode root, excelData x) {
        TreeNode p = root;
        TreeNode newNode = new TreeNode(x);
        
        if(p==null){
            return newNode;
        }else if(p.data.getRuID().compareTo(newNode.data.getRuID())>0){
            p.left = insertKey(p.left, x);
            return p;
        }else{
            p.right = insertKey(p.right, x);
            return p;
        }
    }

    public void insertBST(excelData x){
        root = insertKey(root, x);
    }
    public TreeNode GetRoot(){
        return this.root;
    }
}