package socialmedia;
import java.io.Serializable;
public class Post implements Serializable {
    /**
     *
     *
     */
    private int ID; //unique post id
    private String PostType;//can be either : {comment,endorsement,post,deleted}
    private String Comment;
    private int CommentCount;
    private int ParentPostReference = 0 ;//id if the post/comment this is commenting (0 if its not a comment post)
    private int endorsement = 0;

    //data for endorsed posts:
    private int endorsedID =0;
    private String endorsedHandle ="";
    private String endorsedUserTag ="";

    public Post(int ID,int Reference,String Comment,String PostType,int endorsedID,String endorsedHandle,String end_comment){//constructor method
        this.PostType = PostType;//can be determined by the value of the ParentPostReference
        this.ID = ID;
        this.Comment = Comment;
        this.ParentPostReference = Reference;
        if (PostType.equals("endorsement")){
            this.endorsedID =endorsedID;
            this.endorsedHandle = endorsedHandle;
            this.Comment = end_comment;
            this.endorsedUserTag = "EP@"+endorsedHandle+": "+end_comment;
        }
    }

    public void incrementEndorsement(){
        this.endorsement++;
    }//increments number of endorsements for the post


    public void incrementCommentCount(){
        this.CommentCount++;
    }//increments the number of comments for the post

    public String getPostType(){
        return this.PostType;
    }//helps to identify what post type the post is to know ifs actionable for certain operations in the System

    public void setPostType(String PostType){this.PostType = PostType;}

    public String getEndorsmentDetails(){
        return this.endorsedUserTag;
    }

    public String getEndorsedHandle(){
        return this.endorsedHandle;
    }

    public void UpdatdeEndorsedHandle(String handle){//changes the endorsement string if the account its been endorsed from changed their handle name
        this.endorsedUserTag = "EP@"+handle+": "+Comment;
    }

    public int getEndorsement(){
        return this.endorsement;
    }

    public int getEndorsementID(){return this.endorsedID;}

    public int getID() {
        return ID;
    }

    public int getParentPostReference() {
        return ParentPostReference;
    }

    public String getComment() {
        return Comment;
    }

    public int getCommentCount(){
        return this.CommentCount;
    }
}
