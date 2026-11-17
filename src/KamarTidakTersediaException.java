// Custom exception (checked): kamar yang diminta sedang terisi / tidak ada kamar kosong.
// Dibuat checked agar pemanggil WAJIB memutuskan cara menangani kegagalan ini.
public class KamarTidakTersediaException extends Exception {
    public KamarTidakTersediaException(String pesan) {
        super(pesan);
    }
}
