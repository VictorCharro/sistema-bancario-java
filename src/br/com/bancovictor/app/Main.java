package br.com.bancovictor.app;
import br.com.bancovictor.exceptions.ContaNaoEncontradaException;
import br.com.bancovictor.exceptions.CpfDuplicadoException;
import br.com.bancovictor.exceptions.SaldoInsuficienteException;
import br.com.bancovictor.exceptions.TipoContaInvalidoException;
import br.com.bancovictor.model.*;
import br.com.bancovictor.ui.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Conta> dadosConta = new ArrayList<>();
        ArrayList<Cliente> dadosCliente = new ArrayList<>();

        double quantidade;
        int contaTemporaria;

        Menu menu = new Menu();
        while (true) {
            menu.menuPrincipal();

            System.out.print("Digite sua opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    try {
                        System.out.print("Essa conta é Corrente, Poupança ou Especial? (C/P/E): ");
                        char tipoConta = sc.nextLine().trim().toUpperCase().charAt(0);
                        if (tipoConta != 'C' && tipoConta != 'P' && tipoConta != 'E'){
                            throw new TipoContaInvalidoException("Tipo de conta inválido! Use C, P ou E.");
                        }

                        System.out.print("\nNome: ");
                        String nome = sc.nextLine();

                        String cpf = Cliente.lerCPF(sc);

                        for (Cliente c : dadosCliente) {
                            if (c.getCpf().equals(cpf)) {
                                throw new CpfDuplicadoException("Já existe uma conta cadastrada com esse CPF");
                            }
                        }

                        System.out.print("E-mail: ");
                        String email = sc.nextLine();

                        int numeroConta = dadosCliente.size() + 1;
                        int id = dadosConta.size() + 1;

                        System.out.print("Saldo inicial: R$");
                        double saldoInicial = sc.nextDouble();

                        Cliente novoCliente = new Cliente(nome, cpf, email);
                        dadosCliente.add(novoCliente);

                        Conta novaConta;
                        if (tipoConta == 'C') {
                            novaConta = new ContaCorrente(numeroConta, saldoInicial, id, cpf);
                        } else if (tipoConta == 'P') {
                            novaConta = new ContaPoupanca(numeroConta, saldoInicial, id, cpf);
                        } else {
                            novaConta = new ContaEspecial(numeroConta, saldoInicial, id, cpf);
                        }
                        dadosConta.add(novaConta);
                        System.out.println("Conta criada com sucesso!");
                        novaConta.exibirInfo();
                    }
                    catch (CpfDuplicadoException | TipoContaInvalidoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("\nDigite o numero da conta para depositar: ");
                        contaTemporaria = sc.nextInt();
                        Conta contaParaDeposito = Conta.buscarConta(dadosConta, contaTemporaria);

                        System.out.print("Digite o valor a ser depositado: R$");
                        quantidade = sc.nextDouble();
                        contaParaDeposito.depositar(quantidade);
                    }
                    catch (ContaNaoEncontradaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("\nDigite o número da conta para sacar: ");
                        contaTemporaria = sc.nextInt();
                        Conta contaParaSaque = Conta.buscarConta(dadosConta, contaTemporaria);

                        System.out.print("Digite o valor a ser sacado: R$");
                        quantidade = sc.nextDouble();

                        contaParaSaque.sacar(quantidade);
                    }
                    catch (ContaNaoEncontradaException | SaldoInsuficienteException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;


                case 4:
                    try {
                    System.out.print("\nDigite o número da conta: ");
                    contaTemporaria = sc.nextInt();
                    Conta contaParaExtrato = Conta.buscarConta(dadosConta, contaTemporaria);

                        contaParaExtrato.exibirExtrato();
                    }
                    catch (ContaNaoEncontradaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                    System.out.print("\nDigite o número da conta: ");
                    contaTemporaria = sc.nextInt();
                    Conta contaParaInfo = Conta.buscarConta(dadosConta, contaTemporaria);

                        contaParaInfo.exibirInfo();
                    }
                    catch (ContaNaoEncontradaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 6:
                    try {
                        System.out.print("Digite o numero da conta para saida de valor: ");
                        contaTemporaria = sc.nextInt();
                        Conta contaSaida = Conta.buscarConta(dadosConta, contaTemporaria);

                        System.out.print("Digite o numero da conta para destino de valor: ");
                        contaTemporaria = sc.nextInt();
                        Conta contaDestino = Conta.buscarConta(dadosConta, contaTemporaria);

                        System.out.print("Digite o valor para transferência: R$");
                        quantidade = sc.nextDouble();

                        contaSaida.transferir(quantidade, contaDestino);
                    }
                    catch (ContaNaoEncontradaException | SaldoInsuficienteException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 0:
                    sc.close();
                    System.exit(0);
            }
        }
    }
}
