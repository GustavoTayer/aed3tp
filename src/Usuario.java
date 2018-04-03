import java.io.*;

public class Usuario {

    public String nome;
    public String cpf;
    public int idade;
    public String telefone;
    public String email;
    
    public Usuario(){}

    public Usuario(String nome, String cpf, int idade, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.telefone = telefone;
        this.email = email;
    }
    
    protected void writeObject(RandomAccessFile arq) throws IOException {
        ByteArrayOutputStream registro = new ByteArrayOutputStream();
        DataOutputStream saida = new DataOutputStream(registro);
        saida.writeUTF(nome);
        saida.writeUTF(cpf);
        saida.writeInt(idade);
        saida.writeUTF(telefone);
        saida.writeUTF(email);
        
        arq.writeShort(saida.size());
        arq.write(registro.toByteArray());
    }
    
    protected void readObject(RandomAccessFile arq) throws IOException{
        int tamanho = arq.readShort();
        byte [] ba = new byte[tamanho];
        arq.read(ba);
        
        ByteArrayInputStream registro = new ByteArrayInputStream(ba);
        DataInputStream entrada = new DataInputStream(registro);

        nome = entrada.readUTF();
        cpf = entrada.readUTF();
        idade =  entrada.readInt();
        telefone = entrada.readUTF();
        email = entrada.readUTF();
    }
    
    public String toString(){
        String str =  "\nnome: " + nome +
                      "\ncpf: " + cpf +
                      "\nidade: " + idade +
                      "\ntelefone: " + telefone +
                      "\nemail: " + email;
        return str;
    }
}
