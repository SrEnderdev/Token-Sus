import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Senha {
    private final long id;
    private final String codigo;
    private final TipoSenha tipo;
    private final LocalDateTime emitidaEm;

    private LocalDateTime chamadaEm;
    private String salaAtendimento;

    public Senha(long id, String codigo, TipoSenha tipo) {
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.emitidaEm = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public TipoSenha getTipo() {
        return tipo;
    }

    public LocalDateTime getEmitidaEm() {
        return emitidaEm;
    }

    public LocalDateTime getChamadaEm() {
        return chamadaEm;
    }

    public String getSalaAtendimento() {
        return salaAtendimento;
    }

    public boolean foiChamada() {
        return chamadaEm != null;
    }

    public void marcarComoChamada(String salaAtendimento) {
        this.chamadaEm = LocalDateTime.now();
        this.salaAtendimento = salaAtendimento;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Senha{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", tipo=" + tipo.getDescricao() +
                ", emitidaEm=" + emitidaEm.format(fmt) +
                (foiChamada() ? ", chamadaEm=" + chamadaEm.format(fmt) + ", sala='" + salaAtendimento + '\'' : "") +
                '}';
    }
}
