package helper;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Tag;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("all")
public abstract class TestHelper {
    protected static Faker faker = new Faker();

    public ResultMatcher exists(final String expectedFieldValue) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            assertThat(json.contains(expectedFieldValue)).isTrue();

        };
    }
}
