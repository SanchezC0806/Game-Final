package ProfRoyale;

public class Judy extends Characters {
    
    public Judy(){
        super("Judy", 100, 0);
    }

    public final String[] skillNames = {
        "Is or Are?", 
        "p or q?", 
        "Diba Class?", 
        "Bohth"
    };

    @Override
    public String[] getSkillNames() {
        return skillNames;
    }
    
    @Override
    public void useSkill(int skillNum, Characters opponent) {
        // Use the base class skill system
        switch (skillNum) {
            case 1:
            case 2:
            case 3:
            case 4:
                // call parent method to handle random damage, shield, cooldown
                super.useSkill(skillNum, opponent);
                break;
            default:
                System.out.println(name + " faltered!");
        }
    }
}
