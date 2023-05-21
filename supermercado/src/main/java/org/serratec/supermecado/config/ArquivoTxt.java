package main.java.org.serratec.supermecado.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class ArquivoTxt {
    private String nome;
    File Arquivo;

    public ArquivoTxt(String nome) {
        this.nome = nome;
        File Arq = new File(nome);
        Arquivo = Arq;
    }

    public boolean criarArquivo() {
        try {
            Arquivo.createNewFile();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void escreverArquivo(String path) {
        try {
            Files.write(Paths.get(this.nome), path.getBytes(), StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.err.println("Erro ao acessar o arquivo. " + e.getMessage());
        }
    }

    public ArrayList<String> temDados() {
        ArrayList<String> data = new ArrayList<>();
        try {
            if (Arquivo.exists()) {
                Scanner Linhas = new Scanner(Arquivo);

                while (Linhas.hasNextLine()) {
                    data.add(Linhas.nextLine());
                }

                Linhas.close();
            } else
                System.out.println("Arquivo não existe.");

        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro na leitura.");
            e.printStackTrace();
        }

        return data;
    }

    public boolean apagarArquivo() {
        return Arquivo.delete();
    }

}
