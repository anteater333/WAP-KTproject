public class TreeNode {

	excelData data;
    TreeNode left;
    TreeNode right;

    public TreeNode(){
    	this.data=new excelData();
        this.left = null;
        this.right = null;
    }

    public TreeNode(excelData data){
        this.data = data;
        this.left = null;
        this.right = null;
    }
    
    public Object getData(){
        return data;
    }
    
}