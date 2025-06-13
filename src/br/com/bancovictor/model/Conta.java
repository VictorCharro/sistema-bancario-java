package br.com.bancovictor.model;
import br.com.bancovictor.services.Operacao;
import br.com.bancovictor.services.Tipo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static br.com.bancovictor.services.Tipo.*;

public class Conta {

    private int numeroConta;
    protected double saldo;
    private int id;
    public LocalDateTime dataCriacao;
    protected ArrayList<Operacao> historico;
    private String cpfCliente;

    public Conta(int numeroConta, double saldoInicial, int id, String cpf) {
        this.numeroConta = numeroConta;
        this.saldo = saldoInicial;
        this.id = id;
        this.dataCriacao = LocalDateTime.now();
        this.historico = new ArrayList<>();
        this.cpfCliente = cpf;
    }

    public void depositar(double quantidade) {
        if (quantidade > 0) {
            this.saldo += quantidade;
            historico.add(new Operacao(DEPOSITO, quantidade));
            System.out.printf("Depósito de R$%.2f efetuado com sucesso em %s\n", quantidade,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            System.out.printf("Saldo atual: R$%.2f\n", this.saldo);
        } else {
            System.out.println("ERRO! Digite um valor positivo para depositar!");
        }
    }

    public void sacar(double quantidade) {
        if (quantidade <= saldo && quantidade > 0) {
            this.saldo -= quantidade;
            historico.add(new Operacao(SAQUE, quantidade));
            System.out.printf("Saque de R$%.2f efetuado com sucesso em %s\n", quantidade,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            System.out.printf("Saldo atual: R$%.2f\n", this.saldo);
        } else if (quantidade > saldo) {
            System.out.printf("Saldo insuficiente! Saldo atual: R$ %.2f%n", getSaldo());
        } else {
            System.out.println("Digite um valor positivo!");
        }
    }

    public void exibirSaldo() {
        System.out.printf("Saldo atual: R$%.2f\n", this.saldo);
    }

    public void exibirInfo() {
        System.out.println("\nID: " + id);
        System.out.println("Numero da conta: " + numeroConta);
        System.out.printf("Saldo: %.2f", saldo);
        System.out.println("\nData de criação: " + dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH'h'mm")));
    }

    public void exibirExtrato() {
        System.out.println("Extrato da conta " + numeroConta);
        if (historico.isEmpty()) {
            System.out.println("Nenhuma operação realizada ainda!");
        } else {
            for (Operacao op : historico) {
                System.out.println(op.getDescricao());
            }
        }
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public int getId() {
        return id;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public static Conta buscarConta(ArrayList<Conta> contas, int numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    public void transferir(double quantidade, Conta contaDestino) {
        if (quantidade <= 0) {
            System.out.println("Digite um valor positivo!");
            return;
        }

        // Verifica saldo disponível considerando crédito se for ContaEspecial
        double saldoDisponivel = saldo;
        if (this instanceof ContaEspecial) {
            saldoDisponivel += ((ContaEspecial) this).getCreditoDisponivel();
        }

        if (saldoDisponivel < quantidade) {
            System.out.println("SALDO DA CONTA INSUFICIENTE PARA TRANSFERIR!");
            return;
        }

        sacar(quantidade);
        contaDestino.depositar(quantidade);

        historico.add(new Operacao(TRANSFERENCIA_REALIZADA, quantidade));
        contaDestino.historico.add(new Operacao(TRANSFERENCIA_RECEBIDA, quantidade));
        System.out.printf("Transferência de R$%.2f para a conta %d realizada com sucesso.\n", quantidade, contaDestino.getNumeroConta());
    }
}