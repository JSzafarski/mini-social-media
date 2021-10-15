import socialmedia.*;//imports all classes from social media package
import java.io.IOException;

public class SocialMediaPlatformTestApp {
	public static void main(String[] args) throws IllegalHandleException, InvalidHandleException, HandleNotRecognisedException, InvalidPostException, PostIDNotRecognisedException, NotActionablePostException, AccountIDNotRecognisedException, IOException, ClassNotFoundException {

		/**
		 *createAccount(works-ned to ensure exceptions work)
		 *removeAccount(works)
		 *changeAccountHandle(works,ensure validation)
		 *showAccount(works,exceptions checking needs)
		 *createPost(yes,exceptions)
		 *endorsePost(works,validate and add the)
		 *commentPost(yes,exceptions)
		 *deletePost(works exceptions need checking)
		 *showIndividualPost(works exceptions need checking)
		 *showPostChildrenDetails(works need to ensure exceptions work)
		 *getMostEndorsedPost(works)
		 *getMostEndorsedAccount(works)
		 *erasePlatform(works)
		 *savePlatform(works)
		 *loadPlatform()
		 */

		SocialMedia platform = new SocialMedia();
		platform.createAccount("jan");//1000
		platform.createAccount("Otylia");//1001
		platform.createAccount("Pat");//1002
		platform.changeAccountHandle("jan","HELLO");
		platform.showAccount("HELLO");
		platform.createPost("HELLO","this is test 1");//03
		platform.commentPost("Pat",1003,"muahaha");//04
		platform.commentPost("Pat",1003,"hiya");//05
		platform.commentPost("Pat",1003,"lala");//06
		platform.commentPost("Pat",1003,"yolo");//07
		platform.commentPost("Otylia",1005,"stop the spam!");//08
		platform.commentPost("Pat",1008,"No!");//09
		platform.commentPost("Pat",1008,"go away");//10
		platform.endorsePost("Pat",1003);//11

		System.out.println(platform.getMostEndorsedPost());
		System.out.println(platform.showIndividualPost(1011));
		System.out.println(platform.showPostChildrenDetails(1003));


	}
}
