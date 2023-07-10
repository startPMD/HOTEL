package controller;

import model.ARoomModel;
import model.BookedRoomModel;
import model.LayoutRoomModel;
import service.ARoomService;
import service.LayoutRoomService;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RoomAllLayoutController extends AManagerRoomController {
    LayoutRoomService layoutRoomService;
    RoomAllLayoutView roomAllLayoutView;
    FindCusFormView findCusFormView;

    public RoomAllLayoutController(ARoomModel aRoomModel, ARoomService aRoomService, ARoomView aRoomView) {
        super(aRoomModel, aRoomService, aRoomView);
        loadStateRoom();
        roomAllLayoutView.addButtonListener(new ButtonListener() {
            @Override
            public void buttonPerformed(RoomEvent roomEvent) {
                findCusFormView.showDetail();
                findCusFormView.addButtonListener(new ButtonListener() {
                    @Override
                    public void buttonPerformed(RoomEvent roomEvent) {
                        switch (roomEvent.getNameBtn()) {
                            case "btnExecuteFind": {
                                if (findCusFormView.hasDateNull()){
                                    JOptionPane.showMessageDialog(findCusFormView, "Vui lòng chọn ngày cần tìm!");
                                    return;
                                }

                                Date startDate = findCusFormView.getStartDate();
                                Date endDate = findCusFormView.getEndDate();

                                // tìm kiếm mới => xáo hết dữ liệu tìm trên bảng củ
                                findCusFormView.delAllDateTableCus();
                                List<BookedRoomModel> bookedRoomModels = layoutRoomService.listCusBookedRoom(startDate, endDate);
                                if (bookedRoomModels.size() == 0){
                                    findCusFormView.showMessNotFoundCus();
                                    return;
                                }
                                String valueText = findCusFormView.getValueFind();

                                if (valueText == null || "".equalsIgnoreCase(valueText)) {
                                    for (BookedRoomModel bookedRoomModel : bookedRoomModels) {
                                        findCusFormView.setDateCus(bookedRoomModel);
                                    }
                                    return;
                                }

                                if(valueText.equalsIgnoreCase("Tên khách hàng...")){
                                    List<BookedRoomModel> bookedRoomModels2 = layoutRoomService.listCusBookedRoom(startDate, endDate, 2);
                                    if (bookedRoomModels2.size() == 0){
                                        findCusFormView.showMessNotFoundCus();
                                        return;
                                    }
                                    for (BookedRoomModel bookedRoomModel : bookedRoomModels2) {
                                        findCusFormView.setDateCus(bookedRoomModel);
                                    }
                                    return;
                                }
                                else {
                                    List<BookedRoomModel> bookedRoomModels3 = layoutRoomService.listCusBookedRoom(startDate, endDate, valueText);
                                    if (bookedRoomModels3.size() == 0) {
                                        findCusFormView.showMessNotFoundCus();
                                        return;
                                    }
                                    for (BookedRoomModel bookedRoomModel : bookedRoomModels3) {
                                        findCusFormView.setDateCus(bookedRoomModel);
                                    }
                                }
                                break;
                            }
                            case "btnExportFileExcel": {
                                if (!findCusFormView.tableHasData()){
                                    findCusFormView.exportExcelFiles();
                                    return;
                                }
                                JOptionPane.showMessageDialog(findCusFormView, "Không tồn tại dữ nào để xuất file Excel!");
                                break;
                            }
                        }
                    }
                });

            }
        });
    }

    @Override
    public void construcFirst() {
        roomAllLayoutView = (RoomAllLayoutView) aRoomView;
        layoutRoomService = (LayoutRoomService) aRoomService;
        findCusFormView = FindCusFormView.getInstanceFindCusFormView();
    }

    @Override
    public void loadDataInfoRoom() {
        for (Map.Entry<String, List<LayoutRoomModel>> services : layoutRoomService.getInforRoomModels().entrySet()) {
            String typeRoom = services.getKey();
            List<LayoutRoomModel> infoRooms = services.getValue();
            JPanel p = new JPanel(new FlowLayout());
            roomAllLayoutView.createRoomLayout(typeRoom, p, infoRooms);
        }
    }

    public void loadStateRoom() {
        int[] dataState = layoutRoomService.getDateTop();
        roomAllLayoutView.setTextHome(String.valueOf(dataState[0]));
        roomAllLayoutView.setTextEmpty(String.valueOf(dataState[1]));
        roomAllLayoutView.setTextGuest(String.valueOf(dataState[2]));
        roomAllLayoutView.setTextBooked(String.valueOf(dataState[3]));
        roomAllLayoutView.setTextRepair(String.valueOf(dataState[4]));
        roomAllLayoutView.setTextBroom(String.valueOf(dataState[5]));

    }

    @Override
    public void setActionSaveForm() {

    }
    public static void main(String[] args) {
        RoomAllLayoutView roomAllLayoutView1 = new RoomAllLayoutView();
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(roomAllLayoutView1);
        frame.pack();
        frame.setVisible(true);
        ARoomService layoutRoomService = new LayoutRoomService();
        AManagerRoomController roomAllLayoutController = new RoomAllLayoutController(null,layoutRoomService,roomAllLayoutView1);

    }
}

