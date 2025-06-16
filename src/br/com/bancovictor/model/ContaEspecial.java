package br.com.bancovictor.model;
import br.com.bancovictor.services.Operacao;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static br.com.bancovictor.services.Tipo.DEPOSITO;
import static br.com.bancovictor.services.Tipo.SAQUE;

public final class ContaEspecial extends Conta {

    private double limiteCredito;   // valor máximo do crédito
    private double creditoDisponivel; // crédito que ainda pode ser usado

    public ContaEspecial(int numeroConta, double saldoInicial, int id, String cpf) {
        super(numeroConta, saldoInicial, id, cpf);
        this.limiteCredito = saldoInicial * 0.1;  // 10% do saldo inicial
        this.creditoDisponivel = limiteCredito;  // crédito começa cheio
    }

    @Override
    public void sacar(double quantidade) {
        if (quantidade <= 0) {
            System.out.println("Digite um valor positivo!");
            return;
        }

        double saldoTotalDisponivel = saldo + creditoDisponivel;

        if (quantidade > saldoTotalDisponivel) {
            System.out.println("Saldo insuficiente, incluindo crédito!");
            return;
        }

        if (quantidade <= saldo) {
            saldo -= quantidade;
        } else {
            // Usar todo o saldo e o restante do crédito
            double restante = quantidade - saldo;
            saldo = 0;
            creditoDisponivel -= restante;
        }

        historico.add(new Operacao(SAQUE, quantidade));
        System.out.printf("Saque de R$%.2f efetuado com sucesso em %s\n", quantidade,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        System.out.printf("Saldo atual: R$%.2f | Crédito disponível: R$%.2f\n", saldo, creditoDisponivel);
    }

    @Override
    public void depositar(double quantidade) {
        if (quantidade <= 0) {
            System.out.println("Digite um valor positivo para depositar!");
            return;
        }

        // Primeiro restaura o crédito utilizado, se houver
        double creditoUsado = limiteCredito - creditoDisponivel;

        if (creditoUsado > 0) {
            if (quantidade <= creditoUsado) {
                creditoDisponivel += quantidade;
                quantidade = 0;
            } else {
                quantidade -= creditoUsado;
                creditoDisponivel = limiteCredito;
            }
        }

        // Depois, o restante vai para o saldo
        saldo += quantidade;

        historico.add(new Operacao(DEPOSITO, quantidade));
        System.out.printf("Depósito de R$%.2f efetuado com sucesso em %s\n", quantidade,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        System.out.printf("Saldo atual: R$%.2f | Crédito disponível: R$%.2f\n", saldo, creditoDisponivel);
    }

    public double getCreditoDisponivel() {
        return creditoDisponivel;
    }

    @Override
    public void exibirInfo() {
        super.exibirInfo();
        System.out.printf("Limite de crédito: R$%.2f\n", limiteCredito);
        System.out.printf("Crédito disponível: R$%.2f\n", creditoDisponivel);
    }
}
