package model;

import java.sql.Date;
import java.sql.Timestamp;

public class PaymentModel {
    private int id;
    private int customer_id;
    private int book_a_room_id;
    private Timestamp payment_date;
    private double total_amount;
    private String payment_method;

    public PaymentModel(int customer_id, int book_a_room_id, double total_amount, String payment_method) {
        this.customer_id = customer_id;
        this.book_a_room_id = book_a_room_id;
        this.payment_date = new Timestamp(System.currentTimeMillis());
        this.total_amount = total_amount;
        this.payment_method = payment_method;
    }
    public PaymentModel(int id, int customer_id, int book_a_room_id, double total_amount, String payment_method) {
        this.id = id;
        this.customer_id = customer_id;
        this.book_a_room_id = book_a_room_id;
        this.payment_date = new Timestamp(System.currentTimeMillis());
        this.total_amount = total_amount;
        this.payment_method = payment_method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getBook_a_room_id() {
        return book_a_room_id;
    }

    public void setBook_a_room_id(int book_a_room_id) {
        this.book_a_room_id = book_a_room_id;
    }

    public Timestamp getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Timestamp payment_date) {
        this.payment_date = payment_date;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

}
