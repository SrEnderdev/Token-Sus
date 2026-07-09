import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

public class FilaAtendimento {
    private final EnumMap<TipoSenha, Deque<Senha>> filas = new EnumMap<>(TipoSenha.class);
    private final List<TipoSenha> cicloAtendimento;
    private int ponteiroCiclo = 0;

    public FilaAtendimento() {
        for (TipoSenha tipo : TipoSenha.values()) {
            filas.put(tipo, new ArrayDeque<>());
        }

        cicloAtendimento = montarCiclo();
    }

    private List<TipoSenha> montarCiclo() {
        List<TipoSenha> ciclo = new ArrayList<>();

        for (TipoSenha tipo : TipoSenha.values()) {
            if (!tipo.isPrioridadeAbsoluta()) {
                for (int i = 0; i < tipo.getPesoAtendimento(); i++) {
                    ciclo.add(tipo);
                }
            }
        }

        if (ciclo.isEmpty()) {
            ciclo.add(TipoSenha.NORMAL);
        }

        return ciclo;
    }

    public synchronized void enfileirar(Senha senha) {
        filas.get(senha.getTipo()).offerLast(senha);
    }

    public synchronized Optional<Senha> chamarProxima() {
        if (!filas.get(TipoSenha.EMERGENCIA).isEmpty()) {
            return Optional.of(filas.get(TipoSenha.EMERGENCIA).pollFirst());
        }

        int tentativas = cicloAtendimento.size();

        for (int i = 0; i < tentativas; i++) {
            TipoSenha tipo = cicloAtendimento.get(ponteiroCiclo);
            ponteiroCiclo = (ponteiroCiclo + 1) % cicloAtendimento.size();

            Deque<Senha> fila = filas.get(tipo);
            if (!fila.isEmpty()) {
                return Optional.of(fila.pollFirst());
            }
        }

        for (TipoSenha tipo : TipoSenha.values()) {
            Deque<Senha> fila = filas.get(tipo);
            if (!fila.isEmpty()) {
                return Optional.of(fila.pollFirst());
            }
        }

        return Optional.empty();
    }

    public synchronized boolean estaVazia() {
        for (Deque<Senha> fila : filas.values()) {
            if (!fila.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public synchronized int quantidadeTotalEmEspera() {
        int total = 0;

        for (Deque<Senha> fila : filas.values()) {
            total += fila.size();
        }

        return total;
    }

    public synchronized int quantidadePorTipo(TipoSenha tipo) {
        return filas.get(tipo).size();
    }
}
