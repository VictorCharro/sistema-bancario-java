package br.com.bancovictor.model;

import java.util.Scanner;

public class Cliente {

    private String nome;
    private final String cpf;
    private String email;

    public Cliente(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf=" + cpf +
                ", email='" + email + '\'' +
                '}';
    }

    public static String lerCPF(String next) {
        Scanner sc = new Scanner(System.in);
        String cpf;
        while (true) {
            System.out.print("Digite o CPF (apenas números, 11 dígitos): ");
            cpf = sc.nextLine().trim();

            // Valida se tem 11 dígitos e só contém números
            if (cpf.matches("\\d{11}")) {
                return cpf;
            } else {
                System.out.println("\u001B[31mCPF inválido! Deve conter exatamente 11 dígitos numéricos.\u001B[0m");
            }
        }
    }
}