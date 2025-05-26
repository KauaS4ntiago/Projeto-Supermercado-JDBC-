package enums;

public enum FuncaoFuncionario {
    GERENTE(4700.50, 25),
    ATENDENTE(1500.50, 45),
    CAIXA(1600.00, 48),
    ESTOQUISTA(1900.00, 52),
    ENTREGADOR(1450.00, 40);

    private final double salario;
    private final int cargaSemanal;

    FuncaoFuncionario(double salario, int cargaSemanal) {
        this.salario = salario;
        this.cargaSemanal = cargaSemanal;
    }

    public double getSalario() {
        return salario;
    }

    public int getCargaSemanal() {
        return cargaSemanal;
    }
}