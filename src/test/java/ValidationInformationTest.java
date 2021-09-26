import by.newsportal.news.service.validation.ValidationInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ValidationInformation")
public class ValidationInformationTest {

    static Stream<Arguments> provideArgumentsCheckEmailTest() {
        return Stream.of(
                Arguments.of("evgeniermakov1994@mail.ru", true),
                Arguments.of("evgeniermakov100@gmail.com", true),
                Arguments.of("3", false),
                Arguments.of("", false)
        );
    }

    @MethodSource("provideArgumentsCheckEmailTest")
    @ParameterizedTest()
    void checkEmailTest(String email, boolean expected) {
        boolean actual = ValidationInformation.checkEmail(email);
        assertEquals(expected, actual);
    }


    static Stream<Arguments> provideArgumentsCheckPasswordTest() {
        return Stream.of(
                Arguments.of("Q193213324862q^", true),
                Arguments.of("q1932133Q^", true),
                Arguments.of("3", false),
                Arguments.of("", false)
        );
    }

    @MethodSource("provideArgumentsCheckPasswordTest")
    @ParameterizedTest()
    public void checkPasswordTest(String password, boolean expected) {
        boolean actual = ValidationInformation.checkPassword(password);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> provideArgumentsCheckNameTest() {
        return Stream.of(
                Arguments.of("123", true),
                Arguments.of("qwe", true),
                Arguments.of("3", false),
                Arguments.of("", false),
                Arguments.of("%$$", false),
                Arguments.of("qwe^", false),
                Arguments.of("    ", false)
        );
    }

    @MethodSource("provideArgumentsCheckNameTest")
    @ParameterizedTest()
    public void checkNameTest(String name, boolean expected) {
        boolean actual = ValidationInformation.checkName(name);
        assertEquals(expected, actual);
    }
}
