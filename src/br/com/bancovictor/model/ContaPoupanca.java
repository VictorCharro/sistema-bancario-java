package br.com.bancovictor.model;

import br.com.bancovictor.services.Operacao;
import br.com.bancovictor.services.Tipo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContaPoupanca extends Conta{
    private LocalDateTime ultimorendimento;
    private final double rendimento;

    public ContaPoupanca(int numeroConta, double saldoInicial, int id, String cpf) {
        super(numeroConta, saldoInicial, id, cpf);
        ultimorendimento = LocalDateTime.now();
        rendimento = 0.1;
    }

    public void rendimento() {
        if (ultimorendimento.plusMinutes(1).isBefore(LocalDateTime.now())) {
            historico.add(new Operacao(Tipo.RENDIMENTO, getSaldo()*rendimento));
            saldo += getSaldo() * rendimento;
            ultimorendimento = LocalDateTime.now();
        }
    }

    @Override
    public void exibirInfo() {
        rendimento();
        super.exibirInfo();
        System.out.printf("Rendimento da conta atual em: %.2f%%\n", (rendimento * 100));
        System.out.println("Ultimo rendimento: " + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(ultimorendimento));
    }

    @Override
    public void sacar(double quantidade){
        rendimento();
        super.sacar(quantidade);
    }

    @Override
    public void depositar(double quantidade) {
        rendimento();
        super.depositar(quantidade);
    }

    @Override
    public void transferir(double quantidade, Conta contaDestino) {
        rendimento();
        super.transferir(quantidade, contaDestino);
    }
}