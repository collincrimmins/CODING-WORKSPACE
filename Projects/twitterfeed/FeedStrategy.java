package Projects.twitterfeed;

import java.time.Instant;
import java.util.List;

public interface FeedStrategy {
    public List<Tweet> getFeed(User user, List<Tweet> tweets);
    public List<Tweet> getFeedPagination(User user, List<Tweet> tweets, Instant cursorTimestamp, int paginationLimit);
}
