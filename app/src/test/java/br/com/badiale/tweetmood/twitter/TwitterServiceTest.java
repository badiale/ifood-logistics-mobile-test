package br.com.badiale.tweetmood.twitter;

import org.junit.Test;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class TwitterServiceTest {
    private TwitterService subject = TwitterService.getInstance();

    @Test
    public void shouldReturnAuthentication() {
        final TwitterAuthenticationResult result = subject.authenticate().blockingFirst();
        assertNotNull(result);
        assertThat(result.getTokenType(), equalTo("bearer"));
        assertNotNull(result.getAccessToken());
    }

    @Test
    public void shouldReturnUsersTimeline() {
        final String userIdToSearch = "badiale";
        final TwitterSearchResult result = subject.searchUserTweets(userIdToSearch).blockingFirst();
        assertNotNull(result);
        assertThat(result.getStatuses(), not(empty()));
        assertThat(result.getStatuses().get(0).getUser().getScreenName(), equalTo(userIdToSearch));
    }

    @Test
    public void shouldReloadDataUsingUrl() {
        final String reloadQuery = "?since_id=948633648722599936&q=from%3Abadiale&result_type=recent&include_entities=1";
        final TwitterSearchResult result = subject.searchTweetsByQueryString(reloadQuery).blockingFirst();
        assertNotNull(result);
        assertThat(result.getMetadata().getRefreshUrl(), not(isEmptyString()));
    }
}
