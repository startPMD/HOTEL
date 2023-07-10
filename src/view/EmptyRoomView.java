package view;

import controller.EmptyRoomController;
import model.RoomBookingFormModel;

public class EmptyRoomView extends ARoomView {

    public EmptyRoomView() {
        super();
    }

    @Override
    public String getName() {
        return "Phòng trống";
    }

    @Override
    public ARoomView getObjectRoomView() {
        return this;
    }

    public void setDataEmptyRoomInformation(RoomBookingFormModel.EmptyRoomInformationModel emptyRoomInformationModel) {
        System.out.println(emptyRoomInformationModel.toString());
        // creat Form and set data
        for (ImageRoomView imageRoomView : super.getImageRoomViews()) {
            RoomBookingFormView roomBookingFormView = new RoomBookingFormView();
            roomBookingFormView.setEmptyRoomInformationModel(emptyRoomInformationModel);
            if (imageRoomView.hasRoomCode(emptyRoomInformationModel.getRoomCode())) {
                imageRoomView.setIdNumRoom(emptyRoomInformationModel.getId());
                imageRoomView.setRoomBookingForm(roomBookingFormView);
            }
        }
    }

    public void createRoom(RoomBookingFormModel.EmptyRoomInformationModel emptyRoomInformationModel) {

        for (ImageRoomView imageRoomView : super.getImageRoomViews()) {
            RoomBookingFormView roomBookingFormView = new RoomBookingFormView();
            roomBookingFormView.setEmptyRoomInformationModel(emptyRoomInformationModel);
            if (imageRoomView.hasRoomCode(emptyRoomInformationModel.getRoomCode())) {
                imageRoomView.setIdNumRoom(emptyRoomInformationModel.getId());
                imageRoomView.setRoomBookingForm(roomBookingFormView);
            }
        }
    }

    public void removeView(int codeRoom) {
        removeImgView(codeRoom);
    }

}
