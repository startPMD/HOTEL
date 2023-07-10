package controller;

import service.ManagerPaymentService;
import view.PanelManagerPayment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class ManagerPaymentController {
    PanelManagerPayment panelManagerPayment;
    ManagerPaymentService paymentService;

    public ManagerPaymentController(PanelManagerPayment panelManagerPayment, ManagerPaymentService paymentService) {
        this.panelManagerPayment = panelManagerPayment;
        this.paymentService = paymentService;

        loadBillCusCurrent();
        eventFindNameCus();
        eventFindBillCus();
    }
    List<Object[]> codeBillToday ;
    public void loadBillCusCurrent() {
        List<Object[]> billsCus = paymentService.getBillCurrent();
        panelManagerPayment.setDataBillInDate(billsCus);
        panelManagerPayment.setCodeBills(billsCus);
        codeBillToday = billsCus;

    }

    public void eventFindNameCus() {
        panelManagerPayment.getBtnFind().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Object[]> lsCus = paymentService.getBillCusFind(panelManagerPayment.getStrCusFind());
                panelManagerPayment.setDataListBill( lsCus);
                panelManagerPayment.removeAllCodeBill();
                panelManagerPayment.setCodeBills(codeBillToday);
                panelManagerPayment.setCodeBills(lsCus);
            }
        });
    }

    public void eventFindBillCus() {
        panelManagerPayment.getBtnFindBill().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String dateFind = panelManagerPayment.getDateFind();
                int codeBillFind = panelManagerPayment.getCodeBillFind();
                List<Object[]> lsCus = paymentService.getBillServices(codeBillFind);
                panelManagerPayment.setDataServiceBill(lsCus);
            }
        });
    }

    public static void main(String[] args) {
        PanelManagerPayment panelManagerPayment = new PanelManagerPayment();

        JFrame a = new JFrame();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setSize(800, 600);
        a.add(panelManagerPayment);
        a.setVisible(true);

        ManagerPaymentService paymentService = new ManagerPaymentService();
        ManagerPaymentController managerPaymentController = new ManagerPaymentController(panelManagerPayment, paymentService);

    }

}
