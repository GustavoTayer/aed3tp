import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Vaivanapp {
    public static ArrayList<Usuario> usuarios;

    public static void main(String[] args) {
          while (true) {
              RandomAccessFile arq;
              try {
                  arq = new RandomAccessFile("usuario.db","rw");
                  usuarios = getUsuarios(arq);
                  menu(arq);
                  arq.close();
              } catch(IOException e){
                  e.printStackTrace();
              }
          }
    }

    private static void writeNewUser(Usuario user, RandomAccessFile arq){
        Usuario u = new Usuario();

        try {

            arq.seek(arq.length());
            user.writeObject(arq);

        } catch(IOException e){
            e.printStackTrace();
        }
    }


    /** Informações necessárias para criar um novo usuário.*/
    private static void novoUsuario(RandomAccessFile arq){
        Scanner sc1 = new Scanner(System.in);
        System.out.println("\nDigite o seu nome completo:");
        String nome = sc1.nextLine();
        System.out.println("\nDigite o seu cpf:");
        String cpf = sc1.nextLine();
        System.out.println("\nDigite o seu telefone:");
        String telefone = sc1.nextLine();
        System.out.println("\nDigite o seu email:");
        String email = sc1.nextLine();
        System.out.println("\nDigite a sua idade:");
        int idade = sc1.nextInt();
        writeNewUser(new Usuario(nome, cpf, idade, telefone,email), arq);
    }

    /** String menu */
    private static void menu(RandomAccessFile arq) throws IOException {
        System.out.println("\n\n    (Menu) " +
                "\nDigite:\n" +
                "1 - Criar um novo usuário.\n" +
                "2 - Deletar um usuário.\n"+
                "3 - atualizar dado de algum usuário.\n" +
                "4 - ver usuários existentes.");

        Scanner scan = new Scanner(System.in);
        int menu = scan.nextInt();

        switch (menu) {
            case 1:
                novoUsuario(arq);
                break;
            case 2:
                deletarUsuario(arq);
                break;
            case 3:
                atualizarDado(arq);
                break;
            case 4:
                usuariosExistentes();
                break;
        }
    }

    /** Deletar usuário do sistema.*/
    private static void deletarUsuario(RandomAccessFile arq) throws IOException {
        Usuario usuario = buscarUsuario();
        if(usuario == null){
            System.out.println("Usuário não encontrado.");
            return;
        }
        usuarios.remove(usuario);
        reWriteFile(arq);
    }

    /** Alterar campo especifico de um usuário no banco.*/
    private static void atualizarDado(RandomAccessFile arq) throws IOException{

        Scanner scan = new Scanner(System.in);

        Usuario usuario = buscarUsuario();
        if(usuario == null){
            System.out.println("Não existe usuário cadastrado com este cpf.");
            return;
        }

        System.out.println("Para alterar algum campo, digite:\n" +
                "1 - nome.\n" +
                "2 - idade.\n" +
                "3 - cpf\n" +
                "4 - telefone\n" +
                "5 - email");
        int i = scan.nextInt();
        scan.nextLine();

        String campo;
        switch (i){
            case 1:
                System.out.println("Digite o nome correto:");
                campo = scan.nextLine();
                usuario.nome = campo;
                break;
            case 2:
                System.out.println("Digite a idade correta:");
                int idade = scan.nextInt();
                scan.nextLine();
                usuario.idade = idade;
                break;
            case 3:
                System.out.println("Digite o cpf correto:");
                campo = scan.nextLine();
                usuario.cpf = campo;
                break;
            case 4:
                System.out.println("Digite o telefone correto:");
                campo = scan.nextLine();
                usuario.telefone = campo;
                break;
            case 5:
                System.out.println("Digite o E-mail correto:");
                campo = scan.nextLine();
                usuario.email = campo;
                break;
        }

        usuarios.add(usuario);
        reWriteFile(arq);
    }

    /**Imprime os usuários existentes no sistema.*/
    private static void usuariosExistentes() {
        usuarios.forEach((c) -> System.out.println(c));
    }

    /**Le o arquivo e salva os usuários em uma lista.*/
    public static ArrayList<Usuario> getUsuarios(RandomAccessFile arq) throws IOException{

            usuarios = new ArrayList<>();
            arq.seek(0);

            while (arq.getFilePointer() < arq.length()){
                Usuario u = new Usuario();
                u.readObject(arq);
                usuarios.add(u);
            }
            return usuarios;
    }

    /**Busca um determinado usuário pelo cpf, caso não exista retorna null*/
    private static Usuario buscarUsuario() {
        System.out.println("Digite o cpf do usuário:");
        Scanner scan = new Scanner(System.in);
        String cpf = scan.nextLine();
        for (Usuario usuario : usuarios){
            if(usuario.cpf.equals(cpf)) {
                Usuario usuario1 = usuario;
                usuarios.remove(usuario);
                return usuario;
            }
        }
        return null;
    }

    private static void reWriteFile(RandomAccessFile arq) throws IOException{

        arq.seek(0);
        while (arq.getFilePointer() < arq.length()){
            arq.write('\0');
        }
        arq.seek(0);
        for(Usuario usuario : usuarios) {
            usuario.writeObject(arq);
        }
    }
}


