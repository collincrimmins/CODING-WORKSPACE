package Projects.twitterfeed;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FeedStrategyChronological implements FeedStrategy {
    public List<Tweet> getFeed(User user, List<Tweet> tweets) {
        // Get Users Im Following
        Set<User> setFollowing = user.getFollowing();
        setFollowing.add(user); // Add myself
        
        // Get List of Tweets
        List<Tweet> list = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (setFollowing.contains(tweet.getUser())) {
                list.add(tweet);
            }
        }

        // Sort List
        list.sort((a, b) -> {
            return a.getCreatedAt().compareTo(b.getCreatedAt());
        });

        // Return List
        return list;
    }

    public List<Tweet> getFeedPagination(User user, List<Tweet> tweets, Instant cursorTimestamp, int paginationLimit) {
        // Get Users Im Following
        Set<User> setFollowing = user.getFollowing();
        setFollowing.add(user); // Add myself
        
        // Get List of Tweets
        List<Tweet> list = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (setFollowing.contains(tweet.getUser())) {
                list.add(tweet);
            }
        }

        // Sort List
        list.sort((a, b) -> {
            return a.getCreatedAt().compareTo(b.getCreatedAt());
        });

        // Use cursorTimestamp & paginationLimit
        List<Tweet> paginationList = new ArrayList<>();
        for (Tweet tweet : list) {
            // Limit
            if (paginationList.size() >= paginationLimit) {
                break;
            }

            // Timestamp
            if (tweet.getCreatedAt().compareTo(cursorTimestamp) > 0) {
                paginationList.add(tweet);
            }
        }

        // Return List
        return paginationList;
    }
}
