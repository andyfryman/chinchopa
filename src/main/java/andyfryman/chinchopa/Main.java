package andyfryman.chinchopa;

import andyfryman.chinchopa.strategy.*;
import skadistats.clarity.processor.runner.ControllableRunner;
import skadistats.clarity.processor.runner.SimpleRunner;
import skadistats.clarity.source.MappedFileSource;
import skadistats.clarity.source.Source;

public class Main {

    public void run(String[] args) throws Exception {
        Source source = new MappedFileSource(args[0]);
        String command = args.length > 1 ? args[1] : "";
        Object strategy = null;
        switch (command) {
            case "ability":
                strategy = new AbilityStrategy();
                break;
            case "item":
                strategy = new ItemStrategy();
                break;
            case "purchase":
                strategy = new PurchaseStrategy();
                break;
            case "heal":
                strategy = new HealStrategy();
                break;
            case "damage":
                strategy = new DamageStrategy();
                break;
            case "gold":
                strategy = new GoldStrategy();
                break;
            case "experience":
                strategy = new ExperienceStrategy();
                break;
            case "death":
                strategy = new DeathStrategy();
                break;
            case "debug":
                strategy = new DebugStrategy();
                break;
        }

        if (command.equalsIgnoreCase("stats")) {
            ControllableRunner runner = new ControllableRunner(source);
            StatsStrategy strategy2 = new StatsStrategy(runner, args[0]);
            try {
                runner.runWith(strategy2);
                runner.seek(runner.getLastTick());
                runner.halt();
            }
            catch (Exception e) {
            }
            strategy2.onEnd();
        } else {
            if (strategy != null) new SimpleRunner(source).runWith(strategy);
        }
    }

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

}
