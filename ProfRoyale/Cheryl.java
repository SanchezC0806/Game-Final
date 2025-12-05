package ProfRoyale;

public class Cheryl extends Characters {
    
    public Cheryl(){
        super("Cheryl", 100, 0);
    }

    public final String[] skillNames = {
        "Get one Whole sheet of paper", 
        "Open your lair", 
        "printf kuyawan", 
        "Basin dili ni para imo"
    };

    @Override
    public String[] getSkillNames() {
        return skillNames;
    }
    
    @Override
    public void useSkill(int skillNum, Characters opponent) {
        // Use the base class skill system
        switch (skillNum) {
            case 1, 2, 3, 4 -> // call parent method to handle random damage, shield, cooldown
                super.useSkill(skillNum, opponent);
            default -> System.out.println(name + " faltered!");
        }
    }
}
