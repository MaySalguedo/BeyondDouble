package BeyondDouble;

import BeyondDouble.App;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test @DisplayName("Main method test")
    public void shouldAnswerWithTrue() {

        assertDoesNotThrow(() -> App.main(null));
        assertDoesNotThrow(() -> new App());

    }

}