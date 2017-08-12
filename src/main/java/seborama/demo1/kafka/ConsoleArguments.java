package seborama.demo1.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConsoleArguments {
    private Map<String, String> argumentsMap;

    public ConsoleArguments(String[] args) {
        argumentsMap = parse(args);
    }

    private Map<String, String> parse(String[] args) {
//        Optional<String> maybeKey = Optional.of("");
//        for (String arg : args) {
//            maybeKey.ifPresent();
//            if (!key.isEmpty()) {
//                arguments.put(key, arg);
//                key = "";
//            } else {
//                switch (arg.toLowerCase()) {
//                    case "-sleepduration":
//                        key = "SleepDuration";
//                }
//            }
//        }
        return new HashMap<>();
    }

    public Optional<String> get(String name) {
        return Optional.ofNullable(argumentsMap.get(name));
    }
}
