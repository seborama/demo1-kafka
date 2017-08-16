package seborama.demo1.kafka;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static seborama.demo1.kafka.ConsoleArguments.*;

public class ConsoleArgumentsTest {

    @Test
    public void itReturnsAnEmptyMapWhenNoArgIsSupplied() throws Exception {
        String[] args = new String[]{};
        ConsoleArguments unit = new ConsoleArguments(args);
        assertThat(unit.size()).isEqualTo(0);
    }

    @Test
    public void itReturnsAnEmptyMapWhenOneUnknownArgIsSupplied() throws Exception {
        String[] args = new String[]{"-unknownArgument", "some value"};
        ConsoleArguments unit = new ConsoleArguments(args);
        assertThat(unit.size()).isEqualTo(0);
    }

    @Test
    public void itReturnsTwoValuesWhenTwoKnownArgsAreSupplied() throws Exception {
        String[] args = new String[]{ARG_SLEEPDURATION, "1000", ARG_NUMBERMESSAGES, "100"};
        ConsoleArguments unit = new ConsoleArguments(args);
        assertThat(unit.size()).isEqualTo(2);
        assertThat(unit.get(SLEEP_DURATION).orElse("wrong value")).isEqualTo(1000);
        assertThat(unit.get(NUMBER_OF_MESSAGES).orElse("wrong value")).isEqualTo(100);
    }

    @Test
    public void itReturnsAValueWhenAKnownArgIsSupplied() throws Exception {
        String[] args = new String[]{ARG_SLEEPDURATION, "1000"};
        ConsoleArguments unit = new ConsoleArguments(args);
        assertThat(unit.size()).isEqualTo(1);
        assertThat(unit.get(SLEEP_DURATION).orElse("wrong value")).isEqualTo(1000);
    }

    @Test(expected = NumberFormatException.class)
    public void itBarfsWhenAnInvalidValueIsSuppliedToAKnownArg() throws Exception {
        String[] args = new String[]{ARG_SLEEPDURATION, "abc"};
        ConsoleArguments unit = new ConsoleArguments(args);
        assertThat(unit.size()).isEqualTo(0);
    }

}
