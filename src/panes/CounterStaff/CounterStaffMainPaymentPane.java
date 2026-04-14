package panes.CounterStaff;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import panes.CounterStaff.components.PaymentListener;

public class CounterStaffMainPaymentPane extends JPanel implements PaymentListener{
    
	private CardLayout cardLayout = new CardLayout();
	private PaymentListener listener;
	private CounterStaffPaymentPane paymentPane;
	private CounterStaffPaymentDetails paymentDetailsPane;

    public CounterStaffMainPaymentPane(){
        setBackground(Color.WHITE);
        setLayout(cardLayout);
        setBorder(new EmptyBorder(20, 20, 20, 20));
	
		paymentPane = new CounterStaffPaymentPane(this);
		paymentDetailsPane = new CounterStaffPaymentDetails(this);

		add(paymentPane, "LIST");
		add(paymentDetailsPane, "DETAILS");
		cardLayout.show(this, "LIST");

    }

	public void onMakePayment(String paymentId){
		cardLayout.show(this, "DETAILS");
		paymentDetailsPane.loadPayment(paymentId);
	}
	
	public void onBackToList(){
		cardLayout.show(this, "LIST");
	}

	public void onPrintReceipt(String paymentId){

	}

	public void onViewReceipt(String paymentId){

	}
}
