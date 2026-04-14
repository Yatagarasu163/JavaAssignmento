package panes.CounterStaff.components;

public interface PaymentListener{
	public void onMakePayment(String paymentId);
	public void onBackToList();
	public void onPrintReceipt(String paymentId);
	public void onViewReceipt(String paymentId);
}
