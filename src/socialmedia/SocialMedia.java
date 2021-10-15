package socialmedia;
import java.io.IOException;
import java.util.*;
import java.io.*;

public class SocialMedia implements MiniSocialMediaPlatform{
	/**
	 * uses a Arraylist to store User objects and their corresponding Posts
	 * UniqueID is a cumulative unique integer assigned to every account and post so that every post/account has a unique identifier and its developed in such a way so it never repeats.
	 * developed by : Student ID: 690036000
	 */
	private List<User>Users= new ArrayList<>();//lis of all users registered on the system
	private int  UniqueID = 1000;//this will help assign Id to every post and user(works concurrently)
	public boolean checkHandle(String handle){
		/**
		 *This method checks if a handle exists in the system
		 * @param handle ,string to check in the system
		 * @return a boolean ,true = exists vice versa.
		 */
		int pointer = 0;
		boolean found = false;
		while(pointer < Users.size()){//searches the list
			if (Users.get(pointer).getUserName().equals(handle)){
				found = true;
				break;
			}
			pointer++;
		}
		return found;
	}
	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		/**
		 * The method creates an account in the platform with the given handle.
		 * <p>
		 * The state of this SocialMediaPlatform must be be unchanged if any exceptions
		 * are thrown.
		 *
		 * @param handle account’s handle.
		 * @throws IllegalHandleException if the handle already exists in the platform.
		 * @throws InvalidHandleException if the new handle is empty, has more than 30
		 * characters, or has white spaces.
		 * @return the ID of the created account
		 */
		try{
			if(checkHandle(handle)){
				throw new IllegalHandleException();// as  the account name(handle) already exists
			}else{//checks if the handle meets requirements
				try {
					if (handle.length() > 30 || handle.length() == 0 || handle.contains(" ") || handle.contains("\n")){//check if it's a integer
						throw new InvalidHandleException();
					} else {
						Users.add(new User(UniqueID, handle));//check if description is needed?
						UniqueID++;
						//passed all test so account can be generated
					}
				}catch (InvalidHandleException e){
					throw new InvalidHandleException();
				}
			}
		}catch(IllegalHandleException e){
			throw new IllegalHandleException();
		}
		return UniqueID - 1;
	}
	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException{
		/**
		 * The method removes the account with the corresponding ID from the platform.
		 * When an account is removed, all of their posts and likes should also be
		 * removed.
		 * <p>
		 * The state of this SocialMediaPlatform must be be unchanged if any exceptions
		 * are thrown.
		 *
		 * @param id ID of the account.
		 * @throws AccountIDNotRecognisedException if the ID does not match to any
		 * account in the system.
		 */
		try{
			int pointer = 0;
			boolean found = false;
			while(pointer < Users.size()) {//searches the list of Users
				if(Users.get(pointer).getID()==id){
					Users.remove(pointer);//remove the User object form the list of users
					found = true;
				}
				pointer++;
			}
			if (!found) {
				throw new AccountIDNotRecognisedException();
			}
		}catch(AccountIDNotRecognisedException e){
			throw new AccountIDNotRecognisedException();
		}
	}
	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		/**
		 * The method replaces the oldHandle of an account by the newHandle.
		 * <p>
		 * The state of this SocialMediaPlatform must be be unchanged if any exceptions
		 6* are thrown.
		 *
		 * @param oldHandle account’s old handle.
		 * @param newHandle account’s new handle.
		 * @throws HandleNotRecognisedException if the old handle does not match to any
		 * account in the system.
		 * @throws IllegalHandleException if the new handle already exists in the
		 * platform.
		 * @throws InvalidHandleException if the new handle is empty, has more
		 * than 30 characters, or has white spaces.
		 */
		try{
			if(checkHandle(oldHandle)){//check if the Account with the original handle name exists or not
				//check if new handle is acceptable and if a account with new handle doesn't already exist
				if(checkHandle(newHandle)){
					throw new IllegalHandleException();
				}else{
					//check if new handle meets structural requirements
					if (newHandle.length() > 30 || newHandle.length() == 0 || newHandle.contains(" ") || newHandle.contains("\n")){
						throw new InvalidHandleException();
					} else {
						//passed all test so account can be updated with the new user name
						int pointer = 0;
						while(pointer < Users.size()){//searches the list to find the user with the original handle name to be update to the new handle name
							if (Users.get(pointer).getUserName().equals(oldHandle)){
								Users.get(pointer).changeUserName(newHandle);
								if(Users.get(pointer).getEndorseCount()>0){
									int y = 0;
									while (y < Users.size()) {
										int x = 0;
										while (x != Users.get(y).nextPost){
											if (Users.get(y).Posts[x].getPostType().equals("endorsement")){//deletes any posts that have endorsed the original post
												if(Users.get(y).Posts[x].getEndorsedHandle().equals(oldHandle)){
													Users.get(y).Posts[x].UpdatdeEndorsedHandle(newHandle);//updates the endowment handle string
												}
											}
											x++;
										}
										y++;
									}

								}
								break;
							}
							pointer++;
						}
					}
				}
			}else{//check if the handle meets requirements
				throw new HandleNotRecognisedException();//not found account with that handle
			}
		}catch(HandleNotRecognisedException e){
			throw new HandleNotRecognisedException();
		}
	}
	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException{
		/**
		 * The method creates a formatted string summarising the stats of the account
		 * identified by the given handle. The template should be:
		 *
		 * <pre>
		 * ID: [account ID]
		 * Handle: [account handle]
		 * Description: [account description]
		 * Post count: [total number of posts, including endorsements and replies]
		 * Endorse count: [sum of endorsements received by each post of this account]
		 * </pre>
		 *
		 * @param handle handle to identify the account.
		 * @return the account formatted summary.
		 * @throws HandleNotRecognisedException if the handle does not match to any
		 * account in the system.
		 */

		String stats;
		try {
			if (checkHandle(handle)){//checks if the handle exists
				int pointer = 0;
				while (pointer != Users.size() - 1) {//searches the list to find the position fo the account to be viewed
					if (Users.get(pointer).getUserName().equals(handle)){
						break;
					}
					pointer++;
				}
				String UserId = Integer.toString(Users.get(pointer).getID());
				String UserDesc = Users.get(pointer).getDescription();
				String PostCount = Integer.toString(Users.get(pointer).getPostCount());
				String EndorseCount = Integer.toString(Users.get(pointer).getEndorseCount());
				stats   = "ID: " + UserId + "\n"
						+ "Handle: " + handle + "\n"
						+ "Description: " + UserDesc + "\n"
						+ "Post Count: " + PostCount + "\n"
						+ "Endorse Count: " + EndorseCount + "\n";
			}else{
				throw new HandleNotRecognisedException();
			}
		}catch(HandleNotRecognisedException e){
			throw new HandleNotRecognisedException();
		}
		return stats;
	}
	//post related methods
	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException{
		/**
		 * The method creates a post for the account identified by the given handle with
		 * the following message.
		 * <p>
		 * The state of this SocialMediaPlatform must be be unchanged if any exceptions
		 * are thrown.
		 *
		 * @param handle handle to identify the account.
		 * @param message post message.
		 * @throws HandleNotRecognisedException if the handle does not match to any
		 * account in the system.
		 * @throws InvalidPostException if the message is empty or has more than
		 * 100 characters.
		 * @return the sequential ID of the created post.
		 */
		try {
			if(checkHandle(handle)){
				int pointer = 0;
				while (pointer < Users.size()) {//searches the list
					if (Users.get(pointer).getUserName().equals(handle)){
						break;
					}
					pointer++;
				}
				try {
					if(message.length() !=0 && message.length()<101){
						Users.get(pointer).addPost(message, UniqueID,"Post",0,0,"","");//0 as it's not a comment post
						Users.get(pointer).UpdatePostCount();
						UniqueID++;
					}else{
						throw new InvalidPostException();
					}
				} catch(InvalidPostException e){
					throw new HandleNotRecognisedException();
				}
			} else {
				throw new HandleNotRecognisedException();
			}
		}catch(HandleNotRecognisedException e){
			throw new HandleNotRecognisedException();
		}
		return UniqueID - 1;
	}
	@Override
	public int endorsePost(String handle, int id)//determine what to return if exception happens
			throws HandleNotRecognisedException,PostIDNotRecognisedException,NotActionablePostException{
		/**
		 * The method creates an endorsement post of an existing post, similar to a
		 * retweet on Twitter. An endorsement post is a special post. It contains a
		 * reference to the endorsed post and its message is formatted as:
		 * <p>
		 * <code>"EP@" + [endorsed account handle] + ": " + [endorsed message]</code>
		 * <p>
		 * The state of this SocialMediaPlatform must be be unchanged if any exceptions
		 * are thrown.
		 *
		 * @param handle of the account endorsing a post.
		 * @param id of the post being endorsed.
		 * @return the sequential ID of the created post.
		 * @throws HandleNotRecognisedException if the handle does not match to any
		 * account in the system.
		 * @throws PostIDNotRecognisedException if the ID does not match to any post in
		 * the system.
		 * @throws NotActionablePostException if the ID refers to a endorsement post.
		 * Endorsement posts are not endorsable.
		 * Endorsements are not transitive. For
		 * instance, if post A is endorsed by post
		 * B, and an account wants to endorse B, in
		 * fact, the endorsement must refers to A.
		 */
		String endorsed_comment = "";
		String endorsed_handle = "";
		try {
			if (checkHandle(handle)) {
				int pointer = 0;
				boolean found = false;
				try {
					while (pointer < Users.size()) {//searches the list
						int x = 0;
						while (x != Users.get(pointer).nextPost){
							if (Users.get(pointer).Posts[x] != null) {
								if (Users.get(pointer).Posts[x].getID() == id){
									if (Users.get(pointer).Posts[x].getPostType().equals("endorsement") || Users.get(pointer).Posts[x].getPostType().equals("deleted")){
										//cannot endorse endorsed/deleted posts
										throw new NotActionablePostException();//Can't endorse an endorsed post!
									}else if(Users.get(pointer).Posts[x].getPostType().equals("comment")){
										throw new NotActionablePostException();//cannot endorse a comment of a post!
									}else{
										Users.get(pointer).addEndorsementCount();//its being endorsed so it will ad an endorsement counter to it
										endorsed_handle = Users.get(pointer).getUserName();
										endorsed_comment = Users.get(pointer).Posts[x].getComment();
										Users.get(pointer).Posts[x].incrementEndorsement();//adds endorsement count to that particular post
										found = true;
									}
									break;
								}
							}
							x++;
						}
						pointer++;
					}
				}catch(NotActionablePostException e){
					throw new NotActionablePostException();
				}
				try {
					if (found) {
						pointer = 0;
						while (pointer < Users.size()) {//searches the list for the user with the handle provided
							if (Users.get(pointer).getUserName().equals(handle)){
								break;
							}
							pointer++;
						}
						Users.get(pointer).addPost("", UniqueID, "endorsement", 0, id, endorsed_handle, endorsed_comment);//0 as it's not a comment post
						Users.get(pointer).UpdatePostCount();
						UniqueID++;
					} else {
						throw new PostIDNotRecognisedException();
					}
				}catch(PostIDNotRecognisedException e){
					throw new PostIDNotRecognisedException();
				}
			} else {
				throw new HandleNotRecognisedException();
			}
		}catch(HandleNotRecognisedException e){
			throw new HandleNotRecognisedException();
		}
		return UniqueID-1;
	}
	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException{
		/**
		 * The method creates a comment post referring to an existing post, similarly to
		 * a reply on Twitter. A comment post is a special post. It contains a reference
		 * to the post being commented upon.
		 * <p>
		 * The state of this SocialMediaPlatform must be be unchanged if any exceptions
		 * are thrown.
		 *
		 * @param handle of the account commenting a post.
		 * @param id of the post being commented.
		 * @param message the comment post message.
		 * @return the sequential ID of the created post.
		 * @throws HandleNotRecognisedException if the handle does not match to any
		 * account in the system.
		 * @throws PostIDNotRecognisedException if the ID does not match to any post in
		 * the system.
		 * @throws NotActionablePostException if the ID refers to a endorsement post.
		 * Endorsement posts are not endorsable.
		 * Endorsements cannot be commented. For
		 * instance, if post A is endorsed by post
		 * B, and an account wants to comment B, in
		 * fact, the comment must refers to A.
		 * @throws InvalidPostException if the comment message is empty or has
		 * more than 100 characters.
		 */
		int x = 0;
		try {
			if (checkHandle(handle)){
				//fin the id of the post/comment to be commented by this comment
				//add to a array of comment s for that user
				int pointer = 0;
				boolean foundPostID = false;
				while (pointer < Users.size()){//searches the list
					if(Users.get(pointer).checkPostID(id)){//checks if the id provided belongs to a used
						//check if post is endorsement post if it is then throw exception
						try {
							boolean endorsed;
							while(true){
								if(Users.get(pointer).Posts[x].getID() == id){
									if(!Users.get(pointer).Posts[x].getPostType().equals("deleted")){//makes sure you can't comment on a deleted post
										if (Users.get(pointer).Posts[x].getPostType().equals("endorsement")){//whats this?
											endorsed = true;
										} else {//might need to change this
											endorsed = false;
										}
										foundPostID = true;
										break;
									}else{
										throw new NotActionablePostException();//cannot comment on deleted posts
									}
								}
								x++;
							}
							if(endorsed){
								throw new NotActionablePostException();
							}
						}catch(NotActionablePostException e){
							throw new NotActionablePostException();
						}
					}
					if(foundPostID){
						break;//potentially saves execution time
					}
					pointer++;
				}
				try {
					if (message.length() != 0 && message.length() < 101){//validation
						try {
							if (foundPostID) {
								Users.get(pointer).Posts[x].incrementCommentCount();//increments the comment count for the post being commented on(wrong!!!)
								pointer = 0;
								while(pointer < Users.size()){
									if(Users.get(pointer).getUserName().equals(handle)){//searches the list user with the handle provided
										Users.get(pointer).addPost(message, UniqueID, "comment", id, 0, "", "");
										Users.get(pointer).UpdatePostCount();
										UniqueID++;
									}
									pointer++;
								}
							} else {
								throw new PostIDNotRecognisedException();
							}
						} catch (PostIDNotRecognisedException e){
							throw new PostIDNotRecognisedException();
						}
					} else {
						throw new InvalidPostException();
					}
				}catch(InvalidPostException e){
					throw new InvalidPostException();
				}
				//knows user exists now it has to see if the id of the comment exists and then it has to add it to the users own comments as its a comment of a comment
			} else {
				throw new HandleNotRecognisedException();
			}
		}catch (HandleNotRecognisedException e){
			throw new HandleNotRecognisedException();
		}
		return UniqueID-1; //return sequential id of the comment made
	}
	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException{//this can only allow to delete posts so parent reference has to be equal to 0 other sie thow error
		//also delete all endorsements to that post.
		/**
		 * The method removes the post from the platform. When a post is removed, all
		 * its endorsements should be removed as well. All replies to this post should
		 * be updated by replacing the reference to this post by a generic empty post.
		 * <p>
		 * The generic empty post message should be "The original content was removed
		 * from the system and is no longer available.". This empty post is just a
		 * replacement placeholder for the post which a reply refers to. Empty posts
		 * should not be linked to any account and cannot be acted upon, i.e., it cannot
		 * be available for endorsements or replies.
		 * <p>
		 * The state of this SocialMediaPlatform must be be unchanged if any exceptions
		 * are thrown.
		 *
		 * @param id ID of post to be removed.
		 * @throws PostIDNotRecognisedException if the ID does not match to any post in
		 * the system.
		 */
		int[] Stack = new int[100];
		int next_top = 1;
		int pointer = 0;
		boolean found = false;
		while (pointer < Users.size()) {//searches the list to see if a post with he given id exists(make a function as its repeated very often everywhere(easy fix))
			int x = 0;
			while (x != Users.get(pointer).nextPost){
				if(Users.get(pointer).Posts[x] !=null){
					if (Users.get(pointer).Posts[x].getID() == id){
						//check if this post is not a comment post by checking whether its reference post is "0"
						//check if the post is alredy deleted,if so thorw exeption.
						if (Users.get(pointer).Posts[x].getParentPostReference() == 0) {
							Users.get(pointer).Posts[x] = null;
							Users.get(pointer).Posts[x] = new Post(id, 0, "The original content was removed from the system and is no longer available.", "deleted", 0, "", "");
							Users.get(pointer).UpdatePostCount();
							found = true;
						} else {
							//throw exception as its not a post but a comment
						}
						break;
					}
				}
				x++;
			}
			if(found){
				break;
			}
			pointer++;
		}
		try {
			if (found) {
				//also here find all possible post that have endorsed this post
				pointer = 0;
				while (pointer < Users.size()) {
					int x = 0;
					while (x != Users.get(pointer).nextPost){
						if(Users.get(pointer).Posts[x] !=null){
							if (Users.get(pointer).Posts[x].getEndorsementID() == id){//deletes any posts that have endorsed the original post
								Users.get(pointer).Posts[x] = null;
								Users.get(pointer).Posts[x] = new Post(id,0, "The original content was removed from the system and is no longer available.","deleted",0,"","");
								Users.get(pointer).UpdatePostCount();
							}
						}
						x++;
					}
					pointer++;
				}
				pointer = 0;
				while (true) {
					while (pointer < Users.size()){//searches the list
						int x = 0;
						while (x != Users.get(pointer).nextPost) {
							if (Users.get(pointer).Posts[x] != null) {
								if (Users.get(pointer).Posts[x].getParentPostReference() == id) {//places all comment posts referencing to the current post id on the stack
									Stack[next_top] = Users.get(pointer).Posts[x].getID();//adds all post objects referencing to the current id set
									next_top++;
								}
							}
							x++;
						}
						pointer++;
					}
					next_top--;//decrements the stack pointer to see what is at the top of the stack
					if (next_top == 0){//if pointer is -1 then the stack is empty so program has finished searching
						break;
					}
					pointer = 0;
					id = Stack[next_top];//sets the current id to be searched according to whats at the top of the stack
					while (pointer < Users.size()) {//searches the list for the post with that id to be deleted
						int x = 0;
						while (x != Users.get(pointer).nextPost) {
							if (Users.get(pointer).Posts[x] != null) {
								if (Users.get(pointer).Posts[x].getID() == id){
									Users.get(pointer).Posts[x].setPostType("deleted");
									Users.get(pointer).UpdatePostCount();
								}
							}
							x++;
						}
						pointer++;
					}
					Stack[next_top] = 0;//clears the top most item of the stack as it will now been searched
					pointer = 0;
				}
			} else {
				throw new PostIDNotRecognisedException();
				//exception
			}
		}catch(PostIDNotRecognisedException e){
			throw new PostIDNotRecognisedException();
		}
	}
	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException{//verify the post is not a comment post so it's ParentPostReference = 0.
		/**
		 * The method generates a formated string containing the details of a single
		 * post. The format is as follows:
		 *
		 * <pre>
		 * ID: [post ID]
		 * Account: [account handle]
		 * No. endorsements: [number of endorsements received by the post] | No. comments: [number of comments
		 received by the post]
		 * [post message]
		 * </pre>
		 *
		 * @param id of the post to be shown.
		 * @return a formatted string containing post’s details.
		 * @throws PostIDNotRecognisedException if the ID does not match to any post in
		 * the system.
		 */
		int pointer = 0;
		boolean found = false;

		int comment_count = 0;
		String handle_name= "";
		int endorsement_count= 0;
		String PostMessage = "";
		String stats = "";
		String PostType ="";
		String endorsementString="";

		while (pointer < Users.size()){//searches the list
			int x = 0;
			while (x != Users.get(pointer).nextPost) {
				if(Users.get(pointer).Posts[x] !=null){
					if (Users.get(pointer).Posts[x].getID() == id){
						comment_count = Users.get(pointer).Posts[x].getCommentCount();
						endorsement_count = Users.get(pointer).Posts[x].getEndorsement();//this could be not right
						handle_name = Users.get(pointer).getUserName();
						PostMessage = Users.get(pointer).Posts[x].getComment();
						PostType = Users.get(pointer).Posts[x].getPostType();
						endorsementString=Users.get(pointer).Posts[x].getEndorsmentDetails();
						found = true;
					}
				}
				x++;
			}
			pointer++;
		}
		try {
			if (found && (PostType.equals("Post") || PostType.equals("endorsement") )) {//has to be a id that exists and a id that refers to only a Post or a endorsement
				if (PostType.equals("endorsement")) {
						stats = endorsementString+"\n"
								+"ID: " + id + "\n"
								+ "Handle: " + handle_name + "\n";
				} else {
						stats = "ID: " + id + "\n"
								+ "Handle: " + handle_name + "\n"
								+ "No. endorsements: " + endorsement_count + " | Post Count: " + comment_count + "\n"
								+ "Post Message: " + PostMessage + "\n";
				}
			}else{
				throw new PostIDNotRecognisedException();
			}
		}catch(PostIDNotRecognisedException e){
			throw new PostIDNotRecognisedException();
		}
		return stats;
	}
	@Override
	public StringBuilder showPostChildrenDetails(int id)
		throws PostIDNotRecognisedException, NotActionablePostException{
		/**
		 * The method builds a StringBuilder showing the details of the current post and
		 * all its children posts. The format is as follows:
		 * <pre>
		 * {@link #showIndividualPost(int) showIndividualPost(id)}
		 * |
		 * [for reply: replies to the post sorted by ID]
		 * | > {@link #showIndividualPost(int) showIndividualPost(reply)}
		 * </pre>
		 * @param id of the post to be shown.
		 * @return a formatted StringBuilder containing the details of the post and its
		 * children.
		 * @throws PostIDNotRecognisedException if the ID does not match to any post in
		 * the system.
		 * @throws NotActionablePostException if the ID refers to an endorsement post.
		 * Endorsement posts do not have children
		 * since they are not endorseable nor
		 * commented.
		 */
		int PrevID =0 ;//used to determine if to print a small arrow or a large arrow
		ArrayList<Integer> PrevIDvisited = new ArrayList<>(); //used to determine if to print a small arrow or a large arrow

		StringBuilder stringBuilder = new StringBuilder();//main string builder
		StringBuilder TotalTab = new StringBuilder();//fo building the indents

		String handle_nameX ="";
		int idX = 0;
		int endorsements_countX = 0;
		int comment_countX = 0;
		String PostMessageX = "";
		String moveDown ="\n";
		String Tab = "	";
		int[][]   Stack = new int[100][2];//array [id of the comment on the stack to be explored for sub comments];
		int[][] IndentListArray = new int[100][2];//[ID][indent value]
		int nextFreePosInListArray = 0;
		int next_top = 1;
		int pointer = 0;
		int indent = 0;
		boolean found = false;
		while (pointer < Users.size()){//searches the list if the id(root id) provided exists
			int x = 0;
			while (x != Users.get(pointer).nextPost) {
				if(Users.get(pointer).Posts[x] !=null){
					if (Users.get(pointer).Posts[x].getID() == id){
						if(Users.get(pointer).Posts[x].getPostType().equals("Post")||Users.get(pointer).Posts[x].getPostType().equals("comment")){//if its deleted then it wont show it
							handle_nameX = Users.get(pointer).getUserName();
							idX = id;
							endorsements_countX = Users.get(pointer).Posts[x].getEndorsement();
							comment_countX = Users.get(pointer).Posts[x].getCommentCount();
							PostMessageX = Users.get(pointer).Posts[x].getComment();
							found = true;
						}else{
							throw new NotActionablePostException();
						}
					}
				}
				x++;
			}
			pointer++;
		}
		try {
			if (found) {
				stringBuilder.append("ID: " + idX + "\n"+//build the top post (seed post)
									"Account: " + handle_nameX + "\n"+
									"No. endorsements: " + endorsements_countX +" |"+ " No. comments: " + comment_countX+"\n"+
									"Post Message: " + PostMessageX);
				IndentListArray[nextFreePosInListArray][0]= id;//adds the seed post as zero indent as it's the first post
				IndentListArray[nextFreePosInListArray][1]= 0;
				nextFreePosInListArray++;
				//PrevID =id;
				pointer = 0;
				while (true) {
					while (pointer < Users.size()){//searches the list
						int x = 0;
						while (x < Users.get(pointer).nextPost) {
							if (Users.get(pointer).Posts[x] != null){
								if (Users.get(pointer).Posts[x].getParentPostReference() == id){
									int y = 0;
									while(y < nextFreePosInListArray){
										if(IndentListArray[y][0]==id){//updates the indent list to determine how much tio indent each comment posts
											IndentListArray[nextFreePosInListArray][0]= Users.get(pointer).Posts[x].getID();
											IndentListArray[nextFreePosInListArray][1]= IndentListArray[y][1] + 1;
											nextFreePosInListArray++;
											break;
										}
										y++;
									}
									Stack[next_top][0] = Users.get(pointer).Posts[x].getID();//adds to the stack so it can be explored later for deeper comments of comments ect..
									Stack[next_top][1] = id;
									next_top++;
								}
							}
							x++;
						}
						pointer++;
					}
					next_top--;
					if (next_top == 0) {
						break;
					}
					id = Stack[next_top][0];
					//already_visited.add(id);
					pointer = 0;
					while (pointer < Users.size()){//searches the list of users to gather the data of the commentator
						int x = 0;
						while (x < Users.get(pointer).nextPost) {
							if(Users.get(pointer).Posts[x] !=null){
								if (Users.get(pointer).Posts[x].getID() == id){
									//grab all details;
									handle_nameX = Users.get(pointer).getUserName();
									idX = id;
									endorsements_countX = Users.get(pointer).Posts[x].getEndorsement();
									comment_countX = Users.get(pointer).Posts[x].getCommentCount();
									PostMessageX = Users.get(pointer).Posts[x].getComment();
								}
							}
							x++;
						}
						pointer++;
					}
					pointer = 0;
					int y = 0;
					while(y != nextFreePosInListArray){
						if(IndentListArray[y][0]==id){//grabs the id
							indent = IndentListArray[y][1];//grabs the indentation level for that post id
							break;
						}
						y++;
					}
					int z = 0;
					while(z != indent){//determines how much to indent the string
						TotalTab.append(Tab);//builds the indentation level
						z++;
					}
					stringBuilder.append(moveDown);//may not be necessary(will see how it looks later)
					if(Stack[next_top][1] != PrevID && !PrevIDvisited.contains(Stack[next_top][1])){//determines which type of arrow to use
						stringBuilder.append(TotalTab+"|"+ "\n"+
											 TotalTab+"| >"+"ID: " + idX + "\n");
						PrevID = Stack[next_top][1];
						PrevIDvisited.add(PrevID);
					}else{
						stringBuilder.append(TotalTab+ "| >"+"ID: " + idX + "\n");
					}
					stringBuilder.append(//builds the final output string
							TotalTab
							+ "   Account: " + handle_nameX + "\n"+
							TotalTab
							+ "   No. endorsements: " + endorsements_countX +" |"+ " No. comments: " + comment_countX+"\n"+
							TotalTab
							+ "   Post Message: " + PostMessageX);
					Stack[next_top][0] = 0;//resets stack top value to 0(default)
					TotalTab.setLength(0);//resets the indent amount
				}
			} else {
				throw new PostIDNotRecognisedException();//throws the exception
			}
		}catch(PostIDNotRecognisedException e){
			throw new PostIDNotRecognisedException();
		}
		return stringBuilder;//return the final output
	}
	@Override
	public int getMostEndorsedPost(){
		/**
		 * This method identifies and returns the post with the most number of
		 * endorsements, a.k.a. the most popular post.
		 *
		 * @return the ID of the most popular post.
		 */
		int pointer = 0;
		int most_endorsements=0;
		int id = 0;
		while (pointer < Users.size()){
			int x = 0;
			while (x != Users.get(pointer).nextPost){
				if (Users.get(pointer).Posts[x].getEndorsement() > most_endorsements){
					most_endorsements = Users.get(pointer).getEndorseCount();
					id = Users.get(pointer).Posts[x].getID();
				}
				x++;
			}
			pointer++;
		}
		return id;
	}
	@Override
	public int getMostEndorsedAccount(){
		/**
		 * This method identifies and returns the account with the most number of
		 * endorsements, a.k.a. the most popular account.
		 *
		 * @return the ID of the most popular account.
		 */
		int pointer = 0;
		int most_endorsements=0;
		int id = 0;
		while (pointer < Users.size()){
			if(Users.get(pointer).getEndorseCount()>most_endorsements){
				most_endorsements = Users.get(pointer).getEndorseCount();
				id = Users.get(pointer).getID();
			}
			pointer++;
		}
		return id;
	}
	@Override
	public void erasePlatform(){//clears all data that's saved on the platform
		/**
		 * Method empties this SocialMediaPlatform of its contents and resets all
		 * internal counters.
		 */
		Users.clear();
		UniqueID = 1000;
	}
	@Override
	public void savePlatform(String filename) throws IOException{
		/**
		 * Method saves this SocialMediaPlatform’s contents into a serialised file, with
		 * the filename given in the argument.
		 *
		 * @param filename location of the file to be saved
		 * @throws IOException if there is a problem experienced when trying to save the
		 * store contents to the file
		 */

		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(Users);
			out.writeInt(UniqueID);
			out.close();
			System.out.println("Serialized data is saved in: " + filename);
		} catch (IOException e){
			throw new IOException();
		}
	}
	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException{
		/**
		 * Method should load and replace this SocialMediaPlatform’s contents with the
		 * serialised contents stored in the file given in the argument.
		 * <p>
		 * The state of this SocialMediaPlatform’s must be be unchanged if any
		 * exceptions are thrown.
		 *
		 * @param filename location of the file to be loaded
		 * @throws IOException if there is a problem experienced when trying
		 * to load the store contents from the file
		 * @throws ClassNotFoundException if required class files cannot be found when
		 * loading
		 */
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
			// Read objects
			try {
				Users = (List<User>) input.readObject();//fix
				UniqueID = input.readInt();
				System.out.println("Serialized data read from: " + filename);
				input.close();
			}catch(ClassNotFoundException e){
				throw new ClassNotFoundException();
			}
		}catch(IOException e){
			throw new IOException();
		}
	}
}
