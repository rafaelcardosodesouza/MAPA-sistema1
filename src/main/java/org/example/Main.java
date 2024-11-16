package org.example;

import java.util.Scanner;

public class Main {
    static final Scanner SCANNER = new Scanner(System.in); // Scanner constante para o programa todo

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Jogo da Velha!\nPreparado para iniciar?");
        SCANNER.nextLine(); // Aguarda a entrada do usuário
        iniciarJogo();
    }

    public static void iniciarJogo() {
        try {
            char[][] tabuleiro = {{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}};
            boolean jogoEmAndamento = true;
            int turnos = 0;

            while (jogoEmAndamento) {
                System.out.println("Enter para continuar");
                SCANNER.nextLine();
                limpaTela();
                String jogadorAtual = (turnos % 2 == 0) ? "Player 1" : "Player 2";
                System.out.println("Turno de: " + jogadorAtual);
                imprimirTabuleiro(tabuleiro);

                System.out.println("Digite a posição (ex: a1):");
                String jogada = SCANNER.nextLine();

                if (processarJogada(jogada, jogadorAtual, tabuleiro)) {
                    turnos++;

                    if (verificaVitoria(tabuleiro, jogadorAtual)) {
                        limpaTela();
                        imprimirTabuleiro(tabuleiro);
                        System.out.println(jogadorAtual + " venceu!");
                        jogoEmAndamento = false;
                    } else if (turnos == 9) { // Verifica empate
                        limpaTela();
                        imprimirTabuleiro(tabuleiro);
                        System.out.println("Empate! O tabuleiro está cheio.");
                        jogoEmAndamento = false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    public static boolean processarJogada(String jogada, String jogadorAtual, char[][] tabuleiro) {
        if (jogada.length() != 2) {
            System.out.println("Entrada inválida. Use o formato a1, b2, etc.");
            return false;
        }

        int linha = jogada.charAt(0) - 'a';
        int coluna = jogada.charAt(1) - '1';

        if (linha < 0 || linha >= 3 || coluna < 0 || coluna >= 3) {
            System.out.println("Posição fora dos limites. Tente novamente.");
            return false;
        }

        if (tabuleiro[linha][coluna] != '-') {
            System.out.println("Posição já ocupada. Escolha outra.");
            return false;
        }

        tabuleiro[linha][coluna] = jogadorAtual.equals("Player 1") ? 'X' : 'O';
        return true;
    }

    public static boolean verificaVitoria(char[][] tabuleiro, String jogadorAtual) {
        char simbolo = jogadorAtual.equals("Player 1") ? 'X' : 'O';

        // Verifica linhas e colunas
        for (int i = 0; i < 3; i++) {
            if ((tabuleiro[i][0] == simbolo && tabuleiro[i][1] == simbolo && tabuleiro[i][2] == simbolo) ||
                    (tabuleiro[0][i] == simbolo && tabuleiro[1][i] == simbolo && tabuleiro[2][i] == simbolo)) {
                return true;
            }
        }

        // Verifica diagonais
        return (tabuleiro[0][0] == simbolo && tabuleiro[1][1] == simbolo && tabuleiro[2][2] == simbolo) ||
                (tabuleiro[0][2] == simbolo && tabuleiro[1][1] == simbolo && tabuleiro[2][0] == simbolo);
    }

    public static void imprimirTabuleiro(char[][] tabuleiro) {
        System.out.println("  1 2 3"); // Cabeçalho das colunas
        char[] linhas = {'a', 'b', 'c'};
        for (int i = 0; i < tabuleiro.length; i++) {
            System.out.print(linhas[i] + " ");
            for (int j = 0; j < tabuleiro[i].length; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void limpaTela() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Não foi possível limpar a tela.");
        }
    }
}
