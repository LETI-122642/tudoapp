package iscteiul.ista.qrcode.ui;

import com.google.zxing.NotFoundException;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import iscteiul.ista.qrcode.QrCode;
import iscteiul.ista.qrcode.QrCodeRepository;
import iscteiul.ista.qrcode.QrCodeService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Route("qrcode")
@PageTitle("QR Code Reader")
@Menu(order = 2, icon = "vaadin:qrcode", title = "QR Code Reader")
public class QrCodeView extends Main {

    private final QrCodeRepository repository;
    private final QrCodeService service;

    private final Upload upload;
    private final Button readButton;
    private final Image preview;
    private final Grid<QrCode> qrGrid;

    public QrCodeView() {
        this.repository = new QrCodeRepository();
        this.service = new QrCodeService(repository);

        // Upload de ficheiros
        FileBuffer fileBuffer = new FileBuffer();
        upload = new Upload(fileBuffer);
        upload.setAcceptedFileTypes("image/png", "image/jpeg");
        upload.setMaxFiles(1);
        upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
        upload.setWidth("20em");

        // Botão para ler o código
        readButton = new Button("Ler QR Code", event -> readQrCode(fileBuffer));
        readButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Pré-visualização da imagem carregada
        preview = new Image();
        preview.setMaxWidth("300px");
        preview.setVisible(false);

        // Tabela de histórico de QR Codes lidos
        qrGrid = new Grid<>(QrCode.class, false);
        qrGrid.addColumn(QrCode::getContent).setHeader("Conteúdo Lido").setAutoWidth(true);
        qrGrid.addColumn(qr -> qr.getCreatedAt().toString()).setHeader("Data de Leitura").setAutoWidth(true);
        qrGrid.setItems(repository.findAll());
        qrGrid.setSizeFull();

        // Layout principal (mesmo estilo que o PDF List)
        setSizeFull();
        addClassNames(
                LumoUtility.BoxSizing.BORDER,
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM,
                LumoUtility.Gap.SMALL
        );

        add(
                new Text("Faz upload de uma imagem contendo um código QR:"),
                upload,
                readButton,
                preview,
                qrGrid
        );
    }

    private void readQrCode(FileBuffer fileBuffer) {
        File uploadedFile = fileBuffer.getFileData() != null ? fileBuffer.getFileData().getFile() : null;
        if (uploadedFile == null) {
            Notification.show("Nenhum ficheiro selecionado.", 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        try {
            QrCode qrCode = service.readFromFile(uploadedFile);
            preview.setSrc(uploadedFile.toURI().toString());
            preview.setVisible(true);
            updateGrid();
            Notification.show("Código QR lido com sucesso!", 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (IOException | NotFoundException e) {
            Notification.show("Erro ao ler QR Code: " + e.getMessage(), 4000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void updateGrid() {
        List<QrCode> all = repository.findAll();
        qrGrid.setItems(all);
    }
}
