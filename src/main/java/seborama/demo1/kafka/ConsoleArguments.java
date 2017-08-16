package seborama.demo1.kafka;

import java.util.*;

public class ConsoleArguments {
    public static final String SLEEP_DURATION = "SleepDuration";
    public static final String NUMBER_OF_MESSAGES = "NumberOfMessages";
    static final String ARG_NUMBERMESSAGES = "-numbermessages";
    static final String ARG_SLEEPDURATION = "-sleepduration";
    private Map<String, Object> argumentsMap;

    public ConsoleArguments(String[] args) {
        argumentsMap = parse(args);
    }

    private Map<String, Object> parse(String[] args) {
        Map<String, Object> argumentsMap = new HashMap<>();
        Iterator<String> itr = Arrays.asList(args).iterator();

        while (itr.hasNext()) {
            String s = itr.next().toLowerCase();
            switch (s) {
                case ARG_SLEEPDURATION:
                    if (itr.hasNext()) {
                        String next = itr.next();
                        argumentsMap.put(SLEEP_DURATION, Integer.valueOf(next));
                    }
                    break;

                case ARG_NUMBERMESSAGES:
                    if (itr.hasNext()) {
                        String next = itr.next();
                        argumentsMap.put(NUMBER_OF_MESSAGES, Integer.valueOf(next));
                    }
                    break;
            }
        }

        return argumentsMap;
    }

    public Optional<Object> get(String name) {
        return Optional.ofNullable(argumentsMap.get(name));
    }

    public int size() {
        return argumentsMap.size();
    }

    public Optional<Integer> getAsInteger(String name) {
        return Optional.ofNullable((Integer) argumentsMap.get(name));
    }
}
