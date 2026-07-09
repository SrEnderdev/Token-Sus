import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicLong;

public class GeradorSenha {
    private final EnumMap<TipoSenha, SequenciaAlfaNumerica> sequencias = new EnumMap<>(TipoSenha.class);
    private final AtomicLong proximoId = new AtomicLong(1L);

    public GeradorSenha() {
        for (TipoSenha tipo : TipoSenha.values()) {
            sequencias.put(tipo, new SequenciaAlfaNumerica(tipo.getLetraInicial(), 1));
        }
    }

    public synchronized Senha gerar(TipoSenha tipo) {
        SequenciaAlfaNumerica sequencia = sequencias.get(tipo);

        if (sequencia == null) {
            throw new IllegalArgumentException("Tipo de senha inválido: " + tipo);
        }

        long id = proximoId.getAndIncrement();
        String codigo = sequencia.proximoCodigo();

        return new Senha(id, codigo, tipo);
    }

    public synchronized void reiniciarSequencia(TipoSenha tipo, char letra, int numero) {
        SequenciaAlfaNumerica sequencia = sequencias.get(tipo);

        if (sequencia == null) {
            throw new IllegalArgumentException("Tipo de senha inválido: " + tipo);
        }

        sequencia.reiniciar(letra, numero);
    }

    public synchronized char getLetraAtual(TipoSenha tipo) {
        SequenciaAlfaNumerica sequencia = sequencias.get(tipo);

        if (sequencia == null) {
            throw new IllegalArgumentException("Tipo de senha inválido: " + tipo);
        }

        return sequencia.getLetraAtual();
    }

    public synchronized int getNumeroAtual(TipoSenha tipo) {
        SequenciaAlfaNumerica sequencia = sequencias.get(tipo);

        if (sequencia == null) {
            throw new IllegalArgumentException("Tipo de senha inválido: " + tipo);
        }

        return sequencia.getNumeroAtual();
    }
}
