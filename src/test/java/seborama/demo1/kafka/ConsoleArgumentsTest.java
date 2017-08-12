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

}
