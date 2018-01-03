package br.com.badiale.tweetmood.twitter;

import org.junit.Test;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class TwitterServiceTest {
    private TwitterService subject = TwitterService.getInstance();

    @Test
    public void shouldReturnAuthentication() {
        final TwitterAuthenticationResult twitterAuthenticationResult = subject.authenticate().blockingFirst();
        assertNotNull(twitterAuthenticationResult);
        assertThat(twitterAuthenticationResult.getTokenType(), equalTo("bearer"));
        assertNotNull(twitterAuthenticationResult.getAccessToken());
    }

    @Test
    public void shouldReturnUsersTimeline() {
        final String userIdToSearch = "badiale";
        final TwitterSearchResult result = subject.getUserTimeline(userIdToSearch).blockingFirst();
        assertNotNull(result);
        assertThat(result.getStatuses(), not(empty()));
        assertThat(result.getStatuses().get(0).getUser().getScreenName(), equalTo(userIdToSearch));
    }
}
