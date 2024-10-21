package org.example;

import java.util.Scanner;

public class Main {
    static Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bem vindo ao jogo da velha\nPreparado para iniciar?");
        SC.nextLine();
        jogo();
    }

    public static void jogo() {
        boolean controle = true;
        int contador = 0;
        char[][] tabuleiro = {{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}};

        while (controle) {
            limpaTela();
            String jogadorAtual = (contador % 2 == 0) ? "Player 1" : "Player 2";
            System.out.println("Turno de: " + jogadorAtual);
            imprimirTabuleiro(tabuleiro);

            System.out.println("Digite Linha x Coluna (ex: A2)");
            String playerEscolha = SC.nextLine();

            if (verificadorJogada(playerEscolha, jogadorAtual, tabuleiro)) {
                contador++;

                if (verificaVitoria(tabuleiro, jogadorAtual)) {
                    imprimirTabuleiro(tabuleiro);
                    System.out.println(jogadorAtual + " venceu!");
                    controle = false;
                } else if (contador == 9) { // Verifica se o jogo está empatado
                    imprimirTabuleiro(tabuleiro);
                    System.out.println("Empate!");
                    controle = false;
                }
            }
        }
    }

    public static boolean verificadorJogada(String playerEscolha, String jogadorAtual, char[][] tabuleiro) {
        if (playerEscolha.length() != 2) {
            System.out.println("Entrada inválida. Use o formato A1, B2, etc.");
            return false;
        }

        int linha = playerEscolha.charAt(0) - 'a'; // Corrigido para charAt(0)
        int coluna = playerEscolha.charAt(1) - '1';

        // Verifica se os índices estão dentro dos limites
        if (linha < 0 || linha >= tabuleiro.length || coluna < 0 || coluna >= tabuleiro[0].length) {
            System.out.println("Posição inválida, escolha outra.");
            return false;
        }

        // Verifica se a posição está vazia
        if (tabuleiro[linha][coluna] == '-') {
            tabuleiro[linha][coluna] = jogadorAtual.equals("Player 1") ? 'X' : 'O';
            return true; // Retorna true para indicar que a jogada foi válida
        } else {
            System.out.println("Posição já ocupada, escolha outra.");
            return false; // Retorna false para indicar que a jogada não foi válida
        }
    }

    public static boolean verificaVitoria(char[][] tabuleiro, String jogadorAtual) {
        char simbolo = jogadorAtual.equals("Player 1") ? 'X' : 'O';

        // Verifica linhas e colunas
        for (int i = 0; i < 3; i++) {
            if ((tabuleiro[i][0] == simbolo && tabuleiro[i][1] == simbolo && tabuleiro[i][2] == simbolo) || // Linha
                    (tabuleiro[0][i] == simbolo && tabuleiro[1][i] == simbolo && tabuleiro[2][i] == simbolo)) { // Coluna
                return true; // Vitória encontrada
            }
        }

        // Verifica diagonais
        if ((tabuleiro[0][0] == simbolo && tabuleiro[1][1] == simbolo && tabuleiro[2][2] == simbolo) || // Diagonal principal
                (tabuleiro[0][2] == simbolo && tabuleiro[1][1] == simbolo && tabuleiro[2][0] == simbolo)) { // Diagonal secundária
            return true; // Vitória encontrada
        }

        return false; // Nenhuma vitória
    }

    public static void imprimirTabuleiro(char[][] tabuleiro) {
        System.out.println("  1 2 3"); // Imprimir os números das colunas
        char[] linhas = {'a', 'b', 'c'}; // Letras das linhas
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
            // Verifica o sistema operacional
            String sistema = System.getProperty("os.name");

            if (sistema.contains("Windows")) {
                // Executa o comando 'cls' no Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Executa o comando 'clear' no Linux e MacOS
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Em caso de erro, exibe a mensagem
            System.out.println("Erro ao limpar o terminal: " + e.getMessage());
        }
    }
}