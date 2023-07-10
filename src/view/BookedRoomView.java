package view;


import model.RoomBookingFormModel;

public class BookedRoomView extends ARoomView{

    public BookedRoomView() {
        super();
    }

    @Override
    public String getName() {
        return "Phòng được đặt";
    }

    @Override
    public ARoomView getObjectRoomView() {
        return this;
    }


    public void setDataGuestRoomInformation(RoomBookingFormModel roomBookingFormModel) {
        for (ImageRoomView imageRoomView : super.getImageRoomViews()) {
            InfoCusBookedFormView infoCusBookedFormView = new InfoCusBookedFormView();
            infoCusBookedFormView.setEmptyRoomInformationModel(roomBookingFormModel);
            if (imageRoomView.hasRoomCode(roomBookingFormModel.getRoomCodeEmptyRoomInformationModel())) {
                imageRoomView.setIdRoomImg(roomBookingFormModel.getEmptyRoomInformationModel().getId());
                imageRoomView.setInfoCusBookedFormView(infoCusBookedFormView);
            }
        }

    }
}
