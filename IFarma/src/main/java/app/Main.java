package app;

import java.util.Scanner;

import app.modules.ClienteModule;
import app.modules.MedicamentoModule;
import core.utils.StringUtils;

public class Main {
    private static double valorCaixa = 500;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean lastActionWasSuccess = true;
        System.out.println(StringUtils.removeCPFSymbols("111.111.111-11"));
        while (true) {
            if (lastActionWasSuccess) {
                showMenu();
            }
            System.out.print("> ");
            int option = Integer.parseInt(scanner.nextLine());
            if (option == 11) {
                break;
            }
            System.out.println();
            boolean wasSuccess = lastActionWasSuccess = execute(option);
            if (!wasSuccess) {
                System.out.println("\nVocê deve escolher uma opção válida!\n");
            }
        }
        scanner.close();
        System.out.println("\nAté mais :D!");
    }

    private static boolean execute(int option) {
        switch (option) {
            case 1:
                MedicamentoModule.list();
                break;
            case 3:
                ClienteModule.list();
                break;
            case 4:
                ClienteModule.create();
                break;
            case 5:
                ClienteModule.delete();
            case 10:
                showSaldo();
                break;
            default:
                return false;
        }
        System.out.println("\n-------------------------\n\n");
        return true;
    }

    private static void showMenu() {
        System.out.println("*-----------------------------------------*");
        System.out.println("|                                         |");
        System.out.println("|                FARMA HELL               |");
        System.out.println("|                                         |");
        System.out.println("|  1  - Listar Medicamentos               |  x");
        System.out.println("|  2  - Vender Medicamento                |");
        System.out.println("|  3  - Listar Clientes                   |  x");
        System.out.println("|  4  - Cadastrar Cliente                 |  x");
        System.out.println("|  5  - Excluir Cliente                   |  x");
        System.out.println("|  6  - Listar vendas                     |");
        System.out.println("|  7  - Listar fornecedores               |");
        System.out.println("|  8  - Cadastrar compra de fornecedor    |");
        System.out.println("|  9  - Listar compras de fornecedor      |");
        System.out.println("|  10 - Ver saldo do caixa                |  x");
        System.out.println("|  11 - Sair                              |  x");
        System.out.println("|                                         |");
        System.out.println("*-----------------------------------------*");
    }

    public static Scanner getScanner() {
        return scanner;
    }

    private static void showSaldo() {
        System.out.println("O saldo atual do caixa é de R$ " + valorCaixa + ".");
    }

    // private static void increaseSaldo(double valor) {
    // valorCaixa += valor;
    // }

    // private static void decreaseSaldo(double valor) {
    // valorCaixa -= valor;
    // }
}
