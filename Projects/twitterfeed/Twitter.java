package Projects.twitterfeed;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Twitter {
    List<Tweet> tweets;

    public Twitter() {
        this.tweets = new ArrayList<>();
    }

    // Users
    public void followUser(User user1, User user2) {
        user1.addToFollowing(user2);
    }

    public void unfollowUser(User user1, User user2) {
        user1.removeFromFollowing(user2);
    }

    // Tweets
    public void addTweet(User user, String text) {
        Tweet tweet = new Tweet(user, text);
        tweets.add(tweet);
    }

    // Feed
    public void getFeed(User user, FeedStrategy feedStrategy) {
        List<Tweet> list = feedStrategy.getFeed(user, tweets);

        printListOfFeed(user, list);
    }  

    public void getFeedPagination(User user, FeedStrategy feedStrategy, Instant cursorTimestamp, int paginationLimit) {
        List<Tweet> list = feedStrategy.getFeedPagination(user, tweets, cursorTimestamp, paginationLimit);

        printListOfFeed(user, list);
    }  

    private void printListOfFeed(User user, List<Tweet> list) {
        System.out.println("=== Feed of " + user.getId() + " ===");
        for (Tweet tweet : list) {
            tweet.printString();
        }
    }
}
