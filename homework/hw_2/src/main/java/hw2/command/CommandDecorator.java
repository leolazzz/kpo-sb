package hw2.command;

public class CommandDecorator implements Command {
    private Command decCom;
    public CommandDecorator(Command decCom){
        this.decCom = decCom;
    }
    @Override
    public void execute() {
        long st = System.nanoTime();
        decCom.execute();
        System.out.printf("Command %s work %d ms", decCom.getName(), (System.nanoTime() - st) / 1000000);
    }

    @Override
    public String getName() {
        return decCom.getName();
    }
}
