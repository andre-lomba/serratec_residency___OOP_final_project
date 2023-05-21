package main.java.org.serratec.supermecado.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import main.java.org.serratec.supermecado.common.Util;
import main.java.org.serratec.supermecado.model.Cliente;
import main.java.org.serratec.supermecado.model.Pedido;
import main.java.org.serratec.supermecado.model.PedidoItens;
import main.java.org.serratec.supermecado.model.Produto;
import main.java.org.serratec.supermecado.repository.ClienteDAO;
import main.java.org.serratec.supermecado.repository.PedidoDAO;
import main.java.org.serratec.supermecado.repository.ProdutoDAO;

public class VendasService {

    public void menu(Connection c) throws SQLException, InterruptedException {

        Scanner sc = new Scanner(System.in);
        char opcao;
        boolean continua = true;

        do {
            Util.cabecalho();
            Util.menuItens();
            System.out.print("Opção: ");
            try {
                opcao = sc.nextLine().charAt(0);
            } catch (Exception e) {
                opcao = 'R';
            }

            switch (opcao) {
                case '1': {
                    cadastrarPedido(c);
                    break;
                }
                case '2': {
                    pesquisarPedidos(c);
                    break;
                }
                case '0': {
                    System.out.println("\nObrigado por utilizar o sistema!");
                    Thread.sleep(1500);
                    System.out.println("Programa encerrado.");
                    continua = false;
                    break;
                }
                default: {
                    System.out.println("Opção inválida. Tente novamente");
                    break;
                }
            }
        } while (continua);

        sc.close();

    }

    public void cadastrarPedido(Connection c) {

        Scanner sc = new Scanner(System.in);
        char opcao;
        boolean continua = true;
        int codCliente, codProd, quantidade;
        ClienteDAO consultaCliente = new ClienteDAO();
        ProdutoDAO consultaProduto = new ProdutoDAO();
        PedidoDAO registraPedido = new PedidoDAO();
        ArrayList<PedidoItens> itens = new ArrayList<>();

        do {
            consultaCliente.listarClientes(c);
            System.out.print("Informe o código do novo cliente: ");
            try {
                codCliente = sc.nextInt();
            } catch (Exception e) {
                codCliente = 0;
            }
            sc.nextLine();
            if (codCliente > 0 && codCliente <= 6) {
                Cliente cliente = new Cliente();
                cliente.setIdcliente(codCliente);
                Pedido pedido = new Pedido();
                pedido.setCliente(cliente);
                do {
                    consultaProduto.listarProdutos(c);
                    System.out.print("Informe o código do produto: ");
                    try {
                        codProd = sc.nextInt();
                    } catch (Exception e) {
                        codProd = 0;
                    }
                    sc.nextLine();
                    if (codProd > 0 && codProd <= 5) {
                        Produto produto = new Produto();
                        produto.setIdProduto(codProd);
                        PedidoItens item = new PedidoItens();
                        item.setProduto(produto);
                        do {
                            System.out.print("Informe a quantidade: ");
                            try {
                                quantidade = sc.nextInt();
                            } catch (Exception e) {
                                quantidade = 0;
                            }
                            sc.nextLine();
                            if (quantidade > 0) {

                                item.setQtdProduto(quantidade);
                                itens.add(item);
                                System.out.println("Deseja adicionar mais produtos? (s/n)");
                                try {
                                    opcao = sc.nextLine().charAt(0);
                                } catch (Exception e) {
                                    opcao = 'R';
                                }
                                if (opcao == 'n' || opcao == 'N') {
                                    pedido.setDataEmissao(definirEmissao());
                                    pedido.setDataEntrega(definirEntrega(pedido.getDataEmissao()));
                                    int codPed = registraPedido.cadastrarPedido(c, pedido);
                                    for (PedidoItens pedidoItens : itens) {
                                        registraPedido.cadastrarPedidoCompleto(c, codPed, pedidoItens);
                                        continua = false;
                                    }
                                    System.out.println("");
                                    registraPedido.detalharPedido(c, pedido.getIdPedido());

                                } else {
                                    codProd = 0;
                                }
                            } else {
                                System.out.println("Informe uma quantidade válida.");
                            }
                        } while (quantidade <= 0);
                    } else {
                        codProd = 0;
                        System.out.println("Informe um código de produto válido.");
                    }
                } while (codProd == 0);

            } else {
                System.out.println("Informe um código de cliente válido.");
            }

        } while (continua);

    }

    private Date definirEmissao() {

        Scanner sc = new Scanner(System.in);
        String dataEmissaoS;
        boolean continua = true;

        do {
            Date dataEmissaoD;
            System.out.print("Informe a data de emissão (dd/MM/yyyy):  ");
            try {
                dataEmissaoS = sc.nextLine();
                dataEmissaoD = Util.converterStringParaDate(dataEmissaoS);
            } catch (Exception e) {
                dataEmissaoD = null;
            }
            if (dataEmissaoD != null) {
                continua = false;
                return dataEmissaoD;
            } else {
                System.out.println("Informe uma data válida.");
                return null;
            }
        } while (continua);

    }

    private Date definirEntrega(Date dataEmissao) {

        Scanner sc = new Scanner(System.in);
        String dataEntregaS;
        boolean continua = true;

        do {
            Date dataEntregaD;
            System.out.print("Informe a data de entrega (dd/MM/yyyy):  ");
            try {
                dataEntregaS = sc.nextLine();
                dataEntregaD = Util.converterStringParaDate(dataEntregaS);
            } catch (Exception e) {
                dataEntregaD = null;
            }
            if (dataEntregaD != null) {
                if (dataEntregaD.after(dataEmissao)) {
                    continua = false;
                    return dataEntregaD;
                } else {
                    System.out.println("A data de entrega não pode ser antes da emissão.");
                    return null;
                }

            } else {
                System.out.println("Informe uma data válida.");
                return null;
            }
        } while (continua);

    }

    public void pesquisarPedidos(Connection c) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        char opcao;
        boolean continua = true;

        do {
            PedidoDAO consultaPedido = new PedidoDAO();
            Util.menuPesquisar();
            System.out.print("Opção: ");
            try {
                opcao = sc.nextLine().charAt(0);
            } catch (Exception e) {
                opcao = 'R';
            }
            switch (opcao) {
                case '1': {
                    if (consultaPedido.listarTodos(c)) {
                        menuAlterarExcluirTodos(c);
                    }
                    break;
                }
                case '2': {
                    System.out.print("\nInforme o código do pedido: ");
                    String codS;
                    int cod;
                    try {
                        codS = sc.nextLine();
                        cod = Integer.parseInt(codS);
                    } catch (Exception e) {
                        cod = 0;
                    }
                    if (consultaPedido.listarPorCod(c, cod)) {
                        char mostra;
                        System.out.println("\nMostrar detalhes do pedido? (s/n)");
                        try {
                            mostra = sc.nextLine().charAt(0);
                        } catch (Exception e) {
                            mostra = 'R';
                        }
                        if (mostra == 's' || mostra == 'S') {
                            consultaPedido.detalharPedido(c, cod);
                            menuAlterarEspecifico(c, cod);
                        }
                    }
                    break;
                }
                case '3': {
                    System.out.print("\nInforme o nome do cliente: ");
                    String nome;
                    try {
                        nome = sc.nextLine();
                    } catch (Exception e) {
                        nome = null;
                    }
                    if (consultaPedido.listarPorNome(c, nome)) {
                        menuAlterarExcluirTodos(c);
                    }
                    break;

                }
                case '4': {
                    System.out.print("\nInforme a data (dd/MM/yyyy): ");
                    String data;
                    Date dataSQL;
                    try {
                        data = sc.nextLine();
                        dataSQL = Util.converterStringParaDate(data);
                    } catch (Exception e) {
                        dataSQL = null;
                    }
                    if (consultaPedido.listarPorData(c, dataSQL)) {
                        menuAlterarExcluirTodos(c);
                    }
                    break;
                }
                case '0':
                    continua = false;
                    break;
                default: {
                    System.out.println("Opção inválida. Tente novamente");
                    break;
                }
            }
        } while (continua);
    }

    public void menuAlterarExcluirTodos(Connection c) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        char opcao;
        boolean continua = true;

        do {
            PedidoDAO consultaPedido = new PedidoDAO();
            Util.menuListarTodos();
            System.out.print("Opção: ");
            try {
                opcao = sc.nextLine().charAt(0);
            } catch (Exception e) {
                opcao = 'R';
            }
            switch (opcao) {
                case '1': {
                    System.out.print("\nInforme o código do pedido: ");
                    String codS;
                    int cod;
                    try {
                        codS = sc.nextLine();
                        cod = Integer.parseInt(codS);
                    } catch (Exception e) {
                        cod = 0;
                    }
                    if (consultaPedido.detalharPedido(c, cod)) {
                        menuAlterar(c, cod);
                    }
                    break;
                }
                case '2': {
                    System.out.print("\nInforme o código do pedido: ");
                    String codS;
                    int cod;
                    try {
                        codS = sc.nextLine();
                        cod = Integer.parseInt(codS);
                    } catch (Exception e) {
                        cod = 0;
                    }
                    if (consultaPedido.detalharPedido(c, cod)) {
                        System.out.println("Tem certeza que deseja excluir esse pedido? (s/n)");
                        try {
                            opcao = sc.nextLine().charAt(0);
                        } catch (Exception e) {
                            opcao = 'R';
                        }
                        if (opcao == 's' || opcao == 'S') {
                            consultaPedido.excluirPedido(c, cod);
                            System.out.println("Pedido excluído");
                            Thread.sleep(1000);
                            continua = false;
                        }
                    }
                    break;
                }
                case '0': {
                    continua = false;
                    break;
                }
                default: {
                    System.out.println("Opção inválida. Tente novamente");
                    break;
                }
            }
        } while (continua);
    }

    public void menuAlterarEspecifico(Connection c, int cod) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        char opcao;
        boolean continua = true;

        do {
            PedidoDAO consultaPedido = new PedidoDAO();
            Util.menuFiltrarPorCodigo();
            System.out.print("Opção: ");
            try {
                opcao = sc.nextLine().charAt(0);
            } catch (Exception e) {
                opcao = 'R';
            }
            switch (opcao) {
                case '1': {
                    menuAlterar(c, cod);
                    break;
                }
                case '2': {
                    System.out.println("Tem certeza que deseja excluir esse pedido? (s/n)");
                    try {
                        opcao = sc.nextLine().charAt(0);
                    } catch (Exception e) {
                        opcao = 'R';
                    }
                    if (opcao == 's' || opcao == 'S') {
                        consultaPedido.excluirPedido(c, cod);
                        System.out.println("Pedido excluído");
                        Thread.sleep(1000);
                        continua = false;
                    }

                    break;
                }
                case '0': {
                    continua = false;
                    break;
                }
                default: {
                    System.out.println("Opção inválida. Tente novamente");
                    break;
                }
            }
        } while (continua);
    }

    public void menuAlterar(Connection c, int cod) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        char opcao;
        boolean continua = true;

        do {
            PedidoDAO consultaPedido = new PedidoDAO();
            Util.menuAlterar();
            System.out.print("Opção: ");
            try {
                opcao = sc.nextLine().charAt(0);
            } catch (Exception e) {
                opcao = 'R';
            }
            switch (opcao) {
                case '1': {
                    alterarCliente(c, cod);
                    consultaPedido.detalharPedido(c, cod);
                    break;
                }
                case '2': {
                    alterarProdutos(c, cod);
                    consultaPedido.detalharPedido(c, cod);
                    break;
                }
                case '3': {
                    alterarEmissao(c, cod);
                    consultaPedido.detalharPedido(c, cod);
                    break;
                }
                case '4': {
                    alterarEntrega(c, cod);
                    consultaPedido.detalharPedido(c, cod);
                    break;
                }
                case '5': {
                    alterarObs(c, cod);
                    consultaPedido.detalharPedido(c, cod);
                    break;
                }
                case '0': {
                    continua = false;
                    break;
                }
                default:
                    System.out.println("Opção inválida. Tente novamente");
                    break;
            }

        } while (continua);

    }

    private void alterarObs(Connection c, int cod) {
        Scanner sc = new Scanner(System.in);
        String obs;
        boolean continua = false;
        PedidoDAO consultaPedido = new PedidoDAO();

        do {
            System.out.println("Digite a observação (até 65 caracteres): ");
            obs = sc.nextLine();
            if (obs.length() > 65) {
                System.out.println("Número de caracteres excedido.");
                continua = true;
            } else {
                consultaPedido.alterarObs(c, cod, obs);
                System.out.println("Observação registrada.");
                continua = false;
            }

        } while (continua);

    }

    private void alterarEntrega(Connection c, int cod) {
        Scanner sc = new Scanner(System.in);
        String dataEntregaS;
        boolean continua = true;

        PedidoDAO consultaPedido = new PedidoDAO();
        do {
            Date dataEntregaD;
            System.out.print("Informe a nova data de entrega (dd/MM/yyyy):  ");
            try {
                dataEntregaS = sc.nextLine();
                dataEntregaD = Util.converterStringParaDate(dataEntregaS);
            } catch (Exception e) {
                dataEntregaD = null;
            }
            if (dataEntregaD != null) {
                char d;
                if (dataEntregaD.after(consultaPedido.dataEmissao(c, cod))) {
                    System.out.println("Tem certeza que deseja alterar a data de entrega? (s/n)");
                    try {
                        d = sc.nextLine().charAt(0);
                    } catch (Exception e) {
                        d = 'R';
                        System.out.println("");
                    }
                    if (d == 's' || d == 'S') {
                        consultaPedido.alterarDataEntrega(c, cod, dataEntregaD);
                    }
                    continua = false;

                } else {
                    System.out.println("A data de entrega não pode ser antes da data de emissão.");
                }
            } else {
                System.out.println("Informe uma data válida.");
            }
        } while (continua);

    }

    private void alterarEmissao(Connection c, int cod) {

        Scanner sc = new Scanner(System.in);
        String dataEmissaoS;

        boolean continua = true;

        PedidoDAO consultaPedido = new PedidoDAO();
        do {
            Date dataEmissaoD;
            System.out.print("Informe a nova data de emissão (dd/MM/yyyy):  ");
            try {
                dataEmissaoS = sc.nextLine();
                dataEmissaoD = Util.converterStringParaDate(dataEmissaoS);
            } catch (Exception e) {
                dataEmissaoD = null;
            }
            if (dataEmissaoD != null) {
                char d;
                if (dataEmissaoD.before(consultaPedido.dataEntrega(c, cod))) {
                    System.out.println("Tem certeza que deseja alterar a data de emissão? (s/n)");
                    try {
                        d = sc.nextLine().charAt(0);
                    } catch (Exception e) {
                        d = 'R';
                        System.out.println("");
                    }
                    if (d == 's' || d == 'S') {
                        consultaPedido.alterarDataEmissao(c, cod, dataEmissaoD);
                    }
                    continua = false;

                } else {
                    System.out.println("A data de emissão não pode ser depois da data de entrega.");
                }
            } else {
                System.out.println("Informe uma data válida.");
            }
        } while (continua);

    }

    private void alterarProdutos(Connection c, int cod) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        int codProd;
        char opcao;
        boolean continua = false;

        do {
            ProdutoDAO consultaProduto = new ProdutoDAO();
            PedidoDAO consultaPedido = new PedidoDAO();
            Util.menuAlterarAddProd();

            System.out.print("Opção: ");
            try {
                opcao = sc.nextLine().charAt(0);
            } catch (Exception e) {
                opcao = 'R';
            }
            switch (opcao) {
                case '1': {
                    Util.menuAlteraQtdProd();
                    System.out.print("Opção: ");
                    char o;
                    try {
                        o = sc.nextLine().charAt(0);
                    } catch (Exception e) {
                        o = 'R';
                    }
                    if (o == '1') {
                        consultaPedido.detalharPedidoItens(c, cod);
                        int codItens;
                        int quant;
                        System.out.print("Informe o código do item: ");
                        try {
                            codItens = sc.nextInt();
                        } catch (Exception e) {
                            codItens = 0;
                        }
                        sc.nextLine();
                        System.out.print("Informe a nova quantidade: ");
                        try {
                            quant = sc.nextInt();
                        } catch (Exception e) {
                            quant = 0;
                        }
                        sc.nextLine();
                        if (!consultaPedido.alterarQuantidade(c, codItens, quant)) {
                            System.out.println("Não é possível adicionar a quantidade 0.");
                        }

                    } else if (o == '2') {
                        consultaPedido.detalharPedidoItens(c, cod);
                        int codItens;
                        int quant;
                        System.out.print("Informe o código do item que deseja alterar: ");
                        try {
                            codItens = sc.nextInt();
                        } catch (Exception e) {
                            codItens = 0;
                        }
                        consultaProduto.listarProdutos(c);
                        System.out.print("Informe o código do produto que deseja adicionar: ");
                        try {
                            codProd = sc.nextInt();
                        } catch (Exception e) {
                            codProd = 0;
                        }
                        sc.nextLine();
                        System.out.print("Informe a quantidade que deseja adicionar: ");
                        try {
                            quant = sc.nextInt();
                        } catch (Exception e) {
                            quant = 0;
                        }
                        sc.nextLine();
                        if (!consultaPedido.alterarProduto(c, codItens, codProd, quant)) {
                            System.out.println("Não foi possível realizar a alteração. Verifique os dados informados.");
                        }
                    } else {
                        System.out.println("Opção inválda");
                    }
                    break;
                }
                case '2': {
                    consultaProduto.listarProdutos(c);
                    System.out.print("Informe o código do produto que deseja adicionar: ");
                    try {
                        codProd = sc.nextInt();
                    } catch (Exception e) {
                        codProd = 0;
                    }
                    System.out.print("Informe a quantidade que deseja adicionar: ");
                    int quant;
                    try {
                        quant = sc.nextInt();
                    } catch (Exception e) {
                        quant = 0;
                    }
                    sc.nextLine();
                    if (consultaPedido.adicionarProdutoPedido(c, cod, codProd, quant)) {
                        System.out.println("Produto adicionado.");
                    }
                    break;
                }
                case '3': {
                    consultaPedido.detalharPedidoItens(c, cod);
                    int codItens;
                    System.out.print("Informe o código do item que deseja excluir: ");
                    try {
                        codItens = sc.nextInt();
                    } catch (Exception e) {
                        codItens = 0;
                    }
                    sc.nextLine();
                    boolean valido = true;
                    do {
                        System.out.println(
                                "Continuar na exclusão? Para alterar apenas a quantidade, volte e selecione a opção 'Alterar um produto do pedido'");
                        System.out.println("""
                                1 - Voltar
                                2 - Continuar""");
                        System.out.print("Opção: ");
                        char d = sc.nextLine().charAt(0);
                        if (d == '1') {
                            break;
                        } else if (d == '2') {
                            System.out.println("Tem certeza que deseja excluir esse item do pedido? (s/n)");
                            try {
                                opcao = sc.nextLine().charAt(0);
                            } catch (Exception e) {
                                opcao = 'R';
                            }
                            if (opcao == 's' || opcao == 'S') {
                                if (!consultaPedido.excluirProduto(c, codItens)) {
                                    System.out.println(
                                            "O pedido não pode existir sem itens. Caso necessário, exclua o pedido inteiro.");
                                }
                            }
                        } else {
                            System.out.println("Opção inválida");
                            valido = false;
                        }
                    } while (!valido);

                    break;
                }
                case '0': {
                    break;
                }
                default:
                    System.out.println("Opção inválida. Tente novamente");
                    continua = true;
                    break;
            }

        } while (continua);

    }

    private void alterarCliente(Connection c, int cod) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        int codCliente;
        char o;

        ClienteDAO consultaCliente = new ClienteDAO();
        PedidoDAO consultaPedido = new PedidoDAO();

        consultaCliente.listarClientes(c);
        System.out.print("Informe o código do novo cliente: ");
        try {
            codCliente = sc.nextInt();
        } catch (Exception e) {
            codCliente = 0;
        }
        sc.nextLine();
        System.out.println("Tem certeza que deseja alterar o cliente? (s/n)");

        try {
            o = sc.nextLine().charAt(0);
        } catch (Exception e) {
            o = 'R';
            System.out.println("");
        }
        if (o == 's' || o == 'S') {
            if (consultaPedido.alterarClientePedido(c, cod, codCliente)) {
                System.out.println("Cliente alterado com sucesso.");
            } else {
                System.out.println("Código do cliente inválido. Tente novamente.");
            }
        }

    }

}
