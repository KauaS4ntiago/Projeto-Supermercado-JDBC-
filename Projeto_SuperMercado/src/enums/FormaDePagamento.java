package enums;

public enum FormaDePagamento {
	PIX(0.2), DINHEIRO(0.2), DEBITO(0.1), CREDITO(0.0);

	private final double desconto;

	FormaDePagamento(double desconto) {
		this.desconto = desconto;
	}

	public double getDesconto() {
		return desconto;
	}
}
