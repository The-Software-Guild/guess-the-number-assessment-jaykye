package GuessTheNumber.service;

import GuessTheNumber.TestApplicationConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class GuessTheNumberServiceTest {
    @Autowired
    GuessTheNumberService service;

    @Test
    public void testGenerateAnswer() {
        String answer = service.generateAnswer();
        assertEquals(4, answer.length());

        List<String> seen = new ArrayList<String>();
        for (String l: answer.split("")) {
            assertFalse(seen.contains(l));
            seen.add(l);
        }
    }

    @Test
    public void calculateResult() {
        String res = service.calculateResult("1234", "4321");

        assertEquals("e:0:p:4", res);
    }
}