package ProfRoyale;

public class Lance extends Characters {
    
    public Lance(){
        super("Lance", 100, 0);
    }

    public final String[] skillNames = {
        "Basically", 
        "GG", 
        "Di ba diay?", 
        "Visualize"
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



