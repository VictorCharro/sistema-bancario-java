package br.com.bancovictor.app;
import br.com.bancovictor.model.Cliente;
import br.com.bancovictor.model.Conta;
import br.com.bancovictor.model.ContaEspecial;
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

            switch (opcao) {
                case 1:
                    System.out.print("Essa conta é especial? (S/N): ");
                    String especial = sc.next().trim().toUpperCase();

                    if (especial.equals("S")) {
                        System.out.print("\nNome: ");
                        sc.nextLine();
                        String nome = sc.nextLine();

                        System.out.print("CPF: ");
                        long cpf = sc.nextInt();

                        boolean cpfExiste = false;
                        for (Cliente c : dadosCliente) {
                            if (c.getCpf() == cpf) {
                                cpfExiste = true;
                                break;
                            }
                        }
                        if (cpfExiste) {
                            System.out.println("ERRO: Já existe uma conta cadastrada com esse CPF.");
                            break;
                        }

                        System.out.print("E-mail: ");
                        sc.nextLine();
                        String email = sc.nextLine();

                        int numeroConta = dadosCliente.size() + 1;

                        System.out.print("Saldo inicial: R$");
                        double saldo = sc.nextDouble();

                        int id = dadosConta.size() + 1;

                        ContaEspecial novaConta = new ContaEspecial(numeroConta, saldo, id, cpf);
                        dadosConta.add(novaConta);

                        System.out.println("Conta criada com sucesso!");
                        novaConta.exibirInfo();
                        break;
                    }
                    else {
                    System.out.print("\nNome: ");
                    sc.nextLine();
                    String nome = sc.nextLine();

                    System.out.print("CPF: ");
                    long cpf = sc.nextInt();

                    boolean cpfExiste = false;
                    for (Cliente c : dadosCliente) {
                        if (c.getCpf() == cpf) {
                            cpfExiste = true;
                            break;
                        }
                    }
                    if (cpfExiste) {
                        System.out.println("ERRO: Já existe uma conta cadastrada com esse CPF.");
                        break;
                    }

                    System.out.print("E-mail: ");
                    sc.nextLine();
                    String email = sc.nextLine();

                    Cliente novoCliente = new Cliente(nome, cpf, email);
                    dadosCliente.add(novoCliente);

                    int numeroConta = dadosCliente.size() + 1;

                    System.out.print("Saldo inicial: R$");
                    double saldo = sc.nextDouble();

                    int id = dadosConta.size() + 1;

                    Conta novaConta = new Conta(numeroConta, saldo, id, cpf);
                    dadosConta.add(novaConta);

                    System.out.println("Conta criada com sucesso!");
                    novaConta.exibirInfo();
                    break;
                    }

                case 2:
                    System.out.print("\nDigite o numero da conta para depositar: ");
                    contaTemporaria = sc.nextInt();
                    Conta contaParaDeposito = Conta.buscarConta(dadosConta, contaTemporaria);

                    if (contaParaDeposito != null) {
                        System.out.print("Digite o valor a ser depositado: R$");
                        quantidade = sc.nextDouble();
                        contaParaDeposito.depositar(quantidade);
                    } else {
                        System.out.println("ERRO: Conta inexistente");
                    }
                    break;

                case 3:
                    System.out.print("\nDigite o número da conta para sacar: ");
                    contaTemporaria = sc.nextInt();
                    Conta contaParaSaque = Conta.buscarConta(dadosConta, contaTemporaria);

                    if (contaParaSaque != null) {
                        System.out.print("Digite o valor a ser sacado: R$");
                        quantidade = sc.nextDouble();
                        contaParaSaque.sacar(quantidade);
                    } else {
                        System.out.println("ERRO: Conta inexistente");
                    }
                    break;

                case 4:
                    System.out.print("\nDigite o número da conta: ");
                    contaTemporaria = sc.nextInt();
                    Conta contaParaExtrato = Conta.buscarConta(dadosConta, contaTemporaria);

                    if (contaParaExtrato != null) {
                        contaParaExtrato.exibirExtrato();
                    } else {
                        System.out.println("ERRO: Conta inexistente");
                    }
                    break;

                case 5:
                    System.out.print("\nDigite o número da conta: ");
                    contaTemporaria = sc.nextInt();
                    Conta contaParaInfo = Conta.buscarConta(dadosConta, contaTemporaria);

                    if (contaParaInfo != null) {
                        contaParaInfo.exibirInfo();
                    } else {
                        System.out.println("ERRO: Conta inexistente");
                    }
                    break;

                case 6:
                    System.out.print("Digite o numero da conta para saida de valor: ");
                    contaTemporaria = sc.nextInt();
                    Conta contaSaida = Conta.buscarConta(dadosConta, contaTemporaria);

                    if (contaSaida != null) {
                        System.out.print("Digite o numero da conta para destino de valor: ");
                        contaTemporaria = sc.nextInt();
                        Conta contaDestino = Conta.buscarConta(dadosConta, contaTemporaria);
                        if (contaDestino != null) {
                            System.out.print("Digite o valor para transferência: R$");
                            quantidade = sc.nextDouble();
                            contaSaida.transferir(quantidade, contaDestino);
                        }
                        else {
                            System.out.println("ERRO: Conta inexistente");
                        }
                        break;

                    } else {
                        System.out.println("ERRO: Conta inexistente");
                    }
                    break;

                case 0:
                    sc.close();
                    System.exit(0);
            }
        }
    }
}