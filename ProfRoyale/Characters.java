package ProfRoyale;

import java.util.Random;

public abstract class Characters {
    protected String name;
    protected int health;
    protected int shield;
    

    protected int shieldTurns = 0;
    protected int cooldownSkill2 = 0;
    protected int cooldownSkill3 = 0;
    protected int cooldownSkill4 = 0;
    protected boolean isComputer = false;

    private final Random rand = new Random();

    public Characters(String name ,int health, int shield){
        this.name = name;
        this.health = health;
        this.shield = shield;
    }
    
    public String getName(){return name;}
    public int getHealth(){return health;}   
    public int getShield(){return shield;}

    public void setHealth(int health){
        this.health = health;
    }
    protected void setShield(int amount, int turns) {
        this.shield = amount;
        this.shieldTurns = turns;
    }

    public boolean isAlive(){
        return health > 0;
    }
    
    private void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void receiveDamage(int damage) {
        delay(1000);
        int priorShield = shield;
        int blocked = 0;
        if(shield > 0) {
            blocked = Math.min(shield, damage);
            shield -= blocked;
            damage -= blocked;
        }

        if(priorShield > 0 && blocked > 0) {
            System.out.println(name + " blocked " + blocked + " damage with shield.");
        }

        if(damage > 0) {
            health -= damage;
            System.out.println(name + " took " + damage + " damage.");
            if(health <= 0) {
                health = 0;
                System.out.println(name + " has died");
            }
        }else if (blocked > 0 || priorShield > 0) {
            System.out.println(name + " took no damage.");
        }
        System.out.println(name + " current health: " + health + ".");
        delay(1000);
    }

    public void tickShield() {
        if(shieldTurns > 0) {
            shieldTurns--;
            if(shieldTurns == 0) {
                shield = 0;
                System.out.println(name + "'s shield has worn off!");
            }
        }
    }


    public void startTurn() {

        if(cooldownSkill2 > 0) {
            cooldownSkill2--;
        }
        if(cooldownSkill3 > 0) {
            cooldownSkill3--;
        }
        if(cooldownSkill4 > 0) {
            cooldownSkill4--;
        }
    }

    public int getCooldown(int skillNum) { //Skill 1 has no cooldown because its a basic attack
        switch(skillNum){
            case 2: return cooldownSkill2;
            case 3: return cooldownSkill3;
            case 4: return cooldownSkill4;
            default: return 0; 
        }
    }

    public void setComputer(boolean isComputer) {
        this.isComputer = isComputer;
    }
    public boolean isComputer() {
        return isComputer;
    }


    public abstract String[] getSkillNames();
    
//Skill Systems Below:


    public void useSkill(int skillNum, Characters opponent) {
        switch(skillNum){
            case 1:
                int dmg1 = rand.nextInt(11); // 0-10
                delay(1000);
                System.out.println(name + " attempts to deal " + dmg1 + " damage.");
                opponent.receiveDamage(dmg1);
                delay(1000);
                break;

            case 2:
                int dmg2 = rand.nextInt(16); // 0-15
                delay(1000);
                System.out.println(name + " attempts to deal " + dmg2 + " damage.");
                opponent.receiveDamage(dmg2);
                delay(1000);
                cooldownSkill2 = 2; // 2 turn cooldown
                break;

            case 3:
                delay(1000);
                setShield(10, 3); // Shield lasts 3 turns
                System.out.println(name + " gains a shield of 10 for 3 turns.");
                delay(1000);
                cooldownSkill3 = 3; // 3 turn cooldown
                break;

            case 4:
                if(cooldownSkill4 == 0) {
                    int dmg4 = 15 + rand.nextInt(26); // 15-25
                    delay(1000);
                    System.out.println(name + " attempts to deal " + dmg4 + " damage.");
                    opponent.receiveDamage(dmg4);
                    delay(1000);
                    cooldownSkill4 = 5; // 5 turn cooldown
                }else {
                    System.out.println(name + "'s Ultimate is on cooldown! (" + cooldownSkill4 + " turns left)");
                }
                break;

            default:
                System.out.println("Invalid skill.");
                break;
        }

    }
}