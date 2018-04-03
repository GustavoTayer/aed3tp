public class Arquivo<T extends Entidade> {
    Class<T> classe;
    public Arquivo(Class<T> classe) {
        this.classe = classe;
    }
}
