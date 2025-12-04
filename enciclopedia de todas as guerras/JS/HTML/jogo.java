import java.util.Scanner;
import java.util.Random;

public class SimpleRPG {
    // Atributos do jogador
    private static int playerHealth = 100;
    private static int playerAttack = 15;
    private static int playerDefense = 5;
    private static int playerLevel = 1;
    private static int experience = 0;
    private static int gold = 50;
    
    // Itens do jogador
    private static boolean hasPotion = true;
    private static boolean hasSword = false;
    
    // Scanner para input
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    
    public static void main(String[] args) {
        System.out.println("=== SIMPLE RPG GAME ===");
        System.out.println("Bem-vindo, aventureiro!");
        
        boolean playing = true;
        
        while (playing && playerHealth > 0) {
            showStatus();
            int choice = showMainMenu();
            
            switch (choice) {
                case 1:
                    explore();
                    break;
                case 2:
                    visitShop();
                    break;
                case 3:
                    usePotion();
                    break;
                case 4:
                    System.out.println("Até a próxima, aventureiro!");
                    playing = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
            
            // Verificar se subiu de nível
            checkLevelUp();
        }
        
        if (playerHealth <= 0) {
            System.out.println("💀 Você foi derrotado! Game Over!");
        }
        
        scanner.close();
    }
    
    private static void showStatus() {
        System.out.println("\n=== SEU STATUS ===");
        System.out.println("❤️  Vida: " + playerHealth + "/100");
        System.out.println("⚔️  Ataque: " + playerAttack);
        System.out.println("🛡️  Defesa: " + playerDefense);
        System.out.println("⭐ Nível: " + playerLevel);
        System.out.println("📊 XP: " + experience + "/" + (playerLevel * 50));
        System.out.println("💰 Ouro: " + gold);
        System.out.println("🧪 Poção: " + (hasPotion ? "Sim" : "Não"));
        System.out.println("⚔️ Espada: " + (hasSword ? "Sim" : "Não"));
    }
    
    private static int showMainMenu() {
        System.out.println("\n=== O QUE VOCÊ QUER FAZER? ===");
        System.out.println("1. 🗺️ Explorar");
        System.out.println("2. 🏪 Visitar Loja");
        System.out.println("3. 🧪 Usar Poção");
        System.out.println("4. 🚪 Sair do Jogo");
        
        System.out.print("Escolha uma opção: ");
        return scanner.nextInt();
    }
    
    private static void explore() {
        System.out.println("\n=== EXPLORANDO ===");
        
        // Chance de encontrar algo
        int encounter = random.nextInt(100);
        
        if (encounter < 40) {
            // Encontrou um monstro
            fightMonster();
        } else if (encounter < 70) {
            // Encontrou um tesouro
            findTreasure();
        } else {
            // Nada acontece
            System.out.println("Você explorou a área mas não encontrou nada interessante...");
        }
    }
    
    private static void fightMonster() {
        String[] monsters = {"Goblin", "Orc", "Esqueleto", "Aranha Gigante"};
        String monster = monsters[random.nextInt(monsters.length)];
        
        int monsterHealth = 30 + (playerLevel * 10);
        int monsterAttack = 10 + (playerLevel * 3);
        int monsterDefense = 3 + playerLevel;
        int monsterGold = 10 + (playerLevel * 5);
        int monsterXP = 20 + (playerLevel * 8);
        
        System.out.println("💀 Um " + monster + " selvagem apareceu!");
        System.out.println("Vida do " + monster + ": " + monsterHealth);
        
        boolean fighting = true;
        
        while (fighting && playerHealth > 0 && monsterHealth > 0) {
            System.out.println("\n=== COMBATE ===");
            System.out.println("Sua Vida: " + playerHealth);
            System.out.println(monster + " Vida: " + monsterHealth);
            System.out.println("1. ⚔️ Atacar");
            System.out.println("2. 🏃 Fugir");
            
            System.out.print("Escolha: ");
            int choice = scanner.nextInt();
            
            if (choice == 1) {
                // Jogador ataca
                int playerDamage = Math.max(1, (playerAttack + random.nextInt(10)) - monsterDefense);
                monsterHealth -= playerDamage;
                System.out.println("⚔️ Você causou " + playerDamage + " de dano no " + monster + "!");
                
                // Monstro ataca (se ainda estiver vivo)
                if (monsterHealth > 0) {
                    int monsterDamage = Math.max(1, (monsterAttack + random.nextInt(8)) - playerDefense);
                    playerHealth -= monsterDamage;
                    System.out.println("💀 " + monster + " causou " + monsterDamage + " de dano em você!");
                }
                
            } else if (choice == 2) {
                // Tentar fugir
                if (random.nextInt(100) < 50) {
                    System.out.println("🏃 Você fugiu com sucesso!");
                    fighting = false;
                } else {
                    System.out.println("❌ Falha ao fugir! O " + monster + " ataca!");
                    int monsterDamage = Math.max(1, (monsterAttack + random.nextInt(8)) - playerDefense);
                    playerHealth -= monsterDamage;
                    System.out.println("💀 " + monster + " causou " + monsterDamage + " de dano em você!");
                }
            }
        }
        
        if (monsterHealth <= 0) {
            System.out.println("\n🎉 Você derrotou o " + monster + "!");
            System.out.println("💰 Ganhou " + monsterGold + " de ouro!");
            System.out.println("⭐ Ganhou " + monsterXP + " de experiência!");
            
            gold += monsterGold;
            experience += monsterXP;
        }
    }
    
    private static void findTreasure() {
        int treasureType = random.nextInt(100);
        
        if (treasureType < 30) {
            // Ouro
            int foundGold = 5 + random.nextInt(20);
            System.out.println("💰 Você encontrou " + foundGold + " de ouro!");
            gold += foundGold;
        } else if (treasureType < 60) {
            // Poção
            System.out.println("🧪 Você encontrou uma poção de cura!");
            hasPotion = true;
        } else if (treasureType < 80) {
            // Arma
            if (!hasSword) {
                System.out.println("⚔️ Você encontrou uma espada! (+5 de ataque)");
                playerAttack += 5;
                hasSword = true;
            } else {
                System.out.println("🛡️ Você encontrou uma armadura melhor! (+3 de defesa)");
                playerDefense += 3;
            }
        } else {
            // Tesouro raro
            System.out.println("💎 TESOURO RARO ENCONTRADO!");
            System.out.println("💰 +50 de ouro!");
            System.out.println("⭐ +30 de experiência!");
            gold += 50;
            experience += 30;
        }
    }
    
    private static void visitShop() {
        System.out.println("\n=== 🏪 LOJA DO MERCADOR ===");
        System.out.println("💰 Seu ouro: " + gold);
        System.out.println("1. 🧪 Poção de Cura - 20 ouros");
        System.out.println("2. ⚔️ Espada Melhor - 50 ouros");
        System.out.println("3. 🛡️ Armadura Melhor - 40 ouros");
        System.out.println("4. Voltar");
        
        System.out.print("O que deseja comprar? ");
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                if (gold >= 20) {
                    gold -= 20;
                    hasPotion = true;
                    System.out.println("🧪 Você comprou uma poção de cura!");
                } else {
                    System.out.println("❌ Ouro insuficiente!");
                }
                break;
            case 2:
                if (gold >= 50) {
                    gold -= 50;
                    playerAttack += 8;
                    System.out.println("⚔️ Você comprou uma espada melhor! +8 de ataque!");
                } else {
                    System.out.println("❌ Ouro insuficiente!");
                }
                break;
            case 3:
                if (gold >= 40) {
                    gold -= 40;
                    playerDefense += 6;
                    System.out.println("🛡️ Você comprou uma armadura melhor! +6 de defesa!");
                } else {
                    System.out.println("❌ Ouro insuficiente!");
                }
                break;
            case 4:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
    
    private static void usePotion() {
        if (hasPotion) {
            int healAmount = 30 + random.nextInt(20);
            playerHealth = Math.min(100, playerHealth + healAmount);
            hasPotion = false;
            System.out.println("🧪 Você usou uma poção e recuperou " + healAmount + " de vida!");
            System.out.println("❤️ Vida atual: " + playerHealth);
        } else {
            System.out.println("❌ Você não tem poções!");
        }
    }
    
    private static void checkLevelUp() {
        int xpNeeded = playerLevel * 50;
        
        if (experience >= xpNeeded) {
            playerLevel++;
            experience = 0;
            playerHealth = 100; // Cura completa ao subir de nível
            playerAttack += 3;
            playerDefense += 2;
            
            System.out.println("\n🎉🎉🎉 LEVEL UP! 🎉🎉🎉");
            System.out.println("⭐ Agora você é nível " + playerLevel + "!");
            System.out.println("⚔️ Ataque: +3");
            System.out.println("🛡️ Defesa: +2");
            System.out.println("❤️ Vida totalmente recuperada!");
        }
    }
}