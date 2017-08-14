package seborama.demo1.kafka;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleArgumentsTest {

    @Test
    public void itReturnsAnEmptyMapWhenNoArgIsSupplied() throws Exception {
        String[] args = new String[]{};
        ConsoleArguments unit = new ConsoleArguments(args);
        assertThat(unit.get("")).isEmpty();
    }

    @Test
    public void itReturnsSomethingMapWhenOneArgIsSupplied() throws Exception {
        String[] args = new String[]{"-sleepduration", "1000"};
        ConsoleArguments unit = new ConsoleArguments(args);
        assertThat(unit.get("SleepDuration").orElse("Wrong value")).isEqualTo(1000);
    }

}
