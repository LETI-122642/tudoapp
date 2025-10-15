package iscteiul.ista.qrcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class QrCodeRepository {

    private final List<QrCode> storage = new ArrayList<>();

    public void save(QrCode qrCode) {
        storage.add(qrCode);
    }

    public List<QrCode> findAll() {
        return new ArrayList<>(storage);
    }

    public Optional<QrCode> findById(UUID id) {
        return storage.stream()
                .filter(q -> q.getId().equals(id))
                .findFirst();
    }
}
