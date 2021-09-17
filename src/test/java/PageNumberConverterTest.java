import by.newsportal.news.controller.impl.AfterAuthorization;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("pageNumberConverter")
class PageNumberConverterTest {
    static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of("1", 1),
                Arguments.of(null, 1),
                Arguments.of("3", 3),
                Arguments.of("", 1)
        );
    }

    @MethodSource("provideArguments")
    @ParameterizedTest()
    void test(String currentPageNumber, int expected) {
        int actual = AfterAuthorization.getInstance().pageNumberConverter(currentPageNumber);
        assertEquals(expected, actual);
    }
}