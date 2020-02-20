package andyfryman.chinchopa;

import andyfryman.chinchopa.strategy.*;
import skadistats.clarity.processor.runner.SimpleRunner;
import skadistats.clarity.source.MappedFileSource;
import skadistats.clarity.source.Source;

public class Main {

    public void run(String[] args) throws Exception {
        Source source = new MappedFileSource(args[0]);
        String command = args.length > 1 ? args[1] : "";
        Object strategy;
        switch (command) {
            case "gold":
                strategy = new GoldStrategy();
                break;
            case "experience":
                strategy = new ExperienceStrategy();
                break;
            case "death":
            default:
                strategy = new DeathStrategy();
                break;
        }

        new SimpleRunner(source).runWith(strategy);
    }

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

}
