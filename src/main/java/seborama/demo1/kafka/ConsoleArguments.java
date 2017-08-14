package seborama.demo1.kafka;

import java.util.*;

public class ConsoleArguments {
    private Map<String, Object> argumentsMap;

    public ConsoleArguments(String[] args) {
        argumentsMap = parse(args);
    }

    private Map<String, Object> parse(String[] args) {
        Map<String, Object> argumentsMap = new HashMap<>();
        Iterator<String> itr = Arrays.asList(args).iterator();

        while (itr.hasNext()) {
            switch (itr.next().toLowerCase()) {
                case "-sleepduration":
                    if (itr.hasNext())
                        argumentsMap.put("SleepDuration", Integer.valueOf(itr.next()));
            }
        }

        return argumentsMap;
    }

    public Optional<Object> get(String name) {
        return Optional.ofNullable(argumentsMap.get(name));
    }
}
