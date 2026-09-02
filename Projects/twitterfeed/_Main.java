package Projects.twitterfeed;

import java.time.Instant;

public class _Main {
    public static void main(String[] args) throws InterruptedException {
        Twitter system = new Twitter();

        User user1 = new User("Bob Smith");
        User user2 = new User("Sally Joe");
        User user3 = new User("Rob Robby");

        system.followUser(user1, user2);
        system.followUser(user1, user3);

        system.getFeed(user1, new FeedStrategyChronological());

        Instant cursorTimestamp = null;
        int paginationLimit = 10;
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                system.addTweet(user2, String.valueOf(i));
            } else {
                system.addTweet(user3, String.valueOf(i));
            }
            if (i == 50) {
                // Set Timestamp to Tweet #50
                cursorTimestamp = Instant.now();
            }
            Thread.sleep(1);
        }
        
        //system.getFeed(user1, new FeedStrategyChronological());
        system.getFeedPagination(user1, new FeedStrategyChronological(), cursorTimestamp, paginationLimit);
    }

    /*
        Prompt: Design a social media platform similar to Twitter that allows users to follow/unfollow 
        other users, post tweets, and view a personalized news feed of tweets from people they follow.
        https://github.com/ashishps1/awesome-low-level-design/blob/main/solutions/java/src/socialnetworkingservice/SocialNetworkFacade.java 

        Requirements:
        - Users & Tweets who then I then lookup on Feed Generation
        - Multiple Feed Strategies (such as Chronological)

        Out of Scope:
        - Notifications for when a new tweet has entered my feed

        Entities
        - Users
        - Tweets
        - FeedStrategy
            FeedStrategyChronological

        Class Design

            Twitter
            - List<Tweets> tweets
            + follow(userFollowee, userFollower)
            + getFeed(user, feedStrategy)

            User
            - String id
            - Set<User> following

            Tweet
            - User user
            - String text
            - Time createdAt

            FeedStrategy
            + getFeed(user, tweets)
            
    */
}
