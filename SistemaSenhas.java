import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

enum TipoSenha {
    NORMAL("N"),
    PRIORITARIA("P");

    private final String prefixo;

    TipoSenha(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getPrefixo() {
        return prefixo;
    }
}

class Senha {

    private final String codigo;
    private final TipoSenha tipo;
    private final LocalDateTime horarioEmissao;

    public Senha(String codigo, TipoSenha tipo) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.horarioEmissao = LocalDateTime.now();
    }

    public String getCodigo() {
        return codigo;
    }

    public TipoSenha getTipo() {
        return tipo;
    }

    public LocalDateTime getHorarioEmissao() {
        return horarioEmissao;
    }

    @Override
    public String toString() {
        return codigo;
    }
}

public class SistemaSenhas {

    private int contadorNormal = 1;
    private int contadorPrioritaria = 1;

    private final Queue<Senha> filaNormal = new LinkedList<>();
    private final Queue<Senha> filaPrioritaria = new LinkedList<>();

    public Senha gerarSenhaNormal() {
        Senha senha = new Senha(
                String.format("N%03d", contadorNormal++),
                TipoSenha.NORMAL
        );

        filaNormal.offer(senha);
        return senha;
    }

    public Senha gerarSenhaPrioritaria() {
        Senha senha = new Senha(
                String.format("P%03d", contadorPrioritaria++),
                TipoSenha.PRIORITARIA
        );

        filaPrioritaria.offer(senha);
        return senha;
    }

    public Senha chamarProximaSenha() {

        if (!filaPrioritaria.isEmpty()) {
            return filaPrioritaria.poll();
        }

        if (!filaNormal.isEmpty()) {
            return filaNormal.poll();
        }

        return null;
    }

    public static void main(String[] args) {

        SistemaSenhas sistema = new SistemaSenhas();

        System.out.println("=== Gerando Senhas ===");
        System.out.println(sistema.gerarSenhaNormal());
        System.out.println(sistema.gerarSenhaNormal());
        System.out.println(sistema.gerarSenhaPrioritaria());
        System.out.println(sistema.gerarSenhaPrioritaria());

        System.out.println("\n=== Chamando ===");

        Senha senha;

        while ((senha = sistema.chamarProximaSenha()) != null) {
            System.out.println("Chamando: " + senha.getCodigo()
                    + " (" + senha.getTipo() + ")");
        }

        System.out.println("\nNenhuma senha aguardando atendimento.");
    }
}