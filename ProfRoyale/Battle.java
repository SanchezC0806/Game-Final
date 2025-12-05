package ProfRoyale;

import java.util.Scanner;

public class Battle {
    private final Characters player1;
    private final Characters player2;

    private final Scanner scan = new Scanner(System.in);
    private final GameUI ui = new GameUI();

    private int round = 1;
    private String lastAction1 = "";
    private String lastAction2 = "";

    public Battle(Characters p1, Characters p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    private void displayRoundBanner() {
        System.out.println("████████████████████████████████████████████");
        System.out.println("█                                          █");
        System.out.println("█                                          █");
        System.out.println   ("█                ROUND   %d                 █".formatted(round));
        System.out.println("█                                          █");
        System.out.println("█                                          █");
        System.out.println("████████████████████████████████████████████");
        System.out.println();
    }

    private void displayHealth() {
        System.out.println(player1.getName() + "'s Health: " + player1.getHealth() + "      Shield: " + player1.getShield());
        System.out.println(player2.getName() + "'s Health: " + player2.getHealth() + "      Shield: " + player2.getShield());
        System.out.println("____________________________________________________________________________________________________\n");

        if(round == 1) {
            System.out.println("No actions yet.\n");
        }else{
            if (!lastAction1.isEmpty()) System.out.println(lastAction1);
            if (!lastAction2.isEmpty()) System.out.println(lastAction2);
            System.out.println();
        }
    }
    

    private void displaySkills(Characters p1, Characters p2) {
        System.out.println(p1.getName() + "'s Skills:\t\t\t\t" + p2.getName() + "'s Skills:");
        String[] s1 = p1.getSkillNames();
        String[] s2 = p2.getSkillNames();

        for (int i = 0; i < s1.length; i++) {
            String cd1 = (p1.getCooldown(i+1) > 0) ? " (on cooldown " + p1.getCooldown(i+1) + " turn/s)" : "";
            String cd2 = (p2.getCooldown(i+1) > 0) ? " (on cooldown " + p2.getCooldown(i+1) + " turn/s)" : "";

            String left = "[" + (i+1) + "] " + s1[i] + cd1;
            while (left.length() < 60) left += " "; // spacing for alignment
            String right = "[" + (i+1) + "] " + s2[i] + cd2;

            System.out.println(left + right);
        }
        System.out.println("____________________________________________________________________________________________________\n");
    }

    private String executeAction(Characters attacker, Characters defender, int skillNum) {
        if(skillNum == 0) {
            System.out.println("\n" + attacker.getName() + " has exited the battle.");
            ui.pressAnyKey();
            ui.Mainmenu();
        }

        attacker.useSkill(skillNum, defender);

        String skillName = attacker.getSkillNames()[skillNum - 1];
        return "used " + skillName + "!";
    }   



    public void start() {
        System.out.println("\n=== Battle Start! ===");

        while (player1.isAlive() && player2.isAlive()) {
            player1.startTurn();
            player2.startTurn();
            player1.tickShield();
            player2.tickShield();

            ui.clearScreen();
            displayRoundBanner();
            displayHealth();
            displaySkills(player1, player2);

            int choice1;
            while (true) {
                try {
                    System.out.print(player1.getName() + "'s turn. Choose skill (1-4): ");
            
                    choice1 = Integer.parseInt(scan.nextLine());

                    if (choice1 == 0) {
                        System.out.println("Exiting the battle...");
                        ui.pressAnyKey();
                        ui.clearScreen();
                        ui.Mainmenu();
                    }

                    if (choice1 < 1 || choice1 > 4) {
                        System.out.println("Invalid choice! Please enter a number from 1 to 4.\n");
                        continue;
                    }

                    int cd = player1.getCooldown(choice1);
                    if (cd > 0) {
                        System.out.println(player1.getName() + "'s skill " + choice1 + " is on cooldown (" + cd + " turns left). Choose another skill.\n");
                        continue;
                    }

                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Input must be a number. Try again.\n");
                }
            }
            String action1 = executeAction(player1, player2, choice1);

            String action2;
            if (player2.isComputer()) {
                int choice2;

                do {
                    choice2 = (int)(Math.random() * 4) + 1;
                }while(player2.getCooldown(choice2) > 0);

                action2 = executeAction(player2, player1, choice2);
            } else{
                int choice2;
                while (true) {
                    try{
                        System.out.print(player2.getName() + "'s turn. Choose skill (1-4): ");
                        choice2 = Integer.parseInt(scan.nextLine());

                        if(choice2 == 0) {
                            System.out.println("Exiting the battle...");
                            ui.pressAnyKey();
                            ui.clearScreen();
                            ui.Mainmenu();
                        }

                        if(choice2 < 1 || choice2 > 4) {
                            System.out.println("Invalid choice! Please enter a number from 1 to 4.\n");
                            continue;
                        }

                        int cd2 = player2.getCooldown(choice2);
                        if(cd2 > 0) {
                            System.out.println(player2.getName() + "'s skill " + choice2 + " is on cooldown (" + cd2 + " turns left). Choose another skill.\n");
                            continue;
                        }

                        break;
                    }catch(NumberFormatException e) {
                        System.out.println("Input must be a number. Try again.\n");
                    }
                }
            action2 = executeAction(player2, player1, choice2);
            }   
                lastAction1 = player1.getName() + " " + action1;
                lastAction2 = player2.getName() + " " + action2;

                round++;
        }

            System.out.println("\n=== Battle Over! ===");

            if(player1.isAlive()){
                System.out.println(player1.getName() + " wins!");
            }
            else if(player2.isAlive()){
                System.out.println(player2.getName() + " wins!");
            } 
            else{
                System.out.println("Both fighters fell!");
            }
        }
    }
