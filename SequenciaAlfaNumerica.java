public class SequenciaAlfaNumerica {
    private char letra;
    private int numero;

    public SequenciaAlfaNumerica(char letraInicial, int numeroInicial) {
        if (letraInicial < 'A' || letraInicial > 'Z') {
            throw new IllegalArgumentException("A letra inicial deve estar entre A e Z.");
        }
        if (numeroInicial < 1 || numeroInicial > 999) {
            throw new IllegalArgumentException("O número inicial deve estar entre 1 e 999.");
        }

        this.letra = letraInicial;
        this.numero = numeroInicial;
    }

    public synchronized String proximoCodigo() {
        if (letra == 'Z' && numero > 999) {
            throw new IllegalStateException(
                    "Limite da sequência atingido (Z999). Reinicie a sequência ou salve uma nova regra no banco."
            );
        }

        String codigo = String.format("%c%03d", letra, numero);

        numero++;
        if (numero > 999) {
            numero = 1;
            letra++;
        }

        return codigo;
    }

    public synchronized char getLetraAtual() {
        return letra;
    }

    public synchronized int getNumeroAtual() {
        return numero;
    }

    public synchronized void reiniciar(char novaLetra, int novoNumero) {
        if (novaLetra < 'A' || novaLetra > 'Z') {
            throw new IllegalArgumentException("A letra deve estar entre A e Z.");
        }
        if (novoNumero < 1 || novoNumero > 999) {
            throw new IllegalArgumentException("O número deve estar entre 1 e 999.");
        }

        this.letra = novaLetra;
        this.numero = novoNumero;
    }
}
