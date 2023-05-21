package main.java.org.serratec.supermecado.config;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;

import main.java.org.serratec.supermecado.repository.CreateDAO;
import main.java.org.serratec.supermecado.service.VendasService;

public class Conexao {

    public static Scanner in = new Scanner(System.in);
    public static final String DIRECTORY = "C:\\temp\\";
    public static final String URL = "jdbc:postgresql://";
    public static final String BD_INI = "/postgres";
    public static final String BD = "grupo06";
    public static final String SFILE = "dados_conexao_grupo_06.ini";

    public void conectar() {
        File dir = new File(DIRECTORY);
        dir.mkdirs();

        ArquivoTxt arq = new ArquivoTxt(DIRECTORY + SFILE);
        arq.criarArquivo();

        if (arq.temDados().isEmpty()) {
            try {
                System.out.print("Informe a porta (ex: localhost:5432): ");
                String porta = in.nextLine();
                System.out.print("Informe o usuário: ");
                String user = in.nextLine();
                System.out.print("Informe a senha: ");
                String password = in.nextLine();

                String urlInicial = URL + porta + BD_INI;
                String url = URL + porta + "/" + BD;

                Connection conexaoInicial = DriverManager.getConnection(urlInicial, user, password);

                if (CreateDAO.existeBD(conexaoInicial, BD, url, user, password)) {
                    arq.escreverArquivo(url + "\n" + user + "\n" + password);
                    Connection conexaoFinal = DriverManager.getConnection(url, user, password);
                    VendasService programa = new VendasService();
                    programa.menu(conexaoFinal);
                    conexaoFinal.close();
                }

            } catch (Exception e) {
                System.err.println(
                        "Erro ao iniciar o programa. Verifique os dados informados. " + e.getLocalizedMessage());
                e.printStackTrace();
            }

        } else {
            try {
                ArrayList<String> dados = arq.temDados();
                Connection conexaoFinal = DriverManager.getConnection(dados.get(0), dados.get(1), dados.get(2));
                VendasService programa = new VendasService();
                programa.menu(conexaoFinal);
                conexaoFinal.close();
            } catch (Exception e) {
                System.err.println("Erro ao iniciar o programa. Tente novamente. " + e.getLocalizedMessage());
                e.printStackTrace();
                arq.apagarArquivo();
            }
        }
    }
}
