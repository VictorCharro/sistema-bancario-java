package br.com.bancovictor.services;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Operacao {
    private Tipo tipo;
    private double valor;
    private LocalDateTime dataHora;

    public Operacao(Tipo tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = LocalDateTime.now();
    }

    public String getDescricao() {
        DateTimeFormatter formatado = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH'h'mm");

        if (tipo == Tipo.TRANSFERENCIA_REALIZADA) {
            return "\u001B[91mTransferência realizada no valor de R$" + String.format("%.2f", valor) + " em " + dataHora.format(formatado) + "\u001B[0m";
        } else if (tipo == Tipo.TRANSFERENCIA_RECEBIDA) {
            return "\u001B[32mTransferência recebida no valor de R$" + String.format("%.2f", valor) + " em " + dataHora.format(formatado) + "\u001B[0m";
        } else {
            return tipo.name() + " de R$" + String.format("%.2f", valor) + " em " + dataHora.format(formatado);
        }
    }
}