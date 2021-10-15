package socialmedia;
import java.io.Serializable;
public class User implements Serializable {
    /**
     * Stores all information regarding each User object
     * All Users posts are accessed and stored in each users object class
     */
    private int ID;//unique is of the user
    private int PostCount  = 0;
    private int EndorseCount = 0;//increased as someone endorses that user's post
    //private String Description; not use in mini social media implementation
    private String UserName;//also known as the handle
    public Post[] Posts = new Post[1000];//includes posts ans comment posts(made public for now)
    public int nextPost = 0;

    public User(int ID,String UserName){//constructor method
        this.ID = ID;
        this.UserName = UserName;
    }
    public void addPost(String message,int ID,String PostType,int Reference,int endorsedID,String endorsedHandle,String end_comment){//adds post and the post is never removed but its type set as :"deleted"
        Posts[nextPost] = new Post(ID,Reference,message,PostType,endorsedID,endorsedHandle,end_comment);
        nextPost++;
    }
    public void addEndorsementCount(){
        EndorseCount++;
    }

    public void changeUserName(String newUserName){
        UserName = newUserName;
    }

    public int getID(){
        return this.ID;
    }

    public String getUserName(){
        return this.UserName;
    }

    public String getDescription(){
        return "";
    }

    public int getPostCount(){
        return this.PostCount;
    }

    public void UpdatePostCount(){//searches the array of posts to change the number of posts if a post has been added or removed
        int x = 0;
        int tempCount =0;
        while(x < nextPost){
            if(!Posts[x].getPostType().equals("deleted")){
                tempCount++;
            }
            x++;
        }
        PostCount = tempCount;
    }

    public int getEndorseCount(){
        return this.EndorseCount;
    }

    public boolean checkPostID(int ID){//check if a post with that id exists in the system
        int pointer = 0;
        boolean found = false;
        while(Posts[pointer] !=null){
            if(Posts[pointer].getID()==ID){
                found = true;
                break;
            }
            pointer++;
        }
        return found;
    }

}
